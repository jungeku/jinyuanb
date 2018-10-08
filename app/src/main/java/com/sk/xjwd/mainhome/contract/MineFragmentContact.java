package com.sk.xjwd.mainhome.contract;

import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

public interface MineFragmentContact {
    interface View extends BaseView {
     void  initListener();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void  payPwdIsExist();
    }
}
