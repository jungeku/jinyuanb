package com.sk.xjwd.authenhome.presenter;


import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.account.presenter.ForgetPwdActivityPresenter;
import com.sk.xjwd.authenhome.activity.BankCardAuthActivity;
import com.sk.xjwd.authenhome.contract.BankCardAuthActivityContract;
import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.http.AuthInfoDetailsResponse;
import com.sk.xjwd.http.BaseCallBack;
import com.sk.xjwd.http.BaseResponse;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.http.RetrofitHelper;
import com.sk.xjwd.http.bankAuthGetCodeResponse;
import com.sk.xjwd.http.httpApi;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.minehome.adapter.MineHomeAdapter;
import com.sk.xjwd.minehome.model.SupportBankcardModel;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;
import com.zyf.fwms.commonlibrary.utils.SharedPreUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class BankCardAuthActivityPresenter extends BasePresenter<BankCardAuthActivity> {

    private MyCountDownTimer mc;
    private TextView code;

    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            code.setText("获取验证码");
            code.setEnabled(true);
            mc.cancel();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i("MainActivity", millisUntilFinished + "");
            code.setText(millisUntilFinished / 1000+"秒后重新获取");
            code.setEnabled(false);
        }
    }
    /**
     * 提交银行卡认证
     */
 /*   public void commitBankAuth(String name, String ID, String card, String phone) {
        if (!checkInput(name, ID, card, phone)) {
            return;
        }


        RetrofitHelper.getRetrofit().create(httpApi.class).bankcardAuth(name, card, ID, phone).
                enqueue(new BaseCallBack<BaseResponse>(mContext) {
                    @Override
                    public void onSuccess(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.body().isSuccess()) {
                            mView.authSuccess();
                        } else {
                            CommonUtil.showToast(response.body().getMsg());

                        }
                    }
                });
    }*/
    public void getBankAuthCode(String name, String ID, String card, String phone, TextView codeTex) {
        if (!checkInput(name, ID, card, phone)) {
            return;
        }
        this.code=codeTex;
        mc = new MyCountDownTimer(60000, 1000);
        mc.start();
        RetrofitHelper.getRetrofit().create(httpApi.class).getbankcardCode(name, card, ID, phone).enqueue(new BaseCallBack<BaseResponse<bankAuthGetCodeResponse>>(mContext) {
            @Override
            public void onSuccess(Call<BaseResponse<bankAuthGetCodeResponse>> call, Response<BaseResponse<bankAuthGetCodeResponse>> response) {
                if (response.body().isSuccess()) {
                    String data=response.body().toString();
                    MyApplication.saveString("BankAuthRequestNo",response.body().getData().getRequestno());
                    UIUtil.showToast("验证码已发送，请注意查收！");
                } else {
                    UIUtil.showToast(response.body().getMsg());

                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    public void commitBankAuth(String name, String ID, String card, String phone,String bankName, String code) {
        if (!checkInput(name, ID, card, phone)) {
            return;
        }
        String requestno = MyApplication.getString("BankAuthRequestNo","");
        RetrofitHelper.getRetrofit().create(httpApi.class).bankCardAuth(phone, card, bankName, requestno, code,ID).enqueue(new BaseCallBack<BaseResponse>(mContext) {
            @Override
            public void onSuccess(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().isSuccess()) {
                    mView.authSuccess();
                } else {
                    UIUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.isEnabled(true);
            }
        });
    }



    //查询认证信息详情
    public void selectUserInfo() {
        RetrofitHelper.getRetrofit().create(httpApi.class).selectUserAuthInfo().enqueue(
                new BaseCallBack<BaseResponse<AuthInfoDetailsResponse>>(mContext) {
                    @Override
                    public void onSuccess(Call<BaseResponse<AuthInfoDetailsResponse>> call, Response<BaseResponse<AuthInfoDetailsResponse>> response) {
                        if (response.body().isSuccess()){
                            mView.showBankCardInfo(response.body().getData().getName(),response.body().getData().getIdcardno(),response.body().getData().getBankcardno(),response.body().getData().getBankPhone());
                        }else{
                            UIUtil.showToast(response.body().getMsg());
                        }
                    }
                });
    }
    private boolean checkInput(String name, String id, String card, String phone) {
        if (CommonUtils.isEmpty(name)) {
            UIUtil.showToast("请输入姓名");
            return false;
        }
        if (CommonUtils.isEmpty(id)) {
            UIUtil.showToast("请输入身份证号码");
            return false;
        }
        if (CommonUtils.isEmpty(card)) {
            UIUtil.showToast("请输入银行卡号");
            return false;
        }
     /*   if (CommonUtil.isEmpty(code)) {
            CommonUtil.showToast("请选择所属银行");
            return false;
        }*/
        if (CommonUtils.isEmpty(phone)) {
            UIUtil.showToast("请输入预留手机号码");
            return false;
        }
      /*  if (!isAgreeAgreement) {
            CommonUtil.showToast("未同意协议");
            return false;
        }*/
        return true;
    }


}
