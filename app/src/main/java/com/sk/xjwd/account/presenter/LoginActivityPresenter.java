package com.sk.xjwd.account.presenter;


import android.widget.EditText;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.account.contract.LoginActivityContract;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

public class LoginActivityPresenter extends LoginActivityContract.Presenter {

    @Override
    public void doLogin(final EditText phone, final EditText psw) {
        if ( !UIUtil.isMobile(phone.getText().toString())) {
            return;
        }
        if (CommonUtils.isEmpty(psw.getText().toString())) {
            UIUtil.showToast("输入密码");
            return;
        }
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.LOGINSMS);
        util.putParam("phone",phone.getText().toString());
        util.putParam("password",psw.getText().toString());
        util.setshowDialogcontent("登录中");
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                if(arg0.toString().contains("ConnectException")){
                    UIUtil.showToast("服务器连接异常，稍后再试");
                }

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject object1=object.getJSONObject("data");
                        if(object1.getString("status").equals("1")) {
                            //正常
                            MyApplication.saveBoolean("islogin",true);//false未登录true登录
                            UIUtil.startActivity(MainActivity.class, null);
                        }else if(object1.getString("status").equals("2")){
                            //黑名单，永远被拒
                           UIUtil.showToast("该账户是黑名单");
                            UIUtil.startActivity(MainActivity.class, null);
                        }else if(object1.getString("status").equals("3")){
                            //禁用
                            UIUtil.showToast("该账户被禁用");
                            UIUtil.startActivity(MainActivity.class, null);
                        }else if(object1.getString("status").equals("4")){
                            //被拒绝(拒绝后，一月之后可借款)
                            MyApplication.saveBoolean("islogin",true);//false未登录true登录
                            UIUtil.startActivity(MainActivity.class, null);
                        }
                        MyApplication.saveString("loginstatus",object1.getString("status"));
                        MyApplication.saveString("uuid", object1.getString("uuid"));
                        MyApplication.saveInt("id",object1.getInt("id"));
//                        MyApplication.saveString( "userName", object1.getString("userName"));
//                        MyApplication.saveString("password", object1.getString("password"));
                        MyApplication.saveString("phone", object1.getString("phone"));
//                        MyApplication.saveString("money", object1.getString("money"));
//                        MyApplication.saveInt("userType", object1.getInt("userType"));
                        MyApplication.saveInt("authStatus", object1.getInt("authStatus"));
                        MyApplication.saveString("token", object1.getString("token"));
                        MyApplication.saveString("payPwd", object1.getString("payPwd"));
                        MyApplication.saveString("userPwd",psw.getText().toString());
                        MyApplication.saveString("userPhone",phone.getText().toString());

                        phone.setText("");
                        psw.setText("");
                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();

    }

}
