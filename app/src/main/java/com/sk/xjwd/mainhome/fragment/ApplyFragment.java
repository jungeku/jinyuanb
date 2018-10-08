package com.sk.xjwd.mainhome.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.authenhome.activity.BankCardAuthActivity;
import com.sk.xjwd.authenhome.activity.BaseInfoActivity;
import com.sk.xjwd.authenhome.activity.PhoneAuthActivity;
import com.sk.xjwd.authenhome.activity.ZhimaActivity;
import com.sk.xjwd.base.BaseFragment;
import com.sk.xjwd.databinding.FragmentApplyBinding;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.contract.ApplyFragmentContact;
import com.sk.xjwd.mainhome.presenter.ApplyFragmentPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.HashMap;

import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 *
 */
public class ApplyFragment extends BaseFragment<FragmentApplyBinding,ApplyFragmentPresenter> implements ApplyFragmentContact.View {
    View tishiview;

    @Override
    protected void initView() {
        hideTitleBar(false);
        hideBackImg();
        initListener();
        setTitle("认证");
        initRxBus();
    }


    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public int setContent() {
        return R.layout.fragment_apply;
    }

    @Override
    public void initListener() {
        tishiview = LayoutInflater.from(mContext).inflate(R.layout.dialog_prompt, null);
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

    @OnClick({R.id.rl_bankcard_authen, R.id.rl_phone_authen, R.id.rl_base_info,
            R.id.rl_taobao_auth, R.id.rl_ID_authen, R.id.rl_alipay})
//R.id.sv_zhifubao,R.id.sv_jingdong,R.id.sv_shebao,R.id.sv_gongjijing,R.id.sv_zhima_shouxin
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_bankcard_authen://银行卡认证

                if (MyApplication.getInt("baiscAuth", 0) == 1) {
                    if (MyApplication.getInt("identityAuth", 0) == 1) {
                        if (MyApplication.getInt("phoneAuth", 0) == 1) {
                            if (MyApplication.getInt("bankAuth", 0) == 0) {
                                Intent intent = new Intent(mContext, BankCardAuthActivity.class);
                                intent.putExtra("BankCardAuthType","bankAuth");
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
            case R.id.rl_phone_authen://手机认证

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

            case R.id.rl_base_info://个人信息

                if (MyApplication.getInt("baiscAuth", 0) == 0) {
                    UIUtil.startActivity(BaseInfoActivity.class, null);
                } else {
                    UIUtil.showToast("已认证完成！");
                }
                break;
            case R.id.rl_taobao_auth://淘宝认证

                if (MyApplication.getInt("baiscAuth", 0) == 1) {
                    if (MyApplication.getInt("identityAuth", 0) == 1) {
                        if (MyApplication.getInt("phoneAuth", 0) == 1) {
                            if (MyApplication.getInt("bankAuth", 0) == 1) {
                                if (MyApplication.getInt("taobaoAuth", 0) == 0) {
                                    mPresenter.showTobao(getActivity());
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
            case R.id.rl_ID_authen://身份认证

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
            case R.id.rl_alipay:


                if (MyApplication.getInt("baiscAuth", 0) == 1) {
                    if (MyApplication.getInt("identityAuth", 0) == 1) {
                        if (MyApplication.getInt("phoneAuth", 0) == 1) {
                            if (MyApplication.getInt("bankAuth", 0) == 1) {
                                if (MyApplication.getInt("taobaoAuth", 0) == 1) {
                                    if (MyApplication.getInt("zhifubaoAuth",0)==0){
                                        HashMap  map=new HashMap<>();
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





    private void initTao() {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.qianTaoBao);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
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
                Log.e("loginsss", ">>>>>>>>>>>>>>>>>记录详情>>>>>>>>>>>>>>");
                Log.e("loginsss", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        mPresenter.getTaoBaoAuth();
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

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
            mBindingView.lvBaseinfo.setText("未认证");
            mBindingView.lvBaseinfo.setTextColor(Color.parseColor("#999999"));
        } else if (MyApplication.getInt("baiscAuth", 0) == 1) {
            mBindingView.lvBaseinfo.setText("已认证");
            mBindingView.lvBaseinfo.setTextColor(getResources().getColor(R.color.public_orange));
        } else {
            mBindingView.lvBaseinfo.setText("审核中");
        }

        if (MyApplication.getInt("bankAuth", 0) == 0) {//0未认证1已认证
            mBindingView.lvBankauth.setText("未认证");
            mBindingView.lvBankauth.setTextColor(Color.parseColor("#999999"));
        } else {
            mBindingView.lvBankauth.setText("已认证");
            mBindingView.lvBankauth.setTextColor(getResources().getColor(R.color.public_orange));
        }

        if (MyApplication.getInt("phoneAuth", 0) == 0) {//0未认证1已认证
            mBindingView.lvPhoneauth.setText("未认证");
            mBindingView.lvPhoneauth.setTextColor(Color.parseColor("#999999"));
        } else {
            mBindingView.lvPhoneauth.setText("已认证");
            mBindingView.lvPhoneauth.setTextColor(getResources().getColor(R.color.public_orange));
        }

        if (MyApplication.getInt("identityAuth", 0) == 0) {//0未认证1已认证
            mBindingView.lvShenfenauth.setText("未认证");
            mBindingView.lvShenfenauth.setTextColor(Color.parseColor("#999999"));
        } else {
            mBindingView.lvShenfenauth.setText("已认证");
            mBindingView.lvShenfenauth.setTextColor(getResources().getColor(R.color.public_orange));
        }

        if (MyApplication.getInt("taobaoAuth", 0) == 0) {//0未认证1已认证
            mBindingView.lvZhimaauth.setText("未认证");
            mBindingView.lvZhimaauth.setTextColor(Color.parseColor("#999999"));
        } else {

            mBindingView.lvZhimaauth.setText("已认证");
            mBindingView.lvZhimaauth.setTextColor(getResources().getColor(R.color.public_orange));
        }
        if (MyApplication.getInt("zhifubaoAuth", 0) == 0) {//0未认证1已认证
            mBindingView.lvAlipay.setText("未认证");
            mBindingView.lvAlipay.setTextColor(Color.parseColor("#999999"));
        } else {

            mBindingView.lvAlipay.setText("已认证");
            mBindingView.lvAlipay.setTextColor(getResources().getColor(R.color.public_orange));
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.GetAuthState();
    }

    public void showIntro() {
        AlertDialog mAlertDialog = new AlertDialog.Builder(mContext).create();
        mAlertDialog.show();
        ViewGroup parent = (ViewGroup) tishiview.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        mAlertDialog.getWindow().setContentView(tishiview);
        // mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}
