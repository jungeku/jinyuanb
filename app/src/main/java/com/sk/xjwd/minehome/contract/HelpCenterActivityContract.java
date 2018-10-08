package com.sk.xjwd.minehome.contract;


import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface HelpCenterActivityContract {
    interface View extends BaseView {
        void initListener();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void   initRecyclerView(XRecyclerView xRecyclerView,String url);
    }

}
