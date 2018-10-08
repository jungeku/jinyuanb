package com.sk.xjwd.minehome.activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;


import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.http.BaseCallBack;
import com.sk.xjwd.http.BaseResponse;
import com.sk.xjwd.http.RetrofitHelper;
import com.sk.xjwd.http.bankAuthGetCodeResponse;
import com.sk.xjwd.http.httpApi;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.WaitDialog;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class RentComfirmCodePresenter extends BasePresenter<RentConfirmCodeActivity> {
    private WaitDialog waitDialog;
    private String requestNo, yborderId;
    private int flg=0;//循环次数
    String strhuanorxuqi="";
    String strmoney="";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    getPayResult(requestNo, yborderId);
                    break;
            }
        }
    };

    public void initWaitDialog() {
        waitDialog = new WaitDialog(mContext, "请勿关闭此界面,认证时间约为1-2分钟");
    }

    /**
     * 易宝短信验证码确认
     */
    public void commitCode(String money, String type, String orderid, String requestno, String code,String huanorxuqi) {
        strhuanorxuqi=huanorxuqi;
        strmoney=money;
        RetrofitHelper.getRetrofit().create(httpApi.class).commitPay(money, type, orderid, requestno, code).enqueue(new BaseCallBack<BaseResponse<bankAuthGetCodeResponse>>() {
            @Override
            public void onSuccess(Call<BaseResponse<bankAuthGetCodeResponse>> call, Response<BaseResponse<bankAuthGetCodeResponse>> response) {
                if (response.body().isSuccess()) {
                    waitDialog.show();
                    requestNo = response.body().getData().getRequestno();
                    yborderId = response.body().getData().getYborderid();
                    handler.sendEmptyMessageDelayed(0, 5000);
                } else {
                    UIUtil.showToast(response.body().getMsg());
                }
            }
        });
    }

    /**
     * 查询支付结果
     */
    public void getPayResult(String requestno, String yborderid) {
        RetrofitHelper.getRetrofit().create(httpApi.class).getPayResult(requestno, yborderid).enqueue(new BaseCallBack<BaseResponse<bankAuthGetCodeResponse>>() {
            @Override
            public void onSuccess(Call<BaseResponse<bankAuthGetCodeResponse>> call, Response<BaseResponse<bankAuthGetCodeResponse>> response) {
                if (response.body().isSuccess()) {

                    switch (response.body().getData().getStatus()) {
                        case "TIMEOUT":
                            waitDialog.dismiss();
                            UIUtil.showToast("支付超时");
                            break;
                        case "PAY_FAIL":
                            Map<String,String> map=new HashMap<>();
                            map.put("huanorxuqi",strhuanorxuqi);
                            map.put("successorfail","fail");
                            UIUtil.startActivity(ApplyorhuanActivity.class,map);
//                            waitDialog.dismiss();
//                            UIUtil.showToast(response.body().getData().getErrormsg());

                            break;
                        case "PAY_SUCCESS":
//                            waitDialog.dismiss();
//                            UIUtil.showToast("支付成功!");
//                           UIUtil.startActivity(MainActivity.class,null);
//                            RxBus.getDefault().post(Constants.REQUESTID_0, 5);
                            Map<String,String> map1=new HashMap<>();
                            map1.put("huanorxuqi",strhuanorxuqi);
                            map1.put("successorfail","success");
                            map1.put("money",strmoney);
                            UIUtil.startActivity(ApplyorhuanActivity.class,map1);
                            break;
                        default:
                            if (flg<5){
                                handler.sendEmptyMessageDelayed(0, 5000);
                                flg++;
                            }else {
                                waitDialog.dismiss();
                                UIUtil.showToast("支付失败");
                            }
                            break;
                    }

                } else {
                    waitDialog.dismiss();
                    UIUtil.showToast(response.body().getMsg());
                }
            }
        });
    }
}
