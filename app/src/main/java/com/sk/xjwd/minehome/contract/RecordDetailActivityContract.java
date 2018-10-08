package com.sk.xjwd.minehome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface RecordDetailActivityContract {
    interface View extends BaseView {
        void initListener();
        void initRxBus();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void ShowOrderDetail(String orderid);
    }

}
