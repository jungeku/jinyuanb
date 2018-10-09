package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.account.activity.LoginActivity;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityUpdatePasswordBinding;
import com.sk.xjwd.minehome.contract.UpdatePasswordContract;
import com.sk.xjwd.minehome.presenter.UpdatePasswordActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import butterknife.OnClick;

/**
 * Created by mayn on 2018/9/1.
 */

public class UpdatePasswordActivity extends BaseActivity<UpdatePasswordActivityPresenter,ActivityUpdatePasswordBinding> implements UpdatePasswordContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
    }

    @Override
    protected void initView() {
        setTitle("修改密码");
        mBindingView.txtUpdatepswPhonenum.setText(MyApplication.getString("phone",""));
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.txt_updatepsw_code,R.id.txt_updatepsw_summit})
    public void onClick(View view){
        String phone=mBindingView.txtUpdatepswPhonenum.getText().toString();
        String code=mBindingView.edUppdatepswCode.getText().toString();
        String newPsw1=mBindingView.edUpdatepswNewpsw1.getText().toString();
        String newPs2=mBindingView.edUpdatepswNewpsw2.getText().toString();
        switch (view.getId()){
            case R.id.txt_updatepsw_code:
                if(CommonUtils.isEmpty(phone)){
                    CommonUtils.showToast(mContext,"手机号码不能为空！");
                }else{
                    mPresenter.getCode(mBindingView.txtUpdatepswCode,phone);
                }
                break;
            case R.id.txt_updatepsw_summit:
                if(TextUtils.isEmpty(phone)){
                    UIUtil.showToast("手机号码不能为空！");
                }else if(TextUtils.isEmpty(code)){
                    UIUtil.showToast("验证码不能为空！");
                }else if(TextUtils.isEmpty(newPsw1)){
                    UIUtil.showToast("密码不能为空！");
                }else if(TextUtils.isEmpty(newPs2)){
                    UIUtil.showToast("确认密码不能为空！");
                }else  if (newPsw1.length()<6||newPsw1.length()>12){
                    UIUtil.showToast("密码必须6-12位！");
                }else if(!newPsw1.equals(newPs2)){
                    UIUtil.showToast("前后两次密码不一致！");
                }else if(newPsw1.equals(MyApplication.getString("userPwd",""))) {
                    UIUtil.showToast("与原密码一样");
                }else {
                    mPresenter.postData(phone,code,newPsw1);
//                    if(mBindingView.chXieyi.isChecked()){
//                        mPresenter.postData(phone,code,pwd);
//                    }else{
//                        UIUtil.showToast("未同意协议");
//                    }
                }
                break;
        }
    }

    @Override
    public void activityfinish() {
        UIUtil.showToast("修改成功！");
        UIUtil.startActivity(LoginActivity.class,null);
        this.finish();
    }
}
