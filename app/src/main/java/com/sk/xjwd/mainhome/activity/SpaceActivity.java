package com.sk.xjwd.mainhome.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.sk.xjwd.R;
import com.sk.xjwd.checking.MainTabActivity;
import com.sk.xjwd.utils.UIUtil;

/**
 * Created by mayn on 2018/9/11.
 */

public class SpaceActivity extends FragmentActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //execute the task
                UIUtil.startActivity(MainActivity.class,null);
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
