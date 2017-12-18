package com.yzy.yzyhttplib;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.FutureTask;

/**
 * Created by yzy on 2017/12/16.
 */

public class YzyHttpClient {
    /**
     * 发起网络请求
     * @param urlString 请求地址的url字符串
     * @param args 请求参数
     * @param pType 返回值数据实体类的Type
     * @param listener 网络请求回调
     * @param <M> 泛型
     */
    public static <M> void request(String urlString, HashMap<String,String> args, Type pType, IHttpListener<M> listener){
        HttpTask vHttpTask = new HttpTask(urlString, args, pType,listener);
        ThreadPoolManager.getInstance().excute(new FutureTask<Object>(vHttpTask,null));

    }
}
