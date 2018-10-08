package com.sk.xjwd.checking.fragment;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseFragment;
import com.sk.xjwd.checking.holder.AccountListModel;
import com.sk.xjwd.checking.presenter.AccountFragmentPresenter;
import com.sk.xjwd.databinding.CheckingFragmentAccountBinding;
import com.sk.xjwd.minehome.adapter.MineHomeAdapter;
import com.zyf.fwms.commonlibrary.base.baseadapter.AutoLayoutManager;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayn on 2018/9/11.
 */

public class AccountFragment extends BaseFragment<CheckingFragmentAccountBinding,AccountFragmentPresenter> {
    private MineHomeAdapter adapter;
    private int[] icons={R.mipmap.clicked_cloth,R.mipmap.clicked_commodities,R.mipmap.clicked_phonefee,R.mipmap.clicked_medical,R.mipmap.clicked_eatdrink,};
    private String[] names={"服饰","日用品","电话费","医疗","吃喝"};
    private String[] money={"1000","400","100","500","2,000"};
    private List<BaseRecyclerModel> modelList=new ArrayList<>();
    @Override
    protected void initView() {
        hideTitleBar(true);
        hideBackImg();
        mBindingView.txtPropertyExpandMoney.setText("4,000");
        mBindingView.txtAccountSum.setText("收入: 0.00元 支出: 4,000.00元");
        initRecycler();
    }

    private void initRecycler() {
        adapter = new MineHomeAdapter();
        mBindingView.rvAccount.setLoadingMoreEnabled(false);
        mBindingView.rvAccount.setPullRefreshEnabled(false);
        mBindingView.rvAccount.setLayoutManager(new AutoLayoutManager(mContext,0));
        mBindingView.rvAccount.setNestedScrollingEnabled(false);
        mBindingView.rvAccount.setHasFixedSize(false);
        mBindingView.rvAccount.setAdapter(adapter);
        initData(false);
    }

    private void initData(boolean b) {
        for(int i=0;i<names.length;i++){
            AccountListModel model=new AccountListModel();
            model.icon=icons[i];
            model.name=names[i];
            model.money=money[i];
            model.viewType=MineHomeAdapter.CHECKING_ACCOUNT_LIST;
            modelList.add(model);
        }
        adapter.addAll(modelList);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void initPresenter() {
       mPresenter.setView(this);
    }

    @Override
    public int setContent() {
        return R.layout.checking_fragment_account;
    }
}
