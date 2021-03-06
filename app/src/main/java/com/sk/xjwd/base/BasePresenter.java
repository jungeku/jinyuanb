package com.sk.xjwd.base;


import android.content.Context;

import com.sk.xjwd.http.HttpTask;


/**
 * 公司：
 * 刘宇飞 创建 on 2017/3/6.
 * 描述：p 基类
 */

public abstract class BasePresenter<T>{
    public Context mContext;
    public T mView;
    public HttpTask httpTask;


    public void setView(T v) {
        this.mView = v;
        this.onStart();
    }


    public void onStart(){
    }
    public void onDestroy() {
         mView=null;

    }
}