package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.text.Html;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityHelpDetailBinding;
import com.sk.xjwd.minehome.contract.HelpDetailActivityContract;
import com.sk.xjwd.minehome.presenter.HelpDetailActivityPresenter;

public class HelpDetailActivity extends BaseActivity<HelpDetailActivityPresenter,ActivityHelpDetailBinding> implements HelpDetailActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_detail);
    }

    @Override
    protected void initView() {
        String title=getIntent().getStringExtra("title");
        String content=getIntent().getStringExtra("content");
        setTitle(title);
        mBindingView.txtContent.setText(Html.fromHtml(content));
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {

    }
}
