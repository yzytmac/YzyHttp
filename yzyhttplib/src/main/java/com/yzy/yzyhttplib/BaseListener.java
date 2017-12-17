package com.yzy.yzyhttplib;

import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;

/**
 * Created by yzy on 2017/12/16.
 */

public class BaseListener<M> {
    private IHttpListener<M> mHttpListener;

    public BaseListener(IHttpListener<M> pHttpListener) {
        mHttpListener = pHttpListener;
    }

    void onSuccess(InputStream is) {
        Response<M> vResponse = inputStreamToObj(is);
        mHttpListener.onSuccess(vResponse);
    }

    void onFail(int code, String msg) {
        mHttpListener.onFail(code, msg);
    }

    private String inputStreamToString(InputStream is) {

        byte[] b = new byte[1024];
        int len;
        StringBuffer sb = new StringBuffer();
        try {
            while ((len = is.read(b)) != -1) {
                String vS = new String(b, 0, len);
                sb.append(vS);
            }
        } catch (Exception e) {
            return "";
        }
        return sb.toString();
    }

    private Response<M> inputStreamToObj(InputStream pInputStream){
        String json = inputStreamToString(pInputStream);
        Log.e("yzy", "json: " + json);
        Gson vGson = new Gson();
        Response vResponse = vGson.fromJson(json, Response.class);
        return vResponse;
    }
}
