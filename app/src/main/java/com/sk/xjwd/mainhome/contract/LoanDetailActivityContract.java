package com.sk.xjwd.mainhome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface LoanDetailActivityContract {
    interface View extends BaseView {
        void initListener();
        void initRxBus();
        void isTrue();
        void isFlase();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void showorder(String borrowMoney, String limitDays);
        public abstract void getSignMsg();
        public abstract void getSignPass();
    }

}
