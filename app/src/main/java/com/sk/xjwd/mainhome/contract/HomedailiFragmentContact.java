package com.sk.xjwd.mainhome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;
import com.youth.banner.Banner;

public interface HomedailiFragmentContact {
    interface View extends BaseView {
        void  initListener();
        void  initRxBus();
    }
    abstract class Presenter extends BasePresenter<HomedailiFragmentContact.View> {
        public abstract  void getBannerlist(final Banner banner);
     /*  public abstract void showScrolltext(AutoScrollTextView autoText);
     public abstract void getInfo(String userMoney,String limitDays);
        public abstract void userIsBorrow(String userMoney,String limitDays);
        public abstract void userFirstPageType();
        public abstract void showMoneyDialog(TextView txtmoney);
        public abstract  void getBannerlist(final Banner banner);
        public abstract void payPwdIsExist();*/
    }
}
