package com.cainiao.factory.net;

import com.cainiao.common.base.BaseBean;
import com.cainiao.factory.bean.Index;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wuyinlei on 2017/7/23.
 *
 * @function ApiService
 */

public interface ApiService {




    @GET("loadBanner_v2/")
    public Observable<BaseBean<List<Index>>> getApps(@Query("type") String jsonParam);

}
