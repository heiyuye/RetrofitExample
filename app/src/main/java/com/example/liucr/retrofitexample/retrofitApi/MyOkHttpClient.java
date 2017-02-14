package com.example.liucr.retrofitexample.retrofitApi;

import com.example.liucr.retrofitexample.log.LogUtil;
import com.example.liucr.retrofitexample.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by liucr on 2017/2/6.
 */

public class MyOkHttpClient {

    private volatile static MyOkHttpClient mInstance;
    private OkHttpClient okHttpClient;


    public static MyOkHttpClient getInstance() {
        if (mInstance == null) {
            synchronized (MyOkHttpClient.class) {
                if (mInstance == null)
                    mInstance = new MyOkHttpClient();
            }
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                    .writeTimeout(10000, TimeUnit.MILLISECONDS)
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .addInterceptor(new LoggerInterceptor(LogUtil.TAG, true))
                    .build();
        }
        return okHttpClient;
    }

}
