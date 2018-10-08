package com.sk.xjwd.authenhome.presenter;


import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.authenhome.contract.PhoneAuthActivityContract;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

public class PhoneAuthActivityPresenter extends PhoneAuthActivityContract.Presenter {

    private MyCountDownTimer mc;
    private TextView btnGetcode;
    @Override
    public void senCode(TextView btncode, String phone, String pwd) {
        this.btnGetcode=btncode;
        mc = new MyCountDownTimer(60000, 1000);
        mc.start();
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.PHONEONE);
        util.putParam("phoneNo",phone);
        util.putParam("passwd",pwd);
        util.setShowDialog(false);
        util.putHead("x-client-token", MyApplication.getString("token",""));
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
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("msg").equals("登录不需要验证码")){
//                        UIUtil.showToast("登录不需要验证码");
                        RxBus.getDefault().post(Constants.REQUESTID_5, 1);
                    }else if(object.getString("msg").equals("第二种方式")){
                        RxBus.getDefault().post(Constants.REQUESTID_5, 2);
                    }else{
                        RxBus.getDefault().post(Constants.REQUESTID_5, 3);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }



//    //手机认证后存储信息
//    public void phoneauthinfo(String phonetoken) {
//        HttpUtil util=new HttpUtil(mContext);
//        util.setUrl(Api.phoneauthinfo);
//        util.putParam("phoneToken",phonetoken);
//        util.putHead("x-client-token", MyApplication.getString("token",""));
//        util.setListener(new HttpUtil.HttpUtilListener() {
//            @Override
//            public void onCancelled(Callback.CancelledException arg0) {
//
//            }
//
//            @Override
//            public void onError(Throwable arg0, boolean arg1) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                Log.e("login",result);
//                try {
//                    JSONObject object=new JSONObject(result);
//                    if(object.getString("code").equals("SUCCESS")){
//                        RxBus.getDefault().post(Constants.REQUESTID_1, 1);
//                        UIUtil.startActivity(MainActivity.class,null);
//                    }else{
//                        UIUtil.showToast(object.getString("msg"));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).get();
//    }

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
            Log.i("MainActivity", millisUntilFinished + "");
            btnGetcode.setText(millisUntilFinished / 1000+"秒后重新获取");
            btnGetcode.setEnabled(false);
        }
    }
}
