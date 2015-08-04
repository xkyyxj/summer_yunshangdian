package com.example.administrator.myapplication.holder;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.CategoryInfo;
import com.example.administrator.myapplication.utils.UIUtils;

/**
 * Created by Administrator on 2015/7/13.
 */
public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

    View view;
    TextView textview;
    @Override
    public View initView() {
        view = UIUtils.inflate(R.layout.category_item_title);
        textview = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshView() {
       CategoryInfo categoryInfo = getData();
        textview.setText(categoryInfo.getTitle());
    }
}
