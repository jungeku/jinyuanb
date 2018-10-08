package com.sk.xjwd.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.sk.xjwd.MyApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jh352160 on 2017/9/8.
 */

public abstract class BaseCallBack<T> implements Callback<T> {
    private KProgressHUD mProgressDialog;//进度窗体

    public BaseCallBack(){}

    /**
     * 传入Context则显示进度条
     */
    public BaseCallBack(Context mContext){
        showInfoProgressDialog(mContext,"加载中...");
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onFinish();
        if (response.code()==200){
            onSuccess(call,response);
        }else{
            onError(call,response);
        }
    }

    /**
     * response中的code不为200的情况
     */
    public void onError(Call<T> call, Response<T> response) {
        Toast.makeText(MyApplication.getContext(),response.message(),Toast.LENGTH_LONG).show();
        Log.e("NetWorkError",response.message());
    }

    /**
     * response中的code为200
     */
    public abstract void onSuccess(Call<T> call, Response<T> response);

    /**
     * 网络请求出现异常
     */
    @Override
    public void onFailure(Call call, Throwable t) {
        onFinish();
        Toast.makeText(MyApplication.getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
        Log.e("NetWorkFail",t.toString());
    }

    /**
     * 隐藏等待条
     */
    private void hideInfoProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog=null;
        }
    }

    /**
     * 显示等待条
     */
    private  void showInfoProgressDialog(Context context, final String str) {
        if (mProgressDialog == null) {
            mProgressDialog = new KProgressHUD(context);
            mProgressDialog.setCancellable(true);

        }

        mProgressDialog.setLabel(str);

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 当请求完成时调用（无论成功或失败）
     */
    public void onFinish(){
        //如果没有加入进度条操作可以不调用super
        hideInfoProgressDialog();
    }
}
