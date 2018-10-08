package com.sk.xjwd.mainhome.fragment;


import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.account.activity.LoginActivity;
import com.sk.xjwd.authenhome.activity.ApplyActivity;
import com.sk.xjwd.base.BaseFragment;
import com.sk.xjwd.databinding.FragmentHomeBinding;
import com.sk.xjwd.mainhome.activity.LoanActivity;
import com.sk.xjwd.mainhome.activity.MessageActivity;
import com.sk.xjwd.mainhome.contract.HomeFragmentContact;
import com.sk.xjwd.mainhome.presenter.HomeFragmentPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.OnValueChangeListener;
import com.sk.xjwd.view.firstGotoMainDialog;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeFragmentPresenter> implements HomeFragmentContact.View {
    AlertDialog mAlertDialog;
    View view;
    TextView txt_cancel;
    TextView txt_placeServeMoney;
    TextView txt_msgAuthMoney;
    TextView txt_riskServePercent;
    TextView txt_riskPlanPercent;
    TextView txt_allWasteMoney;
    TextView txt_interestMoney;
    private int LimitDays = 7;
    private int code;
    private boolean isClick=false;

    @Override
    protected void initView() {


        hideTitleBar(false);
        hideBackImg();
        setTitle("唐长老钱包");
        setTitleColor(Color.parseColor("#ffffff"));
        setAllBackgroundDrawable(getResources().getDrawable(R.drawable.login_change));
        MyApplication.saveInt("RadioGroup",0);
        initListener();
        initRxBus();
        Map<String, String> map=new HashMap<>();
        map.put("borrowMoney",String.valueOf(3000));
        map.put("limitDays","7");
        mPresenter.getwufufee(map);
       if(MyApplication.getBoolean("islogin",false)) {
           mPresenter.firstGoto();
           mPresenter.getMoney(mBindingView.layoutLoan.txtLoanMoney);
           mPresenter.userFirstPageType();
       }else {
           mBindingView.layoutLoan.txtHomeSurprise.setVisibility(View.GONE);
           mBindingView.layoutLoan.txtLoanMoney.setText("0");
           mBindingView.layoutLoanSuccess.llLoanSuccess.setVisibility(View.GONE);
           mBindingView.layoutLoan.llLoan.setVisibility(View.VISIBLE);
           mBindingView.layoutLoan.btnFastLoan.setText("立即借款");
           mBindingView.layoutLoanFangkuanzhong.llFangkuanzhong.setVisibility(View.GONE);
           txt_riskPlanPercent.setText("0元");
           txt_msgAuthMoney.setText("0元");
           txt_riskServePercent.setText("0元");
           txt_placeServeMoney.setText("0元");
           txt_allWasteMoney.setText("0元");
           txt_interestMoney.setText("0元");
           mBindingView.layoutLoan.txtHomeZonghefees.setText("0元");
           mBindingView.layoutLoan.txtHomeDaozhang.setText("0元");
       }
//        mPresenter.getMoney(mBindingView.layoutLoan.txtLoanMoney,mBindingView.layoutLoan.seekbar);
        //  mPresenter.getBannerlist(mBindingView.banner);

        if(MyApplication.getBoolean("islogin",false)){
            String InitMoney=String.valueOf(((int) Double.parseDouble(MyApplication.getString("maxMoney","3000"))+1000)/2);
            mBindingView.layoutLoan.txtLoanMoney.setText(InitMoney);
            mBindingView.layoutLoan.homeHorscaleview.setRange(1000,(int) Double.parseDouble(MyApplication.getString("maxMoney","3000")));
            mBindingView.layoutLoan.homeHorscaleview.setOnValueChangeListener(new OnValueChangeListener() {
                @Override
                public void onValueChanged(float value) {
                    int fl=(int) value/100;
                    float fl1=fl*100;
                    mBindingView.layoutLoan.txtLoanMoney.setText((String.valueOf((int)fl1)));
                    Map<String, String> map=new HashMap<>();
                    map.put("borrowMoney",String.valueOf((int)fl1));
                    map.put("limitDays","7");
                    mPresenter.getwufufee(map);
                }
            });

        }else {
            mBindingView.layoutLoan.txtLoanMoney.setText("0");
            mBindingView.layoutLoan.homeHorscaleview.setRange(0,0);
            mBindingView.layoutLoan.homeHorscaleview.setOnValueChangeListener(new OnValueChangeListener() {
                @Override
                public void onValueChanged(float value) {
                    mBindingView.layoutLoan.txtLoanMoney.setText("0");
                }
            });

        }


    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public int setContent() {
        return R.layout.fragment_home;
    }

    @Override
    public void initListener() {
        view= LayoutInflater.from(mContext).inflate(R.layout.cost_intro_dialog,null);
        txt_cancel= (TextView) view.findViewById(R.id.txt_dialog_cancel);
        txt_placeServeMoney= (TextView) view.findViewById(R.id.txt_placeServeMoney);
        txt_msgAuthMoney= (TextView) view.findViewById(R.id.txt_msgAuthMoney);
        txt_riskServePercent= (TextView) view.findViewById(R.id.txt_riskServePercent);
        txt_riskPlanPercent= (TextView) view.findViewById(R.id.txt_riskPlanPercent);
        txt_allWasteMoney= (TextView) view.findViewById(R.id.txt_allWasteMoney);
        txt_interestMoney= (TextView) view.findViewById(R.id.txt_interestMoney);
        txt_allWasteMoney.setText(300+"");
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });



    }

    @Override
    public void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(Constants.REQUESTID_0, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
                        if(type==88){
                            //  mBaseBinding.commonTitle.llRightImg.setBackgroundResource(R.mipmap.xiaoxiweidu2x);
                            setRightImg(R.mipmap.xiaoxiweidu2x, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    UIUtil.startActivity(MessageActivity.class,null);
                                    isClick=true;
                                    mBaseBinding.commonTitle.ivRightImg.setImageResource(R.mipmap.xiaoxi);
                                    MyApplication.saveBoolean("flag",isClick);
                                }
                            });
                        }else if(type==99){
                            isClick=true;
                            mBaseBinding.commonTitle.ivRightImg.setImageResource(R.mipmap.xiaoxi);
                            MyApplication.saveBoolean("flag",isClick);
                            reflsh();
                        }else if (type==5) {
                            mPresenter.userFirstPageType();


                        }

                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getInfo() {
        mPresenter.getInfo(mBindingView.layoutLoan.txtLoanMoney.getText().toString(),LimitDays+"");
    }

    @OnClick({R.id.btn_fast_loan,R.id.btn_apply_xuqi_home,R.id.btn_fast_loan1,R.id.txt_home_zonghe,R.id.lmg_home_contact,R.id.txt_home_surprise})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_fast_loan:
                if(MyApplication.getBoolean("islogin",false)){
//                    mPresenter.userIsBorrow("1200","7");
                    mPresenter.GetHomeAuthState();
                }else{
                    UIUtil.startActivity(LoginActivity.class, null);
                }

//                }

                break;
            case R.id.btn_fast_loan1:
                if (MyApplication.getString("borroworpay", "true").equals("false")) {
                    mPresenter.userIsNeedPay();
                }
                break;
//            case R.id.img_tishi:
//                showIntro();
//                break;
            case R.id.btn_apply_xuqi_home:
                mPresenter.initXuQi();
                break;
            case R.id.txt_home_zonghe://综合费用
                showIntro();
                break;
            case R.id.lmg_home_contact:
                mPresenter.getContact(getActivity());
                break;
            case R.id.txt_home_surprise:
                UIUtil.startWebHelp(getActivity(),"https://tzlapi.hongyds.com/app_center/");
                break;

        }
    }


    //主页提示
    public  void showIntro(){
        mAlertDialog=new AlertDialog.Builder(mContext).create();
        mAlertDialog.show();
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        mAlertDialog.getWindow().setContentView(view);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    @Override
    public void reflsh(){
        /*判断是借款还是还款*/
        if(MyApplication.getString("borroworpay","true").equals("true")){
            if(MyApplication.getString("loginstatus","").equals("1")) mBindingView.layoutLoan.txtHomeSurprise.setVisibility(View.GONE);
            else mBindingView.layoutLoan.txtHomeSurprise.setVisibility(View.VISIBLE);
            mBindingView.layoutLoanSuccess.llLoanSuccess.setVisibility(View.GONE);
            mBindingView.layoutLoan.llLoan.setVisibility(View.VISIBLE);
            mBindingView.layoutLoan.btnFastLoan.setText("立即借款");
            mBindingView.layoutLoanFangkuanzhong.llFangkuanzhong.setVisibility(View.GONE);
            txt_riskPlanPercent.setText(String.valueOf((MyApplication.getInt("riskPlanPercent",0))+"元"));
            txt_msgAuthMoney.setText(MyApplication.getInt("msgAuthMoney",0)+"元");
            txt_riskServePercent.setText(MyApplication.getInt("riskServePercent",0)+"元");
            txt_placeServeMoney.setText(MyApplication.getInt("placeServeMoney",0)+"元");
            txt_allWasteMoney.setText(MyApplication.getInt("allWasteMoney",0)+"元");
            txt_interestMoney.setText(UIUtil.formatintMoney(MyApplication.getString("interestMoney",""))+"元");
            mBindingView.layoutLoan.txtHomeZonghefees.setText(MyApplication.getInt("allWasteMoney",0)+"元");
            mBindingView.layoutLoan.txtHomeDaozhang.setText(UIUtil.formatintMoney(MyApplication.getString("realMoney",""))+"元");

            //    mBindingView.btnApplyXuqiHome.setVisibility(View.GONE);
        }else if(MyApplication.getString("borroworpay","true").equals("false")){
            mBindingView.layoutLoanSuccess.txtDaihuankuan.setText("待还款金额");
            mBindingView.layoutLoanSuccess.llLoanSuccess.setVisibility(View.VISIBLE);
            mBindingView.layoutLoan.llLoan.setVisibility(View.GONE);
            mBindingView.layoutLoanFangkuanzhong.llFangkuanzhong.setVisibility(View.GONE);
            mBindingView.layoutLoanSuccess.btnApplyXuqiHome.setVisibility(View.VISIBLE);
            mBindingView.layoutLoanSuccess.btnFastLoan1.setText("我要还款");
            //  mBindingView.btnFastLoan.setText("我要还款");
             /*还款信息*/
            mBindingView.layoutLoanSuccess.txtHuanMoney.setText(UIUtil.formatMoney(MyApplication.getString("needPayMoney","")));
            mBindingView.layoutLoanSuccess.txtJieMoney.setText(UIUtil.formatMoney(MyApplication.getString("borrowMoney",""))+"元");
            mBindingView.layoutLoanSuccess.txtHuanTime.setText(CommonUtils.getStringDateTwo(MyApplication.getLong("limitPayTime",0)));
            mBindingView.layoutLoanSuccess.txtApplyTime.setText(CommonUtils.getStringDate(MyApplication.getLong("gmtDatetime",0)));

            //   mBindingView.btnApplyXuqiHome.setVisibility(View.VISIBLE);
        }else if(MyApplication.getString("borroworpay","true").equals("loading")){

            mBindingView.layoutLoanSuccess.txtDaihuankuan.setText("待审核金额");
            mBindingView.layoutLoanSuccess.llLoanSuccess.setVisibility(View.VISIBLE);
            mBindingView.layoutLoan.llLoan.setVisibility(View.GONE);
            mBindingView.layoutLoanFangkuanzhong.llFangkuanzhong.setVisibility(View.GONE);
            mBindingView.layoutLoanSuccess.btnApplyXuqiHome.setVisibility(View.GONE);
            mBindingView.layoutLoanSuccess.btnFastLoan1.setText("审核中....");
            mBindingView.layoutLoanSuccess.txtHuanMoney.setText(UIUtil.formatMoney(MyApplication.getString("needPayMoney","")));
            mBindingView.layoutLoanSuccess.txtJieMoney.setText(UIUtil.formatMoney(MyApplication.getString("borrowMoney",""))+"元");
            mBindingView.layoutLoanSuccess.txtHuanTime.setText(CommonUtils.getStringDateTwo(MyApplication.getLong("limitPayTime",0)));
            mBindingView.layoutLoanSuccess.txtApplyTime.setText(CommonUtils.getStringDate(MyApplication.getLong("gmtDatetime",0)));

        }else if(MyApplication.getString("borroworpay","true").equals("fangkuan")){


            mBindingView.layoutLoanSuccess.llLoanSuccess.setVisibility(View.GONE);
            mBindingView.layoutLoan.llLoan.setVisibility(View.GONE);
            mBindingView.layoutLoanFangkuanzhong.llFangkuanzhong.setVisibility(View.VISIBLE);
            mBindingView.layoutLoanFangkuanzhong.txtLoanResultFangkuanzhong.setText("放款中");
            mBindingView.layoutLoanFangkuanzhong.txtFangkuanDetail.setText("工作人员正在放款中，请耐心等待...");
            mBindingView.layoutLoanFangkuanzhong.txtFangkuanBank.setText(UIUtil.cardTuomin(MyApplication.getString("bankNum",""),2,4));

        }else if(MyApplication.getString("borroworpay","true").equals("shenhe")){
            mBindingView.layoutLoanSuccess.llLoanSuccess.setVisibility(View.GONE);
            mBindingView.layoutLoan.llLoan.setVisibility(View.GONE);
            mBindingView.layoutLoanFangkuanzhong.llFangkuanzhong.setVisibility(View.VISIBLE);
            mBindingView.layoutLoanFangkuanzhong.txtLoanResultFangkuanzhong.setText("审核中");
            mBindingView.layoutLoanFangkuanzhong.txtFangkuanDetail.setText("工作人员正在审核中，预计2小时内完成");
            mBindingView.layoutLoanFangkuanzhong.txtFangkuanBank.setText("审核中");

        }else if(MyApplication.getString("borroworpay","true").equals("huankuanzhong")){
            mBindingView.layoutLoanSuccess.llLoanSuccess.setVisibility(View.GONE);
            mBindingView.layoutLoan.llLoan.setVisibility(View.GONE);
            mBindingView.layoutLoanFangkuanzhong.llFangkuanzhong.setVisibility(View.VISIBLE);
            mBindingView.layoutLoanFangkuanzhong.txtLoanResultFangkuanzhong.setText("还款中中");
            mBindingView.layoutLoanFangkuanzhong.txtFangkuanDetail.setText("工作人员正在还款中，预计2小时内完成");
            mBindingView.layoutLoanFangkuanzhong.txtFangkuanBank.setText("还款中");
        }else if(MyApplication.getString("borroworpay","true").equals("daidakuan")){
            mBindingView.layoutLoanSuccess.llLoanSuccess.setVisibility(View.GONE);
            mBindingView.layoutLoan.llLoan.setVisibility(View.GONE);
            mBindingView.layoutLoanFangkuanzhong.llFangkuanzhong.setVisibility(View.VISIBLE);
            mBindingView.layoutLoanFangkuanzhong.txtLoanResultFangkuanzhong.setText("待打款");
            mBindingView.layoutLoanFangkuanzhong.txtFangkuanDetail.setText("工作人员正在处理订单中，预计2小时内完成");
            mBindingView.layoutLoanFangkuanzhong.txtFangkuanBank.setText("待打款");

        }
    }

    @Override
    public void showDialog(String content) {
        firstGotoMainDialog dialog=new firstGotoMainDialog(getActivity(),content);
        dialog.show();
    }

    @Override
    public void geetInto() {
        Map<String, String> map = new HashMap<>();
//        map.put("currMoney", mBindingView.layoutLoan.txtLoanMoney.getText().toString());
        UIUtil.startActivity(LoanActivity.class, null);
    }


}
