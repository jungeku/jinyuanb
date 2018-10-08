package com.sk.xjwd.checking.fragment;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseFragment;
import com.sk.xjwd.checking.presenter.DetailFragmentPresenter;
import com.sk.xjwd.databinding.CheckingFragmentDetailBinding;

/**
 * Created by mayn on 2018/9/11.
 */

public class DetailFragment extends BaseFragment<CheckingFragmentDetailBinding,DetailFragmentPresenter>{
    @Override
    protected void initView() {
       mBindingView.imgDetailDetail.setImageResource(R.mipmap.expanddetail);
       mBindingView.imgDetailPie.setImageResource(R.mipmap.expandpie);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public int setContent() {
        return R.layout.checking_fragment_detail;
    }
}
