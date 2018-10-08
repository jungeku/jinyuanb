package com.sk.xjwd.mainhome.presenter;


import com.example.xrecyclerview.XRecyclerView;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.http.HttpUtil;
import com.sk.xjwd.mainhome.adapter.MainHomeAdapter;
import com.sk.xjwd.mainhome.contract.MessageActivityContract;
import com.sk.xjwd.mainhome.model.MessageModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.AutoLayoutManager;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.http.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class MessageActivityPresenter extends MessageActivityContract.Presenter implements XRecyclerView.LoadingListener{

    private MainHomeAdapter adapter;
    private XRecyclerView xRecyclerView;
    private List<BaseRecyclerModel> modelList=new ArrayList<>();
    @Override
    public void initRecyclerView(XRecyclerView xRecyclerView) {
        adapter = new MainHomeAdapter();
        this.xRecyclerView=xRecyclerView;
        xRecyclerView.setLayoutManager(new AutoLayoutManager(mContext,0));
        xRecyclerView.setNestedScrollingEnabled(false);
        xRecyclerView.setLoadingListener(this);
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
        getpushmessagelist();

    }

    @Override
    public void onRefresh() {
        initData(false);
    }

    @Override
    public void onLoadMore() {
       // initData(true);
    }

    public void getpushmessagelist(){
        HttpUtil util=new HttpUtil(mContext);
        util.setUrl(Api.pushMsgRecord);
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
                try {
                    /*{"code":"SUCCESS","data":[{"id":29,"userId":20869,"title":null,"text":"智障王云鹏","gmtDatetime":"2018-07-19 15:40:35","status":null},{"id":28,"userId":20869,"title":null,"text":"智障王云鹏","gmtDatetime":"2018-07-19 15:39:20","status":null},{"id":27,"userId":20869,"title":null,"text":"智障王云鹏","gmtDatetime":"2018-07-19 15:37:18","status":null},{"id":26,"userId":20869,"title":null,"text":"智障王云鹏","gmtDatetime":"2018-07-19 15:24:35","status":null},{"id":25,"userId":20869,"title":null,"text":"智障王云鹏","gmtDatetime":"2018-07-19 14:30:30","status":null}],"msg":"成功","success":true}*/
                    JSONObject object=new JSONObject(result);
                    if(object.getString("code").equals("SUCCESS")){
                        JSONArray array=object.getJSONArray("data");
                        modelList.clear();
                        adapter.clear();
                        if(array.length()>0){
                            for (int i = 0; i <array.length() ; i++) {
                                JSONObject dataobject=array.getJSONObject(i);
                                MessageModel message=new MessageModel();
                                message.viewType=MainHomeAdapter.MESSAGE_CENTER_ITEM;
                                message.id=dataobject.getInt("id");
                             //   message.title=dataobject.getString("title");
                              //  message.content=dataobject.getString("content");
                                message.content=dataobject.getString("text");
                                message.time=dataobject.getString("gmtDatetime");
                                modelList.add(message);
                            }
                            adapter.addAll(modelList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).get();
    }
}
