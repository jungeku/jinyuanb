package com.sk.xjwd.minehome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityRenewalDebitBinding;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.contract.RenewalDebitActivityContract;
import com.sk.xjwd.minehome.presenter.RenewalDebitActivityPresenter;
import com.sk.xjwd.secure.EnvConstants;
import com.sk.xjwd.utils.DigestUtils;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.utils.pay.BaseHelper;
import com.sk.xjwd.utils.pay.Constants1;
import com.sk.xjwd.utils.pay.MobileSecurePayer;
import com.sk.xjwd.utils.pay.PayOrder;
import com.sk.xjwd.utils.pay.ResultChecker;
import com.sk.xjwd.utils.pay.Rsa;
import com.xyzlf.custom.keyboardlib.IKeyboardListener;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Subscription;
import rx.functions.Action1;

public class NewRenewalDebitActivity extends BaseActivity<RenewalDebitActivityPresenter, ActivityRenewalDebitBinding> implements RenewalDebitActivityContract.View {

    private String ord;
    private String ext;
    private String timeStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renewal_debit);
    }

    private Intent intent;

    @Override
    protected void initView() {
        intent = getIntent();
        ord = intent.getStringExtra("ord");
        Log.i("sddffd", "initView: " + ord);
        ext = intent.getStringExtra("ext");
        timeStr = intent.getStringExtra("timeStr");
        setTitle("还款");
        initListener();
        //initactivity();
        //mPresenter.getPayInterfaceMsg();
        initRxBus();
        if (ord != null) {
            initPayData();
        }
        Log.i("lllkjjj", "initView: "+timeStr);
    }

    private void initPayData() {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.payInfoXu);
        util.putParam("id", ord);
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
                        String bankNum = recordObject.getString("bankNum");
                        String substring = bankNum.substring(bankNum.length() - 4, bankNum.length());
                        String bankName = recordObject.getString("bankName");
//                        mBindingView.txtBankName.setText(bankName);
//                        mBindingView.txtCardNum.setText(substring);
                        // Log.i("ddffff", "onSuccess: "+substring+"----"+MyApplication.getString("ttextendMoney",""));
                        mBindingView.renewalDebitMoney.setText(ext);
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
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    public void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(Constants.REQUESTID_22, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
                        //initactivity();
                        if (type == 2) {
                            pay();
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
                final KeyboardDialog dialog = new KeyboardDialog(mContext);
                dialog.setKeyboardLintener(new IKeyboardListener() {
                    @Override
                    public void onPasswordChange(String pwd) {

                    }

                    @Override
                    public void onPasswordComplete(String pwd) {
                        /*mPresenter.checkpaypwd(pwd);
                        dialog.dismiss();*/
                        initQianYueData(pwd);
                        dialog.dismiss();
                    }

                    @Override
                    public void onForgetPwd() {

                    }
                });
                dialog.show();

            }
        });
    }

    @Override
    public void alipay(String str) {

    }

    @Override
    public void aliZhifu(String s) {

    }

    private void initQianYueData(String pwd) {
        Log.i("sddffd", "initView2: " + ord);
        Log.i("sddffd", "initQianYueData: " + ext);
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.xuqiQianYue);
        util.putParam("orderId", ord);
        util.putParam("money", ext);
        util.putParam("limitDays", timeStr);
        util.putParam("payPwd", DigestUtils.md5(pwd));
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
                Log.e("loginsss", ">>>>>>>>>>>>>>>>>记录详情>>>>>>>>>>>>>>");
                Log.e("loginsss", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {

                        JSONObject dataObject = object.getJSONObject("data");
                        MyApplication.saveString("Renewal_userIds", dataObject.getString("userId"));
                        MyApplication.saveString("Renewal_names", dataObject.getString("name"));
                        MyApplication.saveString("Renewal_idCards", dataObject.getString("idCard"));
                        MyApplication.saveString("Renewal_orderIds", dataObject.getString("orderId"));
                        MyApplication.saveString("no_orders", dataObject.getString("no_order"));
                        MyApplication.saveString("Renewal_moneys", dataObject.getString("money"));
                        MyApplication.saveString("Renewal_extendIds", dataObject.getString("extendId"));
                        MyApplication.saveString("Renewal_risk_items", dataObject.getString("risk_item"));
                        //RxBus.getDefault().post(Constants.REQUESTID_22,2);
                        shouQuan();
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    private void shouQuan() {
        Log.i("loginsss", "shouQuan: " + ord + "---" + ext);
        String renewal_extendIds = MyApplication.getString("Renewal_extendIds", "");
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.xuqiQianQuan);
        util.putParam("orderId", ord);
        util.putParam("extendId", renewal_extendIds);
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
                Log.e("loginsss", ">>>>>>>>>>>>>>>>>记录详情1>>>>>>>>>>>>>>");
                Log.e("loginsss", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        Log.i("sdfdff", "onSuccess: ");
                        RxBus.getDefault().post(Constants.REQUESTID_22, 2);
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void pay() {
        // 手势码+短信验证方式
        PayOrder order = constructGesturePayOrder();
        // order.setShareing_data(((TextView)
        // findViewById(R.id.share_money)).getText()
        // .toString().trim());
        String content4Pay = BaseHelper.toJSONString(order);
        // 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
        Log.i(NewRenewalDebitActivity.class.getSimpleName(), content4Pay);

        MobileSecurePayer msp = new MobileSecurePayer();
        boolean bRet = msp.payRepayment(content4Pay, mHandler,
                Constants1.RQF_PAY, NewRenewalDebitActivity.this, false);

        Log.i(NewRenewalDebitActivity.class.getSimpleName(), String.valueOf(bRet));
    }

    public void initactivity() {
//        mBindingView.txtBankName.setText(MyApplication.getString("Renewal_bankName", ""));
        String num = MyApplication.getString("Renewal_bankNum", "");
//        mBindingView.txtCardNum.setText("尾号" + (TextUtils.isEmpty(num) ? "" : num.substring(num.length() - 4)));
        mBindingView.renewalDebitMoney.setText(UIUtil.textisempty(MyApplication.getString("Renewal_money", "")) + "元");
    }

    private Handler mHandler = createHandler();

    private Handler createHandler() {
        return new Handler() {
            public void handleMessage(Message msg) {
                String strRet = (String) msg.obj;
                switch (msg.what) {
                    case Constants1.RQF_PAY: {
                        JSONObject objContent = BaseHelper.string2JSON(strRet);
                        String retCode = objContent.optString("ret_code");
                        String retMsg = objContent.optString("ret_msg");
                        // 成功
                        if (Constants1.RET_CODE_SUCCESS.equals(retCode)) {
                            ResultChecker checker = new ResultChecker(strRet);
                            checker.checkSign();

                            // TODO 卡前置模式返回的银行卡绑定协议号，用来下次支付时使用，此处仅作为示例使用。正式接入时去掉
                           // UIUtil.startActivity(RepaySuccessActivity.class, null);
                          //  RxBus.getDefault().post(Constants.REQUESTID_0, 5);
                            Intent intent = new Intent(NewRenewalDebitActivity.this,RepaySuccessActivity.class);
                            intent.putExtra("xuqiInfo","xuqiInfo");
                            startActivity(intent);

                        } else if (Constants1.RET_CODE_PROCESS.equals(retCode)) {
                            // TODO 处理中，掉单的情形
                            String resulPay = objContent.optString("result_pay");
                            if (Constants1.RESULT_PAY_PROCESSING
                                    .equalsIgnoreCase(resulPay)) {
                                UIUtil.showToast("处理中");
                            }

                        } else {
                            // TODO 失败
                            UIUtil.showToast("支付失败");
                        }
                    }
                    break;
                }
                super.handleMessage(msg);
            }
        };

    }

    private PayOrder constructGesturePayOrder() {
        SimpleDateFormat dataFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);

        PayOrder order = new PayOrder();
        order.setBusi_partner("101001");
        //order.setNo_order(timeString);
        order.setNo_order(MyApplication.getString("no_orders", ""));
        order.setDt_order(timeString);
        order.setName_goods("");
        String url = Api.NOTIFY_URL_XUQI + "?orderId=" + MyApplication.getString("Renewal_orderIds", "") + "&money=" + MyApplication.getString("Renewal_moneys", "") + "&extendId=" + MyApplication.getString("Renewal_extendIds", "");
        order.setNotify_url(url);
        // MD5 签名方式
//        order.setSign_type(PayOrder.SIGN_TYPE_MD5);
        // RSA 签名方式
        order.setSign_type(PayOrder.SIGN_TYPE_RSA);
        order.setValid_order("100");

        order.setUser_id(MyApplication.getString("Renewal_userIds", ""));
        order.setId_no(MyApplication.getString("Renewal_idCards", ""));
        order.setAcct_name(MyApplication.getString("Renewal_names", ""));
        order.setMoney_order(MyApplication.getString("Renewal_moneys", ""));
        /*
        * add
        * */
        order.setRisk_item(MyApplication.getString("Renewal_risk_items", ""));
//        order.setRepayment_no(((EditText) findViewById(R.id.repayment_no))
//                .getText().toString());
//        String plan = constructPlan();
//        order.setRepayment_plan(plan);
        String sms = "{\"contact_way\":\"0571-56072600\",\"contract_type\":\"LianLianPay\"}";
        order.setSms_param(sms);

        // 风险控制参数
        order.setRisk_item(constructRiskItem());

        order.setFlag_modify("1");
        String sign = "";
        order.setOid_partner(EnvConstants.OID_PARTNER);
        String content = BaseHelper.sortParam(order);
        // MD5 签名方式, 签名方式包括两种，一种是MD5，一种是RSA 这个在商户站管理里有对验签方式和签名Key的配置。
//        sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
        // RSA 签名方式
        sign = Rsa.sign(content, EnvConstants.BUSINESS_PRIVATE_KEY);
        order.setSign(sign);
        return order;
    }

    private String constructRiskItem() {
       /* JSONObject mRiskItem = new JSONObject();
        try {
            String renewal_risk_items = MyApplication.getString("Renewal_risk_items", "");
           *//* mRiskItem.put("user_info_bind_phone", "13958069593");
            mRiskItem.put("user_info_dt_register", "201407251110120");
            mRiskItem.put("frms_ware_category", "4.0");
            mRiskItem.put("request_imei", "1122111221");*//*
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return MyApplication.getString("Renewal_risk_items", "");
    }
}
