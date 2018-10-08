package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityRentConfirmCodeBinding;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;


/**
 * 确认验证码
 * Created by Administrator on 2018/5/23.
 */

public class RentConfirmCodeActivity extends BaseActivity<RentComfirmCodePresenter, ActivityRentConfirmCodeBinding> {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_confirm_code);
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    protected void initView() {
        setTitle("验证码");
        String requestno = getIntent().getStringExtra("requestno");
        String type = getIntent().getStringExtra("type");
        String orderId = getIntent().getStringExtra("orderId");
        String needPayMoney = getIntent().getStringExtra("needPayMoney");
        String huanorxuqi = getIntent().getStringExtra("huanorxuqi");
        mBindingView.btnCommit.setText("确认支付（"+needPayMoney+")元");
        mBindingView.btnCommit.setOnClickListener(v -> {
            mPresenter.initWaitDialog();
            String code=mBindingView.edRentCode.getText().toString();
            if (CommonUtils.isEmpty(code)){
                UIUtil.showToast("验证码不能为空！");
            }else {
                mPresenter.commitCode(needPayMoney, type, orderId, requestno, mBindingView.edRentCode.getText().toString(),huanorxuqi);
            }

        });
    }


 /*   public void setOrderDetail(RentOrderShellResponse data, String alpayMoney) {

    }*/

    public void finishActivity() {
        finish();
    }
}
