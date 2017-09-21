package com.xk.mvp.view;

import com.xk.mvp.model.entity.News;

import java.util.List;

/**
 * Created by xk on 2017/9/21.
 */

public interface INewsListView {

    void onGetNewsListSuccess(List<News> newsList, String tipInfo);
    void onError();

}
