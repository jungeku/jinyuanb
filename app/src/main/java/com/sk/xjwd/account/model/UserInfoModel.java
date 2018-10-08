package com.sk.xjwd.account.model;

import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.utils.SharedPreUtil;

/**
 *
 * 刘宇飞创建 on 2017/3/29.
 * 描述：用户信息
 */

public class UserInfoModel {


    private volatile static UserInfoModel userInfoModel;
    public int id;
    public String nickName;
    public String realName;
    public String phone;
    public String password;
    public String headUrl;
    public String licenseUrl;
    public String storeUrl;
    public String idCard;
    public String identityFront;
    public String identityBack;
    public String healthCard;
    public String address;
    public String birthday;
    public String sex;
    public String wxId;
    public int userType;
    public String shopStartTime;
    public int shopStartMoney;
    public String isTest;
    public String isWork;
    public int isRegister;
    public int voice;
    public int userStatus;
    public String gmtDatetime;
    public String uptDatetime;
    public String fullTime;
    public  String worktimeMon;//早班时间
    public  String worktimeEve;//晚班时间
    public  String changeDays;//轮换周期

    public void setAccountInfo() {

    }

    public long getId() {
        return userInfoModel.id;
    }

    public static UserInfoModel getInstance() {
        if (userInfoModel == null) {
            synchronized (UserInfoModel.class) {
                if (userInfoModel == null) {
                    userInfoModel = new UserInfoModel();
                }
            }
        }
        userInfoModel.id = SharedPreUtil.getInt(UIUtil.getContext(), "id", 0);
        return userInfoModel;
    }

    public String getToken() {
        return SharedPreUtil.getString(UIUtil.getContext(), userInfoModel.id + "token", "");
    }
}
