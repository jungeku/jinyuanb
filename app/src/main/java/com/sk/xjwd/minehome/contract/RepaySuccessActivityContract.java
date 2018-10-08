package com.sk.xjwd.minehome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface RepaySuccessActivityContract {
    interface View extends BaseView {
        void initListener();
    }
    abstract class Presenter extends BasePresenter<View> {

    }

}
