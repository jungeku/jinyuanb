package com.sk.xjwd.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sk.xjwd.R;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

public class SettingAuthenView extends RelativeLayout {


    private ImageView iv_icon;
    private TextView tv_title;
    private LabelView txt_authen_state;

    public SettingAuthenView(Context context) {
        super(context);
    }

    public SettingAuthenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        initView(context, attrs);
    }

    public SettingAuthenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        initView(context, attrs);

    }

    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_setting_authen, this, true);
        iv_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_title = (TextView) view.findViewById(R.id.txt_authen_name);
        txt_authen_state = (LabelView) view.findViewById(R.id.txt_authen_state);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingAuthenView);
        int src = a.getResourceId(R.styleable.SettingAuthenView_authen_src, -1);
        if(src>0) iv_icon.setBackgroundResource(src);

        String text = a.getText(R.styleable.SettingAuthenView_authen_name).toString();
        CommonUtils.setTextValue(tv_title,text);

        int mTextColor=a.getColor(R.styleable.SettingAuthenView_title_color, Color.parseColor("#333333"));
        tv_title.setTextColor(mTextColor);

        int mTexttopColor=a.getColor(R.styleable.SettingAuthenView_txt_top_color, Color.parseColor("#666666"));
        txt_authen_state.setTextColor(mTexttopColor);

        int mTexttopbgColor=a.getColor(R.styleable.SettingAuthenView_txt_top_bg, Color.parseColor("#e8e8e8"));
        txt_authen_state.setBgColor(mTexttopbgColor);

        a.recycle();
    }

}
