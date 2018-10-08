package com.sk.xjwd.minehome.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Toast;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityLoanRecordBinding;
import com.sk.xjwd.minehome.contract.LoanRecordActivityContract;
import com.sk.xjwd.minehome.presenter.LoanRecordActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class LoanRecordActivity extends BaseActivity<LoanRecordActivityPresenter,ActivityLoanRecordBinding> implements LoanRecordActivityContract.View{

    List<String> titles=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_record);
    }

    @Override
    protected void initView() {
        setTitle("借款记录");
        titles.add("全部");
        titles.add("未申请");
        titles.add("审核中");
        titles.add("待打款");
        titles.add("待还款");
//        titles.add("容限期中");
        titles.add("已逾期");
        titles.add("已还款");
        titles.add("审核失败");
//        titles.add("坏账");
        titles.add("放款中");
        mPresenter.initRecyclerView(mBindingView.xRecyclerView,mBindingView.llLoanrecordNull,0);
//        ViewCompat.setElevation(mTabTl, 10);
//        mTabTl.setupWithViewPager(mContentVp);
//        for (int i = 0; i < tabIndicators.size(); i++) {
//            TabLayout.Tab itemTab = mTabTl.getTabAt(i);
//            if (itemTab!=null){
//                itemTab.setCustomView(R.layout.item_tab_layout_custom);
//                TextView itemTv = (TextView) itemTab.getCustomView().findViewById(R.id.tv_menu_item);
//                itemTv.setText(tabIndicators.get(i));
//            }
//        }
//        mTabTl.getTabAt(0).getCustomView().setSelected(true);


        mBindingView.tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mBindingView.tab.setSelectedTabIndicatorHeight(2);
        //tab居中显示
        mBindingView.tab.setTabGravity(TabLayout.GRAVITY_CENTER);
        //tab的字体选择器,默认Red,选择时lanse
        mBindingView.tab.setTabTextColors(Color.parseColor("#037BFF"), Color.parseColor("#FB5F48"));
        //tab的下划线颜色,默认是粉红色
        mBindingView.tab.setSelectedTabIndicatorColor(Color.parseColor("#FB5F48"));
        for (int i = 0; i < titles.size(); i++) {
            //添加tab
            mBindingView.tab.addTab(mBindingView.tab.newTab().setText(titles.get(i)));
        }
        mBindingView.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBindingView.llLoanrecordNull.setVisibility(View.GONE);
                mPresenter.initRecyclerView(mBindingView.xRecyclerView,mBindingView.llLoanrecordNull,tab.getPosition());
//                mPresenter.initRecyclerView(mBindingView.xRecyclerView);
//                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }
}
