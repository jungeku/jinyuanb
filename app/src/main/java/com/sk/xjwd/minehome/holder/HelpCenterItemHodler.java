package com.sk.xjwd.minehome.holder;

import android.view.ViewGroup;

import com.sk.xjwd.databinding.ItemHelpCenterBinding;
import com.sk.xjwd.minehome.model.HelpCenterModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;

public class HelpCenterItemHodler extends BaseRecyclerViewHolder<ItemHelpCenterBinding> {

    public HelpCenterItemHodler(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerModel object, int position) {
       HelpCenterModel model= (HelpCenterModel) object;
        binding.txtTitle.setText(model.title);
//        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//       if(position==0){
//           params.setMargins(0,20,0,20);
//       }else{
//           params.setMargins(0,0,0,20);
//       }
//        itemView.setLayoutParams(params);
//        AutoUtils.autoMargin(itemView);
    }

}
