package com.sk.xjwd.utils.pay;


public final class Constants1 {
	public static final int BASE_ID = 0;
	public static final int RQF_PAY = BASE_ID + 1;
	public static final int RQF_INSTALL_CHECK = RQF_PAY + 1;
	public static final String SERVER_URL = "http://yintong.com.cn/secure_server/x.htm";
	public static final String PAY_PACKAGE = "com.yintong.secure";
	// 银通支付安全支付服务apk的名称，必须与assets目录下的apk名称一致
	public static final String YT_PLUGIN_NAME = "SecurePay.apk";

	public static final String RET_CODE_SUCCESS = "0000";// 0000 交易成功
	public static final String RET_CODE_PROCESS = "2008";// 2008 支付处理中
	public static final String RESULT_PAY_SUCCESS = "SUCCESS";
	public static final String RESULT_PAY_PROCESSING = "PROCESSING";
	public static final String RESULT_PAY_FAILURE = "FAILURE";
	public static final String RESULT_PAY_REFUND = "REFUND";
    //异步通知回调地址
   // public static final String NOTIFY_URL = "http://test.yintong.com.cn:80/apidemo/API_DEMO/notifyUrl.htm";
  /*  public static final String NOTIFY_URL = Api.HOST_URL+"lianpay/fenqipayapi/receiveNotify";
	public static final String NOTIFY_URL_XUQI = Api.HOST_URL+"lianpay/fenqipayapi3/receiveNotify";*/

}
