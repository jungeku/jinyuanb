package com.sk.xjwd.mainhome.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityLoanDetailBinding;
import com.sk.xjwd.mainhome.contract.LoanDetailActivityContract;
import com.sk.xjwd.mainhome.presenter.LoanDetailActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.ConfirmDialog;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class LoanDetailActivity extends BaseActivity<LoanDetailActivityPresenter,ActivityLoanDetailBinding> implements LoanDetailActivityContract.View {

    AlertDialog mAlertDialog;
    TextView txt_cancel;
    TextView txt_placeServeMoney;
    TextView txt_msgAuthMoney;
    TextView txt_riskServePercent;
    TextView txt_riskPlanPercent;
    TextView txt_allWasteMoney;
    TextView txt_interestMoney;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_detail);
    }

    @Override
    protected void initView() {
        setTitle("借款");
        initListener();
       // mPresenter.getSignMsg();
        mPresenter.showorder(getIntent().getStringExtra("money"),getIntent().getStringExtra("time"));
        initRxBus();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
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
       txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        reflsh();
    }

    @OnClick({R.id.img_loan_detail_tishi,R.id.rl_select_coupon,R.id.btn_apply_loan,R.id.txt_loanagreement1,R.id.txt_loanagreement2})
    public void OnClick(View view){
        Map<String,String> map;
        switch (view.getId()){
            case R.id.img_loan_detail_tishi:
               showIntro();
                break;
            case R.id.rl_select_coupon:
                if( UIUtil.textisempty(MyApplication.getString("couponsaveMoney","0")).equals("0")){
                    map=new HashMap<>();
                    map.put("coupontype","2");
                    map.put("orderid",MyApplication.getString("apply_id",""));
                    UIUtil.startActivity(CouponActivity.class,map);
                }
                break;
            case R.id.btn_apply_loan:
                if(mBindingView.chLoandetail.isChecked()){
                    mPresenter.commitloanorder();
                }else{
                    UIUtil.showToast("未同意协议");
                }
                break;
            case R.id.txt_loanagreement1:
//                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(MyApplication.getString("apply_agreementUrl","")));
//                startActivity(intent1);

                break;
            case R.id.txt_loanagreement2:
//               Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(MyApplication.getString("apply_agreementTwoUrl","")));
//                startActivity(intent2);
                break;
        }
    }

    @Override
    public void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(Constants.REQUESTID_2, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
                        reflsh();
                        if(type==2){
                            mPresenter.getSignPass();
                        }
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void isTrue() {
        ConfirmDialog confirmDialog = new ConfirmDialog(mContext,true);
        confirmDialog.show();
    }

    @Override
    public void isFlase() {
        ConfirmDialog confirmDialog = new ConfirmDialog(mContext,false);
        confirmDialog.show();
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

    private void reflsh(){
        mBindingView.txtLoanMoney.setText(MyApplication.getString("apply_borrowMoney","")+"元");
        mBindingView.txtLoanTime.setText(MyApplication.getString("apply_limitDays","")+"天");
        mBindingView.txtDaozhangMoney.setText(MyApplication.getString("apply_realMoney","")+"元");
        String bankCardNum=MyApplication.getString("apply_bankCardNum","");
        mBindingView.txtDaozhangCard.setText(MyApplication.getString("apply_bankName","")+"(****"+(TextUtils.isEmpty(bankCardNum)?"":bankCardNum.substring(bankCardNum.length()-4))+")");
        mBindingView.txtZongfeitong.setText(MyApplication.getString("apply_wateMoney","")+"元");
        txt_riskPlanPercent.setText(MyApplication.getString("apply_riskPlanMoney","")+"元");
        txt_msgAuthMoney.setText(MyApplication.getString("apply_msgAuthMoney","")+"元");
        txt_riskServePercent.setText(MyApplication.getString("apply_riskServeMoney","")+"元");
        txt_placeServeMoney.setText(MyApplication.getString("apply_placeServeMoney","")+"元");
        txt_allWasteMoney.setText(MyApplication.getString("apply_wateMoney","")+"元");
        txt_interestMoney.setText(MyApplication.getString("apply_interestMoney","")+"元");
        mBindingView.txtHuanBenjing.setText(MyApplication.getString("apply_borrowMoney","")+"元");
        mBindingView.txtHuanLixi.setText(MyApplication.getString("apply_interestMoney","")+"元");
        mBindingView.txtLv.setText(MyApplication.getString("apply_interestPrecent","")+"‰");
        mBindingView.txtCouponKou.setText(UIUtil.textisempty(MyApplication.getString("couponsaveMoney",""))+"元");
        mBindingView.txtRealhuan.setText(MyApplication.getString("apply_needPayMoney","")+"元");
        mBindingView.txtLimitPayTime.setText(MyApplication.getString("apply_limitPayTime",""));
        mBindingView.txtRealPayMoney.setText(MyApplication.getString("apply_needPayMoney","")+"元");
        if( UIUtil.textisempty(MyApplication.getString("couponsaveMoney","0")).equals("0")){
            mBindingView.txtYouhuiquan.setText("暂无优惠券");
        }else{
            mBindingView.txtYouhuiquan.setText("已使用");
        }

    }
}
