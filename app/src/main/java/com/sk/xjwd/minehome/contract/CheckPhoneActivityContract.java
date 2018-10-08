package com.sk.xjwd.minehome.contract;


import android.widget.Button;

import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface CheckPhoneActivityContract {
    interface View extends BaseView {

    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void senCode(Button btncode, String phone);
    }

}
