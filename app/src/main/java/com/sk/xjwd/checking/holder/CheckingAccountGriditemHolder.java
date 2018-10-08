package com.sk.xjwd.checking.holder;

import android.view.View;
import android.view.ViewGroup;

import com.sk.xjwd.databinding.CheckingAccountItemBinding;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;
import com.zyf.fwms.commonlibrary.base.baseadapter.OnItemClickListener;

/**
 * Created by mayn on 2018/9/12.
 */

public class CheckingAccountGriditemHolder extends BaseRecyclerViewHolder<CheckingAccountItemBinding> {
    private final OnItemClickListener<BaseRecyclerModel> onlistener;
    String[] unClickicons={};
    String[] clickedicons={};
    String[] names={};
    private boolean isClicked=false;
    public static boolean isIncome=false;
    public static AccountGridModel expandmodel=new AccountGridModel();
    public static AccountGridModel incomemodel=new AccountGridModel();


    public CheckingAccountGriditemHolder(ViewGroup viewGroup, int layoutId, OnItemClickListener<BaseRecyclerModel> onlistener) {
        super(viewGroup, layoutId);
        this.onlistener = onlistener;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerModel object, int position) {
        if(isIncome){
            incomemodel=(AccountGridModel) object;
            binding.imgIcon.setImageResource(incomemodel.unClickicon);
            binding.txtName.setText(incomemodel.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isClicked){
                        binding.imgIcon.setImageResource(incomemodel.Clickedicon);
                        isClicked=true;
                        incomemodel.isChecked=true;
                    }else {
                        binding.imgIcon.setImageResource(incomemodel.unClickicon);
                        isClicked=false;
                        incomemodel.isChecked=false;
                    }
                }
            });
        }else {
            expandmodel=(AccountGridModel) object;
            binding.imgIcon.setImageResource(expandmodel.unClickicon);
            binding.txtName.setText(expandmodel.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                onlistener.onClick(binding.imgIcon,object,position);
                    if(!isClicked){
                        binding.imgIcon.setImageResource(expandmodel.Clickedicon);
                        isClicked=true;
                        expandmodel.isChecked=true;
                    }else {
                        binding.imgIcon.setImageResource(expandmodel.unClickicon);
                        isClicked=false;
                        expandmodel.isChecked=false;
                    }
                }
            });
        }

    }

}
