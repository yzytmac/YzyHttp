package com.yzy.yzyhttplib;

/**
 * Created by yzy on 2017/12/16.
 */

public interface IHttpListener<M> {
    void onSuccess(M pM);
    void onFail(int code,String msg);
}
