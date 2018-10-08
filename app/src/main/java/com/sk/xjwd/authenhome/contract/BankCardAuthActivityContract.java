package com.sk.xjwd.authenhome.contract;


import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface BankCardAuthActivityContract {
    interface View extends BaseView {
        void initListener();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void   initRecyclerView(XRecyclerView xRecyclerView);
        public abstract void postData(String name,String ID,String card,String phone);
    }

}
