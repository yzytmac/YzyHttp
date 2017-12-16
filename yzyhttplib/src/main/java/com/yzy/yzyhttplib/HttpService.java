package com.yzy.yzyhttplib;

import java.io.IOException;
import java.io.InputStream;
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

    protected BaseListener mBaseListener = null;
    protected IHttpListener mHttpListener = null;
    protected HttpURLConnection mURLConnection = null;
    protected URL mURL = null;
    protected HashMap<String, String> mArgs = null;//请求参数
    protected String questMethod = "GET";

    public void setUrl(String pUrlString) {
        try {
            mURL = new URL(pUrlString);
        } catch (MalformedURLException pE) {
            pE.printStackTrace();
        }
    }

    public void setRequestData(HashMap<String, String> pArgs) {
        mArgs = pArgs;
    }

    public <M> void setHttpListener(IHttpListener<M> pListener) {
        mHttpListener = pListener;
    }

    public void setRequestMethod(String me) {
        questMethod = me;
    }


    /**
     * 子线程中执行
     */
    public void excute() {
        mBaseListener = new BaseListener(mHttpListener);
        int vCode = -1;
        InputStream is = null;
        try {
            mURLConnection = (HttpURLConnection) mURL.openConnection();
            mURLConnection.setRequestMethod(questMethod);
            if (mArgs != null) {
                Set<Map.Entry<String, String>> vEntries = mArgs.entrySet();
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
                    mBaseListener.onSuccess(is);
                } else {
                    mBaseListener.onFail(vCode, "");
                }
            }
            mBaseListener.onFail(vCode, "");
        } catch (IOException pE) {
            pE.printStackTrace();
            mBaseListener.onFail(vCode, pE.toString());
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
