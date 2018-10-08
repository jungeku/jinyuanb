package com.sk.xjwd.account.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.account.contract.ForgetPwdActivityContract;
import com.sk.xjwd.account.presenter.ForgetPwdActivityPresenter;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityForgetPwdBinding;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import butterknife.OnClick;

public class ForgetPwdActivity extends BaseActivity<ForgetPwdActivityPresenter,ActivityForgetPwdBinding> implements ForgetPwdActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
    }

    @Override
    protected void initView() {
        setTitle("忘记密码");
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.btn_forget_code,R.id.btn_find_pwd})
    public void onClink(View view){
        String phone=mBindingView.edForgetPhone.getText().toString();
        String code=mBindingView.edForgetCode.getText().toString();
        String pwd=mBindingView.edForgetNewPwd.getText().toString();
        String pwdok=mBindingView.edForgetOkPwd.getText().toString();
        switch (view.getId()){
            case R.id.btn_forget_code:
                if(CommonUtils.isEmpty(phone)){
                    CommonUtils.showToast(mContext,"手机号码不能为空！");
                }else{
                    mPresenter.senCode(mBindingView.btnForgetCode,mBindingView.edForgetPhone.getText().toString());
                }
                break;
            case R.id.btn_find_pwd:
                if(TextUtils.isEmpty(phone)){
                    UIUtil.showToast("手机号码不能为空！");
                }else if(TextUtils.isEmpty(code)){
                    UIUtil.showToast("验证码不能为空！");
                }else if(TextUtils.isEmpty(pwd)){
                    UIUtil.showToast("密码不能为空！");
                }else if(TextUtils.isEmpty(pwdok)){
                    UIUtil.showToast("确认密码不能为空！");
                }else if (pwd.length()<6||pwd.length()>12){
                    UIUtil.showToast("密码必须6-12位！");
                }else if(!pwd.equals(pwdok)){
                    UIUtil.showToast("前后两次密码不一致！");
                }else{
                    mPresenter.postData(phone,code,pwd);
                }
                break;
        }
    }

}
