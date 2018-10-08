package com.sk.xjwd.minehome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.pingplusplus.android.Pingpp;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityRenewalDebitBinding;
import com.sk.xjwd.mainhome.activity.CouponActivity;
import com.sk.xjwd.minehome.contract.RenewalDebitActivityContract;
import com.sk.xjwd.minehome.holder.PayResult;
import com.sk.xjwd.minehome.presenter.RenewalDebitActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;



/*
*
* 最后还款界面
*
* */

public class RenewalDebitActivity extends BaseActivity<RenewalDebitActivityPresenter, ActivityRenewalDebitBinding> implements RenewalDebitActivityContract.View {

    String orderState="";
    String money="";
    String ordid="";
    String homeordid="";
    String huanorxuqi="";//还款是huan，续期是xuqi
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renewal_debit);
    }

    @Override
    protected void initView() {
        orderState=getIntent().getStringExtra("orderstate");
        money=getIntent().getStringExtra("ttextendMoney");
        ordid=getIntent().getStringExtra("orderId");
        homeordid=getIntent().getStringExtra("homeOrderId");
        if(ordid==null && homeordid==null){
            setTitle("还款详情");
            huanorxuqi="huan";
        }else {
            setTitle("续期详情");
            huanorxuqi="xuqi";
        }

        initListener();
        initactivity();
        mPresenter.getPayInterfaceMsg();
        initRxBus();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    public void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(Constants.REQUESTID_6, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
                        initactivity();
                        if (type == 2) {
                        }
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void initListener() {
        mBindingView.btnRenewalOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (mBindingView.checkCard.isChecked()) {
                        //银行卡支付
                        if(ordid==null && homeordid==null) {
                            //还款
                            mPresenter.commitCardPay(UIUtil.textisempty(mBindingView.renewalDebitMoney.getText().toString()), "", MyApplication.getString("Renewal_orderId", ""),huanorxuqi);
                        }else if(ordid!=null){
                            //订单记录详情界面续期
                            mPresenter.commitCardPay(UIUtil.textisempty(mBindingView.renewalDebitMoney.getText().toString()), "", ordid,huanorxuqi);
                        }else if(homeordid!=null){
                            //主界面续期
                            mPresenter.commitCardPay(UIUtil.textisempty(mBindingView.renewalDebitMoney.getText().toString()), "", homeordid,huanorxuqi);
                        }
                    } else if (mBindingView.checkZhifubao.isChecked()) {
                        //支付宝支付
//                        mPresenter.getAlipayId();

                        if(ordid!=null){
                            //订单记录详情界面续期
                            mPresenter.commitZhifuPay(UIUtil.textisempty(mBindingView.renewalDebitMoney.getText().toString()), "", ordid,huanorxuqi,"alipay");
                        }else if(homeordid!=null){
                            //主界面续期
                            mPresenter.commitZhifuPay(UIUtil.textisempty(mBindingView.renewalDebitMoney.getText().toString()), "", homeordid,huanorxuqi,"alipay");
                        }else {
                            mPresenter.commitZhifuPay(UIUtil.textisempty(mBindingView.renewalDebitMoney.getText().toString()), "", MyApplication.getString("orderId", ""),huanorxuqi,"alipay");
                        }

                    }

            }
        });
    }

    private static final int SDK_PAY_FLAG = 1;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SDK_PAY_FLAG) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                    UIUtil.showToast("支付成功");
  /*                  UIUtil.startActivity(MainActivity.class,null);
                    RxBus.getDefault().post(Constants.REQUESTID_0, 5);*/
//                    UIUtil.startActivity(RepaySuccessActivity.class, null);
                    Map<String,String> map1=new HashMap<>();
                    map1.put("huanorxuqi",huanorxuqi);
                    map1.put("successorfail","success");
                    map1.put("money",money);
                    UIUtil.startActivity(ApplyorhuanActivity.class,map1);
                } else if (TextUtils.equals(resultStatus, "6001")) {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    UIUtil.showToast("支付取消");
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                    UIUtil.showToast("支付失败");
                    Map<String,String> map=new HashMap<>();
                    map.put("huanorxuqi",huanorxuqi);
                    map.put("successorfail","fail");
                    map.put("money",money);
                    UIUtil.startActivity(ApplyorhuanActivity.class,map);
                }
            }

        }

        ;
    };

    @Override
    public void alipay(String str) {
        final String orderInfo = str;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(RenewalDebitActivity.this);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            String result = data.getExtras().getString("pay_result");
            if(result.equals("success")){
                Map<String,String> map1=new HashMap<>();
                map1.put("huanorxuqi",huanorxuqi);
                map1.put("successorfail","success");
                map1.put("money",money);
                UIUtil.startActivity(ApplyorhuanActivity.class,map1);
            }else if(result.equals("fail")){
                Map<String,String> map=new HashMap<>();
                map.put("huanorxuqi",huanorxuqi);
                map.put("successorfail","fail");
                map.put("money",money);
                UIUtil.startActivity(ApplyorhuanActivity.class,map);
            }else if(result.equals("cancel")){
                UIUtil.showToast("取消支付");
            }
       /* 处理返回值
        * "success" - 支付成功
        * "fail"    - 支付失败
        * "cancel"  - 取消支付
        * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
        * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
        */
            String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
            String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
        }
    }

    @Override
    public void aliZhifu(String s) {
        Pingpp.createPayment(RenewalDebitActivity.this,s);
    }


    public void initactivity() {
        if(money==null) {
            mBindingView.renewalDebitMoney.setText(UIUtil.textisempty(MyApplication.getString("Renewal_money", "")));
        }else {
            mBindingView.renewalDebitMoney.setText(money);
            mBindingView.btnRenewalOk.setText("确定续期");
        }
//        mBindingView.txtBankName.setText(MyApplication.getString("Renewal_bankName", ""));
        String num = MyApplication.getString("Renewal_bankNum", "");
//        mBindingView.txtCardNum.setText("尾号" + (TextUtils.isEmpty(num) ? "" : num.substring(num.length() - 4)));
        if(orderState!=null && orderState.equals("已逾期")){
            mBindingView.llDelayShow.setVisibility(View.VISIBLE);
            mBindingView.txtRenewalDebitDelayDay.setText("已逾期1天，请及时还款");
        }else {
            mBindingView.llDelayShow.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.rl_renewal_debit_coupon)
    public void click(View view){
         switch (view.getId()){
             case R.id.rl_renewal_debit_coupon:
                 Map map=new HashMap<>();
                 map.put("coupontype","1");
                 map.put("orderid","");
                 UIUtil.startActivity(CouponActivity.class,map);
                 break;
         }
    }

    @OnCheckedChanged({R.id.check_zhifubao,R.id.check_card})
    public void checkedClick(CheckBox checkBox){
        boolean b=checkBox.isChecked();
        switch (checkBox.getId()){
            case R.id.check_card:
                if(b){
                    //银行卡选中
                    mBindingView.checkZhifubao.setChecked(false);
                }else {
                    //银行卡没选中
                    mBindingView.checkZhifubao.setChecked(true);
                }
                break;
            case R.id.check_zhifubao:
                if(b){
                    //支付宝选中
                    mBindingView.checkCard.setChecked(false);
                }else {
                    //支付宝没选中
                    mBindingView.checkCard.setChecked(true);
                }
                break;
        }
    }

}
