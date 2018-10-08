package com.sk.xjwd.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司：杭州融科网络科技
 * 刘宇飞 创建 on 2017/3/14.
 * 描述：
 */

public class TiDialog extends Dialog {



    private Context context;
    private  TextView te;
    private String mmsg="";
    public TiDialog(Context context) {
        super(context, R.style.bottom_select_dialog);
        this.context = context;
    }
    public TiDialog(Context context,String msg) {
        super(context, R.style.bottom_select_dialog);
        this.context = context;
        this.mmsg=msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_ti, null);
         te= (TextView) view.findViewById(R.id.tv_test);

        AutoUtils.auto(view);
        setContentView(view);
        UIUtil.FullScreen((Activity)context,this,0.8);
        ButterKnife.bind(this);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(false);
        if(mmsg!=null && mmsg.length()>1) te.setText(mmsg);
        else initView();



    }

    private void initView() {
        HttpUtil util = new HttpUtil(context);
        util.setUrl(Api.getFailMsg);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", result + "是否借款");
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject data = object.getJSONObject("data");
                        String text = data.getString("text");
                        te.setText(text);
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    @OnClick({R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure://确定
                dismiss();
                break;
        }
    }



}
