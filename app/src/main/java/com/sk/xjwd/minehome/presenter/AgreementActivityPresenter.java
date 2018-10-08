package com.sk.xjwd.minehome.presenter;


import com.sk.xjwd.minehome.contract.AgreementActivityContract;

public class AgreementActivityPresenter extends AgreementActivityContract.Presenter {

//    TextView txtagareement;
//    @Override
//    public void showagareement(TextView txtagareement, int type) {
//        this.txtagareement=txtagareement;
//        if(type==1){
//            loanagreement();//借款协议
//        }
//    }
//
//    public void loanagreement(){
//        HttpUtil util=new HttpUtil(mContext);
//        util.setUrl(Api.Loanagreement);
//        util.putParam("id",13+"");
//        util.putHead("token", MyApplication.getString("token",""));
//        util.setListener(new HttpUtil.HttpUtilListener() {
//            @Override
//            public void onCancelled(Callback.CancelledException arg0) {
//
//            }
//
//            @Override
//            public void onError(Throwable arg0, boolean arg1) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                Log.e("login",result);
//                try {
//                    JSONObject object=new JSONObject(result);
//                    if(object.getString("code").equals("SUCCESS")){
//                        UIUtil.showToast(object.getString("msg"));
//                    }else{
//                        UIUtil.showToast(object.getString("msg"));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).get();
//    }
}
