package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityAboutBinding;
import com.sk.xjwd.minehome.contract.AboutActivityContract;
import com.sk.xjwd.minehome.presenter.AboutActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class AboutActivity extends BaseActivity<AboutActivityPresenter,ActivityAboutBinding> implements AboutActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void initView() {
        setTitle("关于我们");
        mPresenter.getdata();
        initRxBus();
        setview();
        initListener();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {
        mBindingView.txtVersion.setText(UIUtil.getVersionName(mContext));
    }

    @OnClick(R.id.sv_feedback)
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.sv_feedback://用户反馈
                UIUtil.startActivity(FeedbackActivity.class,null);
                break;
           /* case R.id.sv_kefu:
                mPresenter.getqq();
                break;
            case R.id.sv_agreement:
                UIUtil.startActivity(ServiceXIeyiActivity.class,null);
                break;*/
        }
    }

    @Override
    public void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(2, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
                        setview();
                    }
                });
        addSubscription(subscribe);
    }

    public void setview() {
//        mBindingView.svKefu.setSettingValue(MyApplication.getString("servePhone",""));
 //       mBindingView.svWeixin.setSettingValue(MyApplication.getString("wexin",""));
    }
}
