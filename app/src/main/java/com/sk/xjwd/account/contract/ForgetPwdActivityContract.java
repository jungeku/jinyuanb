package com.sk.xjwd.account.contract;


import android.widget.Button;

import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

/**
 * Created by Administrator on 2017/7/7.
 */

public interface ForgetPwdActivityContract {

    interface View extends BaseView {

    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void senCode(Button btncode, String phone);
        public abstract void postData(String phone,String code,String psw);
    }
}
