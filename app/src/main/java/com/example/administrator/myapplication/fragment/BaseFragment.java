package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.utils.ViewUtils;
import com.example.administrator.myapplication.view.LoadingPage;

import java.util.List;

/**
 * Created by Administrator on 2015/7/6.
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPage mContentPage;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        if (mContentPage == null) {
            mContentPage = new LoadingPage(UIUtils.getContext()) {
                @Override
                public View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }
                @Override
                protected LoadResult load() {
                    return BaseFragment.this.load();
                }
            };
        }else{
            ViewUtils.removeSelfFromParent(mContentPage);
        }

        return mContentPage;
    }

    public abstract LoadingPage.LoadResult load();

    public abstract View createSuccessView() ;

    public void show() {
        if(null != mContentPage){
            mContentPage.show();
        }
    }
    public LoadingPage.LoadResult checkData(Object object) {
        if (null == object){
            return LoadingPage.LoadResult.STATE_ERROR;
        }
        if (object instanceof List){
            List list = (List) object;
            if (list.size() == 0){
                return LoadingPage.LoadResult.STATE_EMPTY;
            }
        }
        return LoadingPage.LoadResult.STATE_SUCCESS;
    }
}
