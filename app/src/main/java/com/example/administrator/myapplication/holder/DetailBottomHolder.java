package com.example.administrator.myapplication.holder;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.bean.DownloadInfo;
import com.example.administrator.myapplication.manager.DownloadManager;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.ProgressHorizontal;

/**
 * Created by Administrator on 2015/7/19.
 */
public class DetailBottomHolder extends BaseHolder<AppInfo> implements View.OnClickListener,DownloadManager.DownloadObserver{
    private Button bottom_favorites;
    private Button bottom_share;
    private Button progress_btn;
    private FrameLayout progress_layout;
    private ProgressHorizontal mProgeressHorizontal;

    private float mprogress;
    private DownloadManager downloadManager;
    private int state;

    public DetailBottomHolder() {
        super();
        startObserver();
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.app_detail_bottom);
        bottom_favorites = (Button) view.findViewById(R.id.bottom_favorites);
        bottom_share = (Button) view.findViewById(R.id.bottom_share);
        progress_btn = (Button) view.findViewById(R.id.progress_btn);
        bottom_favorites.setOnClickListener(this);
        bottom_share.setOnClickListener(this);
        progress_btn.setOnClickListener(this);
        bottom_favorites.setText(R.string.bottom_favorites);
        bottom_share.setText(R.string.bottom_share);

        progress_layout = (FrameLayout) view.findViewById(R.id.progress_layout);
        mProgeressHorizontal = new ProgressHorizontal(UIUtils.getContext());
        mProgeressHorizontal.setId(R.id.detail_progress);
        mProgeressHorizontal.setOnClickListener(this);
        mProgeressHorizontal.setProgressTextVisible(true);
        mProgeressHorizontal.setProgressTextColor(Color.WHITE);
        mProgeressHorizontal.setProgressTextSize(UIUtils.dip2px(18));
        mProgeressHorizontal.setBackgroundResource(R.drawable.progress_bg);
        mProgeressHorizontal.setProgressResource(R.drawable.progress);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        progress_layout.addView(mProgeressHorizontal, params);
        return view;
    }

    public void setData(AppInfo data){
        if (downloadManager == null){
            downloadManager = DownloadManager.getInstance();
        }
        DownloadInfo downloadInfo = downloadManager.getDownloadInfo(data.getId());
        if (downloadInfo!=null){
            state = downloadInfo.getDownloadState();
            mprogress = downloadInfo.getProgress();
        }else{
            state = DownloadManager.STATE_NONE;
            mprogress = 0;
        }
        super.setData(data);
    }

    @Override
    public void refreshView() {
        refreshState(state, mprogress);
    }

    private void refreshState(int downloadstate, float progress) {
        state = downloadstate;
        mprogress = progress;
        switch (state) {
            case DownloadManager.STATE_NONE:
                mProgeressHorizontal.setVisibility(View.GONE);
                progress_btn.setVisibility(View.VISIBLE);
                progress_btn.setText(UIUtils.getString(R.string.app_state_download));
                break;
            case DownloadManager.STATE_PAUSED:
                mProgeressHorizontal.setVisibility(View.VISIBLE);
                mProgeressHorizontal.setProgress(progress);
                mProgeressHorizontal.setCenterText(UIUtils.getString(R.string.app_state_paused));
                progress_btn.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_ERROR:
                mProgeressHorizontal.setVisibility(View.GONE);
                progress_btn.setVisibility(View.VISIBLE);
                progress_btn.setText(R.string.app_state_error);
                break;
            case DownloadManager.STATE_WAITING:
                mProgeressHorizontal.setVisibility(View.VISIBLE);
                mProgeressHorizontal.setProgress(progress);
                mProgeressHorizontal.setCenterText(UIUtils.getString(R.string.app_state_waiting));
                progress_btn.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mProgeressHorizontal.setVisibility(View.VISIBLE);
                mProgeressHorizontal.setProgress(progress);
                mProgeressHorizontal.setCenterText((int) (mprogress * 100) + "%");
                progress_btn.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_DOWNLOADED:
                mProgeressHorizontal.setVisibility(View.GONE);
                progress_btn.setVisibility(View.VISIBLE);
                progress_btn.setText(R.string.app_state_downloaded);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_favorites:
                UIUtils.showToastSafe(R.string.bottom_favorites);
                break;
            case R.id.bottom_share:
                UIUtils.showToastSafe(R.string.bottom_share);
                break;
            case R.id.detail_progress:
                if (state == DownloadManager.STATE_NONE || state == DownloadManager.STATE_PAUSED || state == DownloadManager.STATE_ERROR) {
                    downloadManager.download(getData());
                } else if (state == DownloadManager.STATE_WAITING || state == DownloadManager.STATE_DOWNLOADING) {
                    downloadManager.pause(getData());
                } else if (state == DownloadManager.STATE_DOWNLOADED) {
                    downloadManager.install(getData());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshHolder(info);
    }

    private void refreshHolder(final DownloadInfo info) {
        AppInfo appInfo = getData();
        if (appInfo.getId() == info.getId()) {
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    refreshState(info.getDownloadState(), info.getProgress());
                }
            });
        }
    }

    @Override
    public void onDownloadProgressed(DownloadInfo info) {
        refreshHolder(info);
    }

    public void startObserver() {
        DownloadManager.getInstance().registerObserver(this);
    }

    public void stopObserver() {
        DownloadManager.getInstance().unRegisterObserver(this);
    }
}
