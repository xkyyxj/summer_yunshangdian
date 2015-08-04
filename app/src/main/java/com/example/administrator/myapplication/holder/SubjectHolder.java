package com.example.administrator.myapplication.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.SubjectInfo;
import com.example.administrator.myapplication.image.ImageLoader;
import com.example.administrator.myapplication.utils.UIUtils;

/**
 * Created by Administrator on 2015/7/9.
 */
public class SubjectHolder extends BaseHolder<SubjectInfo> {

    private ImageView item_icon;
    private TextView item_txt;
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.subject_item);

        item_icon = (ImageView) view.findViewById(R.id.item_icon);
        item_txt = (TextView) view.findViewById(R.id.item_txt);
        return view;
    }

    @Override
    public void refreshView() {
        SubjectInfo subjectInfo = getData();
        ImageLoader.load(item_icon, subjectInfo.getUrl());
        item_txt.setText(subjectInfo.getDes());

    }
}
