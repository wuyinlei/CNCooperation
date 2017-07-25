package com.cainiao.factory.net;

import com.cainiao.common.base.BaseBean;
import com.cainiao.factory.model.Index;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wuyinlei on 2017/7/23.
 *
 * @function ApiService
 */

public interface ApiService {




    @GET("loadBanner_v2/")
    public Observable<BaseBean<List<Index>>> getApps(@Query("type") String jsonParam);

}
