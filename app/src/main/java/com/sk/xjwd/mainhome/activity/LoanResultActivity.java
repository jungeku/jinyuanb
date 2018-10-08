package com.sk.xjwd.mainhome.activity;

import android.os.Bundle;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityLoanResultBinding;
import com.sk.xjwd.mainhome.presenter.LoanResultActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.ConfirmDialog;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import butterknife.OnClick;

/**
 * Created by mayn on 2018/9/3.
 */

public class LoanResultActivity extends BaseActivity<LoanResultActivityPresenter,ActivityLoanResultBinding> {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_result);

    }

    @Override
    protected void initView() {
      setTitle("借款结果");
      hideBackImg();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick(R.id.btn_loan_result_sure)
    public void onCLick(){
        UIUtil.startActivity(MainActivity.class,null);
        RxBus.getDefault().post(Constants.REQUESTID_0, 5);
    }



}
