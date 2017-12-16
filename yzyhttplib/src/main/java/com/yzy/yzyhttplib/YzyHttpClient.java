package com.yzy.yzyhttplib;

import java.util.HashMap;
import java.util.concurrent.FutureTask;

/**
 * Created by yzy on 2017/12/16.
 */

public class YzyHttpClient {
    public static <M> void request(String urlString,HashMap<String,String> args,IHttpListener<M> listener){
        HttpTask vHttpTask = new HttpTask(urlString, args, listener);
        ThreadPoolManager.getInstance().excute(new FutureTask<Object>(vHttpTask,null));

    }
}
