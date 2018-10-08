package com.sk.xjwd.minehome.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityUpdateBankBinding;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.contract.UpdateBankActivityContract;
import com.sk.xjwd.minehome.presenter.UpdateBankActivityPresenter;
import com.sk.xjwd.utils.AssetsBankInfo;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import butterknife.OnClick;

public class UpdateBankActivity extends BaseActivity<UpdateBankActivityPresenter,ActivityUpdateBankBinding> implements UpdateBankActivityContract.View {

    AlertDialog mAlertDialog;
    View view;
    TextView txt_cancel;
    XRecyclerView mXRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bank);
    }

    @Override
    protected void initView() {
        setTitle("修改银行卡");
        initListener();
        initBank();
        mPresenter.initRecyclerView(mXRecyclerView);
        mBindingView.rlBankcard.setVisibility(View.GONE);
        //银行卡类型

        mBindingView.edCard.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else {
                    // 此处为失去焦点时的处理内容
                    // 此处为失去焦点时的处理内容
                    String nameOfBank = AssetsBankInfo.getNameOfBank(mContext, mBindingView.edCard.getText().toString());//获取银行卡的信息
                    if (!TextUtils.isEmpty(nameOfBank)){
                        mBindingView.rlBankcard.setVisibility(View.VISIBLE);
                        mBindingView.txtBankName.setText(nameOfBank);
                    }else {
                        mBindingView.rlBankcard.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {
        view= LayoutInflater.from(mContext).inflate(R.layout.dialog_bankcard,null);
        txt_cancel= (TextView) view.findViewById(R.id.txt_card_dialog_cancel);
        mXRecyclerView= (XRecyclerView) view.findViewById(R.id.xRecyclerView);
        mBindingView.txtZhiciBankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntro();
            }
        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
    }

    @OnClick({R.id.btn_update_bankcard})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_update_bankcard:

                String name=mBindingView.edName.getText().toString();
                String ID=mBindingView.edID.getText().toString();
                String bankcard=mBindingView.edCard.getText().toString();
                String phone=mBindingView.edPhone.getText().toString();
                if(TextUtils.isEmpty(name)){
                    UIUtil.showToast("请输入姓名");
                }else if(TextUtils.isEmpty(ID)){
                    UIUtil.showToast("请输入身份证号码");
                }else if(TextUtils.isEmpty(bankcard)){
                    UIUtil.showToast("请输入银行卡号");
                }else if(TextUtils.isEmpty(phone)){
                    UIUtil.showToast("请输入预留手机号码");
                }else{
				
                    mPresenter.postData(name,ID,bankcard,phone);

                 //  initBank(name,ID,bankcard,phone);
                }
                break;
        }
    }
    private void initBank() {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.BankInfo);
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
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        mBindingView.edName.setText(object.getJSONObject("data").getString("name"));
                        mBindingView.edID.setText(object.getJSONObject("data").getString("idcardno"));
                        mBindingView.edCard.setText(object.getJSONObject("data").getString("bankcardno"));
                        mBindingView.edPhone.setText(object.getJSONObject("data").getString("bankPhone"));
                        //     {"code":"SUCCESS","data":{"id":111,"userId":232,"user":null,"bankName":null,"cardname":null,"cardtype":null,"bankcardno":"6222081407000281363","idcardno":"352203199007153812","birthday":null,"address":null,"bankPhone":null,"name":"王孔奇","mobileCity":null,"gmtDatetime":"2017-11-22 20:28:32","uptDatetime":"2017-11-22 20:28:32"},"msg":"成功","success":true}

                        if (!TextUtils.isEmpty(mBindingView.edCard.getText().toString())){

                            String nameOfBank = AssetsBankInfo.getNameOfBank(mContext, mBindingView.edCard.getText().toString());//获取银行卡的信息
                            if (!TextUtils.isEmpty(nameOfBank)){
                                mBindingView.txtBankName.setText(nameOfBank);
                                mBindingView.rlBankcard.setVisibility(View.VISIBLE);
                            }else {
                                mBindingView.rlBankcard.setVisibility(View.GONE);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();

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
}
