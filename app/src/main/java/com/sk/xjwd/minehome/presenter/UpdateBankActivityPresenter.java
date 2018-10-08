package com.sk.xjwd.minehome.presenter;


import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;

import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.MainActivity;
import com.sk.xjwd.minehome.adapter.MineHomeAdapter;
import com.sk.xjwd.minehome.contract.UpdateBankActivityContract;
import com.sk.xjwd.minehome.model.SupportBankcardModel;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class UpdateBankActivityPresenter extends UpdateBankActivityContract.Presenter implements XRecyclerView.LoadingListener{

    private MineHomeAdapter adapter;
    private XRecyclerView xRecyclerView;
    private List<BaseRecyclerModel> modelList=new ArrayList<>();
    private String[] name={"农业银行","工商银行","交通银行","邮储银行","浦发银行","广发银行","平安银行","招商银行","中国银行","建设银行","光大银行","兴业银行","中信银行","华夏银行","杭州银行","北京银行","浙商银行","上海银行 "};


    @Override
    public void initRecyclerView(XRecyclerView xRecyclerView) {
        adapter = new MineHomeAdapter();
        this.xRecyclerView=xRecyclerView;
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        xRecyclerView.setNestedScrollingEnabled(false);
        xRecyclerView.setHasFixedSize(false);
        xRecyclerView.setAdapter(adapter);
        initData(false);
    }

    private void initData(boolean isMore) {
        xRecyclerView.refreshComplete();
        if(!isMore){
            modelList.clear();
            adapter.clear();
        }
        for(int i=0;i<name.length;i++){
            SupportBankcardModel model=new SupportBankcardModel();
            model.viewType= MineHomeAdapter.BANKCARD_ITEM;
            model.bankcardname=name[i];
            modelList.add(model);
        }
        adapter.addAll(modelList);
        ViewGroup.LayoutParams layoutParams = xRecyclerView.getLayoutParams();
        int size;
        if(modelList.size()%2==0){
            size=modelList.size()/2;
        }else{
            size=modelList.size()/2+1;
        }
        layoutParams.height=size*90;
        xRecyclerView.setLayoutParams(layoutParams);
        AutoUtils.autoSize(xRecyclerView);
    }

    @Override
    public void onRefresh() {
        initData(false);
    }

    @Override
    public void onLoadMore() {
        initData(true);
    }

    @Override
    public void postData(String name, String ID, String card, String phone) {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.bankCardAuth);
        util.putParam("name",name);
        util.putParam("bankcardno",card);
        util.putParam("idcardno",ID);
        util.putParam("phone",phone);
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
                Log.e("login",result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        UIUtil.showToast(object.getString("msg"));
                        RxBus.getDefault().post(Constants.REQUESTID_1, 2);
                        UIUtil.startActivity(MainActivity.class,null);
                    }else {
                        UIUtil.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
}
