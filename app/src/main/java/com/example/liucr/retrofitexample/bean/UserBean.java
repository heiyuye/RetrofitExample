package com.example.liucr.retrofitexample.bean;

import java.util.List;

/**
 * Created by liucr on 2017/2/5.
 */

public class UserBean {


    /**
     * id : 用户ID
     * corp_id : 企业ID
     * phone : 手机号
     * nickname : 用户昵称
     * create_date : 创建时间
     * status : 用户状态
     * source : 用户来源
     * region_id : 所在区域ID
     * is_vaild : 用户账号是否已认证
     * tags : ["标签1","标签2"]
     * avatar : 头像资源地址url
     * qq_open_id : 绑定的QQ帐号信息
     * wx_open_id : 绑定的微信帐号信息
     * wb_open_id : 绑定的微博帐号信息
     * other_open_id : 绑定的其他平台帐号信息
     * country : 用户所在国家
     * province : 用户所在省份
     * city : 用户所在城市
     * gender : 用户性别, 1为男, 2为女, -1为未知
     * passwd_inited : 是否初始化密码
     * age : 年龄
     */

    private String id;
    private String corp_id;
    private String phone;
    private String nickname;
    private String create_date;
    private String status;
    private String source;
    private String region_id;
    private String is_vaild;
    private String avatar;
    private String qq_open_id;
    private String wx_open_id;
    private String wb_open_id;
    private String other_open_id;
    private String country;
    private String province;
    private String city;
    private String gender;
    private String passwd_inited;
    private String age;
    private List<String> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorp_id() {
        return corp_id;
    }

    public void setCorp_id(String corp_id) {
        this.corp_id = corp_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getIs_vaild() {
        return is_vaild;
    }

    public void setIs_vaild(String is_vaild) {
        this.is_vaild = is_vaild;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getQq_open_id() {
        return qq_open_id;
    }

    public void setQq_open_id(String qq_open_id) {
        this.qq_open_id = qq_open_id;
    }

    public String getWx_open_id() {
        return wx_open_id;
    }

    public void setWx_open_id(String wx_open_id) {
        this.wx_open_id = wx_open_id;
    }

    public String getWb_open_id() {
        return wb_open_id;
    }

    public void setWb_open_id(String wb_open_id) {
        this.wb_open_id = wb_open_id;
    }

    public String getOther_open_id() {
        return other_open_id;
    }

    public void setOther_open_id(String other_open_id) {
        this.other_open_id = other_open_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPasswd_inited() {
        return passwd_inited;
    }

    public void setPasswd_inited(String passwd_inited) {
        this.passwd_inited = passwd_inited;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
