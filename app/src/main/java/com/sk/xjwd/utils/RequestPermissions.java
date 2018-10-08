package com.sk.xjwd.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Administrator on 2017/4/18.
 */
public class RequestPermissions {
    private static   int REQUEST_CODE_ASK_PERMISSIONS = 123;
    /**
     * SD卡读写权限,开启摄像头权限
     * */
    private static final String[] PERMISSION_OCR = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    private static final int REQUEST_OCR = 100;


    /**
     * 用于OCR识别
     * SD卡读写权限,开启摄像头权限
     * */
    public static void verifyOCRPermissions(Activity activity,boolean b) {
        if(b){
            int permissionWrite = ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionWrite != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSION_OCR,
                        REQUEST_OCR);
            }
        }
    }

    public static boolean verifySdkVersion(){
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }else{
            return true;
        }
    }

    public static void showphonePermissions(Activity activity,boolean b){
        if (b){
            int hasWriteContactsPermission = ActivityCompat. checkSelfPermission(activity,Manifest.permission.READ_CONTACTS);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_CONTACTS},REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }
    }

}
