package com.sk.xjwd.http;

import com.sk.xjwd.MyApplication;
import com.zyf.fwms.commonlibrary.http.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jh352160 on 2017/9/8.
 */

public class RetrofitHelper {
    private static Retrofit retrofit;
    private static Retrofit retrofit1;

    /**
     * @link com.zyf.fwms.commonlibrary.http.Api
     */
    public static Retrofit getRetrofit(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(Api.HOST_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        }
        return retrofit;

    }


    private static OkHttpClient getClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request newRequest=chain.request().newBuilder()
                            .addHeader("Connection","close")
                            .addHeader("x-client-token", MyApplication.getString("token",""))
                         //  .addHeader("cookie","JSESSIONID="+MyApplication.getString("token",""))
                            .build();
                    return chain.proceed(newRequest);
                })
                .retryOnConnectionFailure(false)
                .build();
    }
}
