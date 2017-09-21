package com.xk.mvp.retrofit;

import android.text.TextUtils;
import android.widget.Toast;

import com.socks.library.KLog;
import com.xk.mvp.application.AppApplication;
import com.xk.mvp.model.response.ResultResponse;

import rx.Subscriber;

/**
 * Created by xk on 2017/9/21.
 *
 * TODO 这个Callback抽取也能够公用，只需将  ResultResponse 换成 BaseResult即可，但是请求成功的判断条件逻辑需要重写 2017-9-21 17:17:45
 *
 */

public abstract class SubscriberCallBack<T> extends Subscriber<ResultResponse<T>> {


    @Override
    public void onNext(ResultResponse response) {
        boolean isSuccess = (!TextUtils.isEmpty(response.message) && TextUtils.equals(response.message,"success"))
                || !TextUtils.isEmpty(response.success) && TextUtils.equals(response.success,"true");

        if(isSuccess){
            onSuccess((T)response.data);
        }else{
            Toast.makeText(AppApplication.getContext(),response.message,Toast.LENGTH_SHORT).show();
            onFailure(response);
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        KLog.e(e.getLocalizedMessage());
        onError();
    }

    protected abstract void onSuccess(T response);
    protected abstract void onError();
    protected void onFailure(ResultResponse response){};

}
