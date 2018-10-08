package com.sk.xjwd.mainhome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface ApplyFragmentContact {
    interface View extends BaseView {
     void  initListener();
     void initRxBus();
     void reflsh();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void GetAuthState();
    }
}
