package com.sk.xjwd.mainhome.holder;

import android.support.v7.widget.LinearLayoutCompat;
import android.text.Html;
import android.view.ViewGroup;

import com.sk.xjwd.databinding.ItemMessageBinding;
import com.sk.xjwd.mainhome.model.MessageModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

public class MessageItemHodler extends BaseRecyclerViewHolder<ItemMessageBinding> {

    public MessageItemHodler(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerModel object, int position) {
        MessageModel model= (MessageModel) object;
        binding.txtTitle.setText(model.title);
        binding.messageContent.setText(Html.fromHtml(model.content));
        binding.messageTime.setText(model.time);
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
