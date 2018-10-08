package com.sk.xjwd.authenhome.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.sk.xjwd.R;
import com.sk.xjwd.authenhome.contract.IDAuthActivityContract;
import com.sk.xjwd.authenhome.presenter.IDAuthActivityPresenter;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityIdauthBinding;
import com.sk.xjwd.utils.EncodeBase;
import com.sk.xjwd.utils.RequestPermissions;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

public class IDAuthActivity extends BaseActivity<IDAuthActivityPresenter,ActivityIdauthBinding> implements IDAuthActivityContract.View  {
    boolean flag=false;
    String identityFront;
    String identityBack;
    private String identityPortrait;
    String faceUrl;
    String userName;
    String identityNum;
    private String sessionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idauth);
    }

    @Override
    protected void initView() {
        setTitle("身份认证");
        initListener();
        RequestPermissions.verifyOCRPermissions(this,RequestPermissions.verifySdkVersion());

    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void checkIdentitySuccess(String sessionId) {
        this.sessionId=sessionId;

        mPresenter.checkFace(sessionId,identityPortrait,faceUrl);
    }

    @Override
    public void checkFaceSuccess() {
        Map<String,String> map=new HashMap<>();
        map.put("identityFront",identityFront);//正面照
        map.put("identityBack",identityBack);//反面照
        map.put("faceUrl",faceUrl);//
        map.put("userName",userName);//
        map.put("identityNum",identityNum);//
        mPresenter.getIDAuth(map);
    }

    @OnClick({R.id.img_zhengmian,R.id.img_fanmian})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_zhengmian:

             //   getAuthBuilder().faceAuth(this);

                break;
            case R.id.img_fanmian:
                //  getAuthBuilder().faceAuth(this);
                //  mPresenter.checkIdentity(identityNum,userName);
                //   mPresenter.checkIdentity("341225199311184336","李军");
                break;

        }
    }


  /*  *//**
     * 获取AuthBuilder。
     * 请在每次调用前获取新的AuthBuilder
     * 一个AuthBuilder 不要调用两次start()方法
     * @return
     *//*
    private AuthBuilder getAuthBuilder() {

        // 订单号商户自己生成：不超过36位，非空，不能重复
        String partner_order_id = "orider_246246" ;
        //商户pub_key ： 开户时通过邮件发送给商户
        String pubKey = "d61bb374-56ae-453c-bf98-3e9431b7a61f";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //签名时间：有效期5分钟，请每次重新生成 :签名时间格式：yyyyMMddHHmmss
        String sign_time = simpleDateFormat.format(new Date());
        // 商户 security_key  ：  开户时通过邮件发送给商户
        String security_key = "870c7bf6-4961-4b5b-8146-a51ceb05dfa1";
        // 签名规则
        String singStr = "pub_key=" + pubKey + "|partner_order_id=" + partner_order_id + "|sign_time=" + sign_time + "|security_key=" + security_key;
        //生成 签名
        String sign = Md5.encrypt(singStr);
        *//** 以上签名 请在服务端生成，防止key泄露 *//*

        AuthBuilder mAuthBuidler = new AuthBuilder(partner_order_id, pubKey, "http:......",  new OnResultListener() {
            @Override
            public void onResult(String s) {

                Log.e("renzhenginfo",s);
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    identityBack=jsonObject.getString("url_backcard");
                    identityFront=jsonObject.getString("url_frontcard");
                    identityPortrait=jsonObject.getString("url_photoget");
                    userName=jsonObject.getString("id_name");
                    identityNum=jsonObject.getString("id_no");
                    String ss=     jsonObject.getString("result_auth");
                    if (ss.equals("F")){
                        UIUtil.showToast("失败");
                    }else{
                        UIUtil.showToast("成功");
                    }

                }catch (Exception e){

                }

            }
        });



        return mAuthBuidler;
    }
*/
    private void encodeSuccess(Map<String,String> encodeMap){
        Map<String,String> map=new HashMap<>();

        map.put("identityFront",encodeMap.get("front"));//正面照
        map.put("identityBack",encodeMap.get("back"));//正面照
        map.put("faceUrl",encodeMap.get("face"));//正面照
        map.put("userName",userName);//
        map.put("identityNum",identityNum);//
        mPresenter.getIDAuth(map);
    }

    private void  tobase(){
        final Map<String,String> map = new HashMap<>();
        Glide.with(mContext).load(faceUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                map.put("face", EncodeBase.bitmapToBase64(resource));
                if (map.size()==3){
                    encodeSuccess(map);
                }
            }
        });

        Glide.with(mContext).load(identityFront).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                map.put("front",EncodeBase.bitmapToBase64(resource));
                if (map.size()==3){
                    encodeSuccess(map);
                }
            }
        });

        Glide.with(mContext).load(identityBack).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                map.put("back",EncodeBase.bitmapToBase64(resource));
                if (map.size()==3){
                    encodeSuccess(map);
                }
            }
        });
    }
}
