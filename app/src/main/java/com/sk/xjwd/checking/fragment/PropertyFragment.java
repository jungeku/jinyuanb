package com.sk.xjwd.checking.fragment;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseFragment;
import com.sk.xjwd.checking.activity.PropertyFenxiActivity;
import com.sk.xjwd.checking.presenter.PropertyFragmentPresenter;
import com.sk.xjwd.databinding.CheckingFragmentPropertyBinding;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.minehome.adapter.MineHomeAdapter;
import com.sk.xjwd.utils.UIUtil;

import butterknife.OnClick;

/**
 * Created by mayn on 2018/9/11.
 */

public class PropertyFragment extends BaseFragment<CheckingFragmentPropertyBinding,PropertyFragmentPresenter> {
    private MineHomeAdapter adapter;
    @Override
    protected void initView() {
        hideTitleBar(true);
        hideBackImg();
        mBindingView.txtPropertyMoney.setText("15,000");
    }


    @OnClick(R.id.img_property_head_rightarrow)
    void onClick(){
        UIUtil.startActivity(PropertyFenxiActivity.class,null);
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public int setContent() {
        return R.layout.checking_fragment_property;
    }
}
