package com.sk.xjwd.authenhome.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.authenhome.contract.PhoneAuthActivityContract;
import com.sk.xjwd.authenhome.presenter.PhoneAuthActivityPresenter;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityPhoneAuthBinding;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.WaitDialog;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import butterknife.OnClick;

public class PhoneAuthActivity extends BaseActivity<PhoneAuthActivityPresenter,ActivityPhoneAuthBinding> implements PhoneAuthActivityContract.View {
    private String content="请勿关闭本界面，验证时间大约需要3-5分钟";
    private static final int AAA = 1;
    int phone_auth_way = 3;
    private String msgs="请勿关闭此界面,认证时间约为3-5分钟";
    private  String codes="";
    private WaitDialog waitDialog=null;
    private KProgressHUD mProgressDialog;

    private  String messages;
    private  String taskids;
    private  String nextStages=null;
//    private MyCountDownTimer mc;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case AAA:
                    initPhonePicture(phones,pwds,taskids,nextStages,codes,null);
                  /*  try {

                      *//*  if (waitDialog==null) {
                            waitDialog = new WaitDialog(mContext);
                            waitDialog.show();
                      *//**//*  if(mContext!=null){
                            Log.i("eweeerr", "handleMessage1: ");

                          // CommonUtils.getInstance().showInfoNewProgressDialog(mContext,content);*//**//*
                        }
                        Thread.sleep(7000);*//*
                        //showWaitsDialog();
                        Log.i("eweeerr", "handleMessage2: ");
                       // initPhonePicture(phones,pwds,taskids,nextStages,codes,null);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
    }

    @Override
    protected void initView() {
        setTitle("手机认证");
        GetYunyinshnag();
        initRxBus();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    public void GetYunyinshnag() {
        mBindingView.edPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // mBindingView.txtYunyin.setText(OperatorUtils.execute(mBindingView.edPhone.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initRxBus() {
       /* Subscription subscribe = RxBus.getDefault().toObservable(Constants.REQUESTID_5, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
                        phone_auth_way = type;
                        if (type == 1) {
                            mBindingView.rlCode.setVisibility(View.GONE);
                            mBindingView.rlIDNumber.setVisibility(View.GONE);
                            mBindingView.rlName.setVisibility(View.GONE);
                        } else if (type == 2) {
                            mBindingView.rlCode.setVisibility(View.VISIBLE);
                            mBindingView.rlIDNumber.setVisibility(View.VISIBLE);
                            mBindingView.rlName.setVisibility(View.VISIBLE);
                        } else if (type == 3) {
                            mBindingView.rlCode.setVisibility(View.VISIBLE);
                        }
                    }
                });
        addSubscription(subscribe);*/
    }
    /*   public boolean onKeyDown(int keyCode, KeyEvent event) {
           if (keyCode == KeyEvent.KEYCODE_BACK) {
               Log.i("weesss", "onKeyDown1: ");
               if(waitDialog!=null){
                   waitDialog.dismiss();
               }

               //waitDialog.setOnDismissListener(new );
               PhoneAuthActivity.this.finish();
               return true;
           }
           Log.i("weesss", "onKeyDown2: ");
           return super.onKeyDown(keyCode, event);
       }*/
    private  String phones;
    private  String pwds;
    private String code;
    @OnClick({R.id.btn_getcode, R.id.btn_auth_phone})
    public void onClink(View view) {
        //手机号
        phones = mBindingView.edPhone.getText().toString();
        //验证码
        code = mBindingView.edAuthenCode.getText().toString();
        //服务商密码
        pwds = mBindingView.edFuwupwd.getText().toString();
        switch (view.getId()) {
            case R.id.btn_getcode:
                if (CommonUtils.isEmpty(phones)) {
                    CommonUtils.showToast(mContext, "手机号码不能为空！");
                } else if (CommonUtils.isEmpty(pwds)) {
                    CommonUtils.showToast(mContext, "服务密码不能为空！");
                } else {
//                    initPhoneAuth(phones,pwds);
                      mPresenter.senCode(mBindingView.btnGetcode, phones, pwds);
                }
                break;
            case R.id.btn_auth_phone:
                if (TextUtils.isEmpty(phones)) {
                    UIUtil.showToast("手机号码不能为空！");
                } else if (TextUtils.isEmpty(pwds)) {
                    UIUtil.showToast("密码不能为空！");
                } else if (!MyApplication.getString("phone","").equals(phones)){
                    UIUtil.showToast("认证手机号必须与登录账号一致！");
                }else  {

                    if(codes.equals("105")){

//                        mBindingView.rlCode.setVisibility(View.VISIBLE);
                        String codeS = mBindingView.edAuthenCode.getText().toString();
                        if(CommonUtils.isNotEmpty(codeS)){
                            initPhonePicture(phones,pwds,taskids,nextStages,codes,codeS);
                            waitDialog = new WaitDialog(mContext,msgs);
                            waitDialog.show();
                        }else {
                            UIUtil.showToast("请输入验证码");
                        }


                    }else if(codes.equals("101")){
                        String pic = mBindingView.etPic.getText().toString();
                        if(CommonUtils.isNotEmpty(pic)){
                            initPhonePicture(phones,pwds,taskids,nextStages,codes,pic);

                            waitDialog = new WaitDialog(mContext,msgs);
                            waitDialog.show();
                            // showWaitsDialog();
                            //  if(mContext!=null){
                            // CommonUtils.getInstance().showInfoNewProgressDialog(mContext,content);
                            //   }

                        }else {
                            UIUtil.showToast("请输入图片验证码");
                        }

                    }else{

                        initPhoneAuth(phones,pwds);
                    }



                }
                break;
        }
    }

    private void initPhoneAuth(final String phone, final String pwd) {
//        mc = new MyCountDownTimer(60000, 1000);
//        mc.start();
        final HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.PHONEONE);
        util.putParam("phoneNo",phone);
        util.putParam("passwd",pwd);
        //util.setshowDialogcontent("正在处理中...");
        util.putHead("x-client-token", MyApplication.getString("token",""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {
                Log.i("sdfdfrtryy", "onCancelled: ");
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.i("sdfdfrtryy", "onError: ");
            }

            @Override
            public void onFinished() {
                Log.i("sdfdfrtryy", "onFinished: ");
            }

            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        Log.i("sdfdfrtryy", "onSuccess22222: "+result);

                        JSONObject data = object.getJSONObject("data");

                        codes = data.getString("code");
                        messages = data.getString("message");

                        String authCode = null;
                        if (codes.equals("100")){
                            //  showWaitsDialog();
                            taskids = data.getString("taskId");
                            initPhonePicture(phone,pwd,taskids,nextStages,codes,null);
                            if (waitDialog==null) {
                                waitDialog = new WaitDialog(mContext,msgs);
                                waitDialog.show();
                            }

                            // if(mContext!=null){
                            //  CommonUtils.getInstance().showInfoNewProgressDialog(mContext,content);
                            // }
                        }else if (codes.equals("101")){
                            Log.i("sdfdfrtryy", "101011: ");
                        }else if (codes.equals("105")){
                            taskids = data.getString("taskId");
                            nextStages = data.getString("nextStage");
                            //      mBindingView.rlCode.setVisibility(View.VISIBLE);
                            String codeS = mBindingView.edAuthenCode.getText().toString();
                            authCode = data.getString("authCode");
                            initPhonePicture(phone,pwd,taskids,nextStages,codes,codeS);

                        }else if (codes.equals("137")||codes.equals("2007")){
                            Toast.makeText(PhoneAuthActivity.this, "认证完成", Toast.LENGTH_SHORT).show();
                        }else {
                            UIUtil.showToast("认证失败，手机号或者服务密码输入错误");
                        }

                    }else {
                        showToast("手机号或者服务密码输入错误");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }


/*    class MyCountDownTimer extends CountDownTimer {
//        *//**
//         *
//         * @param millisInFuture
//         *      表示以毫秒为单位 倒计时的总数
//         *
//         *      例如 millisInFuture=1000 表示1秒
//         *
//         * @param countDownInterval
//         *      表示 间隔 多少微秒 调用一次 onTick 方法
//         *
//         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
//         *
//         *//*
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mBindingView.btnGetcode.setText("获取验证码");
            mBindingView.btnGetcode.setEnabled(true);
            mc.cancel();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i("MainActivity", millisUntilFinished + "");
            mBindingView.btnGetcode.setText(millisUntilFinished / 1000+"秒后重新获取");
            mBindingView.btnGetcode.setEnabled(false);
        }
    }*/


    private void showWaitsDialog() {
        if(mProgressDialog==null){
            mProgressDialog = new KProgressHUD(mContext);
        }

        mProgressDialog.setCancellable(true);
        mProgressDialog.setLabel("加载中dd...");
        mProgressDialog.show();
    }
    private void dismissDialog() {
        mProgressDialog.dismiss();
    }

    private void initPhonePicture(final String phone, final String pwd, String taskId, String nextStage, final String code, String txt) {

        final HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.PHONETWO);
        //util.setshowDialogcontent("正在处理中...");
        util.putParam("taskId",taskId);
        util.putParam("passwd",pwd);
        util.putParam("nextStage",nextStage);
        util.putParam("code",code);
        util.putParam("txt",txt);
        util.putParam("phoneNo",phone);
        util.putHead("x-client-token", MyApplication.getString("token",""));
        util.setListener(new HttpUtil.HttpUtilListener() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")) {
                        Log.i("ccvvctty", "onSuccess: "+result);//
                        JSONObject object1 = object.getJSONObject("data");

                        codes = object1.getString("code");
                        messages = object1.getString("message");
                        taskids = object1.getString("taskId");
                        nextStages = object1.getString("nextStage");
                        Log.i("sdfdfrtryy", "onSuccess: "+codes);
                        if (codes.equals("100")){

                            // showWaitsDialog();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // Looper.prepare();
                                    Message message = new Message();
                                    message.what=AAA;
                                    // handler.sendEmptyMessage(AAA);
                                    handler.sendEmptyMessageDelayed(AAA,7000);
                                    Log.i("eweeerr", "handleMessage2: ");
                                       /* try {

                                            Thread.sleep(7000);
                                            initPhonePicture(phones,pwds,taskids,nextStages,codes,null);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }*/

                                }
                            }).start();

                              /*  initPhonePicture(phones,pwds,taskids,nextStages,codes,null);
                                if (waitDialog==null) {
                                    waitDialog = new WaitDialog(mContext);
                                    waitDialog.show();
                                }*/





                        }else if(codes.equals("112")){

                            waitDialog.dismiss();
                            //dismissDialog();
                            //CommonUtils.getInstance().hideInfoProgressDialog();
                            Toast.makeText(PhoneAuthActivity.this, messages, Toast.LENGTH_SHORT).show();
                            finish();
                        }else if(codes.equals("105")){
                            /*
                            * 多次请求短信验证码编辑框清除
                            * */
                            // dismissDialog();

                            waitDialog.dismiss();
                            //CommonUtils.getInstance().hideInfoProgressDialog();
                            mBindingView.rlCode.setVisibility(View.VISIBLE);
//                            mBindingView.edAuthenCode.setText("");
                            Toast.makeText(PhoneAuthActivity.this, messages, Toast.LENGTH_SHORT).show();

                        }else if (codes.equals("137")||codes.equals("2007")){
                            // dismissDialog();

                            waitDialog.dismiss();
                            //CommonUtils.getInstance().hideInfoProgressDialog();
                            Toast.makeText(PhoneAuthActivity.this, "认证完成", Toast.LENGTH_SHORT).show();
                            // finish();
//                            RxBus.getDefault().post(Constants.REQUESTID_1, 2);
//                            UIUtil.startActivity(MainActivity.class,null);
                            UIUtil.startActivity(ApplyActivity.class,null);
                            PhoneAuthActivity.this.finish();
                        }else if(codes.equals("101")){
                            // dismissDialog();

                            waitDialog.dismiss();
                            //CommonUtils.getInstance().hideInfoProgressDialog();
                            mBindingView.ivPhoImg.setVisibility(View.VISIBLE);
                            mBindingView.rllImg.setVisibility(View.VISIBLE);
                            String authCode = object1.getString("authCode");
                            Bitmap bitmap = stringtoBitmap(authCode);
                            mBindingView.ivPhoImg.setImageBitmap(bitmap);
                            Toast.makeText(PhoneAuthActivity.this, messages, Toast.LENGTH_SHORT).show();

                        }else if(codes.equals("124")){
                            // dismissDialog();
                            Toast.makeText(PhoneAuthActivity.this, messages, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(PhoneAuthActivity.this, messages, Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        UIUtil.showToast(object.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    /**

     * 将base64转换成bitmap图片

     *

     * @param string base64字符串

     * @return bitmap

     */

    public Bitmap stringtoBitmap(String string) {

// 将字符串转换成Bitmap类型

        Bitmap bitmap = null;

        try {

            byte[] bitmapArray;

            bitmapArray = Base64.decode(string, Base64.DEFAULT);

            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,

                    bitmapArray.length);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return bitmap;

    }

}