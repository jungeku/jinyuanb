package com.sk.xjwd.mainhome.presenter;


import android.util.Log;

import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.contract.HomedailiFragmentContact;
import com.sk.xjwd.minehome.activity.InviteFriendsActivity;
import com.sk.xjwd.utils.GlideImageLoader;
import com.sk.xjwd.utils.UIUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class HomedailiFragemnrPresenter extends  HomedailiFragmentContact.Presenter {
    private List<String> bannerlist=new ArrayList<>();

    public void getBannerlist(final Banner banner){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.getads);
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
                Log.e("login",result+"广告");
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONArray array=object.getJSONArray("data");
                        if(array.length()>0){
                            bannerlist.clear();
                            for (int i = 0; i <array.length() ; i++) {
                                JSONObject dataobject=array.getJSONObject(i);
                                bannerlist.add(dataobject.getString("imgUrl"));
                            }
                            //设置图片加载器
                            banner.setImageLoader(new GlideImageLoader());
                            //设置图片集合
                            banner.setImages(bannerlist);
                            banner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(int position) {
                                    UIUtil.startActivity(InviteFriendsActivity.class,null);
                                }
                            });
                            //banner设置方法全部调用完毕时最后调用
                            banner.start();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
}
