package com.sk.xjwd.authenhome.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.authenhome.contract.ZhimaActivityContract;
import com.sk.xjwd.authenhome.presenter.ZhimaActivityPresenter;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityZhimaBinding;
import com.sk.xjwd.minehome.activity.ApplyorhuanActivity;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ZhimaActivity extends BaseActivity<ZhimaActivityPresenter, ActivityZhimaBinding> implements ZhimaActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhima);
        initListener();
    }

    @Override
    protected void initView() {
        setTitle("支付宝认证");
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {
        String type = getIntent().getStringExtra("type");
        if (type.equals("2")) {
            mPresenter.seleAliInfo();
        }
        mBindingView.btnZhimaShouquan.setOnClickListener(v -> {
            final String name = mBindingView.edID.getText().toString();
            final String pad = mBindingView.edPad.getText().toString();
            if (CommonUtils.isEmpty(name)) {
                UIUtil.showToast("账号不能为空！");
            } else if (CommonUtils.isEmpty(pad)) {
                UIUtil.showToast("密码不能为空！");
            } else {
                mPresenter.savaData(name, pad);

            }

        });
    }

    @Override
    public void finishActivity() {
//        finish();
        Map<String,String> map =new HashMap<>();
        map.put("renzheng","renzheng");
        UIUtil.startActivity(ApplyorhuanActivity.class,map);
    }

    @Override
    public void showInfo(String number, String psd) {
        mBindingView.edID.setText(number);
        mBindingView.edPad.setText(psd);
        mBindingView.edPad.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }


}
