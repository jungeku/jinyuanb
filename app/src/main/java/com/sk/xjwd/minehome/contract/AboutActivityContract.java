package com.sk.xjwd.minehome.contract;


import android.widget.TextView;

import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface AboutActivityContract {
    interface View extends BaseView {
        void initListener();
        void initRxBus();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void  getdata();
        public abstract void getaboutusdetaul(TextView txt_detail);
    }

}
