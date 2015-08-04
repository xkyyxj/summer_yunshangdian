package com.example.administrator.myapplication.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.image.ImageLoader;
import com.example.administrator.myapplication.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/7/17.
 */
public class DetailSafeHolder extends BaseHolder<AppInfo> implements View.OnClickListener {
    private RelativeLayout safe_layout;
    private LinearLayout safe_content;
    private ImageView safe_arrow;
    private ImageView[] iv;
    private ImageView[] div;
    private TextView[] dtv;
    private LinearLayout[] dl;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.app_detail_safe);

        safe_layout = (RelativeLayout) view.findViewById(R.id.safe_layout);
        safe_content = (LinearLayout) view.findViewById(R.id.safe_content);

        safe_content.getLayoutParams().height = 0;
        safe_arrow = (ImageView) view.findViewById(R.id.safe_arrow);
        safe_arrow.setTag(false);
        safe_layout.setOnClickListener(this);

        iv = new ImageView[4];
        iv[0] = (ImageView) view.findViewById(R.id.iv_1);
        iv[1] = (ImageView) view.findViewById(R.id.iv_2);
        iv[2] = (ImageView) view.findViewById(R.id.iv_3);
        iv[3] = (ImageView) view.findViewById(R.id.iv_4);

        div = new ImageView[4];
        div[0] = (ImageView) view.findViewById(R.id.des_iv_1);
        div[1] = (ImageView) view.findViewById(R.id.des_iv_2);
        div[2] = (ImageView) view.findViewById(R.id.des_iv_3);
        div[3] = (ImageView) view.findViewById(R.id.des_iv_4);

        dtv = new TextView[4];
        dtv[0] = (TextView) view.findViewById(R.id.des_tv_1);
        dtv[1] = (TextView) view.findViewById(R.id.des_tv_2);
        dtv[2] = (TextView) view.findViewById(R.id.des_tv_3);
        dtv[3] = (TextView) view.findViewById(R.id.des_tv_4);

        dl = new LinearLayout[4];
        dl[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
        dl[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
        dl[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
        dl[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);

        return view;
    }

    @Override
    public void refreshView() {
        AppInfo info = getData();
        List<String> Url = info.getSafeUrl();
        List<String> DesUrl = info.getSafeDesUrl();
        List<String> Des = info.getSafeDes();

        for (int i = 0;i < 4; i++){
            if (i < Url.size() && i <DesUrl.size() && i < Des.size()){
                ImageLoader.load(iv[i], Url.get(i));
                ImageLoader.load(div[i], DesUrl.get(i));
                dtv[i].setText(Des.get(i));
                int color = Color.rgb(0, 177, 62);
                dtv[i].setTextColor(color);
                iv[i].setVisibility(View.VISIBLE);
                dl[i].setVisibility(View.VISIBLE);

            }
            else {
                iv[i].setVisibility(View.GONE);
                dl[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.safe_layout:
                change();
                break;
            default:
                break;
        }
    }

    private void change() {
        final ViewGroup.LayoutParams params = safe_content.getLayoutParams();
        int targetHeight;
        int height = safe_content.getMeasuredHeight();
        boolean flag = (boolean) safe_arrow.getTag();
        if (flag){
            safe_arrow.setTag(false);
            targetHeight = 0;
        }else{
            safe_arrow.setTag(true);
            targetHeight = measureContentHeight();
        }

        safe_layout.setEnabled(false);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(height, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.height = (int) animation.getAnimatedValue();
                safe_content.setLayoutParams(params);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                boolean flag = (boolean) safe_arrow.getTag();
                safe_arrow.setImageResource(flag ? R.drawable.arrow_up : R.drawable.arrow_down);
                safe_layout.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

    private int measureContentHeight(){
        int width = safe_content.getMeasuredWidth();
        safe_content.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST);
        safe_content.measure(widthMeasureSpec, heightMeasureSpec);

        return safe_content.getMeasuredHeight();
    }
}
