package com.example.administrator.myapplication.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.administrator.myapplication.adapter.AppBaseAdapter;
import com.example.administrator.myapplication.adapter.MyBaseAdapter;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.holder.BaseHolder;
import com.example.administrator.myapplication.holder.GameHolder;
import com.example.administrator.myapplication.protocol.GameProtocol;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/6.
 */
public class GameFragment extends BaseFragment {

    private List<AppInfo> mDatas;
    private GameAdapter adapter;
    @Override
    public LoadingPage.LoadResult load() {
        mDatas = new ArrayList<AppInfo>();
        GameProtocol protocol = new GameProtocol();
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
        ListView mListView = new ListView(UIUtils.getContext());

        adapter = new GameAdapter(mListView,mDatas);

        mListView.setAdapter(adapter);
        return mListView;
    }

    private class GameAdapter extends AppBaseAdapter {
        public GameAdapter(ListView mListView, List<AppInfo> mDatas) {
            super( mListView,mDatas);
        }
        @Override
        protected List onLoadMore() {
            GameProtocol protocol = new GameProtocol();
            return protocol.load(getData().size());
        }
    }
}
