package com.sk.xjwd.utils;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.sk.xjwd.http.HttpTask;
import com.zyf.fwms.commonlibrary.http.HttpUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 杭州融科网络
 * 刘宇飞创建 on 2017/6/28.
 * 描述：
 */

public class CommonRequstUtils {
    private static int mTime = 60;//重新获取验证码时间
    private static TextView textView;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mTime > 0) {
                mTime--;
                if (textView != null) {
                    reduceTime();
                    textView.setText("重新获取(" + mTime + ")");
                    textView.setClickable(true);
                }
            } else {
                if (textView != null) {
                    textView.setText("重新获取");
                    textView.setClickable(true);
                }

            }


        }
    };

    /**
     * 一秒钟减去1
     */
    private static void reduceTime() {
        handler.sendEmptyMessageDelayed(0, 1000);
    }
    public static void endCodeSend(){
        textView=null;
    }


    protected volatile static HttpTask mHttpTask;

    public static HttpTask getHttpTask() {
        if (mHttpTask == null) {
            synchronized (CommonRequstUtils.class) {
                if (mHttpTask == null) {
                    mHttpTask = HttpUtils.getInstance().createRequest(HttpTask.class);
                }
            }
        }
        return mHttpTask;
    }

    /**
     * 验证码
     */
//    public static void requestPhoneCode(Context context, String phone, TextView tetView,int type) {
//        if (!UIUtil.isMobile(phone)) {
//            return;
//        }
//        mTime = 60;
//        textView = tetView;
//        textView.setClickable(false);
//        HttpPresenter.getInstance()
//                .setContext(context)
//                .setRequsetId(Constants.REQUESTID_10)
//                .setObservable(getHttpTask().requestPhoneCode(phone,type))
//                .setCallBack(new HttpTaskListener() {
//                    @Override
//                    public void onSuccess(int requestId, Object o) {
//                    }
//
//                    @Override
//                    public void onException(int requestId, String... code) {
//                        textView.setClickable(true);
//                        if(code!=null&&code.length>0) {
//                          reduceTime();
//                        }else {
//                            mTime = -1;
//                            reduceTime();
//                        }
//
//                    }
//                }).create();
 //   }



    /**
     * 上传文件
     */
   /* public static void uploadFile(Context context, String localUrl, HttpTaskListener listener) {
        HttpPresenter.getInstance()
                .setCallBack(listener)
                .setContext(context)
                .setRequsetId(Constants.REQUESTID_11)
                .setObservable(getHttpTask().postFile(getRequestBody(localUrl)))
                .create();
    }
*/
    /**
     * 单个文件的请求体
     */
    public static MultipartBody.Part getRequestBody(String path) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return body;
    }

}
