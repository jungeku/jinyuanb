package com.sk.xjwd.mainhome.presenter;


import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.mainhome.contract.MainActivityContract;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

public class MainActivityPresenter extends MainActivityContract.Presenter {

    public void isGengxinVersion(String appversion){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.gengxinVersion);
        util.putParam("operatingSystem","1");
        util.putParam("version",appversion);
        util.putParam("appName","唐长老钱包");
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                if(arg0.toString().contains("ConnectException")){
                    UIUtil.showToast("服务器连接异常，稍后再试");
                }

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject object1=object.getJSONObject("data");
                        MyApplication.saveInt("appversion",object1.getInt("isUpdate"));
                        if(!object1.getString("isUpdate").equals(0)){
                            MyApplication.saveString("appversionUrl",object1.getString("url"));
                        }
                        mView.initgengxin();
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
