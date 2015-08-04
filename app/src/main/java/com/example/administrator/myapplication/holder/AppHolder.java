package com.example.administrator.myapplication.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.bean.DownloadInfo;
import com.example.administrator.myapplication.image.ImageLoader;
import com.example.administrator.myapplication.manager.DownloadManager;
import com.example.administrator.myapplication.utils.StringUtils;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.ProgressArc;

/**
 * Created by Administrator on 2015/7/8.
 */
public class AppHolder extends BaseHolder<AppInfo> implements View.OnClickListener {
    private ImageView item_icon;
    private TextView item_title;
    private RatingBar item_rating;
    private TextView item_size;
    private TextView item_bottom;

    private float mprogress;
    private DownloadManager downloadManager;
    private int state;
    private TextView actionText;
    private RelativeLayout item_action;
    private FrameLayout action_progress;
    private ProgressArc progressArc;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item);

        item_icon = (ImageView) view.findViewById(R.id.item_icon);

        item_title = (TextView) view.findViewById(R.id.item_title);

        item_rating = (RatingBar) view.findViewById(R.id.item_rating);

        item_size = (TextView) view.findViewById(R.id.item_size);

        item_bottom = (TextView) view.findViewById(R.id.item_bottom);

        item_action = (RelativeLayout) view.findViewById(R.id.item_action);
        item_action.setOnClickListener(this);
        item_action.setBackgroundResource(R.drawable.list_item_action_bg);

        action_progress = (FrameLayout) view.findViewById(R.id.action_progress);
        progressArc = new ProgressArc(UIUtils.getContext());
        int diameter = UIUtils.dip2px(26);
        progressArc.setArcDiameter(diameter);
        progressArc.setProgressColor(UIUtils.getColor(R.color.progress));
        int size = UIUtils.dip2px(27);
        action_progress.addView(progressArc,new ViewGroup.LayoutParams(size,size));
        actionText = (TextView) view.findViewById(R.id.action_txt);
        return view;
    }

    @Override
    public void refreshView() {

        AppInfo appInfo = getData();

        ImageLoader.load(item_icon, appInfo.getIconUrl());

        item_title.setText(appInfo.getName());

        item_rating.setRating(appInfo.getStars());

        item_size.setText(StringUtils.formatFileSize(appInfo.getSize()));

        item_bottom.setText(appInfo.getDes());

        refreshState(state,mprogress);
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
    public void refreshState(int downloadState, float progress) {
        state = downloadState;
        mprogress = progress;
        switch (state) {
            case DownloadManager.STATE_NONE:
                progressArc.seForegroundResource(R.drawable.ic_download);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                actionText.setText(R.string.app_state_download);
                break;
            case DownloadManager.STATE_PAUSED:
                progressArc.seForegroundResource(R.drawable.ic_pause);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                actionText.setText(R.string.app_state_paused);
                break;
            case DownloadManager.STATE_ERROR:
                progressArc.seForegroundResource(R.drawable.ic_redownload);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                actionText.setText(R.string.app_state_error);
                break;
            case DownloadManager.STATE_WAITING:
                progressArc.seForegroundResource(R.drawable.ic_resume);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                progressArc.setProgress(mprogress, false);
                actionText.setText(R.string.app_state_waiting);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                progressArc.seForegroundResource(R.drawable.ic_pause);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                progressArc.setProgress(mprogress, true);
                actionText.setText((int) (mprogress * 100) + "%");
                break;
            case DownloadManager.STATE_DOWNLOADED:
                progressArc.seForegroundResource(R.drawable.ic_install);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                actionText.setText(R.string.app_state_downloaded);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item_action){
            if (state == DownloadManager.STATE_NONE || state == DownloadManager.STATE_ERROR || state == DownloadManager.STATE_PAUSED){
                downloadManager.download(getData());
            }else if (state == DownloadManager.STATE_DOWNLOADING || state == DownloadManager.STATE_WAITING){
                downloadManager.pause(getData());
            }else if (state == DownloadManager.STATE_DOWNLOADED){
                downloadManager.install(getData());
            }
        }
    }
}
