package com.sk.xjwd.mainhome.contract;

import com.sk.xjwd.base.BaseFragment;
import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;
import com.sk.xjwd.view.NavigationView;

public interface MainActivityContract {
    interface View extends BaseView {
        void initFragment();
        void changeTab(NavigationView newTab);
        void changeFragment(BaseFragment newFragment);

        void initgengxin();
    }
    abstract class Presenter extends BasePresenter<View> {

    }
}
