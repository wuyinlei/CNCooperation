package com.cainiao.factory;

import com.cainiao.common.base.BaseBean;
import com.cainiao.factory.bean.Index;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wuyinlei on 2017/7/23.
 */

public interface ApiService {


    public static final String BASE_URL = "http://api.xinliji.me/com/";


    @GET("loadBanner_v2/")
    public Observable<BaseBean<List<Index>>> getApps(@Query("type") String jsonParam);

}
