package com.sk.xjwd.mainhome.activity;

import android.os.Bundle;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityCouponBinding;
import com.sk.xjwd.minehome.contract.CouponActivityContract;
import com.sk.xjwd.minehome.presenter.CouponActivityPresenter;

public class CouponActivity extends BaseActivity<CouponActivityPresenter,ActivityCouponBinding> implements CouponActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
    }

    @Override
    protected void initView() {
        String coupontype=getIntent().getStringExtra("coupontype");
        String orderid=getIntent().getStringExtra("orderid");
        if(coupontype.equals("1")){
            setTitle("优惠券");
            mPresenter.initRecyclerView(mBindingView.xRecyclerView,coupontype,null);
        }else{
            setTitle("选择优惠券");
            mPresenter.initRecyclerView(mBindingView.xRecyclerView,coupontype,orderid);
        }

    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void isNotData(boolean boo) {
        if (boo){
            mBindingView.xRecyclerView.setVisibility(View.VISIBLE);
            mBindingView.llCouponNull.setVisibility(View.GONE);
        }else {
            mBindingView.xRecyclerView.setVisibility(View.GONE);
            mBindingView.llCouponNull.setVisibility(View.VISIBLE);
        }
    }
}
