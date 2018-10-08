package com.sk.xjwd.http;

/**
 * Created by jh352160 on 2017/9/8.
 */

public class BaseResponse<T> {
    /**
     * code : SUCCESS
     * data : {"id":42,"userName":"谭冬","nickName":null,"sex":"男","loginId":null,"password":"e10adc3949ba59abbe56e057f20f883e","avatarUrl":null,"birthday":"2007-09-05","idCard":null,"recommendUserIds":null,"phone":"15023474083","memberLevel":null,"parsentUserName":null,"source":null,"devAlias":null,"mStatus":1,"qrCode":"http://192.168.0.206:8080/ATTACHMENT/0afe12df-8ac9-44b9-a50f-c1b65e645c61.png","token":"6732838658a089511201c603c56c75a0","machineCode":null,"address":null,"maxMiduoduoRate":null,"maxMiyageRate":null,"userType":null,"memberType":null}
     * msg : 成功
     * success : true
     */

    private String code;
    private T data;
    private String msg;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
