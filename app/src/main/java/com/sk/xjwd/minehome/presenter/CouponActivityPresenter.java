package com.sk.xjwd.minehome.presenter;

import android.util.Log;
import android.view.View;

import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.activity.LoanDetailActivity;
import com.sk.xjwd.minehome.adapter.MineHomeAdapter;
import com.sk.xjwd.minehome.contract.CouponActivityContract;
import com.sk.xjwd.minehome.model.CouponModel;
import com.sk.xjwd.utils.UIUtil;
import com.zyf.fwms.commonlibrary.base.baseadapter.AutoLayoutManager;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.OnItemClickListener;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.Constants;
import com.zyf.fwms.commonlibrary.utils.RxBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class CouponActivityPresenter extends CouponActivityContract.Presenter implements XRecyclerView.LoadingListener{

    private MineHomeAdapter adapter;
    private XRecyclerView xRecyclerView;
    private List<BaseRecyclerModel> modelList=new ArrayList<>();
    private String type;
    private String orderId;


    @Override
    public void initRecyclerView(XRecyclerView xRecyclerView,String type,String orderId) {
        adapter = new MineHomeAdapter();
        this.xRecyclerView=xRecyclerView;
        xRecyclerView.setLoadingMoreEnabled(false);
        this.type=type;
        this.orderId=orderId;
        xRecyclerView.setLayoutManager(new AutoLayoutManager(mContext,0));
        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setNestedScrollingEnabled(false);
        xRecyclerView.setHasFixedSize(false);
        xRecyclerView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        xRecyclerView.refreshComplete();
        getcoupon();
        adapter.setOnItemClickListener(new OnItemClickListener<BaseRecyclerModel>() {
            @Override
            public void onClick(View view, BaseRecyclerModel baseRecyclerModel, int position) {
                if(type.equals("2")){
                    CouponModel model= (CouponModel) baseRecyclerModel;
                     useCoupon(model.id);

                }
            }
        });
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        initData();
    }

    public void getcoupon(){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.userCoupon);
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
                        JSONArray array=object.getJSONArray("data");
                        if(array.length()!=0){
                            mView.isNotData(true);
                            modelList.clear();
                            adapter.clear();
                            for (int i = 0; i <array.length() ; i++) {
                                JSONObject couponobject=array.getJSONObject(i);
                                CouponModel model=new  CouponModel();
                                model.viewType= MineHomeAdapter.COUPON_ITEM;
                                model.id=couponobject.getInt("id");
                                model.limitTime=couponobject.getString("limitTime");
                                model.name=couponobject.getString("name");
                                model.saveMoney=couponobject.getString("saveMoney");
                                modelList.add(model);
                            }
                            adapter.addAll(modelList);
                            adapter.notifyDataSetChanged();
                        }else {
                            mView.isNotData(false);
                            xRecyclerView.noMoreLoading();


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }

    public void useCoupon(int couponid){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.useCoupon);
        util.putParam("userCouponId",couponid+"");
        util.putParam("orderId",orderId);
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
                Log.e("login",result+"使用优惠券");
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONObject object1=object.getJSONObject("data");
                        MyApplication.saveString("apply_needPayMoney",object1.getString("needPayMoney"));//应还金额
                        MyApplication.saveString("couponsaveMoney",object1.getString("saveMoney"));
                        RxBus.getDefault().post(Constants.REQUESTID_2, 1);
                        UIUtil.startActivity(LoanDetailActivity.class,null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
}
