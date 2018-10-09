package com.sk.xjwd.minehome.contract;

import android.widget.Button;
import android.widget.TextView;

import com.sk.xjwd.account.contract.RegisterActivityContract;
import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

/**
 * Created by mayn on 2018/9/1.
 */

public interface UpdatePasswordContract {

    interface View extends BaseView {

        void activityfinish();
    }
    abstract class Presenter extends BasePresenter<UpdatePasswordContract.View> {
        public abstract void getCode(TextView textView, String phone);
        public abstract void postData(String phone,String code,String psw);
    }
}
