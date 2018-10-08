package com.sk.xjwd.authenhome.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.sk.xjwd.authenhome.contract.BaseInfoActivityContract;
import com.sk.xjwd.authenhome.model.PhoneInfo;
import com.sk.xjwd.authenhome.presenter.BaseInfoActivityPresenter;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.databinding.ActivityBaseInfoBinding;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.utils.RequestPermissions;
import com.sk.xjwd.utils.UIUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.OnClick;


public class BaseInfoActivity extends BaseActivity<BaseInfoActivityPresenter,ActivityBaseInfoBinding> implements BaseInfoActivityContract.View {

    public List<PhoneInfo> mlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_info);
    }

    @Override
    protected void initView() {
        setTitle("个人信息");
        RequestPermissions.showphonePermissions(this,RequestPermissions.verifySdkVersion());
    }

    @Override
    protected void initPresenter() {
        mPresenter.setView(this);
    }

    @OnClick({R.id.img_nametongxun1,R.id.img_nametongxun2,R.id.btn_base_info_commite,R.id.rl_marry,R.id.rl_study,
            R.id.rl_relation1,R.id.rl_relation2,R.id.rl_province,
            R.id.txt_relation1_phonechoose,R.id.txt_relation1_namechoose,R.id.txt_relation2_phonechoose,R.id.txt_relation2_namechoose})
    public void OnClick(View view){
        Intent i;
        switch (view.getId()){
            case R.id.img_nametongxun1:
                new RxPermissions(((BaseActivity) mContext)).request(Manifest.permission.READ_CONTACTS).
                        subscribe(aBoolean -> {
                            if (aBoolean){
                                getContact(mContext,1);
                            }else{
                                UIUtil.showToast("请前往设置打开所需权限！");
                            }
                        });
                break;
            case R.id.txt_relation1_phonechoose:
             /*   i = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);//
                startActivityForResult(i, 1);*/
                break;
            case R.id.txt_relation1_namechoose:

                new RxPermissions(((BaseActivity) mContext)).request(Manifest.permission.READ_CONTACTS).
                        subscribe(aBoolean -> {
                            if (aBoolean){
                                getContact(mContext,1);
                            }else{
                                UIUtil.showToast("请前往设置打开所需权限！");
                            }
                        });
                break;
            case R.id.img_nametongxun2:

                new RxPermissions(((BaseActivity) mContext)).request(Manifest.permission.READ_CONTACTS).
                        subscribe(aBoolean -> {
                            if (aBoolean){
                                getContact(mContext,2);
                            }else{
                                UIUtil.showToast("请前往设置打开所需权限！");
                            }
                        });


                break;

            case R.id.txt_relation2_phonechoose:
              /*  i = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);//
                startActivityForResult(i, 2);*/
                break;
            case R.id.txt_relation2_namechoose:
                new RxPermissions(((BaseActivity) mContext)).request(Manifest.permission.READ_CONTACTS).
                        subscribe(aBoolean -> {
                            if (aBoolean){
                                getContact(mContext,2);
                            }else{
                                UIUtil.showToast("请前往设置打开所需权限！");
                            }
                        });
                break;
            case R.id.btn_base_info_commite:
                if(mBindingView.txtMarry.getText().toString().equals("请选择婚姻状况")){
                    UIUtil.showToast("请选择婚姻状况");
                }else if(mBindingView.txtStudy.getText().toString().equals("请选择学历")){
                    UIUtil.showToast("请选择学历");
                }else if(mBindingView.txtProvince.getText().toString().equals("选择省")){
                    UIUtil.showToast("请选择地址");
                }else if(TextUtils.isEmpty(mBindingView.edDetailAddress.getText().toString())){
                    UIUtil.showToast("请填写详细地址");
                }

                else if(mBindingView.txtRelation1Namechoose.getText().toString().equals("请从通讯录获取")){
                    UIUtil.showToast("请从通讯录获取联系信息");
                }else if(mBindingView.txtRelation1.getText().toString().equals("请选择")){
                    UIUtil.showToast("请选择关系");
                }else if(mBindingView.txtRelation2Namechoose.getText().toString().equals("请从通讯录获取")){
                    UIUtil.showToast("请从通讯录获取联系信息");
                }else if(mBindingView.txtRelation2.getText().toString().equals("请选择")){
                    UIUtil.showToast("请选择关系");
                }else if(mBindingView.txtRelation2Phonechoose.getText().toString().equals(mBindingView.txtRelation1Phonechoose.getText().toString())){
                    UIUtil.showToast("请选择不同联系人！");
                }else if(!isMatcheredBoo(isMatcheredStr(mBindingView.txtRelation1Phonechoose.getText().toString()))||!isMatcheredBoo(isMatcheredStr(mBindingView.txtRelation2Phonechoose.getText().toString()))){
                    UIUtil.showToast("联系人格式不正确，请输入11位数字的正确号码！");
                } else if (hasEmoji(mBindingView.txtRelation1Namechoose.getText().toString())||hasEmoji(mBindingView.txtRelation2Namechoose.getText().toString())){
                    UIUtil.showToast("联系人名称不能含有表情！");
                }
                else{


                    List<HashMap<String,String>> onelist=new ArrayList<>();
                    for (int j = 0; j< mlist.size(); j++) {
                        HashMap<String,String> map1=new HashMap<>();
                        map1.put("name",mlist.get(j).contactName);
                        map1.put("phone",mlist.get(j).contactNum);
                        map1.put("link","");
                        onelist.add(map1);
                    }
                    mPresenter.postAllContacts(onelist);
                }

                break;
            case R.id.rl_marry:
                mPresenter.showmarryDialog(BaseInfoActivity.this,mBindingView.txtMarry);
                break;
            case R.id.rl_study:
                mPresenter.showstudyDialog(BaseInfoActivity.this,mBindingView.txtStudy);
                break;
            case R.id.rl_relation1:
                mPresenter.showRelationDialog(BaseInfoActivity.this,mBindingView.txtRelation1);
                break;
            case R.id.rl_relation2:
                mPresenter.showRelationDialog(BaseInfoActivity.this,mBindingView.txtRelation2);
                break;
            case R.id.rl_province:
                mPresenter.showNeedAddressDialog(mBindingView.txtProvince);
                break;
//            case R.id.rl_city:
//                mPresenter.showNeedAddressDialog(mBindingView.txtProvince);
//                break;
//            case R.id.rl_county:
//                mPresenter.showNeedAddressDialog(mBindingView.txtProvince);
//                break;
        }
    }
    public boolean hasEmoji(String content){

        Pattern pattern = Pattern.compile("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]");
        Matcher matcher = pattern.matcher(content);
        if(matcher .find()){
            return true;
        }
        return false;
    }
    public void getContact(Context context,int type) {
        Intent  intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);//
        startActivityForResult(intent, type);
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        String contactNum;
        String contactName;
        mlist=new ArrayList<>();
        mlist.clear();
        while (cursor.moveToNext()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contactNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.e("content",contactName+">>>>>>>>>>>>>>>>"+contactNum);
            PhoneInfo pi=new PhoneInfo();
            String s=  contactName.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");//表情正则判断
            pi.contactName=s;
            pi.contactNum=contactNum;
            mlist.add(pi);

        }

    }
    public static String isMatcheredStr(String patternStr) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(patternStr);
        String  s=m.replaceAll("").trim();
        return s;
    }

    public static boolean isMatcheredBoo(String patternStr) {
        Pattern p = Pattern.compile("^1[0-9][0-9]\\d{8}$");
        Matcher m = p.matcher(patternStr);
        return m.matches();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            // URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
            Uri uri = data.getData();
            switch (requestCode){
                case 1:
                    shownamephone(uri,mBindingView.txtRelation1Namechoose,mBindingView.txtRelation1Phonechoose);
                    break;
                case 2:
                    shownamephone(uri,mBindingView.txtRelation2Namechoose,mBindingView.txtRelation2Phonechoose);
                    break;
            }
        }
    }

    public void shownamephone(Uri uri, TextView edname, TextView edphone){
//        Cursor cursor = managedQuery(uri, null, null, null, null);
//        cursor.moveToFirst();
//        String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//        String contactNumbers = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//        edname.setText(contactName);
//        edphone.setText(contactNumbers);
//        edname.setTextColor(Color.parseColor("#333333"));
//        edphone.setTextColor(Color.parseColor("#333333"));

        ContentResolver reContentResolverol = getContentResolver();
        // 查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
        Cursor cursor = managedQuery(uri, null, null, null, null);
        cursor.moveToFirst();
        // 条件为联系人ID
        String contactId = cursor.getString(cursor
                .getColumnIndex(ContactsContract.Contacts._ID));
        // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
        Cursor phone = reContentResolverol.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                        + contactId, null, null);
        while (phone.moveToNext()) {
            String usernumber = phone
                    .getString(phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String contactName = phone
                    .getString(phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            if(!Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(usernumber).matches()){
//                UIUtil.showToast("请选择手机号码");
//
//            }else{
            edname.setText(CommonUtils.isEmpty(contactName)?"":contactName );
            edphone.setText(CommonUtils.isEmpty(usernumber)?"":usernumber);
            edname.setTextColor(Color.parseColor("#333333"));
            edphone.setTextColor(Color.parseColor("#333333"));
            //  }

        }

    }

    @Override
    public void commitBasicAuth() {
        Map<String,String> map=new HashMap<>();
        map.put("marry",mBindingView.txtMarry.getText().toString());//婚姻状况
        map.put("study",mBindingView.txtStudy.getText().toString());//学历
        map.put("province",mBindingView.txtProvince.getText().toString());//省市区
//        map.put("city",mBindingView.txtCity.getText().toString());//市
//        map.put("county",mBindingView.txtCounty.getText().toString());//区
        map.put("areaCode","000000");//邮编
/*        map.put("workCompany",mBindingView.edWorkCompany.getText().toString());//工作单位
        map.put("workPlace",mBindingView.edWorkAddress.getText().toString());//工作地点
        map.put("workMoney",mBindingView.edMonthIncome.getText().toString());//月收入
        map.put("workPhone",mBindingView.edWorkPhone.getText().toString());//单位电话*/
        map.put("addressDetails",mBindingView.edDetailAddress.getText().toString());//详细地址
        map.put("linkPersonNameOne",mBindingView.txtRelation1Namechoose.getText().toString());//紧急联系人姓名
        map.put("linkPersonPhoneOne",isMatcheredStr(mBindingView.txtRelation1Phonechoose.getText().toString()));//紧急联系人电话
        map.put("linkPersonRelationOne",mBindingView.txtRelation1.getText().toString());//关系
        map.put("linkPersonNameTwo",mBindingView.txtRelation2Namechoose.getText().toString());
        map.put("linkPersonPhoneTwo",isMatcheredStr(mBindingView.txtRelation2Phonechoose.getText().toString()));
        map.put("linkPersonRelationTwo",mBindingView.txtRelation2.getText().toString());
        postData(map);
    }

    public void postData(Map<String, String> map) {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.BaseMsgAuth);
        util.putjson(map);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
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
                Log.e("login", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
//                        RxBus.getDefault().post(Constants.REQUESTID_1, 2);
                        UIUtil.showToast(object.getString("msg"));
//                        UIUtil.startActivity(MainActivity.class, null);
                        BaseInfoActivity.this.finish();
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).post();
    }

 /*   @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }*/
}
