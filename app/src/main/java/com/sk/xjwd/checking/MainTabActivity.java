package com.sk.xjwd.checking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.base.BaseFragment;
import com.sk.xjwd.checking.activity.AddActivity;
import com.sk.xjwd.checking.fragment.AccountFragment;
import com.sk.xjwd.checking.fragment.DetailFragment;
import com.sk.xjwd.checking.fragment.PropertyFragment;
import com.sk.xjwd.checking.presenter.MaintabActivityPresenter;
import com.sk.xjwd.databinding.CheckingActivityMainBinding;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.mainhome.fragment.HomeFragment;
import com.sk.xjwd.utils.UIUtil;

import butterknife.OnClick;

/**
 * Created by mayn on 2018/9/11.
 */

public class MainTabActivity extends BaseActivity<MaintabActivityPresenter,CheckingActivityMainBinding>{

    private AccountFragment accountFragment;
    private DetailFragment detailFragment;
    private PropertyFragment propertyFragment;
    private BaseFragment mCurrFragment;//当前fragment
    private FragmentManager mFragmentManager;
    private ImageView mCurrimg;
    private boolean isadd=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checking_activity_main);
    }

    @Override
    protected void initView() {
        hideTitleBar();
        mCurrimg=mBindingView.imgPropertyPage;
        initFragment();

    }

    private void initFragment() {
        if (mFragmentManager != null) {
            return;
        }
        mFragmentManager = getSupportFragmentManager();
        propertyFragment=new PropertyFragment();

        mCurrFragment = propertyFragment;
        mFragmentManager.beginTransaction().add(R.id.checking_content, propertyFragment).commitAllowingStateLoss();

        accountFragment=new AccountFragment();
        detailFragment=new DetailFragment();
    }


    @OnClick({R.id.img_property_page,R.id.img_detail_page,R.id.img_account_page})
    void pageClick(View view){
         switch (view.getId()){
             case R.id.img_property_page:
                 changeTab(mBindingView.imgPropertyPage);
                 changeFragment(new PropertyFragment());
                 mBindingView.imgPropertyPage.setImageResource(R.mipmap.clicked_property);
                 mBindingView.imgDetailPage.setImageResource(R.mipmap.unclick_detail);
                 isadd=false;
                 mBindingView.imgAccountPage.setImageResource(R.mipmap.unclick_account);
                 break;
             case R.id.img_detail_page:
                 changeTab(mBindingView.imgDetailPage);
                 changeFragment(new DetailFragment());
                 mBindingView.imgPropertyPage.setImageResource(R.mipmap.unclick_property);
                 mBindingView.imgDetailPage.setImageResource(R.mipmap.unclick_detail);
                 isadd=false;
                 mBindingView.imgAccountPage.setImageResource(R.mipmap.unclick_account);
                 break;
             case R.id.img_account_page:
//                 if(!isadd) {
                     changeTab(mBindingView.imgAccountPage);
                     changeFragment(new AccountFragment());
                     mBindingView.imgAccountPage.setImageResource(R.mipmap.checking_add);
                     mBindingView.imgDetailPage.setImageResource(R.mipmap.unclick_detail);
                     mBindingView.imgPropertyPage.setImageResource(R.mipmap.unclick_property);
                     isadd=true;
//                 }else {
//                     UIUtil.startActivity(AddActivity.class,null);
//                 }
                 break;
         }
    }

    private void changeTab(ImageView imgPropertyPage) {
        if (mCurrimg != imgPropertyPage) {
            mCurrimg.setSelected(false);
            imgPropertyPage.setSelected(true);
            mCurrimg = imgPropertyPage;
        }
    }

    private void changeFragment(BaseFragment homeFragment) {
        if(mCurrFragment!=homeFragment){
            if(!homeFragment.isAdded()){
                mFragmentManager.beginTransaction().add(R.id.checking_content,homeFragment)
                        .hide(mCurrFragment)
                        .commitAllowingStateLoss();
            }else {
                mFragmentManager.beginTransaction().show(homeFragment).hide(mCurrFragment).commitAllowingStateLoss();
            }
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }
}
