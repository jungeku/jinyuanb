package com.sk.xjwd.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.xrecyclerview.CheckNetwork;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.utils.DigestUtils;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ljx
 * 先调用构造方法和url方法，putParam和putFile方法在url之后随意调用，最后调用get或post方法
 */
public class HttpUtil {
    private RequestParams params;

    private Context context;

    private HttpUtilListener listener;

    private ProgressDialog progressDialog;

    private Cancelable cancelable;

    private boolean isShowDialog = true;
    private String content="加载中";

    public HttpUtil(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public HttpUtil setUrl(String url) {
        params = new RequestParams(url);
        params.setConnectTimeout(60000);
        params.setReadTimeout(60000);
        params.addBodyParameter("appKey", Api.APP_KEY);
        params.addBodyParameter("timestamp", Api.time + "000");
        params.addBodyParameter("source", "APP");
        params.addBodyParameter("sign", DigestUtils.md5(Api.sign));
        return this;
    }

    public HttpUtil setListener(HttpUtilListener listener) {
        this.listener = listener;
        return this;
    }

    public void get() {
        if(!CheckNetwork.isNetworkConnected(MyApplication.getContext())){
            CommonUtils.showToast(MyApplication.getContext(),"无网络连接,请检查网络");
            return ;
        }

        if (params == null) {
            System.out.println("params is null");
            return;
        }

        if (listener == null) {
            System.out.println("listener is null");
            return;
        }

        cancelable = x.http().get(params, new CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
                // TODO Auto-generated method stub
                listener.onCancelled(arg0);
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                // TODO Auto-generated method stub
                listener.onError(arg0, arg1);
                if(arg0.toString().contains("ConnectException")){
                    UIUtil.showToast("服务器连接异常，稍后再试");
                }
            }

            @Override
            public void onFinished() {
                // TODO Auto-generated method stub
                dismissProgressDialog();
                listener.onFinished();
            }

            @Override
            public void onSuccess(String arg0) {
                // TODO Auto-generated method stub
                listener.onSuccess(arg0.toString());
            }

        });
        if (isShowDialog) {
            showProgressDialog();
        }

    }

    public void post() {
        if(!CheckNetwork.isNetworkConnected(MyApplication.getContext())){
            CommonUtils.showToast(MyApplication.getContext(),"无网络连接,请检查网络");
            return ;
        }
        if (params == null) {
            System.out.println("params is null");
            return;
        }

        if (listener == null) {
            System.out.println("listener is null");
            return;
        }

        x.http().post(params, new CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
                // TODO Auto-generated method stub
                listener.onCancelled(arg0);
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                // TODO Auto-generated method stub
                System.out.println(arg0.toString());
                listener.onError(arg0, arg1);
            }

            @Override
            public void onFinished() {
                // TODO Auto-generated method stub
                dismissProgressDialog();
                listener.onFinished();
            }

            @Override
            public void onSuccess(String arg0) {
                // TODO Auto-generated method stub
                System.out.println("onSuccess" + arg0);
                listener.onSuccess(arg0);
            }
        });

        if(isShowDialog){
            showProgressDialog();
        }

    }

    public HttpUtil putParam(String key, String value) {
        if (params != null) {
            params.addBodyParameter(key, value);
        }
        return this;
    }

    public HttpUtil putFile(String key, File file){
        if (params != null) {
            params.addBodyParameter(key, file);
        }
        return this;
    }

    public HttpUtil putHead(String key,String value){
        if (params != null) {
            params.addHeader(key, value);
        }
        return this;
    }

    public HttpUtil putjson(Map<String,String> map){
        if(params!=null){
            try {
                JSONObject js_request = new JSONObject();//服务器需要传参的json对象
                if(map!=null){
                    for(String key:map.keySet()){
                        js_request.put(key, map.get(key));//根据实际需求添加相应键值对
                    }
                    params.setAsJsonContent(true);
                    params.setBodyContent(js_request.toString());
                    Log.e("login",js_request.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public HttpUtil putjsonarray(List<HashMap<String,String>> list){
        if(params!=null){
            try {
                if(list.size()!=0){
                    JSONArray js_array=new JSONArray();
                    for (int i = 0; i <list.size() ; i++) {
                        JSONObject js_request = new JSONObject();
                        for(String key:list.get(i).keySet()){
                            //服务器需要传参的json对象
                            js_request.put(key, list.get(i).get(key));//根据实际需求添加相应键值对
                        }
                        Log.e("login",js_request.toString());
                        js_array.put(js_request);
                    }
                    Log.e("login",js_array.toString());
                    params.setAsJsonContent(true);
                    params.setBodyContent(js_array.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this;
    }


    public interface HttpUtilListener {
         void onCancelled(CancelledException arg0);

         void onError(Throwable arg0, boolean arg1);

         void onFinished();

         void onSuccess(String result);
    }

    public void showProgressDialog() {
        if(context!=null){
            CommonUtils.getInstance().showInfoProgressDialog(context,content);
        }
    }

    public void dismissProgressDialog() {
        CommonUtils.getInstance().hideInfoProgressDialog();
    }
    //用来设置请求是是否显示progressdialog
    public void setShowDialog(boolean isShowDialog) {
        this.isShowDialog = isShowDialog;
    }

    public void setshowDialogcontent(String msg){
        this.content=msg;
    }

}
