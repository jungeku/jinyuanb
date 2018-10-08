package com.sk.xjwd.authenhome.presenter;


import android.util.Log;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.authenhome.contract.IDAuthActivityContract;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.Map;

public class IDAuthActivityPresenter extends IDAuthActivityContract.Presenter {

    public void getIDAuth(Map<String, String> map){//身份认证
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.getIDAuth);
        util.putjson(map);
        util.putHead("x-client-token", MyApplication.getString("token",""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override public void onCancelled(Callback.CancelledException arg0) {}
            @Override public void onError(Throwable arg0, boolean arg1) {}
            @Override public void onFinished() {}

            @Override
            public void onSuccess(String result) {
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        UIUtil.showToast("认证成功！");
                        RxBus.getDefault().post(Constants.REQUESTID_1, 2);
                        UIUtil.startActivity(MainActivity.class,null);
                    }else{
                        UIUtil.showToast("认证失败！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).post();
    }

    /**
     * 调用人脸对比接口
     */
    public void checkFace(String sessionId,String idPhoto,String facePhoto){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.checkFace);
        util.putParam("idPhoto",idPhoto);
        util.putParam("facePhoto",facePhoto);
        util.putParam("session_id",sessionId);
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {}

            @Override
            public void onError(Throwable arg0, boolean arg1) {}

            @Override
            public void onFinished() {}

            @Override
            public void onSuccess(String result) {
                Log.e("logincheckFace",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        mView.checkFaceSuccess();
                        UIUtil.showToast("认证失败！222"+object.getString("code"));
                    }else{
                        UIUtil.showToast("认证失败！"+object.getString("code"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    /**
     * 调用实名认证接口
     */
    public void checkIdentity(String idNumber,String idName){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.checkIdentity);
        util.putParam("id_number",idNumber);
        util.putParam("id_name",idName);
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {}

            @Override
            public void onError(Throwable arg0, boolean arg1) {}

            @Override
            public void onFinished() {}

            @Override
            public void onSuccess(String result) {
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        mView.checkIdentitySuccess(object.getString("msg"));
                        UIUtil.showToast("认证失败！222"+object.getString("msg"));

                    }else{
                        UIUtil.showToast("认证失败！"+object.getString("msg"));
                    //    mView.checkIdentitySuccess(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }


}
