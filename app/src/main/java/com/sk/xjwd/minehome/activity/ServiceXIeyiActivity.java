package com.sk.xjwd.minehome.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityServicexieyiBinding;
import com.sk.xjwd.minehome.presenter.ServiceXIeyiActivityPresenter;

import butterknife.OnClick;

/**
 * Created by mayn on 2018/9/15.
 */

public class ServiceXIeyiActivity extends BaseActivity<ServiceXIeyiActivityPresenter,ActivityServicexieyiBinding> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicexieyi);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.sv_yinsi,R.id.sv_shouquan})
    void click(View view){
        switch (view.getId()){
            case R.id.sv_yinsi:
                if(MyApplication.getString("apply_agreementUrl","")!=null) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(MyApplication.getString("apply_agreementUrl", "")));
                startActivity(intent1);
            }
                break;
            case R.id.sv_shouquan:
                if (MyApplication.getString("apply_agreementTwoUrl","")!=null) {
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(MyApplication.getString("apply_agreementTwoUrl", "")));
                    startActivity(intent2);
                }
                break;
        }
    }
}
