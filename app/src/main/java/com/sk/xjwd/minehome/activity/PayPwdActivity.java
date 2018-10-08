package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityPayPwdBinding;
import com.sk.xjwd.minehome.contract.PayPwdActivityContract;
import com.sk.xjwd.minehome.presenter.PayPwdActivityPresenter;
import com.sk.xjwd.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

public class PayPwdActivity extends BaseActivity<PayPwdActivityPresenter,ActivityPayPwdBinding> implements PayPwdActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pwd);
    }

    @Override
    protected void initView() {
        setTitle("设置支付密码");
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.btn_set_pwd_next})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_set_pwd_next:
                String pwd= mBindingView.againPaypswdPet.getText().toString();
             if(TextUtils.isEmpty(pwd)||pwd.length()<6){
                    UIUtil.showToast("交易密码不能小于6位");
                }else{
                    Map<String,String> map=new HashMap<>();
                    map.put("pwd",pwd);
                    UIUtil.startActivity(PayOkPwdActivity.class,map);
                }
                break;
        }
    }
}
