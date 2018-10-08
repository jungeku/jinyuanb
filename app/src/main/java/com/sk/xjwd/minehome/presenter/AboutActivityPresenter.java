package com.sk.xjwd.minehome.presenter;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.contract.AboutActivityContract;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.List;

public class AboutActivityPresenter extends AboutActivityContract.Presenter {

    public void getdata(){//获取小额贷信息
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.aboutXed);
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
                        JSONObject object1=object.getJSONObject("data");
                        MyApplication.saveString("servePhone",object1.getString("servePhone"));
                        MyApplication.saveString("wexin",object1.getString("wexin"));
                        RxBus.getDefault().post(2, 1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void getaboutusdetaul(final TextView txt_detail){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.aboutusdetail);
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
                        txt_detail.setText(Html.fromHtml(object.getString("msg")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void getqq(){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.getqq);
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
                Log.e("login", "onSuccess: "+result );
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        if(CommonUtils.isNotEmpty(object.getString("msg"))){
                            if(isQQClientAvailable(mContext)){
                                final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin="+object.getString("msg")+"&version=1";
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                            }else{
                                UIUtil.showToast("请先安装QQ客户端！");
                            }
                        }
                    }else {
                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
}
