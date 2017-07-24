package com.cainiao.factory.net;//package ruolan.com.cnmarket.data.http;
//

import com.cainiao.common.base.BaseBean;
import com.cainiao.factory.ApiService;
import com.cainiao.factory.bean.Index;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public final class HttpManager {

    private ApiService mApiService;

    private static OkHttpClient getOkHttpClient() {

        return new OkHttpClient.Builder()
//                .addInterceptor(null) // TODO: 2017/7/24 在这个地方可以添加拦截器 
                .connectTimeout(10, TimeUnit.SECONDS)//链接超时
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时
                .build();

    }

    private static HttpManager instance;

    private HttpManager() {
        mApiService = getRetrofit(getOkHttpClient()).create(ApiService.class);
    }

    public static HttpManager getInstance() {
        synchronized (HttpManager.class) {
            if (instance == null)
                instance = new HttpManager();
        }
        return instance;
    }

    private static Retrofit getRetrofit(OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client);
        return builder.build();
    }

    /**
     * 用户登录
     * @param userName  用户名
     */
    public Observable<BaseBean<List<Index>>> getApps(String userName) {
        return mApiService.getApps(userName);
    }


}
