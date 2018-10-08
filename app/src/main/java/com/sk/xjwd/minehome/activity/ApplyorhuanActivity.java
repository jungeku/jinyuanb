package com.sk.xjwd.minehome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityApplyorhuanResultBinding;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.minehome.presenter.ApplyorhuanActivityPresenter;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import butterknife.OnClick;

/**
 * Created by mayn on 2018/9/3.
 */

public class ApplyorhuanActivity extends BaseActivity<ApplyorhuanActivityPresenter, ActivityApplyorhuanResultBinding> {

    String huanorxuqi = "";
    String successorfail = "";
    String strmoney = "";
    String renzheng = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyorhuan_result);
    }

    @Override
    protected void initView() {
        huanorxuqi = getIntent().getStringExtra("huanorxuqi");
        successorfail = getIntent().getStringExtra("successorfail");
        strmoney = getIntent().getStringExtra("money");
        renzheng = getIntent().getStringExtra("renzheng");
        if(huanorxuqi!=null && successorfail !=null  && strmoney !=null) {

            if (huanorxuqi.equals("huan") && successorfail.equals("success")) {
                //还款成功
                setTitle("还款结果");
                mBindingView.imgApplyorhuan.setImageDrawable(getResources().getDrawable(R.mipmap.success));
                mBindingView.txtApplyorhuanTitle.setText("订单还款申请成功" );
//                mBindingView.txtApplyorhuanTitleTime.setText("");
//                mBindingView.txtApplyorhuanHuanmoney.setText("");
//                mBindingView.txtApplyorhuanCoupon.setText("");
//                mBindingView.rlApplyorhuanCoupon.setVisibility(View.VISIBLE);
//                mBindingView.rlApplyorhuanHuanmoney.setVisibility(View.VISIBLE);
            } else if (huanorxuqi.equals("huan") && successorfail.equals("fail")) {
//            还款失败
                setTitle("还款结果");
                mBindingView.imgApplyorhuan.setImageDrawable(getResources().getDrawable(R.mipmap.fail));
                mBindingView.txtApplyorhuanTitle.setText("订单还款申请失败");
//                mBindingView.txtApplyorhuanTitleTime.setText("账户扣款失败");
//                mBindingView.rlApplyorhuanCoupon.setVisibility(View.GONE);
//                mBindingView.rlApplyorhuanHuanmoney.setVisibility(View.GONE);
            } else if (huanorxuqi.equals("xuqi") && successorfail.equals("success")) {
//            续期成功
                setTitle("申请续期");
                mBindingView.imgApplyorhuan.setImageDrawable(getResources().getDrawable(R.mipmap.success));
                mBindingView.txtApplyorhuanTitle.setText("订单续期申请成功");
//                mBindingView.txtApplyorhuanTitleTime.setText("");
//                mBindingView.rlApplyorhuanCoupon.setVisibility(View.GONE);
//                mBindingView.rlApplyorhuanHuanmoney.setVisibility(View.GONE);
            } else if (huanorxuqi.equals("xuqi") && successorfail.equals("fail")) {
//            续期失败
                setTitle("申请续期");
                mBindingView.imgApplyorhuan.setImageDrawable(getResources().getDrawable(R.mipmap.fail));
                mBindingView.txtApplyorhuanTitle.setText("订单续期申请失败");
//                mBindingView.txtApplyorhuanTitleTime.setText("账户扣款失败");
//                mBindingView.rlApplyorhuanCoupon.setVisibility(View.GONE);
//                mBindingView.rlApplyorhuanHuanmoney.setVisibility(View.GONE);
            }
        }else {
            if (renzheng != null && renzheng.equals("renzheng")) {
                //认证结果
                setTitle("认证结果");
                mBindingView.imgApplyorhuan.setImageDrawable(getResources().getDrawable(R.mipmap.success));
                mBindingView.txtApplyorhuanTitle.setText("恭喜你提交的认证成功");
//                mBindingView.txtApplyorhuanTitleTime.setVisibility(View.GONE);
//                mBindingView.rlApplyorhuanCoupon.setVisibility(View.GONE);
//                mBindingView.rlApplyorhuanHuanmoney.setVisibility(View.GONE);
                mBindingView.btnApplyorhuanSure.setText("去借钱");
            }
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick(R.id.btn_applyorhuan_sure)
    public void onClick(){
        if(renzheng!=null && renzheng.equals("renzheng")) {
            Intent intent=new Intent(this,MainActivity.class);
//            intent.putExtra("renzheng","renzheng");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
//            UIUtil.startActivity(MainActivity.class, null);
//            RxBus.getDefault().post(Constants.REQUESTID_0, 5);
        }else {
            UIUtil.startActivity(MainActivity.class, null);
            RxBus.getDefault().post(Constants.REQUESTID_0, 5);
        }

    }
}
