package com.sk.xjwd.minehome.presenter;

import com.sk.xjwd.base.BasePresenter;
import com.sk.xjwd.http.AuthInfoDetailsResponse;
import com.sk.xjwd.http.BaseCallBack;
import com.sk.xjwd.http.BaseResponse;
import com.sk.xjwd.http.RetrofitHelper;
import com.sk.xjwd.http.httpApi;
import com.sk.xjwd.minehome.activity.MineInfoActivity;
import com.sk.xjwd.utils.UIUtil;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mayn on 2018/9/1.
 */

public class MineInfoActivityPresenter extends BasePresenter<MineInfoActivity> {

    //查询认证信息详情
    public void getMyUserInfo() {
        RetrofitHelper.getRetrofit().create(httpApi.class).selectUserAuthInfo().enqueue(
                new BaseCallBack<BaseResponse<AuthInfoDetailsResponse>>(mContext) {
                    @Override
                    public void onSuccess(Call<BaseResponse<AuthInfoDetailsResponse>> call, Response<BaseResponse<AuthInfoDetailsResponse>> response) {
                        if (response.body().isSuccess()){
                            if(response.body().getData()!=null) {
                                mView.showMyInfo(response.body().getData());
                            }
                        }else{
                            UIUtil.showToast(response.body().getMsg());
                        }
                    }
                });
    }
}
