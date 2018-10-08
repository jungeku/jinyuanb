package com.sk.xjwd.mainhome.contract;

import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface MessageActivityContract {
    interface View extends BaseView {

    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void   initRecyclerView(XRecyclerView xRecyclerView);
    }
}
