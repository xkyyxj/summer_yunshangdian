package com.test.albert.myapplication;

import android.app.Application;

import bmobObject.User;

/**
 * Created by Albert on 2015/7/22.
 */
public class MyApplication extends Application {

    private User user = null;

    public void onCreate()
    {
        super.onCreate();
    }

    public void setUser(User _user)
    {
        user = _user;
    }

    public User getUser()
    {
        return user;
    }

}
