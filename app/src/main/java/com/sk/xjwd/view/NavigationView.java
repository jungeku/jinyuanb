package com.sk.xjwd.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sk.xjwd.R;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

/**
 * 杭州融科网络
 * 刘宇飞创建 on 2017/6/1.
 * 描述：导航
 */

public class NavigationView extends RelativeLayout {

    private TextView tv_dse;
    private ImageView iv_top;

    public NavigationView(Context context) {
        super(context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        initView(context, attrs);
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        initView(context, attrs);

    }

    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.navigation_tab, this, true);
        tv_dse = (TextView) view.findViewById(R.id.tv_dse);
        iv_top = (ImageView) view.findViewById(R.id.iv_top);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigationView);

        String dse_name = a.getText(R.styleable.NavigationView_dse_name).toString();
        CommonUtils.setTextValue(tv_dse, dse_name);
        if (CommonUtils.isEmpty(dse_name)) {
            tv_dse.setVisibility(GONE);
            ViewGroup.LayoutParams layoutParams = iv_top.getLayoutParams();
            layoutParams.height=70;
            layoutParams.width=70;
            iv_top.setLayoutParams(layoutParams);
        }

        int drawable_src = a.getResourceId(R.styleable.NavigationView_drawable_src, -1);
        if (drawable_src > 0) {
            iv_top.setBackgroundResource(drawable_src);
        }

        boolean is_select = a.getBoolean(R.styleable.NavigationView_is_select, false);
        setSelected(is_select);
        a.recycle();
    }

    /**
     * 是否选中
     *
     * @param selected
     */
    public void setSelected(boolean selected) {
        iv_top.setSelected(selected);
        if (selected)
            tv_dse.setTextColor(CommonUtils.getColor(getContext(), R.color.public_orange));
        else
            tv_dse.setTextColor(CommonUtils.getColor(getContext(), R.color.color666666));
    }
}
