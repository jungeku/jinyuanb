package com.sk.xjwd.minehome.contract;

import android.view.View;

import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface LoanRecordActivityContract {
    interface View extends BaseView {

    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void   initRecyclerView(XRecyclerView xRecyclerView, android.view.View view,int tabpos);
    }
}
