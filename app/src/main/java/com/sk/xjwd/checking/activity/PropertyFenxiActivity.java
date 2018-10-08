package com.sk.xjwd.checking.activity;

import android.os.Bundle;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.checking.presenter.PropertyFenxiActivityPresenter;
import com.sk.xjwd.databinding.CheckingFragmentDetailBinding;

/**
 * Created by mayn on 2018/9/13.
 */

public class PropertyFenxiActivity extends BaseActivity<PropertyFenxiActivityPresenter,CheckingFragmentDetailBinding> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checking_fragment_detail);
    }

    @Override
    protected void initView() {
        mBindingView.imgDetailPie.setImageResource(R.mipmap.incomepie);
        mBindingView.imgDetailDetail.setImageResource(R.mipmap.incomedetail);
    }

    @Override
    protected void initPresenter() {
         mPresenter.setView(this);
    }
}
