package com.example.administrator.myapplication.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.utils.UIUtils;

/**
 * Created by Administrator on 2015/7/16.
 */
public class DetailDesHolder extends BaseHolder<AppInfo> implements View.OnClickListener {
    private TextView des_content;
    private TextView des_author;
    private RelativeLayout des_layout;
    private ImageView des_arrow;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.app_detail_des);


        des_content = (TextView) view.findViewById(R.id.des_content);
        des_content.getLayoutParams().height = 330;
        des_author = (TextView) view.findViewById(R.id.des_author);
        des_layout = (RelativeLayout) view.findViewById(R.id.des_layout);
        des_layout.setOnClickListener(this);
        des_arrow = (ImageView) view.findViewById(R.id.des_arrow);
        des_arrow.setTag(false);
        return view;
    }

    @Override
    public void refreshView() {
        AppInfo info = getData();
        des_content.setText(info.getDes());
        des_author.setText(UIUtils.getString(R.string.app_detail_author) + info.getAuthor());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.des_layout:
                change();
                break;
            default:
                break;
        }
    }

    private void change() {
        final ViewGroup.LayoutParams params = des_content.getLayoutParams();
        int targetHeight;
        int height = des_content.getMeasuredHeight();
        boolean flag = (boolean) des_arrow.getTag();
        if (flag){
            des_arrow.setTag(false);
            targetHeight = 330;
        }else{
            des_arrow.setTag(true);
            targetHeight = measureContentHeight();
        }

        des_layout.setEnabled(false);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(height, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.height = (int) animation.getAnimatedValue();
                des_content.setLayoutParams(params);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                boolean flag = (boolean) des_arrow.getTag();
                des_arrow.setImageResource(flag ? R.drawable.arrow_up : R.drawable.arrow_down);
                des_layout.setEnabled(true);
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
        int width = des_content.getMeasuredWidth();
        des_content.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST);
        des_content.measure(widthMeasureSpec, heightMeasureSpec);

        return des_content.getMeasuredHeight();
    }
}
