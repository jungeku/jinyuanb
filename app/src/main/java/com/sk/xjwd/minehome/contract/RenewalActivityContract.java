package com.sk.xjwd.minehome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface RenewalActivityContract {
    interface View extends BaseView {
        void initListener();
         void alipay(String str);
    }
    abstract class Presenter extends BasePresenter<View> {

    }

}
