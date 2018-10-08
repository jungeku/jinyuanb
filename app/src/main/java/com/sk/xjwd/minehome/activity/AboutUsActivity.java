package com.sk.xjwd.minehome.activity;

import android.os.Bundle;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityAboutUsBinding;
import com.sk.xjwd.minehome.contract.AboutActivityContract;
import com.sk.xjwd.minehome.presenter.AboutActivityPresenter;

public class AboutUsActivity extends BaseActivity<AboutActivityPresenter,ActivityAboutUsBinding> implements AboutActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

    @Override
    protected void initView() {
        setTitle("关于我们");
        mPresenter.getaboutusdetaul(mBindingView.txtDetail);
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initRxBus() {

    }
}
