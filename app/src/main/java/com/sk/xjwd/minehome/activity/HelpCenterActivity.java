package com.sk.xjwd.minehome.activity;

import android.os.Bundle;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityHelpCenterBinding;
import com.sk.xjwd.minehome.contract.HelpCenterActivityContract;
import com.sk.xjwd.minehome.presenter.HelpCenterActivityPresenter;
import com.zyf.fwms.commonlibrary.http.Api;

public class HelpCenterActivity extends BaseActivity<HelpCenterActivityPresenter,ActivityHelpCenterBinding> implements HelpCenterActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
    }

    @Override
    protected void initView() {
        String type=getIntent().getStringExtra("type");
        if(type.equals("1")){
            setTitle("帮助中心");
            mPresenter.initRecyclerView(mBindingView.xRecyclerView, Api.helpcenterlist1);
        }else{
            setTitle("资讯中心");
            mPresenter.initRecyclerView(mBindingView.xRecyclerView, Api.helpcenterlist2);
        }

    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {

    }

}
