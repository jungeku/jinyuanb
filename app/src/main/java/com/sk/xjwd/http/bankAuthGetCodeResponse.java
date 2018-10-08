package com.sk.xjwd.http;

/**
 * Created by Administrator on 2018/6/13.
 */

public class bankAuthGetCodeResponse {
   private String requestno;
    private String yborderid;
    private String status;
    private String errormsg;

    public String getErrormsg() {
        return errormsg;
    }

    public String getStatus() {
        return status;
    }

    public String getRequestno() {
        return requestno;
    }
    public String getYborderid() {
        return yborderid;
    }
}
