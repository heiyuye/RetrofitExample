package com.example.liucr.retrofitexample.ui.viewfeatures;

/**
 * Created by liucr on 2017/2/5.
 */

public interface LoginView extends MvpView{

    void onLoginFail(String msg);

    void onLoginSuccess();

}
