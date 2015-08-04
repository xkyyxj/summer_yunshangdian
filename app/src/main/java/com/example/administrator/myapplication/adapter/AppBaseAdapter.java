package com.example.administrator.myapplication.adapter;



import android.content.Intent;
import android.widget.ListView;

import com.example.administrator.myapplication.DetailActivity;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.bean.DownloadInfo;
import com.example.administrator.myapplication.holder.AppHolder;
import com.example.administrator.myapplication.holder.BaseHolder;
import com.example.administrator.myapplication.manager.DownloadManager;
import com.example.administrator.myapplication.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/7/14.
 */
public abstract class AppBaseAdapter extends MyBaseAdapter<AppInfo> implements DownloadManager.DownloadObserver {
    public AppBaseAdapter(ListView mListview, List<AppInfo> mDatas) {
        super(mListview, mDatas);
        startObserver();
    }

    public void startObserver() {
        DownloadManager.getInstance().registerObserver(this);
    }


    public void stopObserver() {
        DownloadManager.getInstance().unRegisterObserver(this);
    }
    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshHolder(info);
    }

    @Override
    public void onDownloadProgressed(DownloadInfo info) {
        refreshHolder(info);
    }

    private void refreshHolder(final DownloadInfo info) {
        List<BaseHolder> displayedHolders = getDisplayedHolders();
        for (int i = 0; i < displayedHolders.size(); i++) {
            BaseHolder baseHolder = displayedHolders.get(i);
            if (baseHolder instanceof AppHolder) {
                final AppHolder holder = (AppHolder) baseHolder;
                AppInfo appInfo = holder.getData();
                if (appInfo.getId() == info.getId()) {
                    UIUtils.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.refreshState(info.getDownloadState(), info.getProgress());
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onItemClickInner(int position) {
        List<AppInfo> data = getData();
        if (position < data.size()) {
            Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
            AppInfo info = data.get(position);
           intent.putExtra(DetailActivity.PACKAGENAME, info.getPackageName());
            UIUtils.startActivity(intent);
        }
    }

    @Override
    public BaseHolder getHolder() {
        return new AppHolder();
    }

}
