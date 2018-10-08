package com.sk.xjwd.minehome.presenter;


import android.content.Intent;
import android.util.Log;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.BaseCallBack;
import com.sk.xjwd.http.BaseResponse;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.http.RetrofitHelper;
import com.sk.xjwd.http.bankAuthGetCodeResponse;
import com.sk.xjwd.http.httpApi;
import com.sk.xjwd.minehome.activity.ApplyorhuanActivity;
import com.sk.xjwd.minehome.activity.RentConfirmCodeActivity;
import com.sk.xjwd.minehome.contract.RenewalDebitActivityContract;
import com.sk.xjwd.utils.DigestUtils;
import com.sk.xjwd.utils.UIUtil;
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

public class RenewalDebitActivityPresenter extends RenewalDebitActivityContract.Presenter {

    public void getPayInterfaceMsg(){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.getPayInterfaceMsg);
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
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject dataObject=object.getJSONObject("data");
                       MyApplication.saveString("Renewal_bankNum",dataObject.getString("bankNum"));
                       MyApplication.saveString("Renewal_money",dataObject.getString("money"));
                        MyApplication.saveString("Renewal_bankName",dataObject.getString("bankName"));
                        MyApplication.saveString("Renewal_orderId",dataObject.getString("orderId"));
                        RxBus.getDefault().post(Constants.REQUESTID_6,1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }


    public void checkpaypwd(String payPwd){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.checkpaypwd);
        util.putParam("payPwd", DigestUtils.md5(payPwd));
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
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject dataObject=object.getJSONObject("data");
                        MyApplication.saveString("Renewal_userId",dataObject.getString("userId"));
                        MyApplication.saveString("Renewal_name",dataObject.getString("name"));
                        MyApplication.saveString("Renewal_idCard",dataObject.getString("idCard"));
                        MyApplication.saveString("Renewal_orderId",dataObject.getString("orderId"));
                        MyApplication.saveString("no_order",dataObject.getString("no_order"));
						//新增的字段
                        MyApplication.saveString("Renewal_risk_items", dataObject.getString("risk_item"));
                        RxBus.getDefault().post(Constants.REQUESTID_6,2);
                    }else {
                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }


    //获取
    public void getAlipayId(){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.getAlipayId);
        util.putParam("id", MyApplication.getString("orderId",""));
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
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){

                        mView.alipay(object.getString("msg"));

                    }else {
                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).get();
    }




    public void commitPayGetCode(String money, String day, String orderId,String huanorxuqi) {
        RetrofitHelper.getRetrofit().create(httpApi.class).commitPayGetCode(money).enqueue(
                new BaseCallBack<BaseResponse<bankAuthGetCodeResponse>>(mContext) {
                    @Override
                    public void onSuccess(Call<BaseResponse<bankAuthGetCodeResponse>> call, Response<BaseResponse<bankAuthGetCodeResponse>> response) {
                        if (response.body().isSuccess()) {
                            UIUtil.showToast("验证码已发送请注意查收！");
                            Intent intent = new Intent(mContext, RentConfirmCodeActivity.class);
                            intent.putExtra("requestno", response.body().getData().getRequestno());
                            intent.putExtra("needPayMoney", money);
                            intent.putExtra("type", "2");//还款
                            intent.putExtra("orderId", orderId);
                            intent.putExtra("huanorxuqi", huanorxuqi);
                            mContext.startActivity(intent);
                        } else {
                            UIUtil.showToast(response.body().getMsg());
                        }

                    }

                    @Override
                    public void onError(Call<BaseResponse<bankAuthGetCodeResponse>> call, Response<BaseResponse<bankAuthGetCodeResponse>> response) {
                        Log.e("shibai",response.body().toString());

                    }

                    @Override
                    public void onFinish() {
                        Log.e("shibai","finish");
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.e("shibai",t.toString());
                    }
                });
    }

    public void commitCardPay(String money, String day, String orderId,String huanorxuqi){
        String type="";
        if(huanorxuqi.equals("huan")){
            type="2";
        }else if(huanorxuqi.equals("xuqi")){
            type="1";
        }
//      RetrofitHelper.getRetrofit().create(httpApi.class).commitCardPay(money,type,orderId).enqueue(
//              new BaseCallBack<BaseResponse<bankAuthGetCodeResponse>>() {
//                  @Override
//                  public void onSuccess(Call<BaseResponse<bankAuthGetCodeResponse>> call, Response<BaseResponse<bankAuthGetCodeResponse>> response) {
//                      if(response.body().isSuccess()){
//                          String ll=response.body().getData().toString();
//                          Log.e("commitCardPay",ll);
//                      }
//                  }
//              }
//      );

        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.cardPay);
        util.putParam("money", money);
        util.putParam("type", type);
        util.putParam("orderId", orderId);
        util.putHead("x-client-token", MyApplication.getString("token",""));

        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Map<String,String> map=new HashMap<>();
                map.put("huanorxuqi",huanorxuqi);
                map.put("successorfail","fail");
                UIUtil.startActivity(ApplyorhuanActivity.class,map);
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
//                        if(object.getJSONObject("data")!=null) {
//                            JSONObject dataObject = object.getJSONObject("data");
//                            Map<String, String> map1 = new HashMap<>();
//                            map1.put("huanorxuqi", huanorxuqi);
//                            map1.put("successorfail", "success");
//                            map1.put("money", money);
//                            UIUtil.startActivity(ApplyorhuanActivity.class, map1);
//                        }else {
//                        UIUtil.showToast(dataObject.toString());

                            Map<String, String> map1 = new HashMap<>();
                            map1.put("huanorxuqi", huanorxuqi);
                            map1.put("successorfail", "success");
                            map1.put("money", money);
                            UIUtil.startActivity(ApplyorhuanActivity.class, map1);
//                        }
                    }else {
                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }


    public void commitZhifuPay(String money, String day, String orderId,String huanorxuqi,String channel){
        String type="";
        if(huanorxuqi.equals("huan")){
            type="2";
        }else if(huanorxuqi.equals("xuqi")){
            type="1";
        }
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.zhifuPay);
        util.putParam("money", money);
        util.putParam("type", type);
        util.putParam("orderId", orderId);
        util.putParam("channel", "alipay");
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
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        if(object.getJSONObject("data")!=null){
                            JSONObject dataObject=object.getJSONObject("data");
                            mView.aliZhifu(dataObject.toString());
//                            JSONObject credential=dataObject.getJSONObject("credential");
//                            JSONObject alipay=credential.getJSONObject("alipay");
//                            String orderinfo=alipay.getString("orderInfo");
//                            mView.alipay(orderinfo);
                        }else {
//                            mView.alipay(object.getJSONObject("data").toString());
                        }

//                        UIUtil.showToast(dataObject.toString());

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
