package com.example.administrator.myapplication.holder;

import android.view.View;

/**
 * Created by Administrator on 2015/7/7.
 */
public abstract class BaseHolder<T> {

    private View mRootView;
    public   T mData;

    public BaseHolder() {
        super();
        mRootView = initView();
        mRootView.setTag(this);
    }

    public abstract View initView() ;

    public View getRootView(){
        return mRootView;
    }

    public void setData(T data){
        this.mData = data;
        refreshView();
    }

    public T getData(){
        return mData;
    }

    public abstract void refreshView();

    public void recycle() {

    }
}
