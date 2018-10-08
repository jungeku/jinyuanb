package com.sk.xjwd.minehome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

import java.util.List;

public interface FeedbackActivityContract {
    interface View extends BaseView {
        void initListener();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void postData(int type, String content, List<String> list, String phone);
    }

}
