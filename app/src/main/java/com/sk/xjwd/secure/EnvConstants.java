
package com.sk.xjwd.secure;

public class EnvConstants {
    private EnvConstants() {
    }

    /**
     * TODO 商户号，商户MD5 key 配置。本测试Demo里的“PARTNER”；强烈建议将私钥配置到服务器上，以免泄露。“MD5_KEY”字段均为测试字段。正式接入需要填写商户自己的字段
     */
    public static final String PARTNER_PREAUTH = "201504071000272504"; // 短信

    public static final String MD5_KEY_PREAUTH = "201504071000272504_test_20150417";

    public static final String PARTNER = "201408071000001543";

    public static final String MD5_KEY = "201408071000001543test_20140812";

    // 商户（RSA）私钥 TODO 强烈建议将私钥配置到服务器上，否则有安全隐患
    // public static final String RSA_PRIVATE =
    // "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";
    public static final String RSA_PRIVATE =
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";

    // 银通支付（RSA）公钥
    public static final String RSA_YT_PUBLIC =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";



    /** 连连银通公钥（不需替换，这是连连公钥，用于报文加密和连连返回响应报文时验签，不是商户生成的公钥. */
    public static final String PUBLIC_KEY_ONLINE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";

    /** 商户私钥 商户通过openssl工具生成公私钥，公钥通过商户站上传，私钥用于加签，替换下面的值 . */
    public static final String BUSINESS_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMvROxixRZUBhzkoMbpDn4/gOuWLugHavBDpxOtcul36yo0Zc10+BH0mXjSrd4IeigXyU0Yu4Dsjk8CHrQc9HccXmLQBIF8QB4bB17rp2AaXc3qTsNjF4HFPWkl8DEI7HdTwI1oyLE80mAuWiV0FcLNpin0CmrmgF4anSChWxujNAgMBAAECgYBMmSeykfzqkRZ23qpBrGRtOAIqfqa7R69iU9jT85d+9l/lQ2ld447gUekZtk9FsaCBt56gQ8G1uBsxQkw+6jMkEb6Ay77kisLKTWik7WhxY+R1HUy8ZPAt2qre5ury0rzBLl5mA+q0TV1/ze77tLkID1ze4duolANeBHJBNPNMqQJBAPF9UJrR76UTgxVK3rAbwTpTrYFLiYaVp2BXncHHZrcc8HA2k4g3vNOiOawVjmaWFyEHnm/Revx7rndeOjim90cCQQDYEG11UXzu8OKZSoCnxWNwpToAb899W65Rnpp/MSdKN9siMp5adLRi+owjNxxj5Hn6wXVhWPH6Rehx7rxW6VFLAkBoW1AW53wZKRpvfCBJmGTjML/87MisiUWAY7x/vgzMOsxIob0Vk4idKDCmBda4zEmIc4NrLOpp64TUfrrIOpcHAkBb1h+wTgKcTScDoPcuWGf76T7OELhQwDdH2fIxPeNFWJh74KgOKqzBpV0LWtFmZCa0vqX1yqiPiEdUpfTHvEQhAkA6/6mfzHRAtdL15AGlcjR4MmFDQyLtL631KSfAdSYjUyDGj6+4hfQhwUThPc8ajtYBPj4KcpBkftJ7qtXuRCfU";

    /** 商户号（商户需要替换自己的商户号）. */
    public static final String OID_PARTNER = "201710310001082848";

    /** 实时付款api版本. */
    public static final String API_VERSION = "1.0";

    /** 实时付款签名方式. */
    public static final String SIGN_TYPE = "RSA";


}
