package com.sk.xjwd.minehome.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityInviteFriendsBinding;
import com.sk.xjwd.minehome.contract.InviteFriendsActivityContract;
import com.sk.xjwd.minehome.presenter.InviteFriendsActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class InviteFriendsActivity extends BaseActivity<InviteFriendsActivityPresenter,ActivityInviteFriendsBinding> implements InviteFriendsActivityContract.View,View.OnClickListener {

    View view;
    ImageView img_share_close;
    PopupWindow mPopupWindow;
    TextView txt_weixin;
    TextView txt_circle;
    TextView txt_sina;
    TextView txt_qq;
    TextView txt_qzone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
    }

    @Override
    protected void initView() {
        setTitle("邀请好友");

        setview();
        mPresenter.initjianglidata();
        initpopdata();
        initRxBus();
      //  mPresenter.initRecyclerView(mBindingView.xRecyclerView);
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.btn_invite_friends})
    public  void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_invite_friends:
               //mPopupWindow.showAtLocation(view, Gravity.CENTER,0,0);//分享按钮
                break;
        }
    }

    public void initpopdata(){
        view= LayoutInflater.from(mContext).inflate(R.layout.dialog_share,null);
        mPopupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        img_share_close= (ImageView) view.findViewById(R.id.share_close);
        txt_weixin= (TextView) view.findViewById(R.id.txt_weixin);
        txt_circle= (TextView) view.findViewById(R.id.txt_pengyouquan);
        txt_sina= (TextView) view.findViewById(R.id.txt_xinlang);
        txt_qq= (TextView) view.findViewById(R.id.txt_qq);
        txt_qzone= (TextView) view.findViewById(R.id.txt_qqkongjian);
        txt_weixin.setOnClickListener(this);
        txt_circle.setOnClickListener(this);
        txt_sina.setOnClickListener(this);
        txt_qq.setOnClickListener(this);
        txt_qzone.setOnClickListener(this);
        img_share_close.setOnClickListener(this);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a0000000")));
        mPopupWindow.setOutsideTouchable(false);
    }

    @Override
    public void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(11, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
                        setview();
                    }
                });
        addSubscription(subscribe);
    }

    public void setview() {
        mBindingView.txtLeijiMoney.setText( MyApplication.getString("invite_money","0")+"元");
        mBindingView.txtInviteNum.setText(MyApplication.getString("invite_count","0")+"人");
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            UIUtil.showToast("分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            UIUtil.showToast("分享失败");
            Log.e("login",t.toString());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            UIUtil.showToast("取消分享");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_weixin:
                Share(SHARE_MEDIA.WEIXIN);
                mPopupWindow.dismiss();
                break;
            case R.id.txt_pengyouquan:
                Share(SHARE_MEDIA.WEIXIN_CIRCLE);
                mPopupWindow.dismiss();
                break;
            case R.id.txt_xinlang:
                Share(SHARE_MEDIA.SINA);
                mPopupWindow.dismiss();
                break;
            case R.id.txt_qq:
                Share(SHARE_MEDIA.QQ);
                mPopupWindow.dismiss();
                break;
            case R.id.txt_qqkongjian:
                Share(SHARE_MEDIA.QZONE);
                break;
            case R.id.share_close:
                mPopupWindow.dismiss();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void Share(SHARE_MEDIA platform){
        UMWeb web = new UMWeb("http://app.hzrywl.com/share/?type=android&uuid="+MyApplication.getString("uuid",""));
     web.setTitle(Api.Appname);//标题
        web.setThumb(new UMImage(mContext, R.mipmap.android_template));  //缩略图
        web.setDescription("邀请您注册"+Api.Appname);//描述
        new ShareAction(InviteFriendsActivity.this)
                .setPlatform(platform)//传入平台
                .withMedia(web)
                .setCallback(shareListener)//回调监听器
                .share();
        mPopupWindow.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

}
