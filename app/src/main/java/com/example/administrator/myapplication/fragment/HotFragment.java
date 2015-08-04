package com.example.administrator.myapplication.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.protocol.HotProtocol;
import com.example.administrator.myapplication.utils.DrawableUtils;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.FlowLayout;
import com.example.administrator.myapplication.view.LoadingPage;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/7/6.
 */
public class HotFragment extends BaseFragment {
    List<String> mData;
    FlowLayout mLayout;
    @Override
    public LoadingPage.LoadResult load() {
        HotProtocol protocol = new HotProtocol();
        mData = protocol.load(0);
        return checkData(mData);
    }
    @Override
    public View createSuccessView() {
        ScrollView mScrollView = new ScrollView(UIUtils.getContext());
        mScrollView.setFillViewport(true);

        mLayout = new FlowLayout(UIUtils.getContext());
        mLayout.setBackgroundResource(R.drawable.grid_item_bg_normal);
        int layoutPadding = UIUtils.dip2px(13);
        mLayout.setPadding(layoutPadding, layoutPadding, layoutPadding, layoutPadding);
        mLayout.setHorizontalSpacing(layoutPadding);
        mLayout.setVerticalSpacing(layoutPadding);

        int textPaddingV = UIUtils.dip2px(4);
        int textPaddingH = UIUtils.dip2px(7);
        int backColor = 0xffcecece;
        int radius = UIUtils.dip2px(5);

        GradientDrawable pressDrawable = DrawableUtils.createDrawable(backColor, backColor, radius);
        Random mRdm = new Random();
        for (int i = 0; i < mData.size(); i++) {
            TextView tv = new TextView(UIUtils.getContext());
            int red = 32 + mRdm.nextInt(208);
            int green = 32 + mRdm.nextInt(208);
            int blue = 32 + mRdm.nextInt(208);
            int color = Color.rgb(red, green, blue);

            GradientDrawable normalDrawable = DrawableUtils.createDrawable(color, color, radius);
            StateListDrawable selector = DrawableUtils.createSelector(normalDrawable, pressDrawable);
            tv.setBackgroundDrawable(selector);

            final String text = mData.get(i);
            tv.setText(text);
            tv.setTextColor(Color.rgb(255,255,255));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtils.showToastSafe(text);
                }
            });
            mLayout.addView(tv);
        }
        mScrollView.addView(mLayout);
        return mScrollView;
    }
}
