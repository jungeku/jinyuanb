package com.sk.xjwd.mainhome.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityLoanBinding;
import com.sk.xjwd.mainhome.presenter.LoanActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

/**
 * Created by mayn on 2018/9/1.
 */

public class LoanActivity extends BaseActivity<LoanActivityPresenter,ActivityLoanBinding> {

    String currmoney="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
    }

    @Override
    protected void initView() {
        setTitle("借款");
//        currmoney=getIntent().getStringExtra("currMoney");
        currmoney="1200";
        mPresenter.getUnFinishOrder(currmoney,"7");

//        mBindingView.txtLoanTotalmoney.setText(MyApplication.getString("maxMoney","5000"));
        mBindingView.txtLoanTotalmoney.setText("1200");
//        mBindingView.txtLoanAllmoney.setText("授权总额:"+MyApplication.getString("maxMoney","3000"));
        mBindingView.txtLoanAllmoney.setText("授权总额:"+"1200");
//        if(currmoney!=null) mBindingView.edLoanMoney.setText(currmoney);
        currmoney="1200";
        mBindingView.edLoanMoney.setText(currmoney);
        final SpannableStringBuilder style = new SpannableStringBuilder();

        //设置文字
        style.append("点击“立即申请”即表示我同意签约《借款协议》《授权服务协议》");

        //设置部分文字点击事件
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if(MyApplication.getString("apply_agreementUrl","")!=null) {
//                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(MyApplication.getString("apply_agreementUrl", "")));
//                    startActivity(intent1);
                }

            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getString("apply_agreementTwoUrl","")!=null) {
//                    Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(MyApplication.getString("apply_agreementTwoUrl", "")));
//                    startActivity(intent2);
                }
            }
        };
        style.setSpan(clickableSpan1, 16, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(clickableSpan2, 23, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBindingView.txtLoanHips.setText(style);

        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.public_red));
        style.setSpan(foregroundColorSpan, 16, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //配置给TextView
        mBindingView.txtLoanHips.setMovementMethod(LinkMovementMethod.getInstance());
        mBindingView.txtLoanHips.setText(style);
    }

    @Override
    protected void initPresenter() {

        mPresenter.setView(this);
    }

    @OnClick({R.id.rl_loan_coupon,R.id.btn_loan_apply})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_loan_coupon:
                Map map=new HashMap<>();
                map.put("coupontype","1");
                map.put("orderid",MyApplication.getString("apply_id",""));
                UIUtil.startActivity(CouponActivity.class,map);
                break;
            case R.id.btn_loan_apply:
                mPresenter.userIsBorrow(mBindingView.edLoanMoney.getText().toString(), "7");
                break;
        }
    }

    public void showBankCardInfo(){
//        mBindingView.txtLoanCardnum.setText(MyApplication.getString("bankCnName","")+"("+MyApplication.getString("bankNum","").substring(MyApplication.getString("bankCardNum","").length()-4)+")");
        mBindingView.txtLoanCardnum.setText(MyApplication.getString("apply_bankName","")+
                "("+UIUtil.cardTuomin(MyApplication.getString("apply_bankCardNum",""),2,4)+")");
    }


    public void isSuccess() {
        //订单申请成功，到审核界面显示订单信息
        UIUtil.startActivity(LoanResultActivity.class,null);

    }

    public void isFail() {
        ////订单申请失败，到主界面显示订单信息
        UIUtil.showToast("申请失败");
    }
}
