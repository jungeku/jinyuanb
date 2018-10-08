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
 * 杭州融科网络
 * 刘宇飞创建 on 2017/6/1.
 * 描述：设置
 */

public class SettingActionView extends RelativeLayout {


    private ImageView iv_icon;
    private TextView tv_title;
    private ImageView iv_arrow_right;
    private TextView tv_line;

    public SettingActionView(Context context) {
        super(context);
    }

    public SettingActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        initView(context, attrs);
    }

    public SettingActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        initView(context, attrs);

    }

    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.setting_action_view, this, true);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_arrow_right = (ImageView) view.findViewById(R.id.iv_arrow_right);
        tv_line = (TextView) view.findViewById(R.id.tv_line);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingActionView);
        int src = a.getResourceId(R.styleable.SettingActionView_icon_src, -1);
        if(src>0) iv_icon.setBackgroundResource(src);

        String text = a.getText(R.styleable.SettingActionView_title_name).toString();
        CommonUtils.setTextValue(tv_title,text);

        boolean showArrow = a.getBoolean(R.styleable.SettingActionView_is_show_arrow, true);
        if(showArrow) iv_arrow_right.setVisibility(VISIBLE);
        else  iv_arrow_right.setVisibility(GONE);

        boolean showLine = a.getBoolean(R.styleable.SettingActionView_is_show_line, true);
        if(showLine) tv_line.setVisibility(VISIBLE);
        else  tv_line.setVisibility(GONE);

        a.recycle();
    }

    /**
     * 设置原色
     * @param color
     */
    public void setTextColor(int color){
        tv_title.setTextColor(CommonUtils.getColor(getContext(),color));
    }


}
