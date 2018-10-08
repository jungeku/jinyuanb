package com.sk.xjwd.minehome.presenter;


import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.activity.HelpDetailActivity;
import com.sk.xjwd.minehome.adapter.MineHomeAdapter;
import com.sk.xjwd.minehome.contract.HelpCenterActivityContract;
import com.sk.xjwd.minehome.model.HelpCenterModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.AutoLayoutManager;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class HelpCenterActivityPresenter extends HelpCenterActivityContract.Presenter implements XRecyclerView.LoadingListener {

    private MineHomeAdapter adapter;
    private XRecyclerView xRecyclerView;
    private List<BaseRecyclerModel> modelList=new ArrayList<>();
    private String url;

    @Override
    public void initRecyclerView(XRecyclerView xRecyclerView,String url) {
        adapter = new MineHomeAdapter();
        this.xRecyclerView=xRecyclerView;
        xRecyclerView.setLoadingMoreEnabled(false);
        this.url=url;
        xRecyclerView.setLayoutManager(new AutoLayoutManager(mContext,0));
        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setNestedScrollingEnabled(false);
        xRecyclerView.setHasFixedSize(false);
        xRecyclerView.setAdapter(adapter);
        getdata(url);
    }

    public void getdata(String url){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(url);
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
                xRecyclerView.refreshComplete();
                Log.e("login",result+"帮助");
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject object1=object.getJSONObject("data");
                        JSONArray array=object1.getJSONArray("records");
                        if(array.length()>0){
                            modelList.clear();
                            adapter.clear();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject helpobject=array.getJSONObject(i);
                                HelpCenterModel model=new HelpCenterModel();
                                model.viewType= MineHomeAdapter.HELP_CENTER_ITME;
                                model.id=helpobject.getInt("id");
                                model.title=helpobject.getString("title");
                                model.content=helpobject.getString("content");
                                modelList.add(model);
                            }
                            adapter.addAll(modelList);
                            adapter.notifyDataSetChanged();

                            adapter.setOnItemClickListener(new OnItemClickListener<BaseRecyclerModel>() {
                                @Override
                                public void onClick(View view, BaseRecyclerModel baseRecyclerModel, int position) {
                                    HelpCenterModel model= (HelpCenterModel) baseRecyclerModel;
                                    Intent mIntent=new Intent(mContext,HelpDetailActivity.class);
                                    mIntent.putExtra("title",model.title);
                                    mIntent.putExtra("content",model.content);
                                    mContext.startActivity(mIntent);
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    @Override
    public void onRefresh() {
        getdata(url);
    }

    @Override
    public void onLoadMore() {
        xRecyclerView.noMoreLoading();
    }
}
