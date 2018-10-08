package com.sk.xjwd.mainhome.fragment;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseFragment;
import com.sk.xjwd.databinding.LayoutDailiBinding;
import com.sk.xjwd.mainhome.contract.HomedailiFragmentContact;
import com.sk.xjwd.mainhome.presenter.HomedailiFragemnrPresenter;

/**
 * Created by Administrator on 2017/11/13.
 */

public class HomedailiFragment extends BaseFragment<LayoutDailiBinding,HomedailiFragemnrPresenter> implements HomedailiFragmentContact.View {


    @Override
    protected void initView() {
        hideTitleBar(false);
        hideBackImg();
        setTitle("发现");
        mPresenter.getBannerlist(mBindingView.banner);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public int setContent() {
        return R.layout.layout_daili;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initRxBus() {

    }
}
