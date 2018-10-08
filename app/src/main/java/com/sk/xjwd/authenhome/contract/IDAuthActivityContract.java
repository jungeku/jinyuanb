package com.sk.xjwd.authenhome.contract;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.base.BaseView;

import java.util.Map;

public interface IDAuthActivityContract {
    interface View extends BaseView {
        void initListener();
        void checkIdentitySuccess(String sessionId);
        void checkFaceSuccess();
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void getIDAuth(Map<String, String> map);
        public abstract void checkFace(String sessionId,String idPhoto,String facePhoto);
        public abstract void checkIdentity(String idNumber,String idName);
    }


}
