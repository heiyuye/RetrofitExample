package com.example.liucr.retrofitexample.manage;

/**
 * Created by liucr on 2017/2/5.
 */

public class UserManager {

    private volatile static UserManager mInstance;


    public static UserManager getInstance() {
        if (mInstance == null) {
            synchronized (UserManager.class) {
                if (mInstance == null)
                    mInstance = new UserManager();
            }
        }
        return mInstance;
    }

    public String getUserID() {
        return "";
    }
}
