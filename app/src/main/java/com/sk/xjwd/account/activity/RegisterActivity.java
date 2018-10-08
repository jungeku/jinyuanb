package com.sk.xjwd.account.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sk.xjwd.R;
import com.sk.xjwd.account.contract.RegisterActivityContract;
import com.sk.xjwd.account.presenter.RegisterActivityPresenter;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityRegisterBinding;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import java.io.IOException;
import java.util.List;

import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterActivityPresenter, ActivityRegisterBinding> implements RegisterActivityContract.View {

    Boolean bShowPwd1 = false;
    Boolean bShowPwd2 = false;
    //定位都要通过LocationManager这个类实现
    private LocationManager locationManager;
    private String provider;
    private String gpsLat;
    private String gpsLong;
    private String gpsaddress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        UIUtil.fullScreen(this);
    }

    @Override
    protected void initView() {
        hideTitleBar();
//        LocationManager pLocationManager = (LocationManager) Context.getSystemService(Context.LOCATION_SERVICE)；
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取当前可用的位置控制器
        List<String> list = locationManager.getProviders(true);

        if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为GPS位置控制器
            provider = LocationManager.NETWORK_PROVIDER ;
        } else if (list.contains(LocationManager.GPS_PROVIDER)) {
            //是否为网络位置控制器
            provider = LocationManager.GPS_PROVIDER;

        } else {
            Toast.makeText(this, "请检查网络或GPS是否打开",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "请检查网络或GPS是否打开",
                    Toast.LENGTH_LONG).show();
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            //获取当前位置，这里只用到了经纬度
            String string = "纬度为：" + location.getLatitude() + ",经度为："
                    + location.getLongitude();

        }

        //绑定定位事件，监听位置是否改变
//第一个参数为控制器类型第二个参数为监听位置变化的时间间隔（单位：毫秒）
//第三个参数为位置变化的间隔（单位：米）第四个参数为位置监听器
        locationManager.requestLocationUpdates(provider, 2000, 2,
                locationListener);


    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("gpsStatus", "onStatusChanged() called with " + "provider = [" + provider + "], status = [" + status + "], extras = [" + extras + "]");
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.i("gpsStatus", "AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i("gpsStatus", "OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i("gpsStatus", "TEMPORARILY_UNAVAILABLE");
                    break;
            }

        }

        @Override
        public void onProviderEnabled(String arg0) {
            Log.d("GPSProviderEnabled", "onProviderEnabled() called with " + "provider = [" + provider + "]");
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                Log.d("ProviderEnabled", "onProviderDisabled.location = " + location);
                updateView(location);
            }catch (SecurityException e){

            }

        }

        @Override
        public void onProviderDisabled(String arg0) {
            Log.d("GPSonProviderDisabled", "onProviderDisabled() called with " + "provider = [" + provider + "]");

        }

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            // 更新当前经纬度
            updateView(location);
        }
    };

    private void updateView(Location location){
        Geocoder gc = new Geocoder(this);
        List<Address> addresses = null;
        String msg = "";
        Log.d("gpsInfo", "updateView.location = " + location);
        if (location != null) {
            try {
                addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Log.d("gpsInfo", "updateView.addresses = " + addresses);
                if (addresses.size() > 0) {
                    String add=addresses.toString();
                    msg += addresses.get(0).getAdminArea().substring(0,2);
                    msg += " " + addresses.get(0).getLocality().substring(0,2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            gpsaddress=msg;
            gpsLong=String.valueOf(location.getLongitude());
            gpsLat=String.valueOf(location.getLatitude());

        } else {
            //定位为空
        }

    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.btn_getcode,R.id.btn_register,R.id.txt_agreement,R.id.reg_login,R.id.img_reg_back
    ,R.id.img_reg_showpwd,R.id.img_reg_showpwd2})
    public void onClink(View view){
        String phone=mBindingView.edRegisterPhone.getText().toString();
        String code=mBindingView.edRegisterCode.getText().toString();
        String pwd=mBindingView.edRegisterPwd.getText().toString();
        String pwdok=mBindingView.edRegisterPwdOk.getText().toString();
        String appVersion=UIUtil.getVersionName(RegisterActivity.this);
        String phoneBrond=UIUtil.getSystemModel();
        switch (view.getId()){
            case R.id.btn_getcode:
                if(CommonUtils.isEmpty(phone)){
                    CommonUtils.showToast(mContext,"手机号码不能为空！");
                }else{
                    mPresenter.senCode(mBindingView.btnGetcode,mBindingView.edRegisterPhone.getText().toString());
                }
                break;
            case R.id.btn_register:
                if(TextUtils.isEmpty(phone)){
                    UIUtil.showToast("手机号码不能为空！");
                }else if(TextUtils.isEmpty(code)){
                    UIUtil.showToast("验证码不能为空！");
                }else if(TextUtils.isEmpty(pwd)){
                    UIUtil.showToast("密码不能为空！");
                }else if(TextUtils.isEmpty(pwdok)){
                    UIUtil.showToast("确认密码不能为空！");
                }else  if (pwd.length()<6||pwd.length()>12){
                    UIUtil.showToast("密码必须6-12位！");
                }else if(!pwd.equals(pwdok)){
                    UIUtil.showToast("前后两次密码不一致！");
                }else{
                    mPresenter.postData(phone,code,pwd,1,"唐长老钱包",phoneBrond,appVersion,gpsLong,gpsLat,gpsaddress);
//                    if(mBindingView.chXieyi.isChecked()){
//                        mPresenter.postData(phone,code,pwd);
//                    }else{
//                        UIUtil.showToast("未同意协议");
//                    }
                }
                break;
            case R.id.txt_agreement:
                mPresenter.getregisteragreement();
                break;
            case R.id.img_reg_showpwd:
                if(bShowPwd1){
                    //否则隐藏密码
                    mBindingView.edRegisterPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mBindingView.imgRegShowpwd.setImageDrawable(getResources().getDrawable(R.mipmap.hide_pwd));
                    bShowPwd1=false;
                }else {
                    //如果选中，显示密码
                    mBindingView.edRegisterPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mBindingView.imgRegShowpwd.setImageDrawable(getResources().getDrawable(R.mipmap.show_pwd));
                    bShowPwd1=true;
                }
                break;
            case R.id.img_reg_showpwd2:
                if(bShowPwd2){
                    //否则隐藏密码
                    mBindingView.edRegisterPwdOk.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mBindingView.imgRegShowpwd2.setImageDrawable(getResources().getDrawable(R.mipmap.hide_pwd));
                    bShowPwd2=false;
                }else {
                    //如果选中，显示密码
                    mBindingView.edRegisterPwdOk.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mBindingView.imgRegShowpwd2.setImageDrawable(getResources().getDrawable(R.mipmap.show_pwd));
                    bShowPwd2=true;
                }
                break;
            case R.id.img_reg_back:
                finish();
                break;
            case R.id.reg_login:
                UIUtil.startActivity(LoginActivity.class,null);
                break;
        }
    }

}
