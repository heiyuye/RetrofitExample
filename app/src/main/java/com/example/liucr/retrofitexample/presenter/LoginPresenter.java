package com.example.liucr.retrofitexample.presenter;

import com.example.liucr.retrofitexample.bean.UserAuthBean;
import com.example.liucr.retrofitexample.bean.UserBean;
import com.example.liucr.retrofitexample.retrofitApi.XlinkApiManager;
import com.example.liucr.retrofitexample.ui.viewfeatures.LoginView;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liucr on 2017/2/5.
 */

public class LoginPresenter extends BasePresenter {

    private LoginView loginView;

    private Subscription loginSubscription;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    /**
     * 登录
     *
     * @param phone
     * @param password
     */
    public void login(String phone, String password) {

        if (loginSubscription != null) {
            loginSubscription.unsubscribe();
        }
        loginView.showLoading();
        loginSubscription = XlinkApiManager.getInstance()
                .login(phone, password)
                .flatMap(new Func1<UserAuthBean, Observable<UserBean>>() {
                    @Override
                    public Observable<UserBean> call(UserAuthBean userAuthBean) {
                        XlinkApiManager.getInstance().setAccessToken(userAuthBean.getAccess_token());
                        return XlinkApiManager.getInstance()
                                .getUser(userAuthBean.getUser_id());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.hideLoading();
                        String msg = null;
                        try {
                            msg = ((HttpException) e).response().errorBody().string();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        loginView.onLoginFail(msg);
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        loginView.hideLoading();
                        loginView.onLoginSuccess();
                    }
                });
    }

    /**
     * 取消登录
     */
    public void cancelLogin() {
        if (loginSubscription != null) {
            loginSubscription.unsubscribe();
        }
        loginView.hideLoading();
    }

    @Override
    public void doDestroy() {
        super.doDestroy();
        if (loginSubscription != null) {
            loginSubscription.unsubscribe();
        }
    }
}
