package com.sk.xjwd.mainhome.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.account.activity.LoginActivity;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        pass();
        /**
         * millisInFuture:从开始调用start()到倒计时完成并onFinish()方法被调用的毫秒数
         * countDownInterval:接收onTick(long)回调的间隔时间
         */
        LinearLayout linearLayout =(LinearLayout) findViewById(R.id.welcome_image);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash);
        linearLayout.startAnimation(animation);
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if(MyApplication.getBoolean("islogin",false)){//不存在这个偏好设置是返回false
                    UIUtil.startActivity(MainActivity.class,null);
                }else {
                    UIUtil.startActivity(MainActivity.class,null);
                }
//                UIUtil.startActivity(MainActivity.class,null);
                SplashActivity.this.finish();
            }
        }.start();
    }

    public void pass(){
        String action = getIntent().getAction();
        if(Intent.ACTION_VIEW.equals(action)){
            RxBus.getDefault().post(Constants.REQUESTID_1,3);
        }
    }
}
