package com.example.liucr.retrofitexample.retrofitApi;

import com.example.liucr.retrofitexample.bean.UserAuthBean;
import com.example.liucr.retrofitexample.bean.UserBean;
import com.example.liucr.retrofitexample.log.LogUtil;
import com.example.liucr.retrofitexample.manage.UserManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liucr on 2017/2/5.
 */

public interface XlinkService {

    /**
     * 用户登录，获取accesstoken
     *
     * @param phone
     * @param password
     * @param corp_id
     * @return
     */
    @POST("v2/user_auth")
    public Observable<UserAuthBean> login(
            @Body Map json);

    /**
     * 获取用户信息
     *
     * @param user_id
     * @return
     */
    @GET("v2/user/{user_id}?filter=setting")
    public Observable<UserBean> getUser(
            @Header("Access-Token") String accessToken,
            @Path("user_id") String user_id);
}
