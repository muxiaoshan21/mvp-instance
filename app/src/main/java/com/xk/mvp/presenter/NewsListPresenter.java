package com.xk.mvp.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.xk.mvp.model.entity.News;
import com.xk.mvp.model.entity.NewsData;
import com.xk.mvp.model.response.NewsResponse;
import com.xk.mvp.retrofit.SubscriberCallBack;
import com.xk.mvp.utils.ListUtils;
import com.xk.mvp.utils.PreUtils;
import com.xk.mvp.view.INewsListView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by xk on 2017/9/21.
 */

public class NewsListPresenter extends BasePresenter<INewsListView> {
    public NewsListPresenter(INewsListView view) {
        super(view);
    }

    private long lastTime;


    public void getNewsList(final String channelCode){

        lastTime = PreUtils.getLong(channelCode,0);// 读取对应频道下最后一次刷新的时间戳
        if(lastTime == 0){
            //如果为空，则是从来没有刷新过，使用当前时间戳
            lastTime = System.currentTimeMillis()/1000;
        }

        addSubscription(mRetrofitInterface.getNewsList(channelCode, lastTime, System.currentTimeMillis() / 1000), new Subscriber<NewsResponse>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(NewsResponse response) {
                Log.e("xk","response:  "+response);
                lastTime = System.currentTimeMillis()/1000;
                PreUtils.putLong(channelCode,lastTime); //保存刷新的时间戳
                List<NewsData> data = response.data;
                List<News> newsList = new ArrayList<>();
                if (!ListUtils.isEmpty(data)){
                    for (NewsData newsData : data) {
                        News news = new Gson().fromJson(newsData.content, News.class);
                        newsList.add(news);
                    }
                }
                KLog.e(newsList);

                mView.onGetNewsListSuccess(newsList,response.tips.display_info);
            }
        });

    }

}
