package com.sk.xjwd.minehome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityRenewalBinding;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.LoanDetailActivity;
import com.sk.xjwd.minehome.contract.RenewalActivityContract;
import com.sk.xjwd.minehome.holder.PayResult;
import com.sk.xjwd.minehome.presenter.RenewalActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;



/*
*
*
* 最后续期界面
*
* */
public class RenewalActivity extends BaseActivity<RenewalActivityPresenter,ActivityRenewalBinding> implements RenewalActivityContract.View {

    private String homeOrderId;
    private String strTime="7";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renewal);
    }
    private String ordId;
    @Override
    protected void initView() {
        setTitle("申请续期");
        Intent intent = getIntent();
        ordId = intent.getStringExtra("int");
        homeOrderId = intent.getStringExtra("homeOrderId");
        if(ordId!=null){
            initData(ordId);
        }
        if(homeOrderId!=null){
            initData(homeOrderId);
        }
        MyApplication.saveString("xuqiday","7");
    }
    private String extendMoney;
    private void initData(String dataone) {
        Log.i("sddddd", "onSuccess1: "+ordId);
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.xuqiInfo);
        util.putParam("id",dataone);
        util.putParam("limitDays","7");
        util.putHead("x-client-token", MyApplication.getString("token",""));
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
                Log.e("loginss",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject recordObject=object.getJSONObject("data");
                        Log.i("sddddd", "onSuccess: "+ordId);
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                        String dateString = formatter.format(recordObject.getLong("limitPayTime")+"L");
//                        mBindingView.tvLimitPayTime.setText(dateString);*/
                        extendMoney = recordObject.getString("extendMoney");
                        mBindingView.txtHuanMoney.setText(recordObject.getString("needPayMoney")+"");
                        mBindingView.txtOrdernumber.setText("借款编号："+dataone);
                        mBindingView.txtBorrowMoney.setText(MyApplication.getString("borrowMoney",""));
                        mBindingView.txtAccrualMoney.setText(recordObject.getString("interestMoney"));
                        mBindingView.renewalOrderDate.setText("还款日期: "+ CommonUtils.getStringDate(MyApplication.getLong("gmtDatetime",0)));
                        MyApplication.saveString("ttextendMoney",recordObject.getString("extendMoney"));
                      mBindingView.tvInterestMoney.setText(recordObject.getString("extendMoney")+"");

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String limitPayTime = recordObject.getString("limitPayTime");
                        long mm = Long.parseLong(limitPayTime);
                        String dateString = formatter.format(mm);
                        mBindingView.tvXuqiTime.setText(dateString);
                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.btn_commit_renewal})
    public void OnClick(View view){
        Map<String,String> map;
        switch (view.getId()){

            case R.id.btn_commit_renewal://提交续期


               //续期按钮
                String money=    MyApplication.getString("ttextendMoney","0");
                if(ordId!=null){
                    Intent intent = new Intent(mContext, RenewalDebitActivity.class);
                    intent.putExtra("ttextendMoney", money);
                    intent.putExtra("orderId", ordId);
                    mContext.startActivity(intent);
//                    mPresenter.commitPayGetCode(money, "", ordId);
                }else if(homeOrderId!=null){
                    Map<String,String> map1=new HashMap<>();
                    map1.put("ttextendMoney", money);
                    map1.put("orderId", homeOrderId);
                    UIUtil.startActivity(RenewalDebitActivity.class,map1);
//                    mPresenter.commitPayGetCode(money, "", homeOrderId);
                }
               //提交操作
           /*     if(ordId!=null){
                    mPresenter.getAlipayId(ordId,MyApplication.getString("xuqiday",""));
                }else if(homeOrderId!=null){
                    mPresenter.getAlipayId(homeOrderId,MyApplication.getString("xuqiday",""));
                }*/
                break;
        }
    }







/*    private void initTwo(String two) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.xuqiInfo);
        util.putParam("id",two);
        util.putParam("limitDays","14");
        util.putHead("x-client-token", MyApplication.getString("token",""));
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
                Log.e("loginss",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject recordObject=object.getJSONObject("data");
                        Log.i("sddddd", "onSuccess: "+ordId);
                       *//* mBindingView.tvNeedPayMoney.setText(recordObject.getInt("needPayMoney")+"");
                        mBindingView.tvExtendMoney.setText(recordObject.getInt("extendMoney")+"");
                        mBindingView.tvInterestMoney.setText(recordObject.getInt("interestMoney")+"");
                        mBindingView.tvAllWasteMoney.setText(recordObject.getInt("allWasteMoney")+"");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(recordObject.getLong("limitPayTime")+"L");
                        mBindingView.tvLimitPayTime.setText(dateString);*//*
                        Log.i("ddfdfdfe", "onSuccess1: "+recordObject.getString("needPayMoney"));
                        Log.i("ddfdfdfe", "onSuccess2: "+recordObject.getString("extendMoney"));
                        Log.i("ddfdfdfe", "onSuccess3: "+recordObject.getString("allWasteMoney"));
                        Log.i("ddfdfdfe", "onSuccess4: "+recordObject.getString("interestMoney"));
                        Log.i("ddfdfdfe", "onSuccess5: "+recordObject.getString("limitPayTime"));
                        extendMoney = recordObject.getString("extendMoney");
                        mBindingView.txtHuanMoney.setText(recordObject.getString("needPayMoney")+"");
//                        mBindingView.tvExtendMoney.setText(recordObject.getString("extendMoney")+"");
                        mBindingView.txtOrdernumber.setText("借款编号："+recordObject.getString("orderNumber"));
                        MyApplication.saveString("ttextendMoney",recordObject.getString("extendMoney"));
                        mBindingView.tvInterestMoney.setText(recordObject.getString("xuqiMoney")+"");
//                        mBindingView.tvAllWasteMoney.setText(recordObject.getString("allWasteMoney")+"");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String limitPayTime = recordObject.getString("limitPayTime");
                        long mm = Long.parseLong(limitPayTime);
                        String dateString = formatter.format(mm);
                        mBindingView.tvLimitPayTime.setText(dateString);
                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }*/

   /* private void initOne(String one) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.xuqiInfo);
        util.putParam("id",one);
        util.putParam("limitDays","7");
        util.putHead("x-client-token", MyApplication.getString("token",""));
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
                Log.e("loginss",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject recordObject=object.getJSONObject("data");
                        Log.i("sddddd", "onSuccess: "+ordId);

                        Log.i("ddfdfdfe", "onSuccess1: "+recordObject.getString("needPayMoney"));
                        Log.i("ddfdfdfe", "onSuccess2: "+recordObject.getString("extendMoney"));
                        Log.i("ddfdfdfe", "onSuccess3: "+recordObject.getString("allWasteMoney"));
                        Log.i("ddfdfdfe", "onSuccess4: "+recordObject.getString("interestMoney"));
                        Log.i("ddfdfdfe", "onSuccess5: "+recordObject.getString("limitPayTime"));
                        Log.i("ddfdfdfe", "onSuccess6: "+recordObject.getString("borrowMoney"));
                        extendMoney = recordObject.getString("extendMoney");
                        mBindingView.txtHuanMoney.setText(recordObject.getString("needPayMoney")+"");
                        mBindingView.txtOrdernumber.setText("借款编号："+recordObject.getString("orderNumber"));
                        mBindingView.txtBorrowMoney.setText(recordObject.getString("borrowMoney")+"");
                        mBindingView.txtAccrualMoney.setText(recordObject.getString("interestMoney")+"");
//                        mBindingView.tvExtendMoney.setText(recordObject.getString("extendMoney")+"");
                        MyApplication.saveString("ttextendMoney",recordObject.getString("extendMoney"));
                        mBindingView.tvInterestMoney.setText(recordObject.getString("xuqiMoney")+"");
//                        mBindingView.tvAllWasteMoney.setText(recordObject.getString("allWasteMoney")+"");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String limitPayTime = recordObject.getString("limitPayTime");
                        long mm = Long.parseLong(limitPayTime);
                        String dateString = formatter.format(mm);
                        mBindingView.tvXuqiTime.setText(dateString);
                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }*/


    private static final int SDK_PAY_FLAG = 1;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what==SDK_PAY_FLAG){
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    UIUtil.showToast("支付成功");

                    Intent intent = new Intent(RenewalActivity.this,RepaySuccessActivity.class);
                    intent.putExtra("xuqiInfo","xuqiInfo");
                    startActivity(intent);
              /*      UIUtil.startActivity(MainActivity.class,null);
                    RxBus.getDefault().post(Constants.REQUESTID_0, 5);*/
                }  else if(TextUtils.equals(resultStatus, "6001")) {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    UIUtil.showToast("支付取消");
                }else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    UIUtil.showToast("支付失败");
                }
            }

        };
    };
    @Override
    public void alipay(String str) {
        final String orderInfo = str;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(RenewalActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
