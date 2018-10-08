package com.sk.xjwd.authenhome.activity;

import android.os.Bundle;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.authenhome.presenter.BankCardAuthActivityPresenter;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityBankCardAuthBinding;
import com.sk.xjwd.utils.AssetsBankInfo;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import butterknife.OnClick;

public class BankCardAuthActivity extends BaseActivity<BankCardAuthActivityPresenter, ActivityBankCardAuthBinding> {
    String type;
    String existingBankNum;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_auth);
    }

    @Override
    protected void initView() {

        type = getIntent().getStringExtra("BankCardAuthType");
        if (type.equals("upteBankCard")) {
            mPresenter.selectUserInfo();
            setTitle("修改银行卡");

        }else {
            setTitle("银行卡认证");
        }
        //    mPresenter.getBankAuthInfo();
        //   mPresenter.getVerificationCode();
        mBindingView.edCard.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    String nameOfBank = AssetsBankInfo.getNameOfBank(mContext, mBindingView.edCard.getText().toString());//获取银行卡的信息
                    if (!CommonUtils.isEmpty(nameOfBank)) {

                        mBindingView.txtBankName.setText(nameOfBank);
                    } else {
                    }
                }
            }
        });
    }


    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.btn_bankcard_auth,R.id.btn_sumbit})
    public void OnClick(View view) {
        String name = mBindingView.edName.getText().toString();
        String ID = mBindingView.edID.getText().toString();
        String bankcard = mBindingView.edCard.getText().toString();
        String phone = mBindingView.edPhone.getText().toString();
        String code = mBindingView.edCode.getText().toString();
        String bankName=mBindingView.txtBankName.getText().toString();
        switch (view.getId()) {
            case R.id.btn_bankcard_auth:
                if (type.equals("upteBankCard")){
                    if (bankcard.equals(existingBankNum)){
                        UIUtil.showToast("资料未修改!");
                        return;
                    }
                }
                    mPresenter.getBankAuthCode(name, ID, bankcard, phone,mBindingView.btnBankcardAuth);

                break;
                case R.id.btn_sumbit:
                    if (CommonUtils.isEmpty(code)) {
                        UIUtil.showToast("验证码不能为空！");
                    } else if(!mBindingView.checkXieyi.isChecked()) {
                        UIUtil.showToast("需要勾选协议");
                    }else {
                            isEnabled(false);
                            mBindingView.btnSumbit.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_but_summit_gray));
                            mPresenter.commitBankAuth(name, ID, bankcard, phone,bankName, code);
                        }
                    break;
        }
    }


    public void authSuccess() {
        UIUtil.showToast("认证成功！");
        BankCardAuthActivity.this.finish();
    }

    public void isEnabled(Boolean boo) {
        if (boo) {
            mBindingView.btnSumbit.setEnabled(true);
        } else {
            mBindingView.btnSumbit.setEnabled(false);
        }
    }

    public void showBankCardInfo( String name, String identityNum,String bankNum,String phone) {
        mBindingView.edName.setText(name);
        mBindingView.edID.setText(identityNum);
        mBindingView.edCard.setText(bankNum);
        mBindingView.edPhone.setText(phone);
        String nameOfBank = AssetsBankInfo.getNameOfBank(mContext, bankNum);//获取银行卡的信息
        if (!CommonUtils.isEmpty(nameOfBank)) {
            mBindingView.rlBankcard.setVisibility(View.VISIBLE);
            mBindingView.txtBankName.setText(nameOfBank);
        } else {
            mBindingView.rlBankcard.setVisibility(View.GONE);
        }
        existingBankNum=bankNum;
    }

}
