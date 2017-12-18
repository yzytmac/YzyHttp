package com.yzy.yzyhttpdemo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.yzy.yzyhttplib.IHttpListener;
import com.yzy.yzyhttplib.Response;
import com.yzy.yzyhttplib.TestEntity;
import com.yzy.yzyhttplib.YzyHttpClient;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View pView){
        for (int i = 0; i < 20; i++) {
            request();
            SystemClock.sleep(500);
        }
    }

    private void request() {
        String urlString = "https://gitee.com/yzytmac/resource/raw/master/test.json";
        Type vType = new TypeToken<Response<TestEntity>>() {
        }.getType();
        YzyHttpClient.request(urlString, null, vType,new IHttpListener<Response<TestEntity>>() {
            @Override
            public void onSuccess(Response<TestEntity> pResponse) {
                int vCode = pResponse.code;
                String vMessage = pResponse.message;
                ArrayList<TestEntity> vResult = pResponse.result;

                Log.e("yzy", "vCode: " + vCode);
                Log.e("yzy", "vMessage: " + vMessage);

                for (TestEntity vEntity : vResult) {
                    Log.e("yzy", "onSuccess: " + vEntity.id);
                    Log.e("yzy", "imei: " + vEntity.imei);
                    Log.e("yzy", "userId: " + vEntity.userId);
                }
            }

            @Override
            public void onFail(int code, String msg) {
                Log.e("yzy", "onFail: " + code);
                Log.e("yzy", "msg: " + msg);
            }
        });
    }
}
