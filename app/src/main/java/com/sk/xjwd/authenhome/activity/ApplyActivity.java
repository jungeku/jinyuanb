package com.sk.xjwd.authenhome.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.FragmentApplyBinding;
import com.sk.xjwd.mainhome.contract.ApplyFragmentContact;
import com.sk.xjwd.mainhome.presenter.ApplyFragmentPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import java.util.HashMap;

import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by mayn on 2018/8/29.
 */

public class ApplyActivity extends BaseActivity<ApplyFragmentPresenter, FragmentApplyBinding> implements ApplyFragmentContact.View {

    View tishiview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_apply);
    }

    @Override
    protected void initView() {
        initListener();
        setTitle("我的认证");
        initRxBus();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {
        mPresenter.GetAuthState();
    }

    @Override
    public void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(Constants.REQUESTID_1, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
                        if (type == 2) {
                            mPresenter.GetAuthState();
                        } else if (type == 3) {
                            mPresenter.passAuth();
                        }
                        reflsh();
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void reflsh() {
        if (MyApplication.getInt("authStatus", 0) == 0) {//0未认证1已认证
            mBindingView.llNoauth.setVisibility(View.VISIBLE);
            mBindingView.llAuth.setVisibility(View.GONE);
        } else {
            mBindingView.llNoauth.setVisibility(View.GONE);
            mBindingView.llAuth.setVisibility(View.VISIBLE);
            // mBindingView.txtScore.setText(MyApplication.getInt("socre",0)+"");
        }
        if (MyApplication.getInt("baiscAuth", 0) == 0) {//0未认证1已认证2审核中
            mBindingView.lvBaseinfo.setText("去认证");
            mBindingView.lvBaseinfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_home_btn_border));
            mBindingView.lvBaseinfo.setTextColor(Color.parseColor("#ffffffff"));
        } else if (MyApplication.getInt("baiscAuth", 0) == 1) {
            mBindingView.lvBaseinfo.setText("已认证");
            mBindingView.lvBaseinfo.setBackgroundDrawable(null);
            mBindingView.lvBaseinfo.setTextColor(getResources().getColor(R.color.color999999));
        } else {
            mBindingView.lvBaseinfo.setText("审核中");
        }

        if (MyApplication.getInt("bankAuth", 0) == 0) {//0未认证1已认证
            mBindingView.lvBankauth.setText("去认证");
            mBindingView.lvBankauth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_home_btn_border));
            mBindingView.lvBankauth.setTextColor(Color.parseColor("#ffffffff"));
        } else {
            mBindingView.lvBankauth.setText("已认证");
            mBindingView.lvBankauth.setBackgroundDrawable(null);
            mBindingView.lvBankauth.setTextColor(getResources().getColor(R.color.color999999));
        }

        if (MyApplication.getInt("phoneAuth", 0) == 0) {//0未认证1已认证
            mBindingView.lvPhoneauth.setText("去认证");
            mBindingView.lvPhoneauth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_home_btn_border));
            mBindingView.lvPhoneauth.setTextColor(Color.parseColor("#ffffffff"));
        } else {
            mBindingView.lvPhoneauth.setText("已认证");
            mBindingView.lvPhoneauth.setBackgroundDrawable(null);
            mBindingView.lvPhoneauth.setTextColor(getResources().getColor(R.color.color999999));
        }

        if (MyApplication.getInt("identityAuth", 0) == 0) {//0未认证1已认证
            mBindingView.lvShenfenauth.setText("去认证");
            mBindingView.lvShenfenauth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_home_btn_border));
            mBindingView.lvShenfenauth.setTextColor(Color.parseColor("#ffffffff"));
        } else {
            mBindingView.lvShenfenauth.setText("已认证");
            mBindingView.lvShenfenauth.setBackgroundDrawable(null);
            mBindingView.lvShenfenauth.setTextColor(getResources().getColor(R.color.color999999));
        }

        if (MyApplication.getInt("taobaoAuth", 0) == 0) {//0未认证1已认证
            mBindingView.lvZhimaauth.setText("去认证");
            mBindingView.lvZhimaauth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_home_btn_border));
            mBindingView.lvZhimaauth.setTextColor(Color.parseColor("#ffffffff"));
        } else {

            mBindingView.lvZhimaauth.setText("已认证");
            mBindingView.lvZhimaauth.setBackgroundDrawable(null);
            mBindingView.lvZhimaauth.setTextColor(getResources().getColor(R.color.color999999));
        }
        if (MyApplication.getInt("zhifubaoAuth", 0) == 0) {//0未认证1已认证
            mBindingView.lvAlipay.setText("去认证");
            mBindingView.lvAlipay.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_home_btn_border));
            mBindingView.lvAlipay.setTextColor(Color.parseColor("#ffffffff"));
        } else {

            mBindingView.lvAlipay.setText("已认证");
            mBindingView.lvAlipay.setBackgroundDrawable(null);
            mBindingView.lvAlipay.setTextColor(getResources().getColor(R.color.color999999));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.GetAuthState();
    }

    @OnClick({R.id.lv_bankauth, R.id.lv_phoneauth, R.id.lv_baseinfo,
            R.id.lv_zhimaauth, R.id.lv_shenfenauth, R.id.lv_alipay})
//R.id.sv_zhifubao,R.id.sv_jingdong,R.id.sv_shebao,R.id.sv_gongjijing,R.id.sv_zhima_shouxin
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.lv_bankauth://银行卡认证

                if (MyApplication.getInt("baiscAuth", 0) == 1) {
                    if (MyApplication.getInt("identityAuth", 0) == 1) {
                        if (MyApplication.getInt("phoneAuth", 0) == 1) {
                            if (MyApplication.getInt("bankAuth", 0) == 0) {
                                Intent intent = new Intent(mContext, BankCardAuthActivity.class);
                                intent.putExtra("BankCardAuthType", "bankAuth");
                                mContext.startActivity(intent);
                            } else {
                                UIUtil.showToast("已认证完成！");
                            }
                        } else {
                            UIUtil.showToast("请先完成手机认证！");
                        }


                    } else {
                        UIUtil.showToast("请完成身份认证!");
                    }

                } else {
                    UIUtil.showToast("请完成个人信息认证!");
                }

                break;
            case R.id.lv_phoneauth://手机认证

                if (MyApplication.getInt("baiscAuth", 0) == 1) {
                    if (MyApplication.getInt("identityAuth", 0) == 1) {
                        if (MyApplication.getInt("phoneAuth", 0) == 0) {

                            UIUtil.startActivity(PhoneAuthActivity.class, null);
                        } else {
                            UIUtil.showToast("已认证完成！");
                        }
                    } else {
                        UIUtil.showToast("请完成身份认证!");
                    }

                } else {
                    UIUtil.showToast("请完成个人信息认证!");
                }
                break;

            case R.id.lv_baseinfo://个人信息

                if (MyApplication.getInt("baiscAuth", 0) == 0) {
                    UIUtil.startActivity(BaseInfoActivity.class, null);
                    Intent intent=new Intent(this,BaseInfoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    UIUtil.showToast("已认证完成！");
                }
                break;
            case R.id.lv_zhimaauth://淘宝认证

                if (MyApplication.getInt("baiscAuth", 0) == 1) {
                    if (MyApplication.getInt("identityAuth", 0) == 1) {
                        if (MyApplication.getInt("phoneAuth", 0) == 1) {
                            if (MyApplication.getInt("bankAuth", 0) == 1) {
                                if (MyApplication.getInt("taobaoAuth", 0) == 0) {
                                    mPresenter.showTobao(this);
                                } else {
                                    UIUtil.showToast("已认证完成！");
                                }
                            } else {

                                UIUtil.showToast("请先完成银行卡认证！");
                            }
                        } else {
                            UIUtil.showToast("请先完成手机认证！");
                        }

                    } else {
                        UIUtil.showToast("请完成身份认证!");
                    }


                } else {
                    UIUtil.showToast("请完成个人信息认证!");
                }
                break;
            case R.id.lv_shenfenauth://身份认证

                if (MyApplication.getInt("baiscAuth", 0) == 1) {
                    if (MyApplication.getInt("identityAuth", 0) == 0) {
                        mPresenter.getIDAuth();
                    } else {
                        UIUtil.showToast("已认证完成！");
                    }

                } else {
                    UIUtil.showToast("请完成个人信息认证!");
                }
                //UIUtil.startActivity(IDAuthActivity.class,null);
                break;
            case R.id.lv_alipay:


                if (MyApplication.getInt("baiscAuth", 0) == 1) {
                    if (MyApplication.getInt("identityAuth", 0) == 1) {
                        if (MyApplication.getInt("phoneAuth", 0) == 1) {
                            if (MyApplication.getInt("bankAuth", 0) == 1) {
                                if (MyApplication.getInt("taobaoAuth", 0) == 1) {
                                    if (MyApplication.getInt("zhifubaoAuth",0)==0){
                                        HashMap map=new HashMap<>();
                                        map.put("type","1");
                                        UIUtil.startActivity(ZhimaActivity.class,map);
                                    }else {
                                        HashMap  map1=new HashMap<>();
                                        map1.put("type","2");
                                        UIUtil.startActivity(ZhimaActivity.class,map1);
                                    }
                                } else {
                                    UIUtil.showToast("请先完成淘宝认证！");
                                }
                            } else {

                                UIUtil.showToast("请先完成银行卡认证！");
                            }
                        } else {
                            UIUtil.showToast("请先完成手机认证！");
                        }

                    } else {
                        UIUtil.showToast("请完成身份认证!");
                    }


                } else {
                    UIUtil.showToast("请完成个人信息认证!");
                }

                break;
          /*  case R.id.sv_shebao://社保认证
                showIntro();
                break;
            case R.id.sv_gongjijing://公积金认证
                showIntro();
                break;
            case R.id.sv_zhima_shouxin://芝麻授信
                showIntro();
                break;
            case R.id.sv_zhifubao://支付宝认证
                showIntro();
                break;
            case R.id.sv_jingdong://京东认证
                showIntro();
                break;*/
        }
    }

}
