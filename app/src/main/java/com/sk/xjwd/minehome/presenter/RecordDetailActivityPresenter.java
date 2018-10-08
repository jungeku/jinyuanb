package com.sk.xjwd.minehome.presenter;


import android.util.Log;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.contract.RecordDetailActivityContract;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

public class RecordDetailActivityPresenter extends RecordDetailActivityContract.Presenter {

    @Override
    public void ShowOrderDetail(String orderid) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.Loandetail);
        util.putParam("id",orderid);
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
                Log.e("login", ">>>>>>>>>>>>>>>>>记录详情>>>>>>>>>>>>>>");
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject recordObject=object.getJSONObject("data");
                        MyApplication.saveString("record_orderNumber",recordObject.getString("orderNumber"));//订单号
                        MyApplication.saveString("record_bankCardNum",recordObject.getString("bankCardNum"));//银行卡号
                        MyApplication.saveString("record_bankCard_weihao",recordObject.getString("bankCardNum").substring(recordObject.getString("bankCardNum").length()-4));//银行卡尾号
                        MyApplication.saveString("record_bankName",recordObject.getString("bankName"));//银行卡名称
                        MyApplication.saveString("record_limitDays",recordObject.getString("limitDays"));//借款期限（天）
                        MyApplication.saveString("record_borrowMoney",recordObject.getString("borrowMoney"));//借款金额
                        MyApplication.saveString("record_realMoney",recordObject.getString("realMoney"));//到账金额
                        MyApplication.saveString("record_interestMoney",recordObject.getString("interestMoney"));//利息
                        MyApplication.saveString("record_interestPrecent",recordObject.getString("interestPrecent"));//利率
                        MyApplication.saveString("record_placeServeMoney",recordObject.getString("placeServeMoney"));//平台服务费
                        MyApplication.saveString("record_msgAuthMoney",recordObject.getString("msgAuthMoney"));//信息认证费
                        MyApplication.saveString("record_riskServeMoney",recordObject.getString("riskServeMoney"));//风控服务费
                        MyApplication.saveString("record_riskPlanMoney",recordObject.getString("riskPlanMoney"));//风险准备金
                        MyApplication.saveString("record_wateMoney",recordObject.getString("wateMoney"));//综合费用
                        MyApplication.saveString("record_saveMoney",recordObject.getString("saveMoney"));//优惠卷节省金额
                        MyApplication.saveString("record_needPayMoney",recordObject.getString("needPayMoney"));//应还金额
                        MyApplication.saveString("record_realPayMoney",recordObject.getString("realPayMoney"));//实还金额
                        MyApplication.saveString("record_gmtDatetime",recordObject.getString("gmtDatetime"));//借款时间
                        MyApplication.saveString("record_passTime",recordObject.getString("passTime"));//审核通过时间
                        MyApplication.saveString("record_giveTime",recordObject.getString("giveTime"));//打款时间
                        MyApplication.saveString("record_limitPayTime",recordObject.getString("limitPayTime"));//应还款时间
                        MyApplication.saveString("record_overdueTime",recordObject.getString("overdueTime"));//逾期时间
                        MyApplication.saveString("record_realPayTime",recordObject.getString("realPayTime"));//实际还款时间
                        MyApplication.saveString("record_overdueDays",recordObject.getString("overdueDays"));//逾期天数
                        MyApplication.saveString("record_allowDays",recordObject.getString("allowDays"));//容限期
                        MyApplication.saveString("record_overdueMoney",recordObject.getString("overdueMoney"));//逾期金额
                        MyApplication.saveString("record_allowMoney",recordObject.getString("allowMoney"));//容限期费用
                        MyApplication.saveInt("record_orderStatus",recordObject.getInt("orderStatus"));//订单状态
                        MyApplication.saveString("record_agreementUrl",recordObject.getString("agreementUrl"));//借款协议
                        MyApplication.saveString("record_agreementTwoUrl",recordObject.getString("agreementTwoUrl"));//
                        Log.e("xieyi",recordObject.getString("agreementUrl"));
                        RxBus.getDefault().post(Constants.REQUESTID_3, 1);
                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
}
