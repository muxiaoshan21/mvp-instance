package com.xk.mvp.retrofit;

import com.xk.mvp.constants.ApiURL;
import com.xk.mvp.model.response.NewsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xk on 2017/9/19.
 */

public interface RetrofitInterface {

    /**
     * 获取新闻列表
     *
     * @param category 频道
     * @return
     */
    @GET(ApiURL.GET_ARTICLE_LIST)
    Observable<NewsResponse> getNewsList(@Query("category") String category, @Query("min_behot_time") long lastTime, @Query("last_refresh_sub_entrance_interval") long currentTime);


}
