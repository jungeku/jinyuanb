package com.sk.xjwd.minehome.holder;

import android.view.ViewGroup;

import com.sk.xjwd.databinding.ItemBankcardBinding;
import com.sk.xjwd.minehome.model.SupportBankcardModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;

public class BankcardItemHodler extends BaseRecyclerViewHolder<ItemBankcardBinding> {

    public BankcardItemHodler(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerModel object, int position) {
        SupportBankcardModel model= (SupportBankcardModel) object;
        binding.txtBankname.setText(model.bankcardname);
    }

}
