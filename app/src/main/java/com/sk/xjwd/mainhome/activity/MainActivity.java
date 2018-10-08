package com.sk.xjwd.mainhome.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.sk.xjwd.BuildConfig;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.base.BaseFragment;
import com.sk.xjwd.databinding.ActivityMainBinding;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.contract.MainActivityContract;
import com.sk.xjwd.mainhome.fragment.ApplyFragment;
import com.sk.xjwd.mainhome.fragment.HomeFragment;
import com.sk.xjwd.mainhome.fragment.MineFragment;
import com.sk.xjwd.mainhome.presenter.MainActivityPresenter;
import com.sk.xjwd.update.CommonDialog;
import com.sk.xjwd.update.UpdateService;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.NavigationView;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.Set;

import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity<MainActivityPresenter,ActivityMainBinding> implements MainActivityContract.View {

    private NavigationView mCurrTab;//当前导航
    private BaseFragment mCurrFragment;//当前fragment
    private FragmentManager mFragmentManager;
    private HomeFragment homeFragment;//主页
    private ApplyFragment applyFragment;//认证
    private MineFragment mineFragment;//我的
    int WRITE_EXTERNAL_STORAGE_REQUEST_CODE=1;
    String currVersin="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String str=getIntent().getStringExtra("renzheng");
        if(str!=null && str.equals("renzheng")){
            changeTab(mBindingView.nvHome);
            changeFragment(new HomeFragment());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        String str=getIntent().getStringExtra("renzheng");
//        if(str!=null && str.equals("renzheng")){
//            changeTab(mBindingView.nvHome);
//            changeFragment(new HomeFragment());
//        }
        currVersin=UIUtil.getVersionName(MainActivity.this);
        mPresenter.isGengxinVersion(currVersin);
    }

    @Override
    protected void initView() {
        int id = MyApplication.getInt("id", 0);
        Log.i("weeee", "initView: "+id);

        JPushInterface.setAlias(this, id+"", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

                JPushInterface.resumePush(getApplicationContext());
            }
        });

        hideTitleBar();
        mCurrTab = mBindingView.nvHome;
        initFragment();
        // updateVersion();
        initRxBus();
//        initPermission();
        requestPermissionList(this);
    }

    private void requestPermissionList(final Activity activity) {
        new RxPermissions(activity)
                .requestEach(Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                )
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if(permission.granted){
                            // 获得授权
//                            isGrantedAll = true;
                            Log.d("isGrantedAll","isGrantedAll = true" );

                            //AppUtil.showShortToast(activity,"您已经授权该权限");

                        }else{
//                            isGrantedAll = false;
                            Log.d("isGrantedAll","i///sGrantedAll = false" );
                            //未获得授权
                            //AppUtil.showShortToast(activity,"您没有授权该权限，请在设置中打开授权");
                            UIUtil.showToast("您没有授权该权限，请在设置中打开授权");

                        }

                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
            } else {
                // Permission Denied
            }
        }
    }
    private void updateVersion() {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.updateVersion);
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
                Log.e("loginss", ">>>>>>>>>>>>>>>>>记录详情>>>>>>>>>>>>>>");
                Log.e("loginss",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject recordObject=object.getJSONObject("data");
                        String appVersion = recordObject.getString("appVersion");
                        String currentVersion= BuildConfig.VERSION_NAME;
                        if (!currentVersion.equals(appVersion)){
                            showUpdateDialog(recordObject.getString("uploadUrl"));
                        }


                    }else{
                        // UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
    /**
     * 软更弹出提示升级的Dialog
     */
    public void showUpdateDialog(final String downloadUrl){
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle("升级提示");
        builder.setMessage("发现新版本，是否更新？");
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                Intent intent = new Intent(mContext, UpdateService.class);
//                intent.putExtra("apkUrl", downloadUrl);
//                mContext.startService(intent);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                startActivity(intent1);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    /**
     * 强更弹出提示升级的Dialog
     */
    public void showqiangUpdateDialog(final String downloadUrl){
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle("升级提示");
        builder.setMessage("发现新版本，是否更新？");
        builder.setCancelable(false);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                Intent intent = new Intent(mContext, UpdateService.class);
//                intent.putExtra("apkUrl", downloadUrl);
//                mContext.startService(intent);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                startActivity(intent1);
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.nv_home,R.id.nv_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nv_home://主页
                changeTab(mBindingView.nvHome);
                changeFragment(new HomeFragment());
                break;
            case R.id.nv_mine://我的
                changeTab(mBindingView.nvMine);
                changeFragment(mineFragment);
                break;
        }
    }

    @Override
    public void initFragment() {
        if (mFragmentManager != null) {
            return;
        }
        mFragmentManager = getSupportFragmentManager();
        homeFragment=new HomeFragment();

        mCurrFragment = homeFragment;
        mFragmentManager.beginTransaction().add(R.id.fl_content, homeFragment).commitAllowingStateLoss();

        applyFragment=new ApplyFragment();
        mineFragment=new MineFragment();
    }

    @Override
    public void changeTab(NavigationView newTab) {
        if (mCurrTab != newTab) {
            mCurrTab.setSelected(false);
            newTab.setSelected(true);
            mCurrTab = newTab;
        }
    }

    @Override
    public void changeFragment(BaseFragment newFragment) {
        if (mCurrFragment != newFragment) {
            if (!newFragment.isAdded()) {
                mFragmentManager.beginTransaction()
                        .add(R.id.fl_content, newFragment)
                        .hide(mCurrFragment)
                        .commitAllowingStateLoss();
            } else {
                mFragmentManager.beginTransaction()
                        .show(newFragment)
                        .hide(mCurrFragment)
                        .commitAllowingStateLoss();
            }
            mCurrFragment = newFragment;
        }
    }

    @Override
    public void initgengxin() {
        if(MyApplication.getInt("appversion",0)==1){
            //強更
            showqiangUpdateDialog(MyApplication.getString("appversionUrl",""));
        }else if(MyApplication.getInt("appversion",0)==2){
            //软更
            showUpdateDialog(MyApplication.getString("appversionUrl",""));
        }
    }

    public void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(Constants.REQUESTID_4, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
//                        changeTab(mBindingView.nvApply);
//                        changeFragment(applyFragment);
                    }
                });
        addSubscription(subscribe);
    }


    /**
     * 按返回键运行在后台
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
