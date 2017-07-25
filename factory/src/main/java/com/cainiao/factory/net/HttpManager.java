package com.cainiao.factory.net;

import android.support.annotation.Nullable;

import com.cainiao.common.base.BaseBean;
import com.cainiao.common.constant.Common;
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
                .baseUrl(Common.Constance.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client);
        return builder.build();
    }


    //在这个下面写关于地址的请求 第一步先在ApiService里面写  也就是先写下面的第一步
    // @GET("loadBanner_v2/")
    //    public Observable<BaseBean<List<Index>>> getApps(@Query("type") String jsonParam);

    //第二步下面这样写,其实返回值也就是上面第一步的返回值,在这里走了一步转换
    /**
     * @param type 类型
     */
    public Observable<BaseBean<List<Index>>> getApps(@Nullable String type) {
        return mApiService.getApps(type);
    }


}
