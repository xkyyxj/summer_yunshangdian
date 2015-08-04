package com.example.administrator.myapplication.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.CategoryInfo;
import com.example.administrator.myapplication.image.ImageLoader;
import com.example.administrator.myapplication.utils.StringUtils;
import com.example.administrator.myapplication.utils.UIUtils;

/**
 * Created by Administrator on 2015/7/10.
 */
public class CategoryHolder extends BaseHolder<CategoryInfo> {

    private ImageView item_icon1;
    private ImageView item_icon2;
    private ImageView item_icon3;
    private TextView item_name1;
    private TextView item_name2;
    private TextView item_name3;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.category_item);

        item_icon1 = (ImageView) view.findViewById(R.id.iv_1);
        item_icon2 = (ImageView) view.findViewById(R.id.iv_2);
        item_icon3 = (ImageView) view.findViewById(R.id.iv_3);
        item_name1 = (TextView) view.findViewById(R.id.tv_1);
        item_name2 = (TextView) view.findViewById(R.id.tv_2);
        item_name3 = (TextView) view.findViewById(R.id.tv_3);
        rl1 = (RelativeLayout) view.findViewById(R.id.rl_1);
        rl2 = (RelativeLayout) view.findViewById(R.id.rl_2);
        rl3 = (RelativeLayout) view.findViewById(R.id.rl_3);
        return view;
    }

    @Override
    public void refreshView() {
        CategoryInfo categoryInfo = getData();
        String name1 = categoryInfo.getName1();
        String name2 = categoryInfo.getName2();
        String name3 = categoryInfo.getName3();
        String ur1 = categoryInfo.getImageUrl1();
        String ur2 = categoryInfo.getImageUrl2();
        String ur3 = categoryInfo.getImageUrl3();

        if(StringUtils.isEmpty(name1)){
            item_name1.setText("");
            item_icon1.setImageDrawable(null);
            rl1.setEnabled(false);
        }else{
            item_name1.setText(name1);
            ImageLoader.load(item_icon1, ur1);
            rl1.setEnabled(true);
        }
        if(StringUtils.isEmpty(name2)){
            item_name2.setText("");
            item_icon2.setImageDrawable(null);
            rl2.setEnabled(false);
        }else{
            item_name2.setText(name2);
            ImageLoader.load(item_icon2, ur2);
            rl2.setEnabled(true);
        }
        if(StringUtils.isEmpty(name3)){
            item_name3.setText("");
            item_icon3.setImageDrawable(null);
            rl3.setEnabled(false);
        }else{
            item_name3.setText(name3);
            ImageLoader.load(item_icon3, ur3);
            rl3.setEnabled(true);
        }
    }
}
