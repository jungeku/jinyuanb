package com.sk.xjwd.account.presenter;


import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Button;

import com.sk.xjwd.account.activity.LoginActivity;
import com.sk.xjwd.account.contract.RegisterActivityContract;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.activity.AgreementActivity;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

public class RegisterActivityPresenter extends RegisterActivityContract.Presenter {

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

            }
        }).get();
    }

    @Override
    public void postData(String phone, String code, String psw,int phoneTpye,String appname,String phoneBroad,String appversion,String lng,String lat,String address) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.register);
        util.putParam("phone",phone);
        util.putParam("code",code);
        util.putParam("password",psw);
        util.putParam("phoneType",String.valueOf(phoneTpye));
        util.putParam("phoneBrand",phoneBroad);
        util.putParam("appVersion",appversion);
        util.putParam("appName",appname);
        util.putParam("regIng",lng);
        util.putParam("regLat",lat);
        util.putParam("regAddress",address);
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
                        UIUtil.showToast("注册成功！");
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

    public void getregisteragreement(){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.registeragreement);
        util.putParam("type",3+"");
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
                        JSONObject dataobject=object.getJSONObject("data");
                        Intent mIntent=new Intent(mContext, AgreementActivity.class);
                        mIntent.putExtra("content",dataobject.getString("content"));
                        mContext.startActivity(mIntent);
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
