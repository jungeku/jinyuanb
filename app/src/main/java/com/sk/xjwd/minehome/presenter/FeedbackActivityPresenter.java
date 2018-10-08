package com.sk.xjwd.minehome.presenter;


import android.text.TextUtils;
import android.util.Log;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.minehome.activity.RecordDetailActivity;
import com.sk.xjwd.minehome.contract.FeedbackActivityContract;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackActivityPresenter extends FeedbackActivityContract.Presenter {


    @Override
    public void postData(int type, final String content, List<String> list, final String phone) {
        if(TextUtils.isEmpty(content)){
            UIUtil.showToast("请输入反馈内容");
            return;
        }
       /* if(list.size()==0){
            UIUtil.showToast("请上传图片");
            return;
        }*/
        if(type!=0){
            if ( !UIUtil.isMobile(phone)) {
                return;
            }
        }
        uploadfile(list,content,phone,type);
    }

    public void uploadfile(List<String> list, final String content, final String phone, final int type){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.uploadfile);
        for (int i = 0; i <list.size() ; i++) {
            util.putFile("files",new File(list.get(i)));
        }
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
                        JSONArray array=object.getJSONArray("data");
                        if(array.length()!=0){
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i <array.length() ; i++) {
                                sb.append(array.get(i)).append("***");
                            }
                            sb.deleteCharAt(sb.length() - 1);
                            sb.deleteCharAt(sb.length() - 1);
                            sb.deleteCharAt(sb.length() - 1);
                            Log.e("login",sb.toString());
                            if(type!=0){
                                commituserfeedback(type,content,sb.toString(),phone);
                            }else{
                                commitrepayfeedback(content,sb.toString());
                            }

                        }

                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).post();
    }

    public void commituserfeedback(int type, String content,String imgUrl, String phone){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.UserFeedBack);
        Map<String,String> map=new HashMap<>();
        map.put("type",type+"");
        map.put("content",content);
        map.put("phone",phone);
        map.put("imgUrl",imgUrl);
        util.putjson(map);
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
                Log.e("login",result+"反馈");
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        UIUtil.startActivity(MainActivity.class,null);
                        UIUtil.showToast("提交成功！");
                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).post();
    }

    public  void commitrepayfeedback(String reason,String imgUrl){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.repayFeedBack );
        Map<String,String> map=new HashMap<>();
        map.put("reason",reason);
        map.put("imgUrl",imgUrl);
        util.putjson(map);
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
                Log.e("login",result+"反馈");
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        UIUtil.startActivity(RecordDetailActivity.class,null);
                        UIUtil.showToast("提交成功！");
                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).post();
    }
}
