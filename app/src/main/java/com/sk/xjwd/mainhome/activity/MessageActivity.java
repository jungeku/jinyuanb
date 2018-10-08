package com.sk.xjwd.mainhome.activity;

import android.content.Intent;
import android.os.Bundle;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityMessageBinding;
import com.sk.xjwd.mainhome.contract.MessageActivityContract;
import com.sk.xjwd.mainhome.presenter.MessageActivityPresenter;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import rx.Subscription;
import rx.functions.Action1;

public class MessageActivity extends BaseActivity<MessageActivityPresenter,ActivityMessageBinding> implements MessageActivityContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        int dian = intent.getIntExtra("dian",0);

        /*if (dian == 9) {
            Log.i("errrerrr", "initView: ");
            RxBus.getDefault().post(Constants.REQUESTID_0, 99);
        }*/
    }

    @Override
    protected void initView() {

        initRxBus();
        setTitle("消息中心");
        mPresenter.initRecyclerView(mBindingView.xRecyclerView);
        mPresenter.getpushmessagelist();
    }

    public void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(121, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {

                        if(type==66){
                            RxBus.getDefault().post(Constants.REQUESTID_0, 99);
                        }
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }
}
