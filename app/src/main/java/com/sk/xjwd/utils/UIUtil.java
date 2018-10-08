package com.sk.xjwd.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.minehome.activity.WebviewActivity;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;


public class UIUtil {

    private static String mJsonObj;

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 启动activity
     * @param t
     * @param map
     */
    public static void startActivity(Class<?> t, Map<String, String> map) {
        Intent intent = new Intent(getContext(), t);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (map != null) {
            for (String key : map.keySet()) {
                intent.putExtra(key, map.get(key));
            }
        }
        getContext().startActivity(intent);
    }

    /**
     * 设置未读数
     */
    public static void setUnReadNum(TextView textView, int num) {
        textView.setText(num + "");
        if (num > 99) {
            textView.setText("99+");
        }
        if (num > 0) {
            textView.setVisibility(View.VISIBLE);
            if (num > 10) {
                if (num < 100)
                    textView.setPadding(3, 3, 3, 3);
                else textView.setPadding(3, 10, 3, 10);
            } else textView.setPadding(10, 3, 10, 3);

        } else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }

        return false;
    }

    /**
     * 判断是否是手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isMobile(String phoneNumber) {
        if (CommonUtils.isEmpty(phoneNumber)) {
            UIUtil.showToast("请输入手机号码");
            return false;
        }
        if (!phoneNumber.matches("1[0-9]{10}")) {
            UIUtil.showToast("请输入正确的手机号码");
            return false;
        }
        return true;
    }

    public static void showToast(String s) {
        CommonUtils.showToast(getContext(), s);
    }

    public static String textisempty(String defValue) {
        if (defValue == null || "".equals(defValue) || "null".equals(defValue))
            return "0";

        for (int i = 0; i < defValue.length(); i++) {
            char c = defValue.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return defValue;
            }
        }
        return "0";
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    public static String getCityJson(Context context) {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = context.getAssets().open("json/loan.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            is.close();
            mJsonObj = sb.toString();
            return mJsonObj;
        } catch (Exception e) {
            return null;
        }
    }


    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 自定义dialog全屏展示
     *
     * @param activity
     * @param dialog
     */
    public static void FullScreen(Activity activity, Dialog dialog, double scale) {
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        //p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * scale);    //宽度设置为全屏
        dialog.getWindow().setAttributes(p);     //设置生效
    }

    public static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 打开web页帮助文档
     *
     * @param context
     * @param kvs     必须包含title,url,fromFlag可不传默认是-1,isMove可不传默认是false
     */
    public static void startWebHelp(Context context, Object... kvs) {
        Intent i = new Intent(context, WebviewActivity.class);
        String title = "";
        String weburl = "";
        if (kvs.length <= 1) {
            title = "";
            weburl = kvs[0].toString();
        } else if (kvs.length >= 2) {
            title = kvs[0].toString();
            weburl = kvs[1].toString();
        }
        i.putExtra("WebviewTitle", title);
        i.putExtra("WebviewUrl", weburl);

        context.startActivity(i);
    }

    public static String formatMoney(String money) {
        String forhoumoney = "";
        String[] mon = money.split("\\.");
        int cou = mon.length;
        if (mon.length < 2) {
            forhoumoney = money + ".00";
            return forhoumoney;
        }
        if (mon[1].length() == 1) {
            forhoumoney = money + "0";
            return forhoumoney;
        } else {
            forhoumoney = money;
            return forhoumoney;
        }
    }


    public static String formatintMoney(String money) {
        String forhoumoney = "";
        String[] mon = money.split("\\.");
        int cou = mon.length;
        if (mon.length < 2) {
            forhoumoney = money;
            return forhoumoney;
        }else {
            forhoumoney = mon[0];
            return forhoumoney;
        }

    }
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
//    public static String getIMEI(Context ctx) {
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
//        if (tm != null) {
//            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//
//            }
//            return tm.getDeviceId() + "";
//        }
//        return null;
//    }

    public void Downapk(String apkurl){
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(apkurl));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("下载");
        request.setDescription("apk正在下载");
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(getContext(), Environment.DIRECTORY_DOWNLOADS, "mydown");
    }

    public static void downLoadApk(final Context mContext,final String downURL,final String appName ) {

        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(mContext);
        pd.setCancelable(false);// 必须一直下载完，不可取消
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载安装包，请稍后");
        pd.setTitle("版本升级");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = downloadFile(downURL,appName, pd);
                    sleep(3000);
                    installApk(mContext, file);
                    // 结束掉进度条对话框
                    pd.dismiss();
                } catch (Exception e) {
                    pd.dismiss();

                }
            }
        }.start();
    }


    /**
     * 从服务器下载最新更新文件
     *
     * @param path
     *            下载路径
     * @param pd
     *            进度条
     * @return
     * @throws Exception
     */
    private static File downloadFile(String path,String appName ,ProgressDialog pd) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            String SD_FOLDER = Environment.getExternalStorageDirectory()+ "/VersionChecker/";
            String fileName = SD_FOLDER
                    + appName+".apk";
            File file = new File(fileName);
            // 目录不存在创建目录
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            throw new IOException("未发现有SD卡");
        }
    }

    /**
     * 安装apk
     */
    private static void installApk(Context mContext, File file) {
        Uri fileUri = Uri.fromFile(file);
        Intent it = new Intent();
        it.setAction(Intent.ACTION_VIEW);
        it.setDataAndType(fileUri, "application/vnd.android.package-archive");
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 防止打不开应用
        mContext.startActivity(it);
    }

    /**
     * 银行卡脱敏操作
     */
    public static String cardTuomin(String cardnum,int qian,int hou){
        String changeCardnum="";
        changeCardnum=cardnum.substring(0,2)+"****"+cardnum.substring(cardnum.length()-hou,cardnum.length());
        return  changeCardnum;
    }

}
