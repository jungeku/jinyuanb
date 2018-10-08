package com.sk.xjwd.minehome.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.account.activity.LoginActivity;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.AcitivityMineInfoBinding;
import com.sk.xjwd.http.AuthInfoDetailsResponse;
import com.sk.xjwd.minehome.presenter.MineInfoActivityPresenter;
import com.sk.xjwd.utils.AssetsBankInfo;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import butterknife.OnClick;

/**
 * Created by mayn on 2018/9/1.
 */

public class MineInfoActivity extends BaseActivity<MineInfoActivityPresenter,AcitivityMineInfoBinding> {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_mine_info);
    }

    @Override
    protected void initView() {
        setTitle("账户信息");
        mPresenter.getMyUserInfo();
    }

    @Override
    protected void initPresenter() {

        mPresenter.setView(this);
    }

    public void showMyInfo(AuthInfoDetailsResponse info) {
        mBindingView.txtMineInfoRealname.setText(info.getName());
        mBindingView.txtMineInfoPhonenum.setText(info.getBankPhone());
    }

    @OnClick({R.id.img_mine_update_psw,R.id.txt_mine_info_exit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_mine_update_psw:
                UIUtil.startActivity(UpdatePasswordActivity.class,null);
                break;
            case R.id.txt_mine_info_exit:
                dialog();
                break;
        }
    }

    private void dialog(){
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("提示")//设置对话框的标题
                .setMessage("确认退出登录")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", (dialog12, which) -> dialog12.dismiss())
                .setPositiveButton("确定", (dialog1, which) -> {
                    MyApplication.saveBoolean("islogin",false);//false未登录true登录
                    UIUtil.startActivity(LoginActivity.class,null);
                    this.finish();
                    dialog1.dismiss();
                }).create();
        dialog.show();
    }
}
