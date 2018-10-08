package com.sk.xjwd.minehome.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityRecordDetailBinding;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.contract.RecordDetailActivityContract;
import com.sk.xjwd.minehome.presenter.RecordDetailActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class RecordDetailActivity extends BaseActivity<RecordDetailActivityPresenter,ActivityRecordDetailBinding> implements RecordDetailActivityContract.View {

    AlertDialog mAlertDialog;
    View view;
    TextView txt_cancel;
    TextView txt_placeServeMoney;
    TextView txt_msgAuthMoney;
    TextView txt_riskServePercent;
    TextView txt_riskPlanPercent;
    TextView txt_allWasteMoney;
    TextView txt_interestMoney;
    String orderstate="";
    private int stepicon[]={R.mipmap.step_moreren,R.mipmap.step_xuanzhong,R.mipmap.yellow_point};
    private int xainicon[]={R.drawable.shape_xian_n,R.drawable.shape_xian_y};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
    }

    @Override
    protected void initView() {
        setTitle("借款详情");
        initListener();
        initRxBus();
        reflsh();
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }
    private String orderid;
    @Override
    public void initListener() {
        orderid=getIntent().getStringExtra("id");
        Log.i("sddddd", "initListener: "+orderid);
        mPresenter.ShowOrderDetail(orderid);
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
        mBindingView.laySumCost.imgRecordDetailTishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntro();
            }
        });
        mBindingView.layXieyi.txtAgreement1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(MyApplication.getString("record_agreementUrl","")));
                startActivity(intent1);

            }
        });
        mBindingView.layXieyi.txtAgreement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(MyApplication.getString("record_agreementTwoUrl","")));
                startActivity(intent1);
            }
        });
    }

    @OnClick({R.id.txt_feedback,R.id.btn_fast_huan,R.id.btn_apply_xuqi})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.txt_feedback:
                UIUtil.startActivity(RepayFeedbackActivity.class,null);
                break;
            case R.id.btn_fast_huan:
                Map<String,String> map=new HashMap<>();
                if(MyApplication.getInt("record_orderStatus",1)==3){
                    orderstate="待还款";
                }else if(MyApplication.getInt("record_orderStatus",1)==5){
                    orderstate="已逾期";
                }
                map.put("orderstate",orderstate);
                UIUtil.startActivity(RenewalDebitActivity.class,map);
                break;
            case R.id.btn_apply_xuqi:
//                 UIUtil.startActivity(RenewalActivity.class,null);
//                Intent intent = new Intent(this, RenewalActivity.class);
//                intent.putExtra("int",orderid);
//                startActivity(intent);
                initXuQi();
                break;
        }
    }
    private void initXuQi() {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.xuqiInfo);
        util.putParam("id",orderid);
        util.putParam("limitDays","7");
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
                Log.e("loginss", ">>>>>>>>>>>>>>>>>记录详情>>>>>>>>>>>>>>");
                Log.e("loginss",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject recordObject=object.getJSONObject("data");
                        Intent intent = new Intent(mContext, RenewalActivity.class);
                        intent.putExtra("int",orderid);
                        startActivity(intent);

                    }else{
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
    @Override
    public void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(Constants.REQUESTID_3, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
                        reflsh();
                    }
                });
        addSubscription(subscribe);
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

    public void reflsh(){

        switch (MyApplication.getInt("record_orderStatus",1)){
            case 1://审核中（申请提交成功）
                mBindingView.layRecordStep.txtPassContent.setText("您的借款申请正在审核中，请耐心等待");
                mBindingView.layRecordStep.txtPassState.setText("审核中");
                ShowStep(2,1,2);
                mBindingView.layRecordStep.txtPassState.setTextColor(getResources().getColor(R.color.examine));
                mBindingView.layRecordStep.txtStep1Apply.setText("申请提交成功"+MyApplication.getString("record_gmtDatetime",""));
                mBindingView.layRecordStep.txtApplyInfo.setText("申请借款"+MyApplication.getString("record_borrowMoney","")+"元，期限"+MyApplication.getString("record_limitDays","")+"天");
                mBindingView.llRecordDetailFeedback.setVisibility(View.GONE);
                mBindingView.layHuanInfo.llHuanInfo.setVisibility(View.GONE);
                mBindingView.llDaiReturnAll.setVisibility(View.GONE);
                mBindingView.llRecordDetailYuqi.setVisibility(View.GONE);
                break;
            case 2://待打款（审核通过）
                mBindingView.layRecordStep.txtPassContent.setText("恭喜您通过风控审核");
                mBindingView.layRecordStep.txtStep1Apply.setText("申请提交成功"+MyApplication.getString("record_gmtDatetime",""));
                mBindingView.layRecordStep.txtPassState.setText("审核通过");
                mBindingView.layRecordStep.txtPassState.setTextColor(getResources().getColor(R.color.color333333));
                mBindingView.layRecordStep.txtHuanState.setText("已还款");
                mBindingView.layRecordStep.txtHuanContent.setText("我们已经收到您的还款");
                mBindingView.layRecordStep.txtApplyInfo.setText("申请借款"+MyApplication.getString("record_borrowMoney","")+"元，期限"+MyApplication.getString("record_limitDays","")+"天");
                mBindingView.layRecordStep.txtBankname.setText("打款至"+MyApplication.getString("record_bankName","")+"("+MyApplication.getString("record_bankCard_weihao","")+")");
                ShowStep(1,0,2);
                mBindingView.layHuanInfo.rlYuqifei.setVisibility(View.GONE);
                mBindingView.llDaiReturnAll.setVisibility(View.GONE);
                mBindingView.llRecordDetailYuqi.setVisibility(View.GONE);
                break;
            case 3://待还款（打款成功）
                ShowStep(0,1,4);
                mBindingView.layRecordStep.txtStep1Apply.setText("申请提交成功"+MyApplication.getString("record_gmtDatetime",""));
                mBindingView.layRecordStep.txtPassContent.setText("您本次的申请已通过审核，请等待放款");
                mBindingView.layRecordStep.txtPassState.setText("审核通过");
                mBindingView.layRecordStep.txtPassState.setTextColor(getResources().getColor(R.color.color333333));
                mBindingView.layRecordStep.txtHuanState.setText("待还款");
                mBindingView.layRecordStep.txtHuanState.setTextColor(getResources().getColor(R.color.color333333));
                mBindingView.layRecordStep.txtHuanContent.setText("请于"+CommonUtils.getStringDateTwo(MyApplication.getLong("limitPayTime",0))+"进行还款操作");

                mBindingView.layRecordStep.txtApplyInfo.setText("申请借款"+MyApplication.getString("record_borrowMoney","")+"元，期限"+MyApplication.getString("record_limitDays","")+"天");
                mBindingView.layRecordStep.txtBankname.setText("打款至"+MyApplication.getString("record_bankName","")+"("+MyApplication.getString("record_bankCard_weihao","")+")");
                mBindingView.layHuanInfo.rlYuqifei.setVisibility(View.GONE);
                mBindingView.layHuanInfo.llHuanInfo.setVisibility(View.VISIBLE);
                mBindingView.layXieyi.llXieyi.setVisibility(View.VISIBLE);
                mBindingView.laySumCost.rlZonghefei.setVisibility(View.VISIBLE);
                mBindingView.llDaiReturnAll.setVisibility(View.VISIBLE);
                mBindingView.llNowRenwal.setVisibility(View.VISIBLE);
                mBindingView.llDaiReturn.setVisibility(View.VISIBLE);
                mBindingView.llRecordDetailYuqi.setVisibility(View.GONE);
                break;
            case 4://容限期中
                yuqi("容限期中");
                break;
            case 5://已逾期
                yuqi("已逾期");
                break;
            case 6://已还款
                ShowStep(0,0,4);
                mBindingView.layRecordStep.txtPassContent.setText("您本次的申请已通过审核，请等待放款");
                mBindingView.layRecordStep.txtPassState.setText("审核通过");
                mBindingView.layRecordStep.txtPassState.setTextColor(getResources().getColor(R.color.color333333));
                mBindingView.layRecordStep.txtHuanState.setText("已还款");
                mBindingView.layRecordStep.txtHuanState.setTextColor(getResources().getColor(R.color.color333333));
                mBindingView.layRecordStep.txtStep1Apply.setText("申请提交成功"+MyApplication.getString("record_gmtDatetime",""));
                mBindingView.layRecordStep.txtHuanContent.setText("于"+MyApplication.getString("record_realPayTime","")+"还款成功");
                mBindingView.layRecordStep.txtApplyInfo.setText("申请借款"+MyApplication.getString("record_borrowMoney","")+"元，期限"+MyApplication.getString("record_limitDays","")+"天");
                mBindingView.layRecordStep.txtBankname.setText("打款至"+MyApplication.getString("record_bankName","")+"("+MyApplication.getString("record_bankCard_weihao","")+")");
                mBindingView.layHuanInfo.rlYuqifei.setVisibility(View.GONE);
                mBindingView.llDaiReturnAll.setVisibility(View.GONE);
                mBindingView.llRecordDetailFeedback.setVisibility(View.VISIBLE);
                mBindingView.layHuanInfo.llHuanInfo.setVisibility(View.VISIBLE);
                mBindingView.llRecordDetailYuqi.setVisibility(View.GONE);
                break;
            case 7://审核失败
                ShowStep(1,0,2);
                mBindingView.layRecordStep.txtStep1Apply.setText("申请提交成功"+MyApplication.getString("record_gmtDatetime",""));
                mBindingView.layRecordStep.txtApplyInfo.setText("申请借款"+MyApplication.getString("record_borrowMoney","")+"元，期限"+MyApplication.getString("record_limitDays","")+"天");
                mBindingView.layRecordStep.txtPassContent.setText("抱歉您提交的借款申请未通过审核");
                mBindingView.layRecordStep.txtPassState.setText("审核未通过");
                mBindingView.layRecordStep.txtPassState.setTextColor(getResources().getColor(R.color.public_orange));
                mBindingView.llRecordDetailFeedback.setVisibility(View.GONE);
                mBindingView.layHuanInfo.llHuanInfo.setVisibility(View.GONE);
                mBindingView.layXieyi.llXieyi.setVisibility(View.GONE);
                mBindingView.laySumCost.rlZonghefei.setVisibility(View.GONE);
                mBindingView.llDaiReturnAll.setVisibility(View.GONE);
                mBindingView.llRecordDetailYuqi.setVisibility(View.GONE);
                break;
            case 8://坏账
                yuqi("坏账");
                break;
            case 9://放款中
                mBindingView.layRecordStep.txtStep1Apply.setText("申请提交成功"+MyApplication.getString("record_gmtDatetime",""));
                mBindingView.layRecordStep.txtPassContent.setText("正在放款至"+MyApplication.getString("record_bankCardNum","")+"银行卡，请耐心等待");
                mBindingView.layRecordStep.txtPassState.setText("放款中");
                mBindingView.layRecordStep.txtHuanState.setText("已还款");
                mBindingView.layRecordStep.txtHuanContent.setText("我们已经收到您的还款");
                mBindingView.layRecordStep.txtApplyInfo.setText("申请借款"+MyApplication.getString("record_borrowMoney","")+"元，期限"+MyApplication.getString("record_limitDays","")+"天");
                mBindingView.layRecordStep.txtBankname.setText("打款至"+MyApplication.getString("record_bankName","")+"("+MyApplication.getString("record_bankCard_weihao","")+")");
                ShowStep(1,0,2);
                mBindingView.layHuanInfo.rlYuqifei.setVisibility(View.GONE);
                mBindingView.llDaiReturnAll.setVisibility(View.GONE);
                mBindingView.llRecordDetailFeedback.setVisibility(View.GONE);
                mBindingView.layHuanInfo.llHuanInfo.setVisibility(View.GONE);
                mBindingView.layXieyi.llXieyi.setVisibility(View.VISIBLE);
                mBindingView.laySumCost.rlZonghefei.setVisibility(View.VISIBLE);
                break;
        }
        //借款信息
        mBindingView.layLoanInfo.orderNumber.setText("借款编号:"+MyApplication.getString("record_orderNumber",""));//借款编号
        mBindingView.layLoanInfo.txtApplyTime.setText(MyApplication.getString("record_gmtDatetime",""));//申请时间
        mBindingView.layLoanInfo.txtLoanMoney.setText(MyApplication.getString("record_borrowMoney","")+"元");//借款金额
        mBindingView.layLoanInfo.txtLoanTime.setText(MyApplication.getString("record_limitDays","")+"天");//借款期限
        mBindingView.layLoanInfo.txtDaozhangMoney.setText(MyApplication.getString("record_realMoney","")+"元");//到账金额
        mBindingView.layLoanInfo.txtDaozhangCard.setText(MyApplication.getString("record_bankName","")+"(****"+MyApplication.getString("record_bankCard_weihao","")+")");//到账银行卡
        mBindingView.layLoanInfo.txtLv.setText(MyApplication.getString("record_interestPrecent","")+"‰");//利率
        mBindingView.laySumCost.txtZongfeitong.setText(MyApplication.getString("record_wateMoney","")+"元");//综合费用
        //还款信息
        mBindingView.layHuanInfo.txtHuanBenjing.setText(MyApplication.getString("record_needPayMoney","")+"元");//应还本金
        mBindingView.layHuanInfo.txtHuanLixi.setText(MyApplication.getString("record_interestMoney","")+"元");//应还利息
        mBindingView.layHuanInfo.txtCouponKou.setText(UIUtil.textisempty(MyApplication.getString("record_saveMoney",""))+"元");//优惠券抵扣
        if(CommonUtils.isEmpty(MyApplication.getString("record_realPayTime",""))){
            mBindingView.layHuanInfo.txtShijihuankaunri.setText("----");//实际还款日
        }else{
            mBindingView.layHuanInfo.txtShijihuankaunri.setText(MyApplication.getString("record_realPayTime",""));//实际还款日
        }
        mBindingView.layHuanInfo.txtHuanSummoney.setText(MyApplication.getString("record_needPayMoney","")+"元");//合计应还金额
        mBindingView.txtDaiReturnMoney.setText(MyApplication.getString("record_needPayMoney","")+"元");
        //综合费用提示
        txt_riskPlanPercent.setText(MyApplication.getString("record_riskPlanMoney","")+"元");
        txt_msgAuthMoney.setText(MyApplication.getString("record_msgAuthMoney","")+"元");
        txt_riskServePercent.setText(MyApplication.getString("record_riskServeMoney","")+"元");
        txt_placeServeMoney.setText(MyApplication.getString("record_placeServeMoney","")+"元");
        txt_allWasteMoney.setText(MyApplication.getString("record_wateMoney","")+"元");
        txt_interestMoney.setText(MyApplication.getString("record_interestMoney","")+"元");
    }

    public void yuqi(String step4){
        ShowStep(0,1,4);
        mBindingView.layRecordStep.txtPassContent.setText("您本次的申请已通过审核，请等待放款");
        mBindingView.layRecordStep.txtPassState.setText("审核通过");
        mBindingView.layRecordStep.txtPassState.setTextColor(getResources().getColor(R.color.color333333));
        mBindingView.layRecordStep.txtStep1Apply.setText("申请提交成功"+MyApplication.getString("record_gmtDatetime",""));
        mBindingView.layRecordStep.txtHuanState.setText(step4);
        mBindingView.layRecordStep.txtHuanState.setTextColor(getResources().getColor(R.color.public_red));
        mBindingView.layRecordStep.txtApplyInfo.setText("申请借款"+MyApplication.getString("record_borrowMoney","")+"元，期限"+MyApplication.getString("record_limitDays","")+"天");
        mBindingView.layRecordStep.txtHuanContent.setText("*逾期还款将会影响证信和芝麻信用");
        mBindingView.layRecordStep.txtBankname.setText("打款至"+MyApplication.getString("record_bankName","")+"("+MyApplication.getString("record_bankCard_weihao","")+")");
        mBindingView.txtRecordDetailYuqiDay.setText("已逾期2天，请及时还款");
        mBindingView.layHuanInfo.rlYuqifei.setVisibility(View.VISIBLE);
        mBindingView.layHuanInfo.txtYuqifeiyong.setText(MyApplication.getString("record_overdueMoney","")+"元");//逾期费用
        mBindingView.llNowRenwal.setVisibility(View.VISIBLE);
        mBindingView.btnApplyXuqi.setVisibility(View.GONE);
        mBindingView.llDaiReturn.setVisibility(View.GONE);
        mBindingView.llDaiReturnAll.setVisibility(View.VISIBLE);
        mBindingView.layHuanInfo.llHuanInfo.setVisibility(View.VISIBLE);
        mBindingView.llRecordDetailYuqi.setVisibility(View.VISIBLE);

    }

    public void ShowStep(int step2,int step4,int is2or4){
        mBindingView.layRecordStep.imgStep2.setImageResource(stepicon[step2]);
        mBindingView.layRecordStep.imgStep4.setImageResource(stepicon[step4]);
        if(is2or4==2){
            mBindingView.layRecordStep.llStep3.setVisibility(View.GONE);
            mBindingView.layRecordStep.llStep4.setVisibility(View.GONE);
            mBindingView.layRecordStep.imgStep4.setVisibility(View.GONE);
            mBindingView.layRecordStep.imgStep3.setVisibility(View.GONE);
            mBindingView.layRecordStep.imgStepXian2.setVisibility(View.GONE);
            mBindingView.layRecordStep.imgStepXian3.setVisibility(View.GONE);
        }else if(is2or4==4){
            mBindingView.layRecordStep.llStep3.setVisibility(View.VISIBLE);
            mBindingView.layRecordStep.llStep4.setVisibility(View.VISIBLE);
            mBindingView.layRecordStep.imgStep4.setVisibility(View.VISIBLE);
            mBindingView.layRecordStep.imgStep3.setVisibility(View.VISIBLE);
            mBindingView.layRecordStep.imgStepXian2.setVisibility(View.VISIBLE);
            mBindingView.layRecordStep.imgStepXian3.setVisibility(View.VISIBLE);
        }
    }
}
