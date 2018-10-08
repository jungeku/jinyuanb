package com.sk.xjwd.minehome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface RenewalDebitActivityContract {
    interface View extends BaseView {
        void initListener();
        void alipay(String str);

        void aliZhifu(String s);
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void getPayInterfaceMsg();
        public abstract void checkpaypwd(String payPwd);
    }

}
