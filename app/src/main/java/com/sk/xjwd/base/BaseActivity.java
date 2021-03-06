package com.sk.xjwd.base;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.sk.xjwd.R;
import com.sk.xjwd.http.HttpTask;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zyf.fwms.commonlibrary.databinding.ActivityBaseBinding;
import com.zyf.fwms.commonlibrary.http.HttpUtils;
import com.zyf.fwms.commonlibrary.model.AccountInfo;
import com.zyf.fwms.commonlibrary.model.RxCodeConstants;
import com.sk.xjwd.account.model.UserInfoModel;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.LogUtil;
import com.zyf.fwms.commonlibrary.utils.RxBus;
import com.zyf.fwms.commonlibrary.utils.SharedPreUtil;
import com.zyf.fwms.commonlibrary.utils.StatusBarUtil;
import com.zyf.fwms.commonlibrary.utils.TUtil;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;


/**
 *
 * 刘宇飞创建 on 2017/5/18.
 * 描述：
 */
public abstract class BaseActivity<E extends BasePresenter,SV extends ViewDataBinding> extends FragmentActivity {
    //布局view
    protected SV mBindingView;
    private CompositeSubscription mCompositeSubscription;
    protected Context mContext;
    protected HttpTask mHttpTask;
    public E mPresenter;
    private RxPermissions rxPermissions;
    protected ActivityBaseBinding mBaseBinding;

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mHttpTask = HttpUtils.getInstance().createRequest(HttpTask.class);
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = this;
            mPresenter.httpTask = mHttpTask;
        }
        initPresenter();
        //打印类名
        LogUtil.getInstance().e(getClass().toString());
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        //标题栏已经在activity_base 不用到每个布局里面添加
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        mBindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = mBaseBinding.container;
        mContainer.addView(mBindingView.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());
        ButterKnife.bind(this);
        // 设置透明状态栏
        StatusBarUtil.setColor(this, CommonUtils.getColor(this, R.color.transparent),0);
        initLisener();
        mContext =this;
        //根据设计稿设定 preview 切换至对应的尺寸
        AutoUtils.setSize(this, false, 720, 1280);
        //自适应页面
        AutoUtils.auto(this);
        initView();


    }

    protected abstract void initView();

    protected abstract void initPresenter();


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
                onBackPressed();
            }
        });
    }


    /**
     * 隐藏标题栏
     */
      protected void hideTitleBar(){
          mBaseBinding.commonTitle.rlTitleBar.setVisibility(View.GONE);
      }
    /**
     * 隐藏返回箭头
     */
    protected void hideBackImg(){
        mBaseBinding.commonTitle.llLiftBack.setVisibility(View.GONE);
    }
    /**
     * 设置标题
     */
     protected void setTitle(String title){
         mBaseBinding.commonTitle.tvTitle.setText(CommonUtils.isNotEmpty(title)?title:"");
     }
    /**
     * 设置右侧文字
     */
    protected void setRightTitle(String rightTitle, View.OnClickListener listener){
        mBaseBinding.commonTitle.tvRightText.setText(CommonUtils.isNotEmpty(rightTitle)?rightTitle:"");
        mBaseBinding.commonTitle.llRightText.setVisibility(View.VISIBLE);
        mBaseBinding.commonTitle.llRightImg.setVisibility(View.GONE);
        if(listener!=null){
            mBaseBinding.commonTitle.llRightText.setOnClickListener(listener);
        }
    }

    protected void setBackImgClick(View.OnClickListener listener){
        if (listener != null) {
            mBaseBinding.commonTitle.llLiftBack.setOnClickListener(listener);
        }
    }
    /**
     * 设置右侧图片
     */
    protected void setRightImg(int img, View.OnClickListener listener){
        mBaseBinding.commonTitle.llRightText.setVisibility(View.GONE);
        mBaseBinding.commonTitle.llRightImg.setVisibility(View.VISIBLE);
        if(img>0){
            mBaseBinding.commonTitle.ivRightImg.setImageResource(img);
        }
        if(listener!=null){
            mBaseBinding.commonTitle.llRightImg.setOnClickListener(listener);
        }
    }

    /**
     * 显示搜索框
     */
    protected void showEiditText( final  EditViewLisener lisener){
        hideBackImg();
        mBaseBinding.commonTitle.tvTitle.setVisibility(View.GONE);
        mBaseBinding.commonTitle.rlSearch.setVisibility(View.VISIBLE);
        initSerchEditTextView(mBaseBinding.commonTitle.etSearch,lisener);
    }
    /**
     * 初始化搜索输入框
     * @param etInput
     * @param lisener
     */
    private   void initSerchEditTextView(final EditText etInput, final  EditViewLisener lisener){
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
                if(CommonUtils.isEmpty(etInput.getText().toString())){
                    mBaseBinding.commonTitle.llEtLabel.setVisibility(View.VISIBLE);
                }else {
                    mBaseBinding.commonTitle.llEtLabel.setVisibility(View.GONE);
                }
                lisener.onTextChanged(etInput.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public  interface EditViewLisener{
        void searchText(String searchText);
        void onTextChanged(String searchText);
    }
    /**
     * 设置右侧图片
     */
    protected void setRightImg(int img, View.OnClickListener listener,float width,float height){
        mBaseBinding.commonTitle.llRightText.setVisibility(View.GONE);
        mBaseBinding.commonTitle.llRightImg.setVisibility(View.VISIBLE);
        if(img>0){
            mBaseBinding.commonTitle.ivRightImg.setImageResource(img);
        }
        if(listener!=null){
            mBaseBinding.commonTitle.llRightImg.setOnClickListener(listener);
        }
        if(width>0&&height>0){
            ViewGroup.LayoutParams layoutParams = mBaseBinding.commonTitle.ivRightImg.getLayoutParams();
            layoutParams.width= CommonUtils.dip2px(mContext,width);
            layoutParams.height= CommonUtils.dip2px(mContext,height);
            mBaseBinding.commonTitle.ivRightImg.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置整条背景颜色
     * @param color
     */
    protected void setAllBackgroundColor(Drawable color){
        mBaseBinding.commonTitle.rlTitleBar.setBackgroundDrawable(color);
    }
    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }

    /**
     *显示toast
     * @param title
     */
    protected   void showToast(String title) {
      CommonUtils.showToast(mContext,title);
    }
    private KProgressHUD mProgressDialog;

    /**
     * 显示进度框
     * @param str
     */
    public final void showInfoProgressDialog(final String... str) {
        if (mProgressDialog == null) {
            mProgressDialog = new KProgressHUD(this);
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
     * 请求权限
     */
    public void requestPermission(String[] permissions) {
        if(rxPermissions==null){
            rxPermissions = new RxPermissions(this);
        }
        Subscription subscribe = rxPermissions
                .request(permissions)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        requestPermissionCallBack(aBoolean);
                    }
                });
        addSubscription(subscribe);
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
     * 设置未读数
     */
    protected void setUnReadNum(TextView textView,int num) {
        textView.setText(num + "");
        if (num > 99) {
            textView.setText("99+");
        }
        if (num > 0) {
            textView.setVisibility(View.VISIBLE);
            if (num > 10) {
                if (num < 100)
                    textView.setPadding(3, 3, 3, 3);
                else textView.setPadding(3, 10, 3, 10);
            } else textView.setPadding(10, 3, 10, 3);

        } else {
            textView.setVisibility(View.GONE);
        }
    }


    /**
     * 请求结果
     * @param aBoolean
     */
    protected void requestPermissionCallBack(Boolean aBoolean) {
        RxBus.getDefault().post(RxCodeConstants.SHOW_TOAST,"请求权限结果:"+aBoolean);
    }

    /**
     * 显示错误页面或正常页面
     */
    protected void showErroView(boolean isShow){
        if(isShow){
            mBaseBinding.llErrorRefresh.setVisibility(View.VISIBLE);
            mBindingView.getRoot().setVisibility(View.GONE);
        }else {
            mBaseBinding.llErrorRefresh.setVisibility(View.GONE);
            mBindingView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    /**
     * 添加网络请求观察者
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
    protected void onResume() {
        super.onResume();
        //发生异常 恢复用户信息
        if (AccountInfo.id == 0) {
            String userInfoModel = SharedPreUtil.getString(this, "UserInfoModel", "");
            if (CommonUtils.isNotEmpty(userInfoModel)) {
                UserInfoModel model = CommonUtils.getGson().fromJson(userInfoModel, UserInfoModel.class);
                model.setAccountInfo();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideInfoProgressDialog();
        removeSubscription();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        ButterKnife.unbind(this);
    }
}
