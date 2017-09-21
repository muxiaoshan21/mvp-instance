package com.xk.mvp.ui.fragment;

import android.util.Log;

import com.xk.mvp.R;
import com.xk.mvp.model.entity.News;
import com.xk.mvp.presenter.BasePresenter;
import com.xk.mvp.presenter.NewsListPresenter;
import com.xk.mvp.ui.fragment.base.BaseFragment;
import com.xk.mvp.utils.UIUtils;
import com.xk.mvp.view.INewsListView;

import java.util.List;

/**
 * Created by xk on 2017/9/19.
 */

public class HomeFragment extends BaseFragment<NewsListPresenter> implements INewsListView {

    private String mChannelCode;

    @Override
    protected NewsListPresenter createPresenter() {
        return new NewsListPresenter(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {
        String[] channelCodes = UIUtils.getStringArr(R.array.channel_code);
//        isRecommendChannel = mChannelCode.equals(channelCodes[0]);//是否是推荐频道
        mPresenter.getNewsList(channelCodes[1]);
    }

    @Override
    public void onGetNewsListSuccess(List<News> newsList, String tipInfo) {
        Log.e("xk","tipInfo:  "+tipInfo);
        Log.e("xk","newsList:  "+newsList);
    }

    @Override
    public void onError() {

    }
}
