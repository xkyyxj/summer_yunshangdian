package com.example.administrator.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.administrator.myapplication.utils.UIUtils;

/**
 * Created by Administrator on 2015/7/13.
 */
public class IndicatorView extends View{

    private static final int POSITION_NONE = -1;

    private Drawable mDrbIndicator;
    private int mCount;
    private int mSelection;
    private int mInterval;

    public IndicatorView(Context context) {
        super(context);
        init();
    }

    /** ��ʼ�� */
    private void init() {
        mSelection = POSITION_NONE;
    }

    /** ������Ŀ */
    public void setCount(int count) {
        count = count > 0 ? count : 0;
        if (count != mCount) {
            mCount = count;
            requestLayoutInner();
            requestInvalidate();
        }
    }

    /** ����ѡ���� */
    public void setSelection(int selection) {
        if (selection != mSelection) {
            mSelection = selection;
            requestInvalidate();
        }
    }

    /** ����ѡ�����ͼƬ */
    public void setIndicatorDrawable(Drawable drawable) {
        mDrbIndicator = drawable;
        requestLayoutInner();
        requestInvalidate();
    }

    /** ����item֮���� */
    public void setInterval(int interval) {
        if (interval != mInterval) {
            mInterval = interval;
            requestLayoutInner();
            requestInvalidate();
        }
    }

    private void requestInvalidate() {
        if (UIUtils.isRunInMainThread()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    private void requestLayoutInner() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // ������
        if (widthMode == MeasureSpec.EXACTLY) {//����Ǿ�ȷ�ģ��Ͳ��þ�ȷֵ
            width = widthSize;
        } else {//����Ͳ���ͼƬ�Ŀ��
            int indicatorW = mDrbIndicator == null ? 0 : mDrbIndicator.getIntrinsicWidth();
            int expectedW = indicatorW * mCount + mInterval * (mCount - 1) + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(expectedW, widthSize);
            } else {
                width = expectedW;
            }
        }
        // ����߶�
        if (heightMode == MeasureSpec.EXACTLY) {//����Ǿ�ȷ�ģ��Ͳ��þ�ȷֵ
            height = heightSize;
        } else {//����Ͳ���ͼƬ�ĸ߶�
            int indicatorH = mDrbIndicator == null ? 0 : mDrbIndicator.getIntrinsicHeight();
            int expectedH = indicatorH + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(expectedH, heightSize);
            } else {
                height = expectedH;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrbIndicator == null || mCount == 0) {
            return;
        }
        int w = mDrbIndicator.getIntrinsicWidth();
        int h = mDrbIndicator.getIntrinsicHeight();
        int horizontalSideSpacing = (getWidth() - getPaddingLeft() - getPaddingRight() - w * mCount - mInterval * (mCount - 1)) / 2;
        int verticalSideSpacing = (getHeight() - getPaddingTop() - getPaddingBottom() - h) / 2;
        int l = getPaddingLeft() + horizontalSideSpacing;
        int t = getPaddingTop() + verticalSideSpacing;
        int rEdge = getRight() - getPaddingRight();
        int bEdge = getBottom() - getPaddingBottom();
        //�������϶�ͷ�Χ��Ȼ��ͼƬ��ʵ�ʻ�����ͬһ��ͼƬ��ֻ�Ǹı�ͼƬ��bounds
        for (int i = 0; i < mCount; i++) {
            mDrbIndicator.setBounds(l, t, Math.min(l + w, rEdge), Math.min(t + h, bEdge));
            if (i == mSelection) {
                mDrbIndicator.setState(new int[]{android.R.attr.state_selected});
            } else {
                mDrbIndicator.setState(null);
            }
            mDrbIndicator.draw(canvas);
            l += w + mInterval;
        }
    }
}
