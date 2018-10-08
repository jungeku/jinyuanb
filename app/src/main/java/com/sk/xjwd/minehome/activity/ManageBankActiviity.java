package com.sk.xjwd.minehome.activity;

import android.os.Bundle;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityManageBinding;
import com.sk.xjwd.minehome.presenter.ManageBankActivityPresenter;

/**
 * Created by mayn on 2018/9/1.
 */

public class ManageBankActiviity extends BaseActivity<ManageBankActivityPresenter,ActivityManageBinding> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
    }

    @Override
    protected void initView() {
        mPresenter.getBankInfo();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    public void showBankInfo(){
        mBindingView.manageBankName.setText("");
        mBindingView.manageBankType.setText("");
        mBindingView.manageBankNum.setText("");
    }
}
