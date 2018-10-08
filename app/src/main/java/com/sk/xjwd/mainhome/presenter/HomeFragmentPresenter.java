package com.sk.xjwd.mainhome.presenter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.authenhome.activity.ApplyActivity;
import com.sk.xjwd.contact.KfStartHelper;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.LoanDetailActivity;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.mainhome.contract.HomeFragmentContact;
import com.sk.xjwd.minehome.activity.PayPwdActivity;
import com.sk.xjwd.minehome.activity.RenewalActivity;
import com.sk.xjwd.minehome.activity.RenewalDebitActivity;
import com.sk.xjwd.utils.AssetsBankInfo;
import com.sk.xjwd.utils.DigestUtils;
import com.sk.xjwd.utils.GlideImageLoader;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.ActionSheetDialog;
import com.sk.xjwd.view.AutoScrollTextView;
import com.sk.xjwd.view.TiDialog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.http.HttpUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.http.request.HttpRequest;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.umeng.socialize.utils.DeviceConfig.context;

public class HomeFragmentPresenter extends HomeFragmentContact.Presenter {
    private List<String> list = new ArrayList<>();
    private List<String> bannerlist = new ArrayList<>();
    private List<String> moneylist = new ArrayList<>();
    private TextView text;
    private ViewGroup.LayoutParams layoutParams;
    private int screenWidth;//屏幕宽度
    private float moveStep = 0;//托动条的移动步调
    private String orderId;


    public void initGetFailMsg() {
        HttpUtil util = new HttpUtil(context);
        util.setUrl(Api.getFailMsg);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", result + "是否借款");
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject data = object.getJSONObject("data");
                        String text=data.getString("text");
                        if(text!=null&& text.length()>0){
//                            UIUtil.showToast(text);
                            mView.geetInto();
                        }else {

                        }

                    } else {
//                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void getBannerlist(final Banner banner) {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.getads);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", result + "广告");
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONArray array = object.getJSONArray("data");
                        if (array.length() > 0) {
                            bannerlist.clear();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject dataobject = array.getJSONObject(i);
                                bannerlist.add(dataobject.getString("imgUrl"));
                            }
                            //设置图片加载器
                            banner.setImageLoader(new GlideImageLoader());
                            //设置图片集合
                            banner.setImages(bannerlist);
                            banner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(int position) {
                                    //  UIUtil.startActivity(InviteFriendsActivity.class,null);
                                }
                            });
                            //banner设置方法全部调用完毕时最后调用
                            banner.start();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    @Override
    public void showScrolltext(AutoScrollTextView autoText) {
        list.clear();
        try {
            InputStream inputStream = mContext.getAssets().open("json/loan.json");
            JSONObject object = new JSONObject(UIUtil.getString(inputStream));
            JSONArray array = object.getJSONArray("result");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.getJSONObject(i);
                list.add(object1.getString("loanInfo"));
            }
            autoText.setList(list);
            autoText.stopScroll();
            autoText.startScroll();
            autoText.setClickLisener(new AutoScrollTextView.ItemClickLisener() {
                @Override
                public void onClick(int position) {
                    CommonUtils.showToast(mContext, list.get(position));

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getInfo(String userMoney, String limitDays) {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.homeinfo);
        util.putParam("userMoney", userMoney);
        util.putParam("limitDays", limitDays);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", result + "111111aaa1111111");
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject object1 = object.getJSONObject("data");
                        if (MyApplication.getString("borroworpay", "true").equals("false")) {//还款信息
                            MyApplication.saveString("borrowMoney", object1.getString("borrowMoney"));//借款金额
                            MyApplication.saveString("needPayMoney", object1.getString("needPayMoney"));//应还金额
                            MyApplication.saveLong("gmtDatetime", object1.getLong("gmtDatetime"));//申请时间
                            MyApplication.saveLong("limitPayTime", object1.getLong("limitPayTime"));//还款时间
                            MyApplication.saveString("orderId", object1.getString("orderId"));

                        } else if (MyApplication.getString("borroworpay", "true").equals("loading")) {
                            MyApplication.saveString("borrowMoney", object1.getString("borrowMoney"));//借款金额
                            MyApplication.saveString("needPayMoney", object1.getString("needPayMoney"));//应还金额
                            MyApplication.saveLong("gmtDatetime", object1.getLong("gmtDatetime"));//申请时间
                            MyApplication.saveLong("limitPayTime", object1.getLong("limitPayTime"));//还款时间
                            MyApplication.saveString("orderId", object1.getString("orderId"));
                        }else if(MyApplication.getString("borroworpay", "true").equals("fangkuan")) {

                        }else if(MyApplication.getString("borroworpay", "true").equals("shenhe")){

                        }else if(MyApplication.getString("borroworpay", "true").equals("daidakuan")){

                        }else if(MyApplication.getString("borroworpay", "true").equals("huankuanzhong")){

                        } else {
//                            MyApplication.saveString("riskPlanPercent", object1.getString("riskPlanPercent"));//风险准备金
//                            MyApplication.saveString("msgAuthMoney", object1.getString("msgAuthMoney"));//信息认证费
//                            MyApplication.saveString("riskServePercent", object1.getString("riskServePercent"));//风控服务费
//                            MyApplication.saveString("userMoney", object1.getString("userMoney"));//额度
//                            MyApplication.saveString("placeServeMoney", object1.getString("placeServeMoney"));//平台服务费
//                            MyApplication.saveString("realMoney", object1.getString("realMoney"));//到账金额
//                            MyApplication.saveString("interestMoney", object1.getString("interestMoney"));//利息
//                            MyApplication.saveString("allWasteMoney", object1.getString("allWasteMoney"));//综合费用
                            //  MyApplication.saveInt("userMoney",object1.getInt("userMoney"));//可借额度
                        }
                        mView.reflsh();
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    @Override
    public void userIsBorrow(final String userMoney, final String limitDays) {//是否可以借款
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.userIsBorrow);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", result + "是否借款");
                try {
                    JSONObject object = new JSONObject(result);
                  //  if (object.getString("code").equals("SUCCESS")) {
                        if (object.getString("code").equals("SUCCESS")) {
//                            GetHomeAuthState();
                            mView.geetInto();
                        } else {
                            String msg=object.getString("msg");
                          MyApplication.saveString("userIsborrowMsg",object.getString("msg"));
                          new TiDialog(mContext,msg).show();
                        }
                  /*  } else {
                        UIUtil.showToast(object.getString("msg"));
                    }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }


    @Override
    public void userFirstPageType() {//当前是借款还是还款
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.userFirstPageType);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", result + "借款还是还款");
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        if (object.getString("msg").equals("false")) {
                            userStatus();
                        } else {
                            MyApplication.saveString("borroworpay", object.getString("msg"));
                            mView.getInfo();
                        }
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }


    //是否可以还款
    public void userStatus() {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.getStatus);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", result + "ssssss1111");
                try {

                    JSONObject jsonObj = new JSONObject(result).getJSONObject("data");
                    if (jsonObj.getInt("status") == 0) {//未申请

                        MyApplication.saveString("borroworpay", "loading");//借款还是还款
                        //    RxBus.getDefault().post(Constants.REQUESTID_0, 2);
                        mView.getInfo();

                    }else if(jsonObj.getInt("status") == 1){//审核中
                        MyApplication.saveString("borroworpay", "shenhe");
                        mView.getInfo();
                    }else if (jsonObj.getInt("status") == 2){//待打款
                        MyApplication.saveString("borroworpay", "daidakuan");
                        mView.getInfo();
                    }else if(jsonObj.getInt("status")==3){

                        MyApplication.saveString("borroworpay", "false");
                        //  RxBus.getDefault().post(Constants.REQUESTID_0, 2);
                        mView.getInfo();
                    }else if(jsonObj.getInt("status") == 9){
                        MyApplication.saveString("borroworpay", "fangkuan");//放款
                        MyApplication.saveString("bankNum",jsonObj.getString("bankNum"));
                        MyApplication.saveString("bankEngName",jsonObj.getString("bankName"));
                        MyApplication.saveString("bankCnName",AssetsBankInfo.getNameOfBank(mContext,MyApplication.getString("bankEngName",jsonObj.getString("bankName"))));//获取银行卡的信息);
                        mView.getInfo();
                    }else if(jsonObj.getInt("status") == 10){
                        MyApplication.saveString("borroworpay", "huankuanzhong");//放款
                        mView.getInfo();
                    }else {
                        MyApplication.saveString("borroworpay", "true");//放款
                        mView.getInfo();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    @Override
    public void showMoneyDialog(final TextView txtmoney) {
       /* moneylist.clear();
        try {
            int a= MyApplication.getInt("userMoney",2000);
            int size=(a-1000)/100+5;
            for (int i = 0; i <= size; i++) {
                moneylist.add(500+i*100+"");
            }
            PickCityUtil.showSinglePickView(mContext, moneylist, "金额", new PickCityUtil.ChoosePositionListener() {
                @Override
                public void choosePosition(int position, String s) {
                    txtmoney.setText(s);
                    RxBus.getDefault().post(Constants.REQUESTID_0, 2);
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }*/

    }


    //是否可以还款
    public void userIsNeedPay() {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.userIsNeedPay);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", result + "是否可以还款");
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        UIUtil.startActivity(RenewalDebitActivity.class, null);
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    //判断是否设置过交易密码
    public void payPwdIsExist(final boolean boo) {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.payPwdIsExist);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", "onSuccess: " + result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        if (object.getString("msg").equals("true")) {
                            if (boo == true) {
                                userIsNeedPay();
                            } else {
                                initXuQi();
                            }
                        } else {
                            UIUtil.showToast("请先设置交易密码");
                            UIUtil.startActivity(PayPwdActivity.class, null);
                        }
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void initXuQi() {
        orderId = MyApplication.getString("orderId", "");
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.xuqiInfo);
        util.putParam("id", orderId);
        util.putParam("limitDays", "7");
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("loginss", ">>>>>>>>>>>>>>>>>记录详情>>>>>>>>>>>>>>");
                Log.e("loginss", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject recordObject = object.getJSONObject("data");
                        Intent intent = new Intent(mContext, RenewalActivity.class);
                        intent.putExtra("homeOrderId", orderId);
                        mContext.startActivity(intent);

                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void getContact(Context mContext) {
        new ActionSheetDialog(mContext)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("0571-28070194", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        //dadianhua
                        //用intent启动拨打电话
                        new AlertDialog.Builder(mContext)
                                .setTitle("确定")
                                .setMessage("确定拨打该电话")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0571-28070194"));
                                        mContext.startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();



                    }
                })
                .addSheetItem("在线客服", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        //接入客服SDK
//                        UIUtil.showToast("敬请期待");
                        /**
                         * 第一步：初始化help 文件
                         */
                        final KfStartHelper helper = new KfStartHelper((MainActivity)mContext);

                        helper.initSdkChat("com.m7.imkf.KEFU_NEW_MSG", "26a6fd50-aff0-11e8-88b1-4d7a83c49a67", "pwapp", "pwapp");
                    }
                })
                .show();
    }

    public void getMoney(final TextView te) {
        HttpUtil util = new HttpUtil(context);
        util.setUrl(Api.getRecordInfo);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        //JSONObject jsonObject = object.getJSONObject("data");
                        if (CommonUtils.isNotEmpty(object.getString("data"))){
                            JSONObject object1 = object.getJSONObject("data");
                            MyApplication.saveString("maxMoney", object1.getString("maxMoney"));
                            MyApplication.saveString("minMoney", object1.getString("minMoney"));
                            MyApplication.saveString("spanMoney", object1.getString("spanMoney"));
                            MyApplication.saveString("myMoney", object1.getString("myMoney"));
//                            showseekbar(te, seekBar);
                            te.setText(UIUtil.formatMoney(String.valueOf(object.getDouble("data"))));
                        }

                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();

    }


   /* public void MoneySection(final TextView te, final SeekBar seekBar) {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.MoneySection);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("MoneySection", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject recordObject = object.getJSONObject("data");
                        MyApplication.saveString("minMoney", "500");
                        MyApplication.saveString("maxMoney", "1500");
                        showseekbar(te, seekBar);

                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }*/
/*    public void showseekbar(final TextView txtmoney, SeekBar seekBar) {

        final int minMoney = Integer.parseInt(MyApplication.getString("minMoney", "1000"));
        int  maxMoney=    new BigDecimal( MyApplication.getString("maxMoney", "1000")).intValue();
       // int maxMoney = Integer.parseInt(MyApplication.getString("maxMoney", ""));
        seekBar.setMax((maxMoney - minMoney) / 100);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtmoney.setText(minMoney + 100 * progress + "");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int progress = seekBar.getProgress();

                txtmoney.setText(minMoney + 100 * progress + "");

                mView.getInfo();
            }
        });
    }*/

   /* public void getqq() {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.getqq);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", "onSuccess: " + result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        if (CommonUtils.isNotEmpty(object.getString("msg"))) {
                            if (isQQClientAvailable(mContext)) {
                                final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + object.getString("msg") + "&version=1";
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                            } else {
                                UIUtil.showToast("请先安装QQ客户端！");
                            }
                        }
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }*/

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


   /* public void GetAuthState(String money,String days) {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.AuthState);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject object1 = object.getJSONObject("data");
                        MyApplication.saveInt("socre", object1.getInt("socre"));//分数
                        JSONObject objectauth = object1.getJSONObject("auth");
                        int baiscAuth = objectauth.getInt("baiscAuth");
                        int bankAuth = objectauth.getInt("bankAuth");
                        int phoneAuth = objectauth.getInt("phoneAuth");
                        int identityAuth = objectauth.getInt("identityAuth");
                        int zhifubaoAuth = objectauth.getInt("zhifubaoAuth");
                        int taobaoAuth = objectauth.getInt("taobaoAuth");
                        if (baiscAuth == 1 && phoneAuth == 1 && identityAuth == 1 && zhifubaoAuth == 1 && taobaoAuth == 1 && baiscAuth == 1) {
                            userIsBorrow(money,days);
                        } else {
                            UIUtil.showToast("请先认证再借款!");
                            RxBus.getDefault().post(Constants.REQUESTID_4, 1);//通知跳转认证界面
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }*/

    public void firstGoto() {
        HttpUtil util = new HttpUtil(context);
        util.setUrl(Api.firstGotoMain);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONArray array=object.getJSONArray("data");
                        if(array.length()>0){
                                JSONObject dataobject=array.getJSONObject(0);
                              mView.showDialog(dataobject.getString("text"));
                        }
                    }else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();

    }


    public void getwufufee(Map<String,String> map) {
        HttpUtil util = new HttpUtil(context);
        util.setUrl(Api.fuwufee);
        util.putjson(map);
        util.putHead("x-client-token", MyApplication.getString("token", ""));

        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {
                Log.e("onCancelled","onCancelled");
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.e("onError","onError");
            }

            @Override
            public void onFinished() {
                Log.e("onFinished","onFinished");
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject object1=object.getJSONObject("data");
//                        MyApplication.saveString("msgAuthMoney", object1.getString("msgAuthMoney"));//风险准备金
                        MyApplication.saveInt("riskPlanPercent", object1.getInt("riskPlanMoney"));//风险准备金
                        MyApplication.saveInt("msgAuthMoney", object1.getInt("msgAuthMoney"));//信息认证费
                        MyApplication.saveInt("riskServePercent", object1.getInt("riskServeMoney"));//风控服务费
//                        MyApplication.saveString("userMoney", object1.getString("userMoney"));//额度
                        MyApplication.saveInt("placeServeMoney", object1.getInt("placeServeMoney"));//平台服务费
                        MyApplication.saveString("realMoney", object1.getString("realMoney"));//到账金额
                        MyApplication.saveString("interestMoney", object1.getString("interestMoney"));//利息
                        MyApplication.saveInt("allWasteMoney", object1.getInt("allWasteMoney"));//综合费用
                        mView.reflsh();

                    }else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).post();

    }


    public void GetHomeAuthState() {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.AuthState);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject object1 = object.getJSONObject("data");
                        MyApplication.saveInt("socre", object1.getInt("socre"));//分数
                        JSONObject objectauth = object1.getJSONObject("auth");
                        MyApplication.saveInt("baiscAuth", objectauth.getInt("baiscAuth"));//基本认证
                        MyApplication.saveInt("bankAuth", objectauth.getInt("bankAuth"));//银行卡认证
                        MyApplication.saveInt("phoneAuth", objectauth.getInt("phoneAuth"));//手机认证
                        MyApplication.saveInt("identityAuth", objectauth.getInt("identityAuth"));//身份认证
                        MyApplication.saveInt("zhimaAuth", objectauth.getInt("zhimaAuth"));//芝麻授信
                        MyApplication.saveInt("shebaoAuth", objectauth.getInt("shebaoAuth"));//社保认证
                        MyApplication.saveInt("gongjijinAuth", objectauth.getInt("gongjijinAuth"));//公积金认证
                        MyApplication.saveInt("zhifubaoAuth", objectauth.getInt("zhifubaoAuth"));//支付宝认证
                        MyApplication.saveInt("jindongAuth", objectauth.getInt("jindongAuth"));//京东认证
                        MyApplication.saveInt("taobaoAuth", objectauth.getInt("taobaoAuth"));//淘宝认证

                        if (MyApplication.getInt("baiscAuth", 0) == 1 && MyApplication.getInt("identityAuth", 0) == 1
                                && MyApplication.getInt("phoneAuth", 0) == 1 && MyApplication.getInt("bankAuth", 0) == 1
                                && MyApplication.getInt("taobaoAuth", 0) == 1 && MyApplication.getInt("zhifubaoAuth", 0) == 1) {
//                            initGetFailMsg();
                            userIsBorrow("1200","7");
                        } else {
                            UIUtil.startActivity(ApplyActivity.class, null);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
}
