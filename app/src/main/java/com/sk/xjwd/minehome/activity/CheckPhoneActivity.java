package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityCheckPhoneBinding;
import com.sk.xjwd.minehome.contract.CheckPhoneActivityContract;
import com.sk.xjwd.minehome.presenter.CheckPhoneActivityPresenter;
import com.sk.xjwd.utils.UIUtil;

import butterknife.OnClick;

public class CheckPhoneActivity extends BaseActivity<CheckPhoneActivityPresenter,ActivityCheckPhoneBinding> implements CheckPhoneActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone);
    }

    @Override
    protected void initView() {
        setTitle("验证手机");
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.btn_check_phone_code,R.id.btn_find_pwd})
    public void OnClick(View view){
        String phone=mBindingView.edPhone.getText().toString();
        String code=mBindingView.ptCode.getPsw();
        switch (view.getId()){
            case R.id.btn_check_phone_code:
                if ( !UIUtil.isMobile(phone)) {
                    return;
                }else{
                    mPresenter.senCode(mBindingView.btnCheckPhoneCode,phone);
                }
                break;
            case R.id.btn_find_pwd:
                if ( !UIUtil.isMobile(phone)) {
                    return;
                }else if(TextUtils.isEmpty(code)){
                    UIUtil.showToast("验证码不能为空!");
                }else if(!MyApplication.getString("forgetPwdCode","").equals(code)){
                    UIUtil.showToast("验证码错误!"+MyApplication.getString("forgetPwdCode",""));
                }else{
                    UIUtil.startActivity(PayPwdActivity.class,null);
                }
                break;
        }
    }
}
