package com.xk.mvp.model.response;

/**
 * Created by xk on 2017/9/21.
 */

public class ResultResponse<T> {

    public String has_more;
    public String message;
    public String success;
    public T data;

    public ResultResponse(String more,String msg,T result){
        has_more = more;
        message = msg;
        data = result;

    }

}
