package com.sk.xjwd.http;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jh352160 on 2017/9/8.
 */

public interface httpApi {
    /**
     * 易宝银行卡认证第一步 获取验证码
     */
    @GET("userBank/bankCardAuth")
    Call<BaseResponse<bankAuthGetCodeResponse>> getbankcardCode(@Query("name") String name,
                                                                @Query("bankcardno") String bankcardno,
                                                                @Query("idcardno") String idcardno,
                                                                @Query("phone") String phone);
    /**
     * 易宝银行卡认证第二步 确认提交
     */
    @GET("userBank/bankCardAuthConfirm")
    Call<BaseResponse> bankCardAuth(@Query("phone") String phone,
                                    @Query("bankcardno") String bankcardno,
                                    @Query("bankName") String bankName,
                                    @Query("requestno") String requestno,
                                    @Query("code") String code,
                                    @Query("idcardno")String idcardno);

    /**
     * 查询认证信息详情
     */
    @GET("userBank/selectBankByLoginUser")
    Call<BaseResponse<AuthInfoDetailsResponse>> selectUserAuthInfo();

    /**
     * 银行卡支付
     */
    @GET("user/bindCardPayNoSms")
    Call<BaseResponse<bankAuthGetCodeResponse>> commitCardPay(@Query("money") String money,
                                                              @Query("type") String type,
                                                              @Query("orderId") String orderid);



    /**
     * 支付宝支付
     */
    @GET("user/createCharge")
    Call<BaseResponse<bankAuthGetCodeResponse>> commitZhifuPay(@Query("money") String money,
                                                              @Query("type") String type,
                                                              @Query("orderId") String orderid,
                                                               @Query("channel") String alipay);


    @FormUrlEncoded
    @POST("loanOrder/getLoanInfo")













    /**
     * 去付租  提交易宝支付获取验证码
     */
    @GET("user/bindCardPayNew")
    Call<BaseResponse<bankAuthGetCodeResponse>> commitPayGetCode(@Query("money") String money);
                                                                /* @Query("days") String day,
                                                                 @Query("orderId") String orderid);*/

    /**
     * 去付租  易宝短信验证码确认
     */
    @GET("user/bindCardConfirm")
    Call<BaseResponse<bankAuthGetCodeResponse>> commitPay(@Query("money") String money,
                                                          @Query("type") String type,
                                                          @Query("orderId") String orderid,
                                                          @Query("requestno") String requestno,
                                                          @Query("code")String code);
    /**
     * 去付租  易宝查询支付结果
     */
    @GET("user/bindCardQuery")
    Call<BaseResponse<bankAuthGetCodeResponse>> getPayResult(@Query("requestno") String requestno,
                                                             @Query("yborderid") String yborderid);
}