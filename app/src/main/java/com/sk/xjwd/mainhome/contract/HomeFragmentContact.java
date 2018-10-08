package com.sk.xjwd.mainhome.contract;


import android.widget.TextView;

import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;
import com.sk.xjwd.view.AutoScrollTextView;
import com.youth.banner.Banner;

public interface HomeFragmentContact {
    interface View extends BaseView {
        void  initListener();
         void  initRxBus();
         void  getInfo();
         void reflsh();
         void showDialog(String content);

        void geetInto();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void showScrolltext(AutoScrollTextView autoText);
        public abstract void getInfo(String userMoney,String limitDays);
        public abstract void userIsBorrow(String userMoney,String limitDays);
        public abstract void userFirstPageType();
        public abstract void showMoneyDialog(TextView txtmoney);
        public abstract  void getBannerlist(final Banner banner);
        public abstract void payPwdIsExist(boolean boo);
    }
}
