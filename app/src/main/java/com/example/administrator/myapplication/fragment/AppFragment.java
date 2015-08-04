package com.example.administrator.myapplication.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.administrator.myapplication.adapter.AppBaseAdapter;
import com.example.administrator.myapplication.adapter.MyBaseAdapter;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.holder.AppHolder;
import com.example.administrator.myapplication.holder.BaseHolder;
import com.example.administrator.myapplication.protocol.AppProtocol;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/6.
 */
public class AppFragment extends BaseFragment {

    private List<AppInfo> mDatas;
    private AppAdapter adapter;

    @Override
    public LoadingPage.LoadResult load() {
        mDatas = new ArrayList<AppInfo>();
        AppProtocol protocol = new AppProtocol();
        mDatas = protocol.load(0);
        return checkData(mDatas);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startObserver();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.stopObserver();
        }
    }
    @Override
    public View createSuccessView() {
        ListView  mListView = new ListView(UIUtils.getContext());
        adapter = new AppAdapter(mListView,mDatas);
        mListView.setAdapter(adapter);

        return mListView;
    }

     public class AppAdapter extends AppBaseAdapter {
        public AppAdapter(ListView mListview, List<AppInfo> mDatas) {
            super(mListview, mDatas);
        }

        @Override
        protected List onLoadMore() {
            AppProtocol protocol = new AppProtocol();
            return protocol.load(getData().size());
        }
    }
}
