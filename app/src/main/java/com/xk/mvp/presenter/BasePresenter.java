package com.xk.mvp.presenter;

import com.xk.mvp.retrofit.RetrofitInterface;
import com.xk.mvp.retrofit.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by xk on 2017/9/19.
 */

public abstract class BasePresenter<V> {

    protected RetrofitInterface mRetrofitInterface = RetrofitManager.getInstance().getRetrofitInterface();
    protected V mView;
    private CompositeSubscription mCompositeSubscription;

    public BasePresenter(V view){
        attachView(view);
    }

    public void attachView(V view){
        mView = view;
    }

    public void detachView(){
        mView = null;
        onUnsubscribe();
    }

    //取消RXjava注册，以避免内存泄露
    public void onUnsubscribe(){
        if(mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Observable observable, Subscriber subscriber){
        if(mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(observable.subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(subscriber));

    }


}
