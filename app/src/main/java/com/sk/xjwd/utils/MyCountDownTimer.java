package com.sk.xjwd.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.sk.xjwd.minehome.contract.UpdatePasswordContract;

/**
 * Created by mayn on 2018/9/19.
 */

public class MyCountDownTimer extends CountDownTimer {
    public View view;
    public MyCountDownTimer(long millisInFuture, long countDownInterval,View mview) {
        super(millisInFuture, countDownInterval);
        this.view=mview;
    }

    @Override
    public void onTick(long l) {

    }

    @Override
    public void onFinish() {
//        (TextView)view.("获取验证码");
//        view.setEnabled(true);

    }
}
