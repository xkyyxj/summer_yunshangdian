package com.example.administrator.myapplication.holder;

import android.view.View;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.image.ImageLoader;
import com.example.administrator.myapplication.utils.UIUtils;

/**
 * Created by Administrator on 2015/7/15.
 */
public class DetailScreenHolder extends BaseHolder<AppInfo> {


    private ImageView[] mImageView;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.app_detail_screen);
        mImageView = new ImageView[5];
        mImageView[0] = (ImageView) view.findViewById(R.id.screen_1);
        mImageView[1] = (ImageView) view.findViewById(R.id.screen_2);
        mImageView[2] = (ImageView) view.findViewById(R.id.screen_3);
        mImageView[3] = (ImageView) view.findViewById(R.id.screen_4);
        mImageView[4] = (ImageView) view.findViewById(R.id.screen_5);

        return view;
    }

    @Override
    public void refreshView() {
        AppInfo info = getData();
        for (int i = 0; i < 5; i++) {
            if (i < info.getScreen().size()) {
                ImageLoader.load(mImageView[i], info.getScreen().get(i));
                mImageView[i].setVisibility(View.VISIBLE);
            } else {
                mImageView[i].setVisibility(View.GONE);
            }
        }
    }
}
