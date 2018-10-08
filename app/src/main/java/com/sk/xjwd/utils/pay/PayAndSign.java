package com.sk.xjwd.utils.pay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.secure.EnvConstants;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/6.
 */

public class PayAndSign {

    public static void
    Sign(Context mContext){
        PayOrder order = constructSignCard();
        String content4Pay = BaseHelper.toJSONString(order);
        // 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
        MobileSecurePayer msp = new MobileSecurePayer();
        boolean bRet = msp.payRepaySign(content4Pay, mHandler, Constants1.RQF_PAY, (Activity)mContext, false);
    }

    private static PayOrder constructSignCard() {

        PayOrder order = new PayOrder();
        // RSA 签名方式
        order.setSign_type(PayOrder.SIGN_TYPE_RSA);

        order.setUser_id(MyApplication.getString("sign_userId",""));//用户id
        order.setId_no(MyApplication.getString("sign_idCard",""));//身份证
        order.setAcct_name(MyApplication.getString("sign_name",""));//姓名
        order.setCard_no(MyApplication.getString("sign_bankNum",""));//卡号

//        order.setRepayment_no(((EditText) findViewById(R.id.repayment_no))
//                .getText().toString());
//        String plan = constructPlan();
//        order.setRepayment_plan(plan);
        String sms = "{\"contact_way\":\"0571-56072600\",\"contract_type\":\"LianLianPay\"}";
        order.setSms_param(sms);

        // 风险控制参数
        order.setRisk_item(constructRiskItem());

        String sign;
        order.setOid_partner(EnvConstants.OID_PARTNER);
        String content = BaseHelper.sortParamForSignCard(order);
        // RSA 签名方式
        sign = Rsa.sign(content, EnvConstants.BUSINESS_PRIVATE_KEY);
        order.setSign(sign);
        return order;
    }

    private static String constructRiskItem() {
        /*JSONObject mRiskItem = new JSONObject();
        try {
            mRiskItem.put("user_info_bind_phone", "13958069593");
            mRiskItem.put("user_info_dt_register", "201407251110120");
            mRiskItem.put("frms_ware_category", "4.0");
            mRiskItem.put("request_imei", "1122111221");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mRiskItem.toString();*/
        return MyApplication.getString("Renewal_risk_items", "");
    }
    private static Handler mHandler =new Handler() {
        public void handleMessage(Message msg) {
            String strRet = (String) msg.obj;
            switch (msg.what) {
                case Constants1.RQF_PAY: {
                    JSONObject objContent = BaseHelper.string2JSON(strRet);
                    String retCode = objContent.optString("ret_code");
                    String retMsg = objContent.optString("ret_msg");
                    Log.e("login1",retCode+ retMsg);
                    // 成功
                    if (Constants1.RET_CODE_SUCCESS.equals(retCode)) {
                        ResultChecker checker = new ResultChecker(strRet);
                        checker.checkSign();
                        try {
                            JSONObject object=new JSONObject(strRet);
                            MyApplication.saveString("no_agree",object.getString("no_agree"));
                            RxBus.getDefault().post(Constants.REQUESTID_2,2);//通知签约授权
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("login2",strRet);

                    }else if("1804".equals(retCode)){
                        UIUtil.showToast(retMsg);
                    } else if (Constants1.RET_CODE_PROCESS.equals(retCode)) {
                        // TODO 处理中，掉单的情形
                        String resulPay = objContent.optString("result_pay");
                        if (Constants1.RESULT_PAY_PROCESSING
                                .equalsIgnoreCase(resulPay)) {
                            Log.e("login3",objContent.optString("ret_msg") + "交易状态码："+ retCode+" 返回报文:"+strRet);
                        }

                    } else {
                        // TODO 失败
                        Log.e("login4",retMsg+ "，交易状态码:" + retCode+" 返回报文:"+strRet);
                      //  UIUtil.showToast();
                    }
                }
                break;
            }
            super.handleMessage(msg);
        }
    };
}
