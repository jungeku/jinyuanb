package com.sk.xjwd.minehome.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityWebviewBinding;
import com.sk.xjwd.minehome.presenter.WebviewActivityPresenter;

/**
 * Created by mayn on 2018/9/3.
 */

public class WebviewActivity extends BaseActivity<WebviewActivityPresenter,ActivityWebviewBinding> {

    // Intent传递过来的值
    private String title, urls;
    // 是否能右滑结束Activity
    private boolean isMove = false;
    // 当前WebView打开的地址(拼接的)
    private String mIsNowUrl;

    public static final int FROM_RECHARGE = 1;
    private int fromFlag;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
    }


    @Override
    protected void initView() {
        title=getIntent().getStringExtra("WebviewTitle");
        urls=getIntent().getStringExtra("WebviewUrl");
        if(title!=null && title.length()>0) {
            setTitle(title);
        }
        android.webkit.WebSettings ws = mBindingView.webview.getSettings();
       ws.setJavaScriptEnabled(true);//开启JavaScript支持
        mBindingView.webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//加载网页面开始时触发，一般加个进度条
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载网页面结束时触发，一般去掉进度条
            }
        });
        mBindingView.webview.loadUrl(urls);

    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void onBackPressed() {
        if (mBindingView.webview.canGoBack()) {
            mBindingView.webview.goBack();
        } else {
            finish();
        }
    }
}
