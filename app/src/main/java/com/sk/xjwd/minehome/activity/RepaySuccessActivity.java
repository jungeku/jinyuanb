package com.sk.xjwd.minehome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityRepaySuccessBinding;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.minehome.contract.RepaySuccessActivityContract;
import com.sk.xjwd.minehome.presenter.RepaySuccessActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

public class RepaySuccessActivity extends BaseActivity<RepaySuccessActivityPresenter,ActivityRepaySuccessBinding> implements RepaySuccessActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_success);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("xuqiInfo");
        if(CommonUtils.isNotEmpty(stringExtra)){
            setTitle("续期成功");
            mBindingView.clHuanSuccess.tvHuanInfo.setText("恭喜您！续期成功");
            mBindingView.clHuanSuccess.btnLoanAgain.setText("返回");
        }else {
            setTitle("还款成功");
            mBindingView.clHuanSuccess.tvHuanInfo.setText("恭喜您！还款成功");
            mBindingView.clHuanSuccess.btnLoanAgain.setText("再次借款");
        }

        initListener();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {
        mBindingView.clHuanSuccess.btnLoanAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.startActivity(MainActivity.class,null);
              //  MyApplication.saveString("borroworpay","true");
                RxBus.getDefault().post(Constants.REQUESTID_0, 5);
            }
        });
    }
}
