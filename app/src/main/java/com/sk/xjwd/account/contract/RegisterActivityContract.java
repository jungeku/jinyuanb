package com.sk.xjwd.account.contract;

import android.widget.Button;

import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;


/**
 * Created by Administrator on 2017/7/7.
 */

public interface RegisterActivityContract {

    interface View extends BaseView {

    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void senCode(Button btncode, String phone);
        public abstract void postData(String phone,String code,String psw,int phoneTpye,String phoneBroad,String appversion,String appname,String lng,String lat,String address);
        public abstract void getregisteragreement();
    }
}
