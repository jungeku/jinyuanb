package com.sk.xjwd.account.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.account.contract.LoginActivityContract;
import com.sk.xjwd.account.presenter.LoginActivityPresenter;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityLoginBinding;
import com.sk.xjwd.utils.UIUtil;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginActivityPresenter,ActivityLoginBinding> implements LoginActivityContract.View {

    Boolean bShowPwd=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        UIUtil.fullScreen(this);
    }

    @Override
    protected void initView() {
       hideTitleBar();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.txt_login_forget,R.id.btn_login,R.id.img_show_pwd,R.id.img_login_back,R.id.login_reg})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_reg://注册
                UIUtil.startActivity(RegisterActivity.class,null);
                break;
            case R.id.txt_login_forget://忘记密码
                UIUtil.startActivity(ForgetPwdActivity.class,null);
                break;
            case R.id.img_login_back://返回
                finish();
                break;
            case R.id.btn_login://登录
                mPresenter.doLogin(mBindingView.etPhone, mBindingView.etPsw);
                break;
            case R.id.img_show_pwd:
                if(bShowPwd){
                    //否则隐藏密码
                    mBindingView.etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mBindingView.imgShowPwd.setImageDrawable(getResources().getDrawable(R.mipmap.hide_pwd));
                    bShowPwd=false;
                }else {
                    //如果选中，显示密码
                    mBindingView.etPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mBindingView.imgShowPwd.setImageDrawable(getResources().getDrawable(R.mipmap.show_pwd));
                    bShowPwd=true;
                }
                break;
        }
    }

}
