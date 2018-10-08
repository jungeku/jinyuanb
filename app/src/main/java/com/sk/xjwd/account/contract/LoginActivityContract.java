package com.sk.xjwd.account.contract;


import android.widget.EditText;

import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface LoginActivityContract {
    interface View extends BaseView {

    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void doLogin(EditText phone,EditText psw);
    }

}
