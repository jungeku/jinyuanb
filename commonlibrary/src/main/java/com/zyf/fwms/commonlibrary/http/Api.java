package com.zyf.fwms.commonlibrary.http;

/**
 *
 * 徐锦征 创建 on 2017/5/15.
 * 描述：
 */

public class Api {

//  public static String HOST_URL="http://t_dev.jinxfu.com/api/";//测试
//  public static String HOST_URL="http://tzlapi.dev.hongyds.com/api/";//测试
  public static String HOST_URL="https://tzlapi.hongyds.com/api/";//测试
//  public static String HOST_URL="http://192.168.3.128:8080/api/";//测试
//  public static String HOST_URL="http://b0a3dd20.ngrok.io/api/";//测试
//  public static String HOST_URL="http://192.168.1.177:8011/api/";//测试
//  public static String HOST_URL="http://192.168.0.166:8080/api/";//测试
//  public static String HOST_URL="http://jybapp.guoji1818.com/api/";//正式

  /*有盾身份认证、人脸识别key*/
  public final static String  partner_order_id = "orider_efg";//商户唯一订单号，长度不超过 36 位(自定义)
  public final static String pubKey = "88743c48-e52f-448a-b8e9-07aeddf002e4";//商户认证密钥（pubkey、Apikey、authKey）
  public final static String  security_key = "34d336ea-818a-4f5e-b9a9-adad637584cf";
  /*异步回调地址  EnvConstants*/
  public static final String NOTIFY_URL = HOST_URL+"lianpay/fenqipayapi/receiveNotify";
  public static final String NOTIFY_URL_XUQI = HOST_URL+"lianpay/fenqipayapi3/receiveNotify";

  /*Appname MyReceiver*/
  public  static final  String  Appname="唐长老钱包";


  public final static String APP_KEY = "E10ADC3949BA59ABBE56E057F20F883E";
  public final static String SECRET_KEY = "E10ADC3949BA59ABBE56E057F20F883E";
  public static long time = System.currentTimeMillis();
  public static String sign = APP_KEY + SECRET_KEY + time+"000"+ "APP";


  /**
   * 登录
   **/
  public static final String LOGINSMS =HOST_URL+"user/loginPhone";
  public static final String PHONEONE =HOST_URL+"user/mobileAuth1";
  public static final String PHONETWO =HOST_URL+"user/mobileAuth2";

  /**
   * 获取验证码
   */
  public static final String GET_PHONE_CODE = HOST_URL+"user/getPhoneCode";
  public static final String homeinfo = HOST_URL+"user/userFirstPage";
  public static final String register = HOST_URL+"user/register";//注册
  public static final String LOAN_RECORD = HOST_URL+"loanOrder/selectAllOrderPage";//借款记录
  public static final String inviteFriend = HOST_URL+"userCoupon/inviteFriend";//邀请好友
  public static final String UserFeedBack = HOST_URL+"appFeedback/add";//用户反馈
  public static final String repayFeedBack = HOST_URL+"payFeedback/add";//退款反馈
  public static final String FORGETPWD = HOST_URL+"user/updatePasswordPhone";//忘记密码
  public static final String AUTHPHONE = HOST_URL+"user/getAuthPhone";//手机认证
  public static final String SmsCodePhoneConfirm = HOST_URL+"user/getSmsCodePhoneConfirm";//手机验证获取验证码
  public static final String AuthState= HOST_URL+"userAuth/selectAll";//认证状态
  public static final String bankCardAuth= HOST_URL+"userBank/bankCardAuth";//银行卡认证
  public static final String BaseMsgAuth= HOST_URL+"userBasicMsg/add";//基本信息认证
  public static final String Loanagreement= HOST_URL+"loanOrder/selectAgreement";//借款协议
  public static final String addNewOrder= HOST_URL+"loanOrder/addNewOrder";//生成未申请订单
  public static final String Loandetail= HOST_URL+"loanOrder/selectOne";//借款详情
  public static final String userCoupon= HOST_URL+"userCoupon/selectByUser";//用户优惠券
  public static final String userIsBorrow= HOST_URL+"user/userIsBorrow";//用户是否能借款
  public static final String settingPayPwd= HOST_URL+"user/payPwdSetting";//设置交易密码
  public static final String userFirstPageType= HOST_URL+"user/userFirstPageType";//判断用户主页是借款还是还款
  public static final String commitloanorder= HOST_URL+"loanOrder/confirmOrder";//确认申请借款
  public static final String uploadfile= HOST_URL+"attachment/upload";//上传文件
  public static final String useCoupon= HOST_URL+"loanOrder/useCoupon";//使用优惠券
  public static final String aboutXed= HOST_URL+"aboutXed/selectAll";//关于小额贷
  public static final String aboutusdetail= HOST_URL+"aboutXed/selectAboutUs";//关于我们详情
  public static final String helpcenterlist1= HOST_URL+"helpCenter/selectPage1";//帮助中心列表
  public static final String helpcenterlist2= HOST_URL+"userMessage/selectMyMessage";//资讯中心列表
 // public static final String helpcenterlist2= HOST_URL+"helpCenter/selectPage2";//资讯中心列表
  public static final String helpcenterdetail= HOST_URL+"helpCenter/selectOne";//帮助中心详情
  public static final String payPwdIsExist= HOST_URL+"user/payPwdIsExist";//用户是否设置过交易密码
  public static final String oldPayPwdConfirm= HOST_URL+"user/oldPayPwdConfirm";//旧交易密码验证
  public static final String phoneauthinfo= HOST_URL+"user/getReport";//手机认证后存储信息
  public static final String saveUserPhoneList= HOST_URL+"userBasicMsg/saveUserPhoneList";//储存用户通讯录
  public static final String getZhiMaUrl= HOST_URL+"user/getZhiMaUrl";//芝麻认证
  public static final String getTaoBaoAuth= HOST_URL+"user/getTaoBaoAuth";//淘宝认证h5url
  public static final String getZhiMaIsPass= HOST_URL+"user/getZhiMaIsPass";//芝麻认证是否通过
  public static final String registeragreement= HOST_URL+"agreement/selectOneByType";//用户注册协议
  public static final String getPayInterfaceMsg= HOST_URL+"user/getPayInterfaceMsg";//获取支付界面的信息
  public static final String checkpaypwd= HOST_URL+"user/getPayMsg";//验证还款支付密码
  public static final String shareUrlParam= HOST_URL+"user/shareUrlParam";//分享链接参数
  public static final String userIsNeedPay= HOST_URL+"user/userIsNeedPay";//用户是否需要还款
  public static final String getSignMsg= HOST_URL+"user/getSignMsg";//获取签约信息
  public static final String getSignPass= HOST_URL+"user/getSignPass";//获取签约授权
  public static final String getads= HOST_URL+"eachPicture/selectList";//广告
 // public static final String pushMsgRecord= HOST_URL+"pushMsgRecord/selectListByUser";//当前登录用户的推送信息列表
 public static final String pushMsgRecord= HOST_URL+"userMessage/selectMyMessage";//当前登录用户的推送信息列表
  public static final String isTaoBaoPass=HOST_URL+"userTaobao/taobaoLoading";  //获取淘宝是否通过
  public static final String xuqiInfo=HOST_URL+"orderExtend/extendMsg";  //获取续期界面
  public static final String payInfoXu=HOST_URL+"orderExtend/extendPayMsg";  //续期支付界面信息
  public static final String xuqiQianYue=HOST_URL+"orderExtend/getSignMsg";  //获取续期签约信息
  public static final String xuqiQianQuan=HOST_URL+"orderExtend/getSignPass";  //续期签约授权

  public static final String qianTaoBao=HOST_URL+"user/beforeTaoBaoAuth";  //淘宝认证前置
  public static final String qianYingHang=HOST_URL+"user/beforeBankAuth";  //银行卡认证前置
  public static final String houYingHang=HOST_URL+"user/bankCardAuth";  //银行卡认证后置


  public static final String checkIdentity= HOST_URL+"userIdentity/getIdCardPass";//实名认证
  public static final String checkFace= HOST_URL+"userIdentity/getPhotoPass";//人脸对比
  public static final String getIDAuth= HOST_URL+"userIdentity/add";//身份认证
  public static final String getStatus= HOST_URL+"loanOrder/selectApplyStatus";//审核状态
  public static final String BankInfo=HOST_URL+"userBank/selectBankByLoginUser";
  public static final String MoneySection=HOST_URL+"user/selectMoneySection";//获取借款金额区间
  public static final String shenFenRenZheng= HOST_URL+"youDunLog/add";
  public static final String Backurl=HOST_URL+"userIdentity/userIdentityBack";
 // public static final String getRecordInfo=HOST_URL+"sysConfig/selectPayInfo";
  public static final String getRecordInfo=HOST_URL+"user/selectUserlimit";
  public static final String getAlipayId=HOST_URL+"user/alipayNew";
  public static final String getXuqiAlipauId=HOST_URL+"user/xuqiPay";//续期
  public static final String saveAlipayInfo=HOST_URL+"userZhifubao/zhifubaoAuth";//储存支付宝账号与密码
  public static final String getqq=HOST_URL+"sysConfig/selectCustomServiceQq";
  public static final String seleAliInfo=HOST_URL+"userZhifubao/seleAliInfo";
    public static final String firstGotoMain=HOST_URL+"userMessage/myNewMessage";
    public static final String cardPay=HOST_URL+"user/bindCardPayNoSms";//银行卡支付
    public static final String zhifuPay=HOST_URL+"user/createCharge";//支付宝支付
    public static final String fuwufee=HOST_URL+"loanOrder/getLoanInfo";//获取服务费用
    public static final String gengxinVersion=HOST_URL+"AppVersion/isForceUpdate";//获取服务费用



  /*
  * 版本升级
  * */
  public static final String updateVersion=HOST_URL+"appVersion/newVersion";
  public static final String getFailMsg=HOST_URL+"userMessage/applyFailMessage";
}
