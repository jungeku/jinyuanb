package com.sk.xjwd.base;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.sk.xjwd.R;
import com.sk.xjwd.http.HttpTask;
import com.zyf.fwms.commonlibrary.databinding.FragmentBaseBinding;
import com.zyf.fwms.commonlibrary.http.HttpUtils;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.LogUtil;
import com.zyf.fwms.commonlibrary.utils.TUtil;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 刘宇飞创建 on 2017/5/18.
 * 描述：
 */
public abstract class BaseFragment<SV extends ViewDataBinding, T extends BasePresenter> extends Fragment {
    //布局view
    protected SV mBindingView;
    private CompositeSubscription mCompositeSubscription;
    // 内容布局
    protected RelativeLayout mContainer;
    protected Context mContext;
    //加载失败
    private LinearLayout mRefresh;
    protected HttpTask mHttpTask;
    public T mPresenter;
    protected FragmentBaseBinding mBaseBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHttpTask = HttpUtils.getInstance().createRequest(HttpTask.class);
        mPresenter = TUtil.getT(this, 1);
        mContext = getContext();
        if (mPresenter != null) {
            mPresenter.httpTask = mHttpTask;
            mPresenter.mContext = mContext;
        }
        initPresenter();
        //打印类名
        LogUtil.getInstance().e(getClass().toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false);
        mBindingView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), setContent(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBindingView.getRoot().setLayoutParams(params);
        mContainer = mBaseBinding.container;
        mContainer.addView(mBindingView.getRoot());
        mContext = getContext();
        initLisener();
        initView();
        loadData();
        ButterKnife.bind(this, mBaseBinding.getRoot());
        AutoUtils.auto(mBaseBinding.getRoot());
        return mBaseBinding.getRoot();
    }

    protected abstract void initView();

    protected abstract void initPresenter();

    /**
     * 加载数据
     */
    protected void loadData() {
        LogUtil.getInstance().e("loadData()");
    }


    private void initLisener() {
        mBaseBinding.llErrorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        mBaseBinding.commonTitle.llLiftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    /**
     * 隐藏标题栏
     */
    protected void hideTitleBar(boolean mIsHide) {
        if (mIsHide)
            mBaseBinding.commonTitle.rlTitleBar.setVisibility(View.GONE);
        else
            mBaseBinding.commonTitle.rlTitleBar.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏返回箭头
     */
    protected void hideBackImg() {
        mBaseBinding.commonTitle.llLiftBack.setVisibility(View.GONE);
    }

    /**
     * 设置标题
     */
    protected void setTitle(String title) {
        mBaseBinding.commonTitle.tvTitle.setText(CommonUtils.isNotEmpty(title) ? title : "");
    }

    /**
     * 设置标题颜色
     */
    protected void setTitleColor(int color) {
        mBaseBinding.commonTitle.tvTitle.setTextColor(color);
    }
    /**
     * 设置右侧文字
     */
    protected void setRightTitle(String rightTitle, View.OnClickListener listener) {
        mBaseBinding.commonTitle.tvRightText.setText(CommonUtils.isNotEmpty(rightTitle) ? rightTitle : "");
        mBaseBinding.commonTitle.llRightText.setVisibility(View.VISIBLE);
        mBaseBinding.commonTitle.llRightImg.setVisibility(View.GONE);
        if (listener != null) {
            mBaseBinding.commonTitle.llRightText.setOnClickListener(listener);
        }
    }
    /**
     * 获取右侧菜单文字
     */
    protected String getRightTitle(){
        return  mBaseBinding.commonTitle.tvRightText.getText().toString();
    }

    /**
     * 设置右侧图片
     */
    protected void setRightImg(int img, View.OnClickListener listener) {
        mBaseBinding.commonTitle.llRightText.setVisibility(View.GONE);
        mBaseBinding.commonTitle.llRightImg.setVisibility(View.VISIBLE);
        if (img > 0) {
            mBaseBinding.commonTitle.ivRightImg.setImageResource(img);
        }
        if (listener != null) {
            mBaseBinding.commonTitle.llRightImg.setOnClickListener(listener);
        }
    }

    /**
     * 设置整条背景颜色
     */
    protected void setAllBackgroundColor(int color){
        mBaseBinding.commonTitle.rlTitleBar.setBackgroundColor(color);
    }

    protected void setAllBackgroundDrawable(Drawable drawable) {
        mBaseBinding.commonTitle.rlTitleBar.setBackgroundDrawable(drawable);
    }
    /**
     * 设置未读数
     */
    protected void setUnReadNum(int num) {
        mBaseBinding.commonTitle.tvUnRead.setText(num + "");
        if (num > 99) {
            mBaseBinding.commonTitle.tvUnRead.setText("99+");
        }
        if (num > 0) {
            mBaseBinding.commonTitle.tvUnRead.setVisibility(View.VISIBLE);
            if (num > 10) {
                if (num < 100)
                    mBaseBinding.commonTitle.tvUnRead.setPadding(3, 3, 3, 3);
                else mBaseBinding.commonTitle.tvUnRead.setPadding(3, 10, 3, 10);
            } else mBaseBinding.commonTitle.tvUnRead.setPadding(10, 3, 10, 3);

        } else {
            mBaseBinding.commonTitle.tvUnRead.setVisibility(View.GONE);
        }
    }

    /**
     * 显示搜索框
     */
    protected void showEiditText(final BaseActivity.EditViewLisener lisener) {
        hideBackImg();
        mBaseBinding.commonTitle.tvTitle.setVisibility(View.GONE);
        mBaseBinding.commonTitle.rlSearch.setVisibility(View.VISIBLE);
        initSerchEditTextView(mBaseBinding.commonTitle.etSearch, lisener);
    }

    /**
     * 初始化搜索输入框
     *
     * @param etInput
     * @param lisener
     */
    private void initSerchEditTextView(final EditText etInput, final BaseActivity.EditViewLisener lisener) {
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            //发送请求
                            lisener.searchText(etInput.getText().toString());
                            return true;

                        default:
                            return true;
                    }
                }
                return false;
            }
        });
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (CommonUtils.isEmpty(etInput.getText().toString())) {
                    mBaseBinding.commonTitle.llEtLabel.setVisibility(View.VISIBLE);
                } else {
                    mBaseBinding.commonTitle.llEtLabel.setVisibility(View.GONE);
                }
                lisener.onTextChanged(etInput.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * 设置右侧图片
     */
    protected void setRightImg(int img, View.OnClickListener listener, float width, float height) {
        mBaseBinding.commonTitle.llRightText.setVisibility(View.GONE);
        mBaseBinding.commonTitle.llRightImg.setVisibility(View.VISIBLE);
        if (img > 0) {
            mBaseBinding.commonTitle.ivRightImg.setImageResource(img);
        }
        if (listener != null) {
            mBaseBinding.commonTitle.llRightImg.setOnClickListener(listener);
        }
        if (width > 0 && height > 0) {
            ViewGroup.LayoutParams layoutParams = mBaseBinding.commonTitle.ivRightImg.getLayoutParams();
            layoutParams.width = CommonUtils.dip2px(mContext, width);
            layoutParams.height = CommonUtils.dip2px(mContext, height);
            mBaseBinding.commonTitle.ivRightImg.setLayoutParams(layoutParams);
        }
    }


    protected <T extends View> T getView(int id) {
        return (T) getView().findViewById(id);
    }

    /**
     * 布局
     */
    public abstract int setContent();

    /**
     * 加载失败后点击后的操作
     */
    protected void onRefresh() {

    }


    /**
     * 显示toast
     *
     * @param title
     */
    protected void showToast(String title) {
        CommonUtils.showToast(mContext, title);
    }

    private KProgressHUD mProgressDialog;

    /**
     * 显示进度框
     *
     * @param str
     */
    public final void showInfoProgressDialog(final String... str) {
        if (mProgressDialog == null) {
            mProgressDialog = new KProgressHUD(mContext);
            mProgressDialog.setCancellable(true);
        }
        if (str.length == 0) {
            mProgressDialog.setLabel("加载中...");
        } else {
            mProgressDialog.setLabel(str[0]);
        }

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏等待条
     */
    public final void hideInfoProgressDialog() {
        CommonUtils.getInstance().hideInfoProgressDialog();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 显示错误页面或正常页面
     */
    protected void showErroView(boolean isShow) {
        if (isShow) {
            mRefresh.setVisibility(View.VISIBLE);
            mBindingView.getRoot().setVisibility(View.GONE);
        } else {
            mRefresh.setVisibility(View.GONE);
            mBindingView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    /**
     * 添加网络请求观察者
     *
     * @param s
     */
    public void addSubscription(Subscription s) {
        if(s==null){
            return;
        }
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    /**
     * 移除网络请求
     */
    public void removeSubscription() {
        CommonUtils.getInstance().removeSubscription();
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideInfoProgressDialog();
        removeSubscription();
        ButterKnife.unbind(this);
    }
}
