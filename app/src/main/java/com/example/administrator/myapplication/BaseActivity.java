package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2015/7/3.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private static BaseActivity mForegroundActivity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initActionbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mForegroundActivity = this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mForegroundActivity = null;
    }

    protected abstract void initActionbar();

    protected abstract void initView() ;

    protected abstract void init() ;

    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }
}
