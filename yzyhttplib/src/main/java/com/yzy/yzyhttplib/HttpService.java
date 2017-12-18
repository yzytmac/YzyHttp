package com.yzy.yzyhttplib;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
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
    protected URL mURL = null;
    protected HashMap<String, String> mHeards = null;//请求参数
    protected String questMethod = "GET";
    private String mParams;

    public void setUrl(String pUrlString) {
        try {
            mURL = new URL(pUrlString);
        } catch (MalformedURLException pE) {
            pE.printStackTrace();
        }
    }

    public void setRequestHeards(HashMap<String, String> pHeards) {
        mHeards = pHeards;
    }

    public <R> void setHttpListener(IHttpListener<R> pListener) {
        mHttpListener = pListener;
    }

    public void setRequestParams(HashMap<String, String> pParams) {
        StringBuffer sb = new StringBuffer();
        sb.append("?");
        for (Map.Entry<String, String> vEntry : pParams.entrySet()) {
            sb.append(vEntry.getKey()+"="+vEntry.getValue()+"&");
        }
        mParams = sb.toString();
    }

    public void setRequestMethod(String pMethod) {
        questMethod = pMethod;
    }


    /**
     * 子线程中执行
     */
    public void excute(Type pType) {
        mBaseCallBack = new BaseCallBack(mHttpListener, pType);
        int vCode = -1;
        InputStream is = null;
        try {
            mURLConnection = (HttpURLConnection) mURL.openConnection();
            mURLConnection.setRequestMethod(questMethod);
            mURLConnection.setConnectTimeout(20000);
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
