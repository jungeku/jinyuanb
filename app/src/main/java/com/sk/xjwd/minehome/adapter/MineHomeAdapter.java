package com.sk.xjwd.minehome.adapter;

import android.view.ViewGroup;

import com.sk.xjwd.R;
import com.sk.xjwd.checking.holder.CheckingAccountGriditemHolder;
import com.sk.xjwd.checking.holder.CheckingAccountListitemHolder;
import com.sk.xjwd.minehome.holder.BankcardItemHodler;
import com.sk.xjwd.minehome.holder.ChoosePhotoVRHodler;
import com.sk.xjwd.minehome.holder.CouponItemHodler;
import com.sk.xjwd.minehome.holder.HelpCenterItemHodler;
import com.sk.xjwd.minehome.holder.LoanRecordItemHodler;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewAdapter;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;
import com.zyf.fwms.commonlibrary.base.baseadapter.OnItemClickListener;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MineHomeAdapter extends BaseRecyclerViewAdapter {
    public static final int LOAN_RECORD_ITEM=1;//借款记录
    public static final int COUPON_ITEM=2;//优惠券
    public static final int BANKCARD_ITEM=3;//支持银行卡
    public static final int CHOOOSE_PHOTO_ITME =4;//多选图片
    public static final int HELP_CENTER_ITME =5;//帮助中心列表
    public static final int CHECKING_ACCOUNT_GRID =6;//审核中界面添加支出收入gridview
    public static final int CHECKING_ACCOUNT_LIST =7;//审核中界面添加支出收入listview

    private OnItemClickListener<BaseRecyclerModel> onlistener;

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case LOAN_RECORD_ITEM://借款记录
                return new LoanRecordItemHodler(parent, R.layout.item_loan_record);
            case COUPON_ITEM://优惠券
                return new CouponItemHodler(parent, R.layout.item_coupon_new);
            case BANKCARD_ITEM://支持银行卡
                return new BankcardItemHodler(parent, R.layout.item_bankcard);
            case CHOOOSE_PHOTO_ITME://多选图片
                return new ChoosePhotoVRHodler(parent, R.layout.item_photo_choose,onlistener);
            case HELP_CENTER_ITME://帮助中心
                return new HelpCenterItemHodler(parent, R.layout.item_help_center);
            case CHECKING_ACCOUNT_GRID://审核中界面添加支出收入gridview
                return new CheckingAccountGriditemHolder(parent, R.layout.checking_account_item, onlistener);
            case CHECKING_ACCOUNT_LIST://审核中界面添加支出收入listview
                return new CheckingAccountListitemHolder(parent, R.layout.checking_account_list_item);
        }
       return null;
    }

    public void setOnClickListener(OnItemClickListener<BaseRecyclerModel> listener) {
        this.onlistener=listener;
    }
}
