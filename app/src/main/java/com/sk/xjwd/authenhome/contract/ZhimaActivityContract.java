package com.sk.xjwd.authenhome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface ZhimaActivityContract {
    interface View extends BaseView {
        void  initListener();
        void finishActivity();
        void showInfo(String number,String psd);
    }
    abstract class Presenter extends BasePresenter<View> {

    }

}
