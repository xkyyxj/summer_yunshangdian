package com.example.administrator.myapplication.fragment;

//import android.app.Fragment;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.myapplication.adapter.AppBaseAdapter;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.holder.AppHolder;
import com.example.administrator.myapplication.holder.BaseHolder;
import com.example.administrator.myapplication.holder.HomePictureHolder;
import com.example.administrator.myapplication.protocol.HomeProtocol;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/5.
 */
public class HomeFragment extends BaseFragment {

    private HomePictureHolder mHolder;
    private List<AppInfo> mDatas;
    private List<String> mPicture;
    private HomeAdapter adapter;

    @Override
    public LoadingPage.LoadResult load() {
        mDatas = new ArrayList<AppInfo>();
        HomeProtocol protocol = new HomeProtocol();
        mDatas = protocol.load(0);
        mPicture = protocol.getPictureUrl();
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

        ListView mListView = new ListView(UIUtils.getContext());

        if(mPicture != null && mPicture.size() > 0){
            mHolder = new HomePictureHolder();
            mHolder.setData(mPicture);
            mListView.addHeaderView(mHolder.getRootView());
        }
        adapter = new HomeAdapter(mListView,mDatas);

        mListView.setAdapter(adapter);

          return mListView;
    }

    private class HomeAdapter extends AppBaseAdapter {
        public HomeAdapter(ListView mListview, List<AppInfo> mDatas) {
            super(mListview, mDatas);
        }

        @Override
        protected List onLoadMore() {
            HomeProtocol protocol = new HomeProtocol();
            return protocol.load(getData().size());
        }
    }
}
