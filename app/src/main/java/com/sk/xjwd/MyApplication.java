/**
 * Copyright (c) 2017, smuyyh@gmail.com All Rights Reserved.
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 */
package com.sk.xjwd;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zyf.fwms.commonlibrary.pickerview.PickCityUtil;

import org.xutils.x;

import cn.fraudmetrix.octopus.aspirit.main.OctopusManager;
import cn.jpush.android.api.JPushInterface;


public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        context=this;
        PickCityUtil.initPickView(context);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        UMShareAPI.get(this);
        Config.DEBUG = true;
        PlatformConfig.setWeixin("wx5779ac01abad41ac", "e8f6c8097564dba92bf2ffce9b643bde");
        PlatformConfig.setSinaWeibo("453985678","0c9cc03c6e6c93d315f0004d3b66263d","https://api.weibo.com/oauth2/default.html");
        PlatformConfig.setQQZone("1106301755","4EpLVsk5wIHknTX8");

        //partnerCode，partnerKey需在平台上申请
        OctopusManager.getInstance().init(context,"dxjk_mohe","5b5db83fce0045188d53621fdd05a276");

    }
    public static Context getContext(){
        return context;
    }

    public static SharedPreferences getPreferences() {
        return getContext().getSharedPreferences("data", MODE_PRIVATE);
    }

    public static String getString(String key, String defValue) {

        return getPreferences().getString(key, defValue);
    }

    public static Boolean getBoolean(String key, Boolean defValue) {

        return getPreferences().getBoolean(key, defValue);
    }
    public static int getInt(String key, int defValue) {

        return getPreferences().getInt(key, defValue);
    }

    public static long getLong(String key, long defValue) {

        return getPreferences().getLong(key, defValue);
    }

    /**
     * 保存字符串变量
     */
    public static void saveString( String key, String value) {
        getPreferences().edit().putString(key, value).commit();
    }
    public static void saveBoolean( String key, Boolean value) {
        getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveInt( String key, int value) {
        getPreferences().edit().putInt(key, value).commit();
    }

    public static void saveLong( String key, long value) {
        getPreferences().edit().putLong(key, value).commit();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
