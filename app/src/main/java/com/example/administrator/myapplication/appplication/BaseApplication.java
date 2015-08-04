package com.example.administrator.myapplication.appplication;

import android.app.Application;
import android.os.Looper;
import android.os.Handler;



/**
 * Created by Administrator on 2015/7/3.
 */
public class BaseApplication extends Application {

    private  static BaseApplication mContext;
    private static Handler mMainThreadHandler;
    private  static Looper mMainThreadLooper;
    private  static  Thread mMainThread;
    private  static  int mMainThreadId;

    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThread = Thread.currentThread();
        this.mMainThreadId = android.os.Process.myTid();

    }
    public static BaseApplication getApplication(){ return mContext;}

    public static Handler getMainThreadHandler(){
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper(){
        return mMainThreadLooper;
    }

    public static Thread getMainThread(){
        return mMainThread;
    }

    public static int getMainThreadId(){
        return mMainThreadId;
    }
}
