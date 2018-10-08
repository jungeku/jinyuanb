package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityPayPwdUpdateBinding;
import com.sk.xjwd.minehome.contract.PayPwdActivityContract;
import com.sk.xjwd.minehome.presenter.PayPwdActivityPresenter;
import com.sk.xjwd.utils.UIUtil;

import butterknife.OnClick;

public class PayPwdUpdateActivity extends BaseActivity<PayPwdActivityPresenter,ActivityPayPwdUpdateBinding> implements PayPwdActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pwd_update);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.btn_update_pay_pwd,R.id.txt_forget_pay_pwd})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_update_pay_pwd:
                String pwd= mBindingView.againPaypswdPet.getText().toString();
                if(TextUtils.isEmpty(pwd)||pwd.length()<6){
                    UIUtil.showToast("交易密码不能小于6位");

                }else{
                    mPresenter.checkoldpaypwd(pwd);
                }
                break;
            case R.id.txt_forget_pay_pwd:
                UIUtil.startActivity(CheckPhoneActivity.class,null);
                break;
        }
    }
}
