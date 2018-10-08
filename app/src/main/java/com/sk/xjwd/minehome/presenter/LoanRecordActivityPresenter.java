package com.sk.xjwd.minehome.presenter;


import android.util.Log;
import android.view.View;

import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.activity.RecordDetailActivity;
import com.sk.xjwd.minehome.adapter.MineHomeAdapter;
import com.sk.xjwd.minehome.contract.LoanRecordActivityContract;
import com.sk.xjwd.minehome.model.OrderModel;
import com.sk.xjwd.utils.Logger;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.base.baseadapter.AutoLayoutManager;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.OnItemClickListener;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanRecordActivityPresenter extends LoanRecordActivityContract.Presenter implements XRecyclerView.LoadingListener{

    private MineHomeAdapter adapter;
    private XRecyclerView xRecyclerView;
    private View mView;
    private int mTabPos;
    private List<BaseRecyclerModel> modelList;
    int page=1;//当前页
    int sumpages;//总页数

    @Override
    public void initRecyclerView(XRecyclerView xRecyclerView,View view,int tabPos) {
        adapter = new MineHomeAdapter();
        modelList=new ArrayList<>();
        this.xRecyclerView=xRecyclerView;
        this.mView=view;
        this.mTabPos=tabPos;
        xRecyclerView.setLayoutManager(new AutoLayoutManager(mContext,0));
        xRecyclerView.setNestedScrollingEnabled(false);
        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setHasFixedSize(false);
        xRecyclerView.setAdapter(adapter);
        initData(false,mView,tabPos);
    }

    private void initData(boolean isMore,View view,int tabPos) {
        xRecyclerView.refreshComplete();
        if(!isMore){
            adapter.clear();
            page=1;
        }
        if(page==1){
            GetLoanRecord(view);
            page++;
        }else if(page<=sumpages){
            GetLoanRecord(view);
            page++;
        }else{
            xRecyclerView.noMoreLoading();
        }
        adapter.setOnItemClickListener(new OnItemClickListener<BaseRecyclerModel>() {
            @Override
            public void onClick(View view, BaseRecyclerModel baseRecyclerModel, int position) {
                OrderModel model= (OrderModel) baseRecyclerModel;
                Map<String,String> map=new HashMap<>();
                map.put("id",model.id+"");
                UIUtil.startActivity(RecordDetailActivity.class,map);
            }
        });
    }

    @Override
    public void onRefresh() {
        initData(false,mView,mTabPos);
    }

    @Override
    public void onLoadMore() {
        initData(true,mView,mTabPos);
    }

    public void GetLoanRecord(View view) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.LOAN_RECORD);
        util.putParam("current",page+"");
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
                Log.i("td",">>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<");
                Logger.show(result);
                try {
                    JSONObject object=new JSONObject(result);
                    Log.i("loanRecord",result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject object1=object.getJSONObject("data");
                        sumpages=object1.getInt("pages");
                        JSONArray array=object1.getJSONArray("records");
                        if(array.length()!=0){
                            modelList.clear();
                            view.setVisibility(View.GONE);
                            for (int i = 0; i <array.length() ; i++) {
                                JSONObject couponobject=array.getJSONObject(i);
                                OrderModel model=new OrderModel();
                                model.viewType= MineHomeAdapter.LOAN_RECORD_ITEM;
                                model.id=couponobject.getInt("id");
                                model.orderNumber=couponobject.getString("orderNumber");
                                model.borrowMoney=couponobject.getString("borrowMoney");
                                model.needPayMoney=couponobject.getString("needPayMoney");
                                model.limitDays=couponobject.getInt("limitDays");
                                model.orderStatus=couponobject.getInt("orderStatus");
                                model.gmtDatetime=couponobject.getString("gmtDatetime");
                                if(mTabPos==0){
                                    modelList.add(model);
                                }else {
                                    if(model.orderStatus==mTabPos-1){
                                        modelList.add(model);
                                    }
                                }

                            }if(modelList==null||modelList.size()==0){
                                view.setVisibility(View.VISIBLE);
                            }else {
                                adapter.addAll(modelList);
                                adapter.notifyDataSetChanged();
                            }
                        }else {
                            view.setVisibility(View.VISIBLE);
                        }
                    }else {
                        view.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

}
