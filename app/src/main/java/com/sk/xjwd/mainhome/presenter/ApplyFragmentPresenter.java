package com.sk.xjwd.mainhome.presenter;


import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.EditText;

import com.authreal.api.AuthBuilder;
import com.authreal.api.OnResultListener;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.authenhome.activity.ApplyActivity;
import com.sk.xjwd.authenhome.activity.ZhimaActivity;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.mainhome.activity.TaoBsoActivity;
import com.sk.xjwd.mainhome.contract.ApplyFragmentContact;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.WaitDialog;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.fraudmetrix.octopus.aspirit.bean.OctopusParam;
import cn.fraudmetrix.octopus.aspirit.main.OctopusManager;
import cn.fraudmetrix.octopus.aspirit.main.OctopusTaskCallBack;

public class ApplyFragmentPresenter extends ApplyFragmentContact.Presenter {
    WaitDialog dialog;
    String userName, identityNum;
    private Map<String, String> map = new HashMap<>();

    @Override
    public void GetAuthState() {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.AuthState);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
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
                Log.e("login", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject object1 = object.getJSONObject("data");
                        MyApplication.saveInt("socre", object1.getInt("socre"));//分数
                        JSONObject objectauth = object1.getJSONObject("auth");
                        MyApplication.saveInt("baiscAuth", objectauth.getInt("baiscAuth"));//基本认证
                        MyApplication.saveInt("bankAuth", objectauth.getInt("bankAuth"));//银行卡认证
                        MyApplication.saveInt("phoneAuth", objectauth.getInt("phoneAuth"));//手机认证
                        MyApplication.saveInt("identityAuth", objectauth.getInt("identityAuth"));//身份认证
                        MyApplication.saveInt("zhimaAuth", objectauth.getInt("zhimaAuth"));//芝麻授信
                        MyApplication.saveInt("shebaoAuth", objectauth.getInt("shebaoAuth"));//社保认证
                        MyApplication.saveInt("gongjijinAuth", objectauth.getInt("gongjijinAuth"));//公积金认证
                        MyApplication.saveInt("zhifubaoAuth", objectauth.getInt("zhifubaoAuth"));//支付宝认证
                        MyApplication.saveInt("jindongAuth", objectauth.getInt("jindongAuth"));//京东认证
                        MyApplication.saveInt("taobaoAuth", objectauth.getInt("taobaoAuth"));//淘宝认证
                        mView.reflsh();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void getTaoBaoAuth() {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.getTaoBaoAuth);
        util.putParam("success_url", "http://www.baidu.com");
        util.putParam("failed_url", "http://www.google.com");
        util.putHead("x-client-token", MyApplication.getString("token", ""));
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
                Log.e("login", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject dataobject = object.getJSONObject("data");
                        Intent intent2 = new Intent(mContext, TaoBsoActivity.class);
                        intent2.putExtra("url", dataobject.getString("url"));
                        intent2.putExtra("title", "淘宝认证");
                        mContext.startActivity(intent2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void passAuth() {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.getZhiMaIsPass);
        util.putParam("biz_no", MyApplication.getString("biz_no", ""));
        util.putParam("userId", MyApplication.getString("ID_userId", ""));
        util.putHead("x-client-token", MyApplication.getString("token", ""));
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
                Log.e("login", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        RxBus.getDefault().post(Constants.REQUESTID_1, 2);
                    } else {
                        UIUtil.showToast("认证失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }


    public void showTobao(Activity context) {
        //对导航栏的返回按钮，导航栏背景，导航栏title进行设置（可选操作）
        //设置导航返回图标
        OctopusManager.getInstance().setNavImgResId(R.mipmap.fanhui_bai);
        //设置导航背景
        OctopusManager.getInstance().setPrimaryColorResId(R.color.public_orange1);
        //设置title字体颜色
        OctopusManager.getInstance().setTitleColorResId(R.color.color_white);
        //sp 设置title字体大小
        OctopusManager.getInstance().setTitleSize(16);
        //强制对话框是否显示 v1.2.0增加
        OctopusManager.getInstance().setShowWarnDialog(true);
        //设置状态栏背景 v1.2.0增加
        OctopusManager.getInstance().setStatusBarBg(R.color.public_orange1);
        //调用SDK
        //activity为当前所在Activity实例,channelCode为渠道码,例如淘宝对应的渠道码: 005003
        OctopusManager.getInstance().getChannel(context, "005003", new OctopusParam(), octopusTaskCallBack);

    }

    private OctopusTaskCallBack octopusTaskCallBack = new OctopusTaskCallBack() {
        @Override
        public void onCallBack(int code, String taskId) {
            String msg = "成功";
            if (code == 0) {//code为0表示成功，f非0表示失败
                //只有code为0时taskid才会有值。
                msg += taskId;
                doLogin(MyApplication.getString("userPhone",""),MyApplication.getString("userPwd",""),taskId);
                //    UIUtil.showToast(msg);
            } else {
                msg = "失败：" + code;
                UIUtil.showToast(msg);
            }
        }
    };

    public void doLogin(String phone, String psw,String taskid) {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.LOGINSMS);
        util.putParam("phone", phone);
        util.putParam("password", psw);
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                if (arg0.toString().contains("ConnectException")) {
                    UIUtil.showToast("服务器连接异常，稍后再试");
                }

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject object1 = object.getJSONObject("data");
                        //正常
                        MyApplication.saveBoolean("islogin", true);//false未登录true登录
                        MyApplication.saveString("loginstatus", object1.getString("status"));
                        MyApplication.saveString("uuid", object1.getString("uuid"));
                        MyApplication.saveInt("id", object1.getInt("id"));
                        MyApplication.saveString("userName", object1.getString("userName"));
                        MyApplication.saveString("password", object1.getString("password"));
                        MyApplication.saveString("phone", object1.getString("phone"));
                        MyApplication.saveString("money", object1.getString("money"));
                        MyApplication.saveInt("userType", object1.getInt("userType"));
                        MyApplication.saveInt("authStatus", object1.getInt("authStatus"));
                        MyApplication.saveString("token", object1.getString("token"));
                        MyApplication.saveString("payPwd", object1.getString("payPwd"));
                        sendSuccessMessage(true, taskid);
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();

    }

    /**
     * 有盾
     * 个人身份认证和人脸识别
     * 获取AuthBuilder。
     * 请在每次调用前获取新的AuthBuilder
     * 一个AuthBuilder 不要调用两次start()方法
     *
     * @return
     */
    public AuthBuilder getAuthBuilder(String orderid) {
        AuthBuilder mAuthBuidler = new AuthBuilder(orderid, Api.pubKey, Api.Backurl, s -> {
            try {

                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("result_auth").equals("T")) {
                    dialog = new WaitDialog(mContext, "正在加载中...");
                    dialog.show();
                    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(4000, 1000);
                    myCountDownTimer.start();


                } else {
                    UIUtil.showToast("认证失败！");
                }

            } catch (Exception e) {

            }
        });

        return mAuthBuidler;
    }

    private void encodeSuccess(Map<String, String> encodeMap) {
        if (userName.equals("") || identityNum.equals("")) {

        } else {
            Map<String, String> map1 = new HashMap<>();
            map1.put("identityFront", map.get("key2"));//正面照
            map1.put("identityBack", map.get("key1"));//正面照
            map1.put("faceUrl", map.get("key3"));//正面照
            map1.put("userName", userName);//
            map1.put("identityNum", identityNum);//
            getIDAuth();
        }
    }


    public void getIDAuth() {//身份认证
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //签名时间：有效期5分钟，请每次重新生成 :签名时间格式：yyyyMMddHHmmss
        final String sign_time = simpleDateFormat.format(new Date());
        Log.e("login1212", sign_time);
        Map<String, String> map = new HashMap<>();
        map.put("identityOrderNo", sign_time);
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.shenFenRenZheng);
        util.putjson(map);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
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
                Log.e("login1212", result);
                getAuthBuilder(sign_time).faceAuth(mContext);

            }
        }).post();
    }

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            dialog.dismiss();
            UIUtil.showToast("认证成功！");
            RxBus.getDefault().post(Constants.REQUESTID_1, 2);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }
    }

    /*
    *
    * 淘宝认证
    * */
    private void sendSuccessMessage(final boolean isSuccess, String taskid) {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.isTaoBaoPass);
        util.putParam("flag", isSuccess ? "yes" : "no");
        util.putParam("task_id", taskid);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
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
                    if (new JSONObject(result).getString("code").equals("SUCCESS")) {
                        CommonUtils.showToast(mContext, isSuccess ? "淘宝认证成功！" : "淘宝认证失败！");
                        RxBus.getDefault().post(Constants.REQUESTID_1, 2);
                        MyApplication.saveInt("authStatus", 1);
//                        UIUtil.startActivity(MainActivity.class, null);
                        UIUtil.startActivity(ApplyActivity.class, null);

                    } else {
                        UIUtil.showToast(new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).get();
    }


}
