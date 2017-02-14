package com.example.liucr.retrofitexample.retrofitApi;

import com.example.liucr.retrofitexample.bean.UserAuthBean;
import com.example.liucr.retrofitexample.bean.UserBean;
import com.example.liucr.retrofitexample.config.HttpServerConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;

/**
 * Created by liucr on 2017/2/6.
 */

public class XlinkApiManager {

    private volatile static XlinkApiManager mInstance;
    private XlinkService xlinkService;
    private Object monitor = new Object();

    private String accessToken;

    public static XlinkApiManager getInstance() {
        if (mInstance == null) {
            synchronized (XlinkApiManager.class) {
                if (mInstance == null)
                    mInstance = new XlinkApiManager();
            }
        }

        return mInstance;
    }

    public XlinkApiManager() {
        if (xlinkService == null) {
            synchronized (monitor) {
                if (xlinkService == null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    xlinkService = new Retrofit.Builder()
                            .baseUrl(HttpServerConfig.xlinkServer)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//用于返回Rxjava调用,非必须
                            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                            .client(MyOkHttpClient.getInstance().getOkHttpClient())
                            .build()
                            .create(XlinkService.class);
                }
            }
        }
    }

    public Observable<UserAuthBean> login(String phone, String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("corp_id", HttpServerConfig.companyId);
        params.put("password", password);
        return xlinkService.login(params);
    }

    public Observable<UserBean> getUser(String uid) {
        return xlinkService.getUser(accessToken, uid);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
