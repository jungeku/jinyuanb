package com.sk.xjwd.authenhome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface SheBaoAuthActivityContract {
    interface View extends BaseView {
        void  initListener();
    }
    abstract class Presenter extends BasePresenter<View> {

    }

}
