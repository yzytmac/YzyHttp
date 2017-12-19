package com.yzy.yzyhttplib;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yzy on 2017/12/16.
 */

public class HttpService {

    protected BaseCallBack mBaseCallBack = null;
    protected IHttpListener mHttpListener = null;
    protected HttpURLConnection mURLConnection = null;
    protected StringBuffer mURL = null;
    protected HashMap<String, String> mHeards = null;//请求参数
    private String mParams;
    private int mTimeout;

    public void setUrl(String pUrlString) {
        mURL = new StringBuffer(pUrlString);
    }

    public void setRequestHeards(HashMap<String, String> pHeards) {
        mHeards = pHeards;
    }

    public <R> void setHttpListener(IHttpListener<R> pListener) {
        mHttpListener = pListener;
    }

    public void setRequestParams(HashMap<String, String> pParams) {
        if (pParams.size() == 0) {
            mParams = "";
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("?");
        for (Map.Entry<String, String> vEntry : pParams.entrySet()) {
            sb.append(vEntry.getKey() + "=" + vEntry.getValue() + "&");
        }
        mParams = sb.toString();
    }

    public void setRequestMethod(String pMethod) {
        try {
            if ("GET".equalsIgnoreCase(pMethod)) {
                mURL.append(mParams);
                mURLConnection = (HttpURLConnection) new URL(mURL.toString()).openConnection();
                mURLConnection.setRequestMethod(pMethod);
                mURLConnection.setConnectTimeout(mTimeout);
                mURLConnection.setDoInput(true);
                mURLConnection.setDoOutput(false);

            } else if ("POST".equalsIgnoreCase(pMethod)) {
                mURLConnection = (HttpURLConnection) new URL(mURL.toString()).openConnection();
                mURLConnection.setRequestMethod(pMethod);
                mURLConnection.setDoInput(true);
                mURLConnection.setDoOutput(true);
                mURLConnection.setConnectTimeout(mTimeout);
                OutputStream os = mURLConnection.getOutputStream();
                os.write(mParams.getBytes());
                os.close();
            }
        } catch (IOException pE) {
            pE.printStackTrace();
        }
    }

    public void setRequestTimeout(int pTimeout){
        mTimeout=pTimeout;
    }


    /**
     * 子线程中执行
     */
    public void excute(Type pType) {
        mBaseCallBack = new BaseCallBack(mHttpListener, pType);
        int vCode = -1;
        InputStream is = null;
        try {
            if (mHeards != null) {
                Set<Map.Entry<String, String>> vEntries = mHeards.entrySet();
                if (vEntries != null) {
                    for (Map.Entry<String, String> vEntry : vEntries) {
                        mURLConnection.setRequestProperty(vEntry.getKey(), vEntry.getValue());
                    }
                }
            }

            mURLConnection.connect();
            vCode = mURLConnection.getResponseCode();
            if (vCode == 200) {
                is = mURLConnection.getInputStream();
                if (is != null) {
                    mBaseCallBack.onSuccess(is);
                } else {
                    mBaseCallBack.onFail(vCode, "InputStream is Null");
                }
            } else {
                mBaseCallBack.onFail(vCode, "Error code is " + vCode);
            }
        } catch (IOException pE) {
            Log.e("yzy", "excute: " + pE.toString());
            mBaseCallBack.onFail(vCode, pE.toString());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException pE) {
                    pE.printStackTrace();
                }
            }
        }
    }

}
