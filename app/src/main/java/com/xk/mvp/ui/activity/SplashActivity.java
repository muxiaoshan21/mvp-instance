package com.xk.mvp.ui.activity;

import com.xk.mvp.R;
import com.xk.mvp.presenter.BasePresenter;
import com.xk.mvp.statusbar.Eyes;
import com.xk.mvp.ui.activity.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by xk on 2017/9/19.
 */

public class SplashActivity extends BaseActivity {

    @Override
    public boolean enableSlideClose() {
        return false;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        Eyes.translucentStatusBar(this,false);//
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        readyGoThenKill(MainActivity.class);
                    }
                });
    }
}
