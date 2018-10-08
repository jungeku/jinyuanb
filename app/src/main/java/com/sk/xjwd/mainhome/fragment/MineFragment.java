package com.sk.xjwd.mainhome.fragment;


import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.account.activity.LoginActivity;
import com.sk.xjwd.authenhome.activity.ApplyActivity;
import com.sk.xjwd.authenhome.activity.BankCardAuthActivity;
import com.sk.xjwd.base.BaseFragment;
import com.sk.xjwd.databinding.FragmentMineBinding;
import com.sk.xjwd.mainhome.activity.CouponActivity;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.mainhome.activity.MessageActivity;
import com.sk.xjwd.mainhome.contract.MineFragmentContact;
import com.sk.xjwd.mainhome.presenter.MineFragmentPresenter;
import com.sk.xjwd.minehome.activity.AboutActivity;
import com.sk.xjwd.minehome.activity.HelpCenterActivity;
import com.sk.xjwd.minehome.activity.LoanRecordActivity;
import com.sk.xjwd.minehome.activity.ManageBankActiviity;
import com.sk.xjwd.minehome.activity.MineInfoActivity;
import com.sk.xjwd.minehome.activity.UpdateBankActivity;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

/**
 * 我的
 */
public class MineFragment extends BaseFragment<FragmentMineBinding,MineFragmentPresenter> implements MineFragmentContact.View {



    @Override
    protected void initView() {
        hideTitleBar(false);
        hideBackImg();
        setTitle("我的");
        setTitleColor(Color.parseColor("#ffffff"));
        setAllBackgroundColor(Color.parseColor("#ED3D25"));
        setRightImg(R.mipmap.mine_customer, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.showchatDialog(getActivity());
            }
        });
        initListener();
        mBindingView.waveView.setAnim(true);
//        UIUtil.fullScreen(getActivity());
    }


    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public int setContent() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initListener() {
        if(MyApplication.getInt("authStatus",0)==0){
//            mBindingView.txtMineApply.setText("未认证");
        }else{
//            mBindingView.txtMineApply.setText("已认证");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MyApplication.getBoolean("islogin",false)) {
            mBindingView.txtMinePhone.setText(MyApplication.getString("phone", ""));
        }else {
            mBindingView.txtMinePhone.setText("注册/登录");
        }

    }

    @OnClick({R.id.mine_loanrecord,R.id.mine_coupon,R.id.mine_authentication,R.id.sv_mine_friend,R.id.sv_mine_about_us,
    R.id.sv_mine_card,R.id.sv_mine_help,R.id.sv_mine_pwd,R.id.sv_mine_zixun,R.id.img_mine_head,R.id.rl_mine_myinfo})
    public  void OnClink(View view){
        Map<String,String> map;
        switch (view.getId()){
            case R.id.mine_loanrecord://借款记录
                if(MyApplication.getBoolean("islogin",false)) {
                    UIUtil.startActivity(LoanRecordActivity.class, null);
                }else {
                    UIUtil.startActivity(LoginActivity.class, null);
                }
                break;
            case R.id.mine_coupon://优惠券
                if(MyApplication.getBoolean("islogin",false)){
                    map=new HashMap<>();
                    map.put("coupontype","1");
                    map.put("orderid","");
                    UIUtil.startActivity(CouponActivity.class,map);
                }else UIUtil.startActivity(LoginActivity.class, null);

                break;
            case R.id.mine_authentication://我的认证
                if(MyApplication.getBoolean("islogin",false)){
                    UIUtil.startActivity(ApplyActivity.class,null);
                }else UIUtil.startActivity(LoginActivity.class, null);


                break;
            case R.id.sv_mine_friend://邀请好友
                UIUtil.showToast("正在建设中！");
            //    UIUtil.startActivity(InviteFriendsActivity.class,null);
                break;
            case R.id.sv_mine_about_us://关于我们
                if(MyApplication.getBoolean("islogin",false))
                UIUtil.startActivity(AboutActivity.class,null);
            else UIUtil.startActivity(LoginActivity.class,null);
                break;
            case R.id.sv_mine_card://银行卡管理
                if(MyApplication.getBoolean("islogin",false)){
                    if (MyApplication.getInt("bankAuth",0)==1){
                        Intent intent = new Intent(mContext, BankCardAuthActivity.class);
                        intent.putExtra("BankCardAuthType","upteBankCard");
                        mContext.startActivity(intent);
                    }else {
                        UIUtil.showToast("请先完成银行卡认证！");
                    }
                }else UIUtil.startActivity(LoginActivity.class, null);


                break;
            case R.id.sv_mine_help://帮助中心
                UIUtil.startWebHelp(getActivity(),"帮助中心","https://tzlapi.hongyds.com/helpcenter/");
                break;
            case R.id.sv_mine_pwd://设置交易密码
                mPresenter.payPwdIsExist();
                break;
            case R.id.sv_mine_zixun://资讯中心
                UIUtil.startActivity(MessageActivity.class,null);
                break;
            case R.id.img_mine_head://头像
                break;
            case R.id.rl_mine_myinfo:
                if(MyApplication.getBoolean("islogin",false)) {
                    UIUtil.startActivity(MineInfoActivity.class, null);
                }else {
                    UIUtil.startActivity(LoginActivity.class,null);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBindingView.waveView.setAnim(false);
    }

}
