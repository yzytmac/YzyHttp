package com.yzy.yzyhttplib;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.FutureTask;

/**
 * Created by yzy on 2017/12/16.
 */

public class YzyHttpClient {

    private YzyHttpClient(RequestBuilder pBuilder){
        HttpTask vHttpTask = new HttpTask(pBuilder.url, pBuilder.type,pBuilder.listener,pBuilder.heards,pBuilder.params,pBuilder.method,pBuilder.timeOut);
        ThreadPoolManager.getInstance().excute(new FutureTask(vHttpTask,null));
    }

    public static class RequestBuilder<R> {
        private String url;
        private Type type;
        private IHttpListener<R> listener;
        private String method="GET";
        private HashMap<String, String> params=new HashMap<>();
        private HashMap<String, String> heards=new HashMap<>();
        private int timeOut = 20000;

        /**
         * 发起网络请求
         * @param pUrl 请求地址的url字符串
         * @param pType 返回值数据实体类的Type
         * @param pListener 网络请求回调
         */
        public RequestBuilder(String pUrl, Type pType, IHttpListener<R> pListener) {
            url = pUrl;
            type = pType;
            listener = pListener;
        }

        public RequestBuilder setParams(HashMap<String, String> pParams){
            params = pParams;
            return this;
        }

        public RequestBuilder setRequestMethod(String pMethod) {
            method = pMethod;
            return this;
        }

        public RequestBuilder setHeards(HashMap<String, String> pHeards) {
            heards = pHeards;
            return this;
        }

        public RequestBuilder setTimeOut(int pTimeOut) {
            timeOut = pTimeOut;
            return this;
        }

        public YzyHttpClient request(){
            return new YzyHttpClient(this);
        }

    }
}
