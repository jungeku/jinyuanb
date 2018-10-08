package com.sk.xjwd.mainhome.presenter;


import android.util.Log;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.mainhome.contract.LoanDetailActivityContract;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.WaitDialog;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

public class LoanDetailActivityPresenter extends LoanDetailActivityContract.Presenter {
    private    WaitDialog    dialog;
    @Override
    public void showorder(String borrowMoney, String limitDays) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.addNewOrder);
        util.putParam("borrowMoney",borrowMoney);
        util.putParam("limitDays",limitDays);
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
                Log.e("login",result+"未生成订单");
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject object1=object.getJSONObject("data");
                        MyApplication.saveString("apply_riskPlanMoney",object1.getString("riskPlanMoney"));//风险准备金
                        MyApplication.saveString("apply_msgAuthMoney",object1.getString("msgAuthMoney"));//信息认证费
                        MyApplication.saveString("apply_riskServeMoney",object1.getString("riskServeMoney"));//风控服务费
                        MyApplication.saveString("apply_placeServeMoney",object1.getString("placeServeMoney"));//平台服务费
                        MyApplication.saveString("apply_realMoney",object1.getString("realMoney"));//到账金额
                        MyApplication.saveString("apply_interestMoney",object1.getString("interestMoney"));//利息
                        MyApplication.saveString("apply_wateMoney",object1.getString("wateMoney"));//综合费用
                        MyApplication.saveString("apply_bankName",object1.getString("bankName"));//到账银行卡
                        MyApplication.saveString("apply_bankCardNum",object1.getString("bankCardNum"));//银行卡账号
                        MyApplication.saveString("apply_limitDays",object1.getString("limitDays"));//借款期限
                        MyApplication.saveString("apply_borrowMoney",object1.getString("borrowMoney"));//借款金额
                        MyApplication.saveString("apply_interestPrecent",object1.getString("interestPrecent"));//利率
                        MyApplication.saveString("couponsaveMoney",object1.getString("saveMoney"));//优惠券
                        MyApplication.saveString("apply_needPayMoney",object1.getString("needPayMoney"));//应还金额
                        MyApplication.saveString("apply_realPayMoney",object1.getString("realPayMoney"));//实际还款
                        MyApplication.saveString("apply_gmtDatetime",object1.getString("gmtDatetime"));//生成订单时间
                        MyApplication.saveString("apply_limitPayTime",object1.getString("limitPayTime"));//应还款时间
                        MyApplication.saveString("apply_agreementUrl",object1.getString("agreementUrl"));//借款协议
                        MyApplication.saveString("apply_agreementTwoUrl",object1.getString("agreementTwoUrl"));//平台服务协议
                        MyApplication.saveString("apply_id",object1.getString("id"));//订单id
                        RxBus.getDefault().post(Constants.REQUESTID_2, 1);
                        getSignMsg();
                    }else {
                        UIUtil.showToast(object.getString("msg"));
                        UIUtil.startActivity(MainActivity.class,null);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void commitloanorder() {
        dialog = new WaitDialog(mContext,"提交订单中!  请稍等...");
        dialog.show();
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.commitloanorder);
        util.putParam("orderId",MyApplication.getString("apply_id",""));
        util.putParam("appName","gjhz_and");
        util.putHead("x-client-token", MyApplication.getString("token",""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                dialog.dismiss();
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login",result+"提交未生成订单");
                try {
                    dialog.dismiss();
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        mView.isTrue();
                    }else {
                        UIUtil.showToast(object.getString("msg"));
                        mView.isFlase();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
    //获取签约信息
    @Override
    public void getSignMsg() {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.getSignMsg);
        util.putParam("orderId",MyApplication.getString("apply_id",""));
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
                Log.e("login",result+"签约信息");
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject dataobject=object.getJSONObject("data");
                        MyApplication.saveString("sign_no_order",dataobject.getString("no_order"));
                        MyApplication.saveString("sign_bankNum",dataobject.getString("bankNum"));
                        MyApplication.saveString("sign_money",dataobject.getString("money"));
                        MyApplication.saveString("sign_orderId",dataobject.getString("orderId"));
                        MyApplication.saveString("sign_idCard",dataobject.getString("idCard"));
                        MyApplication.saveString("sign_name",dataobject.getString("name"));
                        MyApplication.saveString("sign_userId",dataobject.getString("userId"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }


    //获取签约授权
    @Override
    public void getSignPass() {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.getSignPass);
        util.putParam("no_agree",MyApplication.getString("no_agree",""));
        util.putParam("user_id",MyApplication.getString("sign_userId",""));
        util.putParam("no_order",MyApplication.getString("sign_no_order",""));
        util.putParam("orderId",MyApplication.getString("sign_orderId",""));
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
                Log.e("login",result+"签约授权");
                try {

                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        dialog = new WaitDialog(mContext,"签约成功！请稍后...");
                        dialog.show();
                        commitloanorder();//提交订单
                    }else {
                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
}
