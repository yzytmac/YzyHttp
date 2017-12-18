package com.yzy.yzyhttplib;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by yzy on 2017/12/16.
 */

public  class HttpTask implements Runnable{
    private HttpService mHttpService;
    private Type mType;


    public <M> HttpTask(String pUrlString, HashMap<String,String> pArgs, Type pType, IHttpListener<M> pListener) {
        mHttpService = new HttpService();
        mHttpService.setUrl(pUrlString);
        mHttpService.setRequestData(pArgs);
        mHttpService.setHttpListener(pListener);
        mType = pType;
    }
    public void setRequestMethod(String pMethod){
        mHttpService.setRequestMethod(pMethod);
    }

    @Override
    public void run() {
        mHttpService.excute(mType);
    }
}
