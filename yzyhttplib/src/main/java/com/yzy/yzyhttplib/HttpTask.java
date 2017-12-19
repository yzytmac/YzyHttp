package com.yzy.yzyhttplib;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by yzy on 2017/12/16.
 */

public class HttpTask implements Runnable {
    private HttpService mHttpService;
    private Type mType;


    public HttpTask(String url, Type pType,
                     IHttpListener<R> pListener,
                     HashMap<String, String> pHeards,
                     HashMap<String, String> pParams,
                     String pMethod,
                     int pTimeOut) {
        mHttpService = new HttpService();
        mHttpService.setUrl(url);
        mHttpService.setRequestParams(pParams);
        mHttpService.setHttpListener(pListener);
        mHttpService.setRequestMethod(pMethod);
        mHttpService.setRequestHeards(pHeards);
        mHttpService.setRequestTimeout(pTimeOut);
        mType = pType;
    }


    @Override
    public void run() {
        mHttpService.excute(mType);
    }

}
