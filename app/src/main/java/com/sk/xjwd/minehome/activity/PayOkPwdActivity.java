package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityPayOkPwdBinding;
import com.sk.xjwd.minehome.contract.PayPwdActivityContract;
import com.sk.xjwd.minehome.presenter.PayPwdActivityPresenter;
import com.sk.xjwd.utils.UIUtil;

import butterknife.OnClick;

public class PayOkPwdActivity extends BaseActivity<PayPwdActivityPresenter,ActivityPayOkPwdBinding> implements PayPwdActivityContract.View{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ok_pwd);
    }

    @Override
    protected void initView() {
        setTitle("设置支付密码");
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.btn_set_pwd_ok})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_set_pwd_ok:
                String pwd=getIntent().getStringExtra("pwd");
                String pwdok= mBindingView.againPaypswdPet.getText().toString();
               if(pwd.length()<6){
                    UIUtil.showToast("交易密码不能小于6位");
                }else if(!pwd.equals(pwdok)){
                    UIUtil.showToast("前后两次密码不一致！");
                }else {
                    mPresenter.setpaypwd(pwd,pwdok);
                }
                break;
        }
    }
}
