package com.sk.xjwd.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.sk.xjwd.R;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司：杭州融科网络科技
 * 刘宇飞 创建 on 2017/3/14.
 * 描述：
 */

public class ConfirmDialog extends Dialog {



    private Context context;
    private  boolean boo;
    public ConfirmDialog(Context context,boolean bool) {
        super(context, R.style.bottom_select_dialog);
        this.context = context;
        this.boo=bool;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_confirm, null);
        TextView te= (TextView) view.findViewById(R.id.dialog_content);
        if (boo==true){
            te.setText("订单申请成功，请等待处理！");
        }else {
            te.setText("订单申请失败！");
        }
        AutoUtils.auto(view);
        setContentView(view);
        UIUtil.FullScreen((Activity)context,this,0.8);
        ButterKnife.bind(this);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(false);
        initView();


    }

    private void initView() {

    }

    @OnClick({R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure://确定
                dismiss();
                UIUtil.startActivity(MainActivity.class,null);
                RxBus.getDefault().post(Constants.REQUESTID_0, 5);
                break;
        }
    }



}
