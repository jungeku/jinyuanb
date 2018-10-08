package com.sk.xjwd.minehome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface PayPwdActivityContract {
    interface View extends BaseView {

    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void setpaypwd(String payPwd,String payPwdConfirm);
        public abstract void checkoldpaypwd(String payoldPwd);
    }

}
