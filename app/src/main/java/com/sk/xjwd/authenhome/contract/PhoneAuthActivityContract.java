package com.sk.xjwd.authenhome.contract;


import android.widget.Button;
import android.widget.TextView;

import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface PhoneAuthActivityContract {
    interface View extends BaseView {

    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void senCode(TextView btncode, String phone, String pwd);
    }

}
