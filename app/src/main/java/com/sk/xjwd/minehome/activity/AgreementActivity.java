package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityAgreementBinding;
import com.sk.xjwd.minehome.contract.AgreementActivityContract;
import com.sk.xjwd.minehome.presenter.AgreementActivityPresenter;

public class AgreementActivity extends BaseActivity<AgreementActivityPresenter,ActivityAgreementBinding> implements AgreementActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
    }

    @Override
    protected void initView() {
        String content=getIntent().getStringExtra("content");
        setTitle("用户注册协议");
        if(!TextUtils.isEmpty(content)){
            mBindingView.txtRegisterAgreement.setText(Html.fromHtml(content));
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }
}
