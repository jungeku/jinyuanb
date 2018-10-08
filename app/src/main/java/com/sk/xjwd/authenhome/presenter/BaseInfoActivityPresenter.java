package com.sk.xjwd.authenhome.presenter;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.authenhome.activity.ApplyActivity;
import com.sk.xjwd.authenhome.contract.BaseInfoActivityContract;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.utils.UIUtil;
import com.sk.xjwd.view.ActionSheetDialog;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.pickerview.PickCityUtil;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseInfoActivityPresenter extends BaseInfoActivityContract.Presenter {

    private List<String> reasonList = new ArrayList<>();

    @Override
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
                        UIUtil.startActivity(ApplyActivity.class, null);
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).post();
    }

    @Override
    public void showmarryDialog(Context mContext, final TextView txtmarry) {
        new ActionSheetDialog(mContext)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("未婚", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        txtmarry.setText("未婚");
                        txtmarry.setTextColor(Color.parseColor("#333333"));
                    }
                })
                .addSheetItem("已婚", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        txtmarry.setText("已婚");
                        txtmarry.setTextColor(Color.parseColor("#333333"));
                    }
                })
                .addSheetItem("离异", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        txtmarry.setText("离异");
                        txtmarry.setTextColor(Color.parseColor("#333333"));
                    }
                })
                .show();
    }

    @Override
    public void showstudyDialog(Context mContext, final TextView txtstudy) {

        new ActionSheetDialog(mContext)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("硕士及以上",
                        ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtstudy.setText("硕士及以上");
                                txtstudy.setTextColor(Color.parseColor("#333333"));
                            }
                        })
                .addSheetItem("本科",
                        ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtstudy.setText("本科");
                                txtstudy.setTextColor(Color.parseColor("#333333"));
                            }
                        })
                .addSheetItem("大专",
                        ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtstudy.setText("大专");
                                txtstudy.setTextColor(Color.parseColor("#333333"));
                            }
                        })
                .addSheetItem("中专/高中及以下",
                        ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtstudy.setText("中专/高中及以下");
                                txtstudy.setTextColor(Color.parseColor("#333333"));
                            }
                        }).show();
    }

    @Override
    public void showRelationDialog(Context context, final TextView txtRelation) {

        new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("姐妹",
                        ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtRelation.setText("姐妹");
                                txtRelation.setTextColor(Color.parseColor("#333333"));
                            }
                        })
                .addSheetItem("兄弟",
                        ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtRelation.setText("兄弟");
                                txtRelation.setTextColor(Color.parseColor("#333333"));
                            }
                        })
                .addSheetItem("配偶",
                        ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtRelation.setText("配偶");
                                txtRelation.setTextColor(Color.parseColor("#333333"));
                            }
                        })
                .addSheetItem("父母",
                        ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtRelation.setText("父母");
                                txtRelation.setTextColor(Color.parseColor("#333333"));
                            }
                        }).show();

    }

    @Override
    public void showNeedAddressDialog(final TextView txtprovince) {
        PickCityUtil.showCityPickView(mContext, new PickCityUtil.ChooseCityListener() {
            @Override
            public void chooseCity(String s) {
                txtprovince.setTextColor(Color.parseColor("#333333"));
                if (s.split("_").length == 2) {
                    txtprovince.setText(s.split("_")[0] + s.split("_")[1]);
                } else {
                    txtprovince.setText(s.split("_")[0] + s.split("_")[1] + s.split("_")[2]);
                }
                //settingView.setSettingValue(s.replaceAll("_", ""));
            }
        });
    }

    @Override
    public void postAllContacts(List<HashMap<String, String>> list) {
        HttpUtil util = new HttpUtil(mContext);
        util.setUrl(Api.saveUserPhoneList);
        util.putjsonarray(list);
        util.putHead("x-client-token", MyApplication.getString("token", ""));
        util.setShowDialog(false);
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
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("code").equals("SUCCESS")) {
                        mView.commitBasicAuth();
                    } else {
                        UIUtil.showToast(object.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).post();
    }
}
