package com.example.administrator.myapplication.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.image.ImageLoader;
import com.example.administrator.myapplication.utils.StringUtils;
import com.example.administrator.myapplication.utils.UIUtils;

/**
 * Created by Administrator on 2015/7/15.
 */
public class DetailInfoHolder extends BaseHolder<AppInfo> {
    private ImageView item_icon;
    private RatingBar item_rating;
    private TextView item_title;
    private TextView item_download;
    private TextView item_version;
    private TextView item_date;
    private TextView item_size;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.app_detail_info);
        item_icon = (ImageView) view.findViewById(R.id.item_icon);
        item_title = (TextView) view.findViewById(R.id.item_title);
        item_rating = (RatingBar) view.findViewById(R.id.item_rating);
        item_download = (TextView) view.findViewById(R.id.item_download);
        item_version = (TextView) view.findViewById(R.id.item_version);
        item_date = (TextView) view.findViewById(R.id.item_date);
        item_size = (TextView) view.findViewById(R.id.item_size);
        return view;
    }

    @Override
    public void refreshView() {
        AppInfo info = getData();
        ImageLoader.load(item_icon, info.getIconUrl());
        item_title.setText(info.getName());
        item_rating.setRating(info.getStars());
        item_download.setText(UIUtils.getString(R.string.app_detail_download) + info.getDownloadNum());
        item_version.setText(UIUtils.getString(R.string.app_detail_version) + info.getVersion());
        item_date.setText(UIUtils.getString(R.string.app_detail_date) + info.getDate());
        item_size.setText(UIUtils.getString(R.string.app_detail_size) + StringUtils.formatFileSize(info.getSize()));
    }
}
