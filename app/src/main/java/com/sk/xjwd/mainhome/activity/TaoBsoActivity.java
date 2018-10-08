package com.sk.xjwd.mainhome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityTaoBsoBinding;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.contract.TaobaoActivityContact;
import com.sk.xjwd.mainhome.presenter.TaoBaoActivityPresenter;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import butterknife.ButterKnife;

public class TaoBsoActivity extends BaseActivity<TaoBaoActivityPresenter, ActivityTaoBsoBinding> implements TaobaoActivityContact.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_bso);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        setTitle(getIntent().getStringExtra("title"));
        initListener();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {

        mBindingView.wvTaobao.getSettings().setJavaScriptEnabled(true);
        mBindingView.wvTaobao.setWebChromeClient(new WebChromeClient());
//重点就在这里我们在我们要传递的URL之前加上这么一段url:"http://docs.google.com/gviewembedded=true&url="
        String pdfUrl = getIntent().getStringExtra("url");
        mBindingView.wvTaobao. loadUrl(
                pdfUrl);
    }

    // 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //这里进行url拦截
            /*if (url != null && url.contains("baidu.com")) {
                Intent intent = new Intent(TaoBsoActivity.this, MainActivity.class);
                TaoBsoActivity.this.startActivity(intent);
                finish();
                UIUtil.showToast("淘宝认证成功！");
                RxBus.getDefault().post(Constants.REQUESTID_1, 2);
                return true;
            }*/

            if(url != null){
                if (url.contains("baidu.com")) {
                        sendSuccessMessage(true);
                return true;
            }else if(url.contains("google")){
                sendSuccessMessage(false);
                return true;
            }
        }
// else if (url != null && url.contains("zmcustprod.zmxy.com.cn")) {
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                view.getContext().startActivity(intent);
//                Log.e("login", "111" + url);
//            }
            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);

        }
    }

    private void sendSuccessMessage(final boolean isSuccess) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.isTaoBaoPass);
        util.putParam("flag",isSuccess?"yes":"no");
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {}

            @Override
            public void onError(Throwable arg0, boolean arg1) {}

            @Override
            public void onFinished() {}

            @Override
            public void onSuccess(String result) {
                try {
                    if (new JSONObject(result).getString("code").equals("SUCCESS")){
                        CommonUtils.showToast(mContext,isSuccess?"淘宝认证成功！":"淘宝认证失败！");

                        RxBus.getDefault().post(Constants.REQUESTID_1,2);
                        Intent intent = new Intent(TaoBsoActivity.this, MainActivity.class);
                        TaoBsoActivity.this.startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).get();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //改写物理返回键的逻辑
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBindingView.wvTaobao.canGoBack()) {
                mBindingView.wvTaobao.goBack();//返回上一页面
                return true;
            } else {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
