package com.sk.xjwd.minehome.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sk.xjwd.R;
import com.sk.xjwd.databinding.ItemPhotoChooseBinding;
import com.sk.xjwd.minehome.model.ChoosePhotoModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;
import com.zyf.fwms.commonlibrary.base.baseadapter.OnItemClickListener;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.ImgLoadUtil;

public class ChoosePhotoVRHodler extends BaseRecyclerViewHolder<ItemPhotoChooseBinding> {
    private final OnItemClickListener<BaseRecyclerModel> onlistener;

    public ChoosePhotoVRHodler(ViewGroup viewGroup, int layoutId, OnItemClickListener<BaseRecyclerModel> onlistener) {
        super(viewGroup, layoutId);
        this.onlistener=onlistener;
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerModel object, final int position) {
        ChoosePhotoModel choosePhotoModel= (ChoosePhotoModel) object;
        if(CommonUtils.isEmpty(choosePhotoModel.url)){
            binding.tvImg.setImageResource(R.mipmap.paizhao_shangchuantupian);
            binding.ivClose.setVisibility(View.GONE);
        }else {
            ImgLoadUtil.display(itemView.getContext(),binding.tvImg,choosePhotoModel.url);
            binding.ivClose.setVisibility(View.VISIBLE);
        }

        //设置高度
        ViewGroup.LayoutParams layoutParams = binding.tvImg.getLayoutParams();
        layoutParams.height= (AutoUtils.designWidth-20*4)/3;
        binding.tvImg.setLayoutParams(layoutParams);
        //设置间距
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position % 3 == 0) {
            params.setMargins(0,0,10,10);
        } else {
            params.setMargins(10,0,0,10);
        }
        itemView.setLayoutParams(params);

        AutoUtils.autoSize(binding.tvImg);
        binding.tvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlistener.onClick(binding.tvImg,object,position);
            }
        });
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlistener.onClick(binding.ivClose,object,position);
            }
        });

    }
}
