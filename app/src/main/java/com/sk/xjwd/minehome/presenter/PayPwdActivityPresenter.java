package com.sk.xjwd.minehome.presenter;


import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.minehome.activity.PayPwdActivity;
import com.sk.xjwd.minehome.contract.PayPwdActivityContract;
import com.sk.xjwd.utils.DigestUtils;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

public class PayPwdActivityPresenter extends PayPwdActivityContract.Presenter {

    @Override
    public void setpaypwd(String payPwd,String payPwdConfirm) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.settingPayPwd);
        util.putParam("payPwd",DigestUtils.md5(payPwd));
        util.putParam("payPwdConfirm",DigestUtils.md5(payPwdConfirm));
        util.putHead("x-client-token", MyApplication.getString("token",""));
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
                        UIUtil.showToast("密码设置成功");
                        UIUtil.startActivity(MainActivity.class,null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    @Override
    public void checkoldpaypwd(String payoldPwd) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.oldPayPwdConfirm);
        util.putParam("oldPayPwd", DigestUtils.md5(payoldPwd));
        util.putHead("x-client-token", MyApplication.getString("token",""));
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
                        UIUtil.startActivity(PayPwdActivity.class,null);
                    }else {
                        UIUtil.showToast("原密码输入错误！！！");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
}
