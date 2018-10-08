package com.sk.xjwd.checking.holder;

import android.view.ViewGroup;

import com.sk.xjwd.R;
import com.sk.xjwd.databinding.CheckingAccountListItemBinding;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;

/**
 * Created by mayn on 2018/9/12.
 */

public class CheckingAccountListitemHolder extends BaseRecyclerViewHolder<CheckingAccountListItemBinding> {

//    int[] imgs={R.mipmap.pay_cash,R.mipmap.pay_wechat,R.mipmap.checking_pay_zhifubao};
//    String[] names={"现金","微信","支付宝"};
//    String[] mongey={"6,500","5,500","4,500"};
    public CheckingAccountListitemHolder(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerModel object, int position) {
        AccountListModel model= (AccountListModel) object;
       binding.imgIcon.setImageResource(model.icon);
       binding.txtName.setText(model.name);
       binding.txtMoney.setText(model.money);
    }
}
