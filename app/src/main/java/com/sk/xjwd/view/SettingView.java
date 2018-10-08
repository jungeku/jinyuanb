package com.sk.xjwd.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sk.xjwd.R;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;


/**
 * 公司：杭州融科网络科技
 * 刘宇飞 创建 on 2017/3/9.
 * 描述：设置view
 */

public class SettingView extends RelativeLayout {


    private TextView tvName;
    private TextView tvValue;
    private ImageView ivArrowRight;

    public SettingView(Context context) {
        super(context);

    }



    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        initView(context,attrs);
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        initView(context, attrs);

    }
    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_setting_action, this, true);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvValue = (TextView) view.findViewById(R.id.tv_value);
        ivArrowRight = (ImageView)view.findViewById(R.id.iv_arrow_right);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingView);

        String title = a.getText(R.styleable.SettingView_setting_title).toString();
        CommonUtils.setTextValue(tvName,title);
        String value = a.getText(R.styleable.SettingView_setting_value).toString();
        CommonUtils.setTextValue(tvValue,value);
        boolean isShowArrow=a.getBoolean(R.styleable.SettingView_arrow_is_show,true);
        if(isShowArrow){
            ivArrowRight.setVisibility(VISIBLE);
        }else ivArrowRight.setVisibility(GONE);
        a.recycle();
    }

    public void setSettingValue(String value){
        CommonUtils.setTextValue(tvValue,value);
    }
    public void setSettingValueColor(int value){
        tvValue.setTextColor(value);
    }
    public String getSettingValue(){
       return tvValue.getText().toString();
    }

}

