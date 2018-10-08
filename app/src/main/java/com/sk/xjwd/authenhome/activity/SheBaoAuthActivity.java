package com.sk.xjwd.authenhome.activity;

import android.os.Bundle;

import com.sk.xjwd.R;
import com.sk.xjwd.authenhome.contract.SheBaoAuthActivityContract;
import com.sk.xjwd.authenhome.presenter.SheBaoAuthActivityPresenter;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivitySheBaoAuthBinding;



public class SheBaoAuthActivity extends BaseActivity<SheBaoAuthActivityPresenter,ActivitySheBaoAuthBinding> implements SheBaoAuthActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_bao_auth);
        //对导航栏的返回按钮，导航栏背景，导航栏title进行设置（可选操作）

    }

    @Override
    protected void initView() {
        String type=getIntent().getStringExtra("type");
        if(type.equals("1")){
            setTitle("社保认证");
        }else{
            setTitle("公积金认证");
        }
        initListener();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {

    }
}
