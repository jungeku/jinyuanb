package com.sk.xjwd.minehome.holder;

import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;

import com.sk.xjwd.databinding.ItemCouponBinding;
import com.sk.xjwd.databinding.ItemCouponNewBinding;
import com.sk.xjwd.minehome.model.CouponModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;

public class CouponItemHodler extends BaseRecyclerViewHolder<ItemCouponNewBinding> {

    public CouponItemHodler(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerModel object, int position) {
        CouponModel model= (CouponModel) object;
//        binding.setCoupon(model);
        binding.txtItemCouponNewDuringtime.setText("有效期至"+model.limitTime);
        binding.txtItemCouponNewMoney.setText("¥"+model.saveMoney);
//        binding.rlItemCouponNew.setBackgroundDrawable();=;
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       if(position==0){
           params.setMargins(20,20,20,20);
       }else{
           params.setMargins(20,0,20,20);
       }
        itemView.setLayoutParams(params);
        AutoUtils.autoMargin(itemView);
    }

}
