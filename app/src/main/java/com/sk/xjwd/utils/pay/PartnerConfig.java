
package com.sk.xjwd.utils.pay;

import com.sk.xjwd.secure.EnvConstants;

public class PartnerConfig {

    // 合作商户ID。
    public static final String PARTNER = EnvConstants.PARTNER;

    public static final String MD5_KEY = EnvConstants.MD5_KEY;
    // 商户（RSA）私钥 TODO 强烈建议将私钥配置到服务器上，否则有安全隐患
    public static final String RSA_PRIVATE = EnvConstants.BUSINESS_PRIVATE_KEY;
    // 银通支付（RSA）公钥
    public static final String RSA_YT_PUBLIC = EnvConstants.PUBLIC_KEY_ONLINE;

}
