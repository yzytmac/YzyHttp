package com.yzy.yzyhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yzy.yzyhttplib.IHttpListener;
import com.yzy.yzyhttplib.Response;
import com.yzy.yzyhttplib.TestEntity;
import com.yzy.yzyhttplib.YzyHttpClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View pView){
        String urlString = "https://gitee.com/yzytmac/resource/raw/master/test.json";
        YzyHttpClient.request(urlString, null, new IHttpListener<TestEntity>() {
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
