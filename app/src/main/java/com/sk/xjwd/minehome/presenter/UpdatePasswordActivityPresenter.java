package com.sk.xjwd.minehome.presenter;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import com.sk.xjwd.account.activity.LoginActivity;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.activity.UpdatePasswordActivity;
import com.sk.xjwd.minehome.contract.UpdatePasswordContract;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

/**
 * Created by mayn on 2018/9/1.
 */

public class UpdatePasswordActivityPresenter extends UpdatePasswordContract.Presenter {
    private MyCountDownTimer mc;
    private TextView txtGetcode;
    @Override
    public void getCode(TextView textView, String phone) {

        this.txtGetcode=textView;
        mc = new MyCountDownTimer(60000, 1000);
        mc.start();
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.GET_PHONE_CODE);
        util.putParam("phone",phone);
        util.setShowDialog(false);
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {

            }
        }).get();
    }

    class MyCountDownTimer extends CountDownTimer{

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            txtGetcode.setText(millisUntilFinished / 1000+"秒后重新获取");
            txtGetcode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            txtGetcode.setText("获取验证码");
            txtGetcode.setEnabled(true);
            mc.cancel();
        }
    }

    @Override
    public void postData(String phone, String code, String psw) {

        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.FORGETPWD);
        util.putParam("phone",phone);
        util.putParam("code",code);
        util.putParam("password",psw);
        util.setshowDialogcontent("正在提交");
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        UIUtil.showToast("修改成功！");
                        UIUtil.startActivity(LoginActivity.class,null);
                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
}
