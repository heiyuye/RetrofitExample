package com.example.liucr.retrofitexample.ui.activities.account;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.Toast;

import com.example.clientlib.client.BaseDevice;
import com.example.liucr.retrofitexample.R;
import com.example.liucr.retrofitexample.presenter.LoginPresenter;
import com.example.liucr.retrofitexample.ui.activities.BaseActivity;
import com.example.liucr.retrofitexample.ui.activities.DeviceDebugActivity;
import com.example.liucr.retrofitexample.ui.viewfeatures.LoginView;
import com.example.servicelib.UDPService;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liucr on 2017/2/5.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @Bind(R.id.login_input_phone)
    TextInputEditText loginInputPhone;
    @Bind(R.id.login_input_password)
    TextInputEditText loginInputPassword;
    @Bind(R.id.login_enter)
    AppCompatButton loginEnter;

    private LoginPresenter loginPresenter;

    @Override
    protected int initLayoutResID() {
        return R.layout.act_login;
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loginPresenter.cancelLogin();
    }

    @OnClick(R.id.login_enter)
    public void onClick() {
        String phone = loginInputPhone.getText().toString();
        String password = loginInputPassword.getText().toString();

        loginPresenter.login(phone, password);
    }

    @OnClick(R.id.start_service)
    void clickService() {
        startService(new Intent(this, UDPService.class));
    }

    @OnClick(R.id.start_client)
    void clickClient() {

    }

    @OnClick(R.id.start_discovery)
    void clickDiscovery() {
        startActivity(new Intent(this, DeviceDebugActivity.class));
    }

    @Override
    public void onLoginFail(String msg) {
        Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show();
        Log.i("liucr", msg);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }
}
