package com.yzy.yzyhttplib;

import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by yzy on 2017/12/16.
 */

public class BaseListener<R> {
    private IHttpListener<R> mHttpListener;
    private Type mType;

    public BaseListener(IHttpListener<R> pHttpListener, Type pType) {
        mHttpListener = pHttpListener;
        mType = pType;
    }

    void onSuccess(InputStream is) {
        R vResponse = inputStreamToObj(is);
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

    private R inputStreamToObj(InputStream pInputStream){
        String json = inputStreamToString(pInputStream);
        Log.e("yzy", "json: " + json);
        Gson vGson = new Gson();
        R vResponse = vGson.fromJson(json, mType);
        return vResponse;
    }
}
