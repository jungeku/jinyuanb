package com.sk.xjwd.mainhome.presenter;

import android.util.Log;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.http.AuthInfoDetailsResponse;
import com.sk.xjwd.http.BaseCallBack;
import com.sk.xjwd.http.BaseResponse;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.http.RetrofitHelper;
import com.sk.xjwd.http.httpApi;
import com.sk.xjwd.mainhome.activity.LoanActivity;
import com.sk.xjwd.mainhome.activity.LoanDetailActivity;
import com.sk.xjwd.mainhome.activity.LoanResultActivity;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.ConfirmDialog;
import com.sk.xjwd.view.TiDialog;
import com.sk.xjwd.view.WaitDialog;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mayn on 2018/9/1.
 */

public class LoanActivityPresenter extends BasePresenter<LoanActivity> {

    //查询认证信息详情
//    public void selectUserInfo() {
//        RetrofitHelper.getRetrofit().create(httpApi.class).selectUserAuthInfo().enqueue(
//                new BaseCallBack<BaseResponse<AuthInfoDetailsResponse>>(mContext) {
//                    @Override
//                    public void onSuccess(Call<BaseResponse<AuthInfoDetailsResponse>> call, Response<BaseResponse<AuthInfoDetailsResponse>> response) {
//                        if (response.body().isSuccess()){
////                            String res=response.toString();
//                            mView.showBankCardInfo(response.body().getData().getBankcardno());
//                        }else{
//                            UIUtil.showToast(response.body().getMsg());
//                        }
//                    }
//                });
//    }

    public void getUnFinishOrder(final String userMoney, final String limitDays) {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.addNewOrder);
        util.putParam("borrowMoney", userMoney);
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
                Log.e("login", result + "未生成订单");
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        JSONObject object1 = object.getJSONObject("data");
//                        MyApplication.saveString("apply_riskPlanMoney", object1.getString("riskPlanMoney"));//风险准备金
//                        MyApplication.saveString("apply_msgAuthMoney", object1.getString("msgAuthMoney"));//信息认证费
//                        MyApplication.saveString("apply_riskServeMoney", object1.getString("riskServeMoney"));//风控服务费
//                        MyApplication.saveString("apply_placeServeMoney", object1.getString("placeServeMoney"));//平台服务费
//                        MyApplication.saveString("apply_realMoney", object1.getString("realMoney"));//到账金额
//                        MyApplication.saveString("apply_interestMoney", object1.getString("interestMoney"));//利息
//                        MyApplication.saveString("apply_wateMoney", object1.getString("wateMoney"));//综合费用
                        MyApplication.saveString("apply_bankName", object1.getString("bankName"));//到账银行卡
                        MyApplication.saveString("apply_bankCardNum", object1.getString("bankCardNum"));//银行卡账号
//                        MyApplication.saveString("apply_limitDays", object1.getString("limitDays"));//借款期限
//                        MyApplication.saveString("apply_borrowMoney", object1.getString("borrowMoney"));//借款金额
//                        MyApplication.saveString("apply_interestPrecent", object1.getString("interestPrecent"));//利率
//                        MyApplication.saveString("couponsaveMoney", object1.getString("saveMoney"));//优惠券
//                        MyApplication.saveString("apply_needPayMoney", object1.getString("needPayMoney"));//应还金额
//                        MyApplication.saveString("apply_realPayMoney", object1.getString("realPayMoney"));//实际还款
//                        MyApplication.saveString("apply_gmtDatetime", object1.getString("gmtDatetime"));//生成订单时间
//                        MyApplication.saveString("apply_limitPayTime", object1.getString("limitPayTime"));//应还款时间
//                        MyApplication.saveString("apply_agreementUrl", object1.getString("agreementUrl"));//借款协议
//                        MyApplication.saveString("apply_agreementTwoUrl", object1.getString("agreementTwoUrl"));//平台服务协议
//                        MyApplication.saveString("apply_id", object1.getString("id"));//订单id
//                        RxBus.getDefault().post(Constants.REQUESTID_2, 1);
                        mView.showBankCardInfo();
                    } else {
//                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

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
//                        Map<String, String> map = new HashMap<>();
//                        map.put("money", userMoney);
//                        map.put("time", limitDays);
                        showorder(userMoney,limitDays);

//                        UIUtil.startActivity(LoanDetailActivity.class, map);
                    } else {

                        new TiDialog(mContext).show();
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
//                        UIUtil.startActivity(LoanResultActivity.class,null);
                        commitloanorder();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }


    public void commitloanorder() {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.commitloanorder);
        util.putParam("orderId", MyApplication.getString("apply_id",""));
        util.putParam("appName","gjhz_and");
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
                Log.e("login",result+"提交未生成订单");
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        mView.isSuccess();
                    }else {
                        UIUtil.showToast(object.getString("msg"));
                        mView.isFail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
}
