package com.example.liucr.retrofitexample.bean;

/**
 * Created by liucr on 2017/2/5.
 */

public class UserAuthBean {

    /**
     * user_id : 用户ID
     * access_token : 调用凭证
     * refresh_token : 刷新凭证
     * expire_in : 有效期（秒）
     * authorize : 用户认证码
     */

    private String user_id;
    private String access_token;
    private String refresh_token;
    private String expire_in;
    private String authorize;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(String expire_in) {
        this.expire_in = expire_in;
    }

    public String getAuthorize() {
        return authorize;
    }

    public void setAuthorize(String authorize) {
        this.authorize = authorize;
    }
}
