package com.example.administrator.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.manager.ThreadManager;
import com.example.administrator.myapplication.utils.UIUtils;

/**
 * Created by Administrator on 2015/7/6.
 */
public abstract class LoadingPage extends FrameLayout{

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    final int STATE_UNLOAD = 1;
    final int STATE_LOADING = 2;
    final int STATE_ERROR = 3;
    final int STATE_EMPTY = 4;
    final int STATE_SUCCESS = 5;

    int mState = STATE_UNLOAD;
    private View page_bt;

    public LoadingPage(Context context) {
        super(context);
        init();
    }

    private void init() {
        mLoadingView = createLoadingView();
        if (null != mLoadingView){
            addView(mLoadingView,-1,-1);
        }
        mErrorView = createErrorView();
        if (null != mErrorView){
            addView(mErrorView,-1,-1);
        }
        mEmptyView = createEmptyView();
        if (null != mEmptyView){
            addView(mEmptyView,-1,-1);
        }
        showSafePage();
    }

    private void showSafePage() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    private void showPage() {
        //mLoadingView = createLoadingView();addView(mLoadingView,-1,-1);mLoadingView.setVisibility(VISIBLE);
        if (null != mLoadingView){
            mLoadingView.setVisibility(mState == STATE_UNLOAD || mState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (null != mErrorView){
            mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (null != mEmptyView){
            mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }
        if (mState == STATE_SUCCESS && mSuccessView == null){
            mSuccessView = createSuccessView();
            addView(mSuccessView, -1, -1);
        }
        if (null != mSuccessView) {
            mSuccessView.setVisibility(mState == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public abstract View createSuccessView() ;

    protected View createEmptyView() {
        return UIUtils.inflate(R.layout.loading_page_empty);
    }

    protected View createErrorView() {
        View view = UIUtils.inflate(R.layout.loading_page_error);
        view.findViewById(R.id.page_bt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    protected View createLoadingView() {
        return UIUtils.inflate(R.layout.loading_page_loading);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public  class LoadTask implements Runnable {
        LoadResult result;

        @Override
        public void run() {
            result = load();
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    mState = result.getValue();
                    showPage();
                }
            });
        }
    }
    protected abstract LoadResult load();

    public enum LoadResult {
        STATE_ERROR(3), STATE_EMPTY(4), STATE_SUCCESS(5);
        int value;
        LoadResult(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    public void reset(){
        mState = STATE_UNLOAD;
        showSafePage();
    }
    protected boolean needReset() {
        return mState == STATE_ERROR || mState == STATE_EMPTY;
    }
    public synchronized void show(){
        if (needReset()){
            mState = STATE_UNLOAD;
        }
        if (mState == STATE_UNLOAD){
            mState = STATE_LOADING;

            LoadTask task = new LoadTask();
            ThreadManager.getLongPool().execute(task);
        }
    showSafePage();
    }
}
