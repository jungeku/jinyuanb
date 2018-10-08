package com.sk.xjwd.mainhome.presenter;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.contact.KfStartHelper;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.mainhome.contract.MineFragmentContact;
import com.sk.xjwd.minehome.activity.PayPwdActivity;
import com.sk.xjwd.minehome.activity.PayPwdUpdateActivity;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.ActionSheetDialog;
import com.sk.xjwd.view.TiDialog;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.List;

public class MineFragmentPresenter extends MineFragmentContact.Presenter {


    public void payPwdIsExist(){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.payPwdIsExist);
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
                        if(object.getString("msg").equals("true")){
                            UIUtil.startActivity(PayPwdUpdateActivity.class,null);
                        }else{
                            UIUtil.startActivity(PayPwdActivity.class,null);
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

    public void showchatDialog(Context mContext) {
        new ActionSheetDialog(mContext)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("0571-28070194", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        //dadianhua
                        //用intent启动拨打电话
                        new AlertDialog.Builder(mContext)
                                .setTitle("确定")
                                .setMessage("确定拨打该电话")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0571-28070194"));
                                        mContext.startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();



                    }
                })
                .addSheetItem("在线客服", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        //接入客服SDK
//                        UIUtil.showToast("敬请期待");
                        /**
                         * 第一步：初始化help 文件
                         */
                        final KfStartHelper helper = new KfStartHelper((MainActivity)mContext);

                        helper.initSdkChat("com.m7.imkf.KEFU_NEW_MSG", "26a6fd50-aff0-11e8-88b1-4d7a83c49a67", "pwapp", "pwapp");

                    }
                })
                .show();
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
