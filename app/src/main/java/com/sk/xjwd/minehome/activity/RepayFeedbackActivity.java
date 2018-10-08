package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityRepayFeedbackBinding;
import com.sk.xjwd.minehome.contract.FeedbackActivityContract;
import com.sk.xjwd.minehome.presenter.FeedbackActivityPresenter;

public class RepayFeedbackActivity extends BaseActivity<FeedbackActivityPresenter,ActivityRepayFeedbackBinding> implements FeedbackActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_feedback);
    }

    @Override
    protected void initView() {
        setTitle("还款反馈");
        initListener();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {
        mBindingView.btnRepayFeedbackCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBindingView.repayChoosephonto.getData()!=null && mBindingView.repayChoosephonto.getData().size()>0) {
                    mPresenter.postData(0, mBindingView.edRepayReason.getText().toString(), mBindingView.repayChoosephonto.getData(), null);
                }else {
                    mPresenter.commitrepayfeedback(mBindingView.edRepayReason.getText().toString(),null);
                }
                RepayFeedbackActivity.this.finish();
            }
        });
    }
}
