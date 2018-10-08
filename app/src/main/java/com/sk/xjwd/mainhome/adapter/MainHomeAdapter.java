package com.sk.xjwd.mainhome.adapter;

import android.view.ViewGroup;
import com.sk.xjwd.R;
import com.sk.xjwd.mainhome.holder.MessageItemHodler;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewAdapter;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MainHomeAdapter extends BaseRecyclerViewAdapter {
    public static final int MESSAGE_CENTER_ITEM=1;//消息中心

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case MESSAGE_CENTER_ITEM://消息中心
                return new MessageItemHodler(parent, R.layout.item_message);
        }
       return null;
    }
}
