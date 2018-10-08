package com.sk.xjwd.minehome.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioGroup;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityFeedbackBinding;
import com.sk.xjwd.minehome.contract.FeedbackActivityContract;
import com.sk.xjwd.minehome.presenter.FeedbackActivityPresenter;

public class FeedbackActivity extends BaseActivity<FeedbackActivityPresenter,ActivityFeedbackBinding> implements FeedbackActivityContract.View {

    int type=1;
    String phone="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    @Override
    protected void initView() {
        setTitle("意见反馈");
        initListener();
        phone= MyApplication.getString("phone","");
        mBindingView.edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               int start = mBindingView.edContent.getSelectionStart();
               int end = mBindingView.edContent.getSelectionEnd();
               int length=editable.length();
               mBindingView.txtFeedbackCounts.setText(length+"");

            }
        });
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void initListener() {
        mBindingView.rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_suggest://建议1
                        type=1;
                        break;
                    case R.id.rb_renzheng://认证2
                        type=2;
                        break;
                    case R.id.rb_jiekuan://借款3
                        type=3;
                        break;
                    case R.id.rb_huankuan://还款4
                        type=4;
                        break;

                }
            }
        });
        mBindingView.btnFeedbackCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBindingView.choosephonto.getData()!=null && mBindingView.choosephonto.getData().size()>0) {
                    mPresenter.postData(type, mBindingView.edContent.getText().toString(), mBindingView.choosephonto.getData(), phone);
                }else {
                    mPresenter.commituserfeedback(type,mBindingView.edContent.getText().toString(),null,phone);
                }
            }
        });
    }
}
