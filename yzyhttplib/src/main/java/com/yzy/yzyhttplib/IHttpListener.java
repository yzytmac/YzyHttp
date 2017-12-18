package com.yzy.yzyhttplib;

/**
 * Created by yzy on 2017/12/16.
 */

public interface IHttpListener<R> {
    void onSuccess(R pResponse);
    void onFail(int code,String msg);
}
