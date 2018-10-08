package com.sk.xjwd.minehome.presenter;

import android.util.Log;

import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.minehome.adapter.MineHomeAdapter;
import com.sk.xjwd.minehome.contract.InviteFriendsActivityContract;
import com.zyf.fwms.commonlibrary.base.baseadapter.AutoLayoutManager;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class InviteFriendsActivityPresenter extends InviteFriendsActivityContract.Presenter implements XRecyclerView.LoadingListener{

    private MineHomeAdapter adapter;
    private XRecyclerView xRecyclerView;
    private List<BaseRecyclerModel> modelList=new ArrayList<>();

    @Override
    public void initRecyclerView(XRecyclerView xRecyclerView) {
        adapter = new MineHomeAdapter();
        this.xRecyclerView=xRecyclerView;
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLayoutManager(new AutoLayoutManager(mContext,0));
        xRecyclerView.setNestedScrollingEnabled(false);
        xRecyclerView.setHasFixedSize(false);
        xRecyclerView.setAdapter(adapter);
        initData(false);
    }

    @Override
    public void initjianglidata() {
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.inviteFriend);
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
                        JSONObject object1=object.getJSONObject("data");
                        MyApplication.saveString("invite_money",object1.getString("money").endsWith("null")?"0":object1.getString("money"));
                        MyApplication.saveString("invite_count",object1.getString("count").endsWith("null")?"0":object1.getString("count"));
                        RxBus.getDefault().post(11, 1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    private void initData(boolean isMore) {
        xRecyclerView.refreshComplete();
        if(!isMore){
            modelList.clear();
            adapter.clear();
        }
        for(int i=0;i<2;i++){
            BaseRecyclerModel model=new BaseRecyclerModel();
            model.viewType= MineHomeAdapter.COUPON_ITEM;
            modelList.add(model);
        }
        adapter.addAll(modelList);

    }

    @Override
    public void onRefresh() {
        initData(false);
    }

    @Override
    public void onLoadMore() {
        initData(true);
    }
}
