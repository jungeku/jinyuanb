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
import com.sk.xjwd.minehome.activity.RentConfirmCodeActivity;
import com.sk.xjwd.minehome.contract.RenewalActivityContract;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import retrofit2.Call;
import retrofit2.Response;

public class RenewalActivityPresenter extends RenewalActivityContract.Presenter {


    public void getAlipayId(String id,String day) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.getXuqiAlipauId);
        util.putParam("id",id);
        util.putParam("days",day);
        util.putParam("money",MyApplication.getString("ttextendMoney",""));
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

                        mView.alipay(object.getString("msg"));
                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }




    public void commitPayGetCode(String money, String day, String orderId) {
        RetrofitHelper.getRetrofit().create(httpApi.class).commitPayGetCode(money).enqueue(
                new BaseCallBack<BaseResponse<bankAuthGetCodeResponse>>() {
                    @Override
                    public void onSuccess(Call<BaseResponse<bankAuthGetCodeResponse>> call, Response<BaseResponse<bankAuthGetCodeResponse>> response) {
                        if (response.body().isSuccess()) {
                            UIUtil.showToast("验证码已发送请注意查收！");
                            Intent intent = new Intent(mContext, RentConfirmCodeActivity.class);
                            intent.putExtra("requestno", response.body().getData().getRequestno());
                            intent.putExtra("needPayMoney", money);
                            intent.putExtra("type", "1");//续期
                            intent.putExtra("orderId", orderId);
                            mContext.startActivity(intent);
                        } else {
                            UIUtil.showToast(response.body().getMsg());
                        }
                    }
                });
    }
}
