package com.sk.xjwd.authenhome.presenter;


import android.util.Log;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.authenhome.activity.ZhimaActivity;
import com.sk.xjwd.authenhome.contract.ZhimaActivityContract;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.HashMap;

public class ZhimaActivityPresenter extends ZhimaActivityContract.Presenter {
    public void savaData(String name,String pad) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.saveAlipayInfo);
        util.putParam("aliNumber",name);
        util.putParam("aliPwd",pad);
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
                    if(object.getString("code").equals("SUCCESS")){
                        UIUtil.showToast(object.getString("msg"));
                        mView.finishActivity();
                      //  UIUtil.startActivity(MainActivity.class,null);
                    }else {
                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void seleAliInfo() {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.seleAliInfo);
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
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject js=   object.getJSONObject("data");
                        if (CommonUtils.isNotEmpty(js.getString("aliNumber"))){
                            mView.showInfo(js.getString("aliNumber"),js.getString("aliPwd"));
                        }
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).get();
    }
}
