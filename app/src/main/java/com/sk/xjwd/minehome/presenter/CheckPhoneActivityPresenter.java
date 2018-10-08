package com.sk.xjwd.minehome.presenter;


import android.os.CountDownTimer;
import android.widget.Button;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.contract.CheckPhoneActivityContract;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

public class CheckPhoneActivityPresenter extends CheckPhoneActivityContract.Presenter {

    private MyCountDownTimer mc;
    private Button btnGetcode;
    @Override
    public void senCode(Button btncode, String phone) {
        this.btnGetcode=btncode;
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
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        MyApplication.saveString("forgetPwdCode",object.getString("data"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            btnGetcode.setText("获取验证码");
            btnGetcode.setEnabled(true);

            mc.cancel();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnGetcode.setText(millisUntilFinished / 1000+"秒后重新获取");
            btnGetcode.setEnabled(false);
        }
    }
}
