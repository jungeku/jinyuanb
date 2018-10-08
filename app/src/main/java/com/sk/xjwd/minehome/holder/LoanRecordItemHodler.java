package com.sk.xjwd.minehome.holder;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;

import com.sk.xjwd.databinding.ItemLoanRecordBinding;
import com.sk.xjwd.minehome.model.OrderModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;

public class LoanRecordItemHodler extends BaseRecyclerViewHolder<ItemLoanRecordBinding> {

    public LoanRecordItemHodler(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerModel object, int position) {
        OrderModel model= (OrderModel) object;
        binding.txtOrdernumber.setText("借款编号:"+model.orderNumber);
        binding.txtBorrowMoney.setText("￥"+model.borrowMoney);
        binding.txtNeedPayMoney.setText("￥"+model.needPayMoney);
        binding.txtLimitDays.setText(model.limitDays+"");
        binding.orderDate.setText("借款日期："+model.gmtDatetime);
         switch (model.orderStatus){
             case 0://未申请
                 binding.orderState.setText("未申请");
                 break;
             case 1://审核中
                 binding.orderState.setText("审核中");
                 binding.orderState.setTextColor(Color.parseColor("#FE960F"));
                 break;
            case 2://待打款
                 binding.orderState.setText("待打款");
                 break;
             case 3://待还款
                 binding.orderState.setText("待还款");
                 binding.orderState.setTextColor(Color.parseColor("#333333"));
                 break;
             case 4://容限期中
                 binding.orderState.setText("容限期中");
                 break;
             case 5://已逾期
                 binding.orderState.setText("已逾期");
                 binding.orderState.setTextColor(Color.parseColor("#FD5A5E"));
                 break;
             case 6://已还款
                 binding.orderState.setText("已还款");
                 binding.orderState.setTextColor(Color.parseColor("#B3B3B3"));
                 break;
             case 7://审核失败
                 binding.orderState.setText("未通过");
                 binding.orderState.setTextColor(Color.parseColor("#FD5A5E"));
                 break;
             case 8://坏账
                 binding.orderState.setText("坏账");
                 break;
             case 9://放款中
                 binding.orderState.setText("放款中");
                 break;

         }
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       if(position==0){
           params.setMargins(0,20,0,20);
       }else{
           params.setMargins(0,0,0,20);
       }
        itemView.setLayoutParams(params);
        AutoUtils.autoMargin(itemView);
    }

}
