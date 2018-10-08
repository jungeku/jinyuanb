package com.sk.xjwd.authenhome.contract;


import android.content.Context;
import android.widget.TextView;

import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BaseInfoActivityContract {
    interface View extends BaseView {
        void commitBasicAuth();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void postData(Map<String,String> map);
        public abstract void showmarryDialog(Context mContext,TextView txtmarry);
        public abstract void showstudyDialog(Context mContext,TextView txtstudy);
        public abstract void showRelationDialog(Context mContext,TextView txtRelation);
        public abstract void showNeedAddressDialog(TextView txtprovince);
        public abstract void postAllContacts(List<HashMap<String,String>> list);
    }

}
