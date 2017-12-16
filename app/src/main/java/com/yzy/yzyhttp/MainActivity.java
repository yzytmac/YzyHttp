package com.yzy.yzyhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yzy.yzyhttplib.IHttpListener;
import com.yzy.yzyhttplib.YzyHttp;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        YzyHttp.request("", null, new IHttpListener() {
            @Override
            public void onSuccess(InputStream pInputStream) {

            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }
}
