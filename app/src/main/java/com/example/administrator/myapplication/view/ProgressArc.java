package com.example.administrator.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.administrator.myapplication.utils.UIUtils;


public class ProgressArc extends View {
	private final static int START_PROGRESS = -90;
	private static final int SET_PROGRESS_END_TIME = 1000;
	private static final float RATIO = 360f;
	public final static int PROGRESS_STYLE_NO_PROGRESS = -1;
	public final static int PROGRESS_STYLE_DOWNLOADING = 0;
	public final static int PROGRESS_STYLE_WAITING = 1;

	private int mDrawableForegroudResId;
	private Drawable mDrawableForegroud;//ͼƬ
	private int mProgressColor;//����������ɫ
	private RectF mArcRect;//���ڻ�Բ�ε�����
	private Paint mPaint;//�û����������Ļ���
	private boolean mUserCenter = false;
	private OnProgressChangeListener mProgressChangeListener;//���ȸı�ļ���
	private float mStartProgress;//��������ʼ����
	private float mCurrentProgress;//��ǰ����
	private float mProgress;//Ŀ�����
	private float mSweep;
	private long mStartTime, mEndTime;
	private int mStyle = PROGRESS_STYLE_NO_PROGRESS;
	private int mArcDiameter;

	public ProgressArc(Context context) {
		super(context);
		int strokeWidth = UIUtils.dip2px(1);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(strokeWidth);

		mUserCenter = false;

		mArcRect = new RectF();
	}

	public void setProgressChangeListener(OnProgressChangeListener listener) {
		mProgressChangeListener = listener;
	}

	public void seForegroundResource(int resId) {
		if (mDrawableForegroudResId == resId) {
			return;
		}
		mDrawableForegroudResId = resId;
		mDrawableForegroud = UIUtils.getDrawable(mDrawableForegroudResId);
		invalidateSafe();
	}

	/** ����ֱ�� */
	public void setArcDiameter(int diameter) {
		mArcDiameter = diameter;
	}

	/** ���ý���������ɫ */
	public void setProgressColor(int progressColor) {
		mProgressColor = progressColor;
		mPaint.setColor(progressColor);
	}

	public void setStyle(int style) {
		this.mStyle = style;
		if (mStyle == PROGRESS_STYLE_WAITING) {
			invalidateSafe();
		} else {
		}
	}

	/** ���ý��ȣ��ڶ��������Ƿ����ƽ������ */
	public void setProgress(float progress, boolean smooth) {
		mProgress = progress;
		if (mProgress == 0) {
			mCurrentProgress = 0;
		}
		mStartProgress = mCurrentProgress;
		mStartTime = System.currentTimeMillis();
		if (smooth) {
			mEndTime = SET_PROGRESS_END_TIME;
		} else {
			mEndTime = 0;
		}
		invalidateSafe();
	}

	private void invalidateSafe() {
		if (UIUtils.isRunInMainThread()) {
			postInvalidate();
		} else {
			invalidate();
		}
	}

	/** ���� */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = 0;
		int height = 0;

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthMode == MeasureSpec.EXACTLY) {//����Ǿ�ȷ��
			width = widthSize;
		} else {//����ͼƬ�Ĵ�С
			width = mDrawableForegroud == null ? 0 : mDrawableForegroud.getIntrinsicWidth();
			if (widthMode == MeasureSpec.AT_MOST) {
				width = Math.min(width, widthSize);
			}
		}

		if (heightMode == MeasureSpec.EXACTLY) {//����Ǿ�ȷ��
			height = heightSize;
		} else {//����ͼƬ�Ĵ�С
			height = mDrawableForegroud == null ? 0 : mDrawableForegroud.getIntrinsicHeight();
			if (heightMode == MeasureSpec.AT_MOST) {
				height = Math.min(height, heightSize);
			}
		}
		//�����������������
		mArcRect.left = (width - mArcDiameter) * 0.5f;
		mArcRect.top = (height - mArcDiameter) * 0.5f;
		mArcRect.right = (width + mArcDiameter) * 0.5f;
		mArcRect.bottom = (height + mArcDiameter) * 0.5f;

		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mDrawableForegroud != null) {//�Ȱ�ͼƬ������
			mDrawableForegroud.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
			mDrawableForegroud.draw(canvas);
		}
		//�ٻ�����
		drawArc(canvas);
	}

	protected void drawArc(Canvas canvas) {
		if (mStyle == PROGRESS_STYLE_DOWNLOADING || mStyle == PROGRESS_STYLE_WAITING) {
			float factor;
			long currentTime = System.currentTimeMillis();
			if (mProgress == 100) {
				factor = 1;
			} else {
				if (currentTime - mStartTime < 0) {
					factor = 0;
				} else if (currentTime - mStartTime > mEndTime) {
					factor = 1;
				} else {
					factor = (currentTime - mStartTime) / (float) mEndTime;
				}
			}
			mPaint.setColor(mProgressColor);
			mCurrentProgress = mStartProgress + factor * (mProgress - mStartProgress);
			mSweep = mCurrentProgress * RATIO;
			canvas.drawArc(mArcRect, START_PROGRESS, mSweep, mUserCenter, mPaint);
			if (factor != 1 && mStyle == PROGRESS_STYLE_DOWNLOADING) {
				invalidate();
			}
			if (mCurrentProgress > 0) {
				notifyProgressChanged(mCurrentProgress);
			}
		}
	}

	private void notifyProgressChanged(float currentProgress) {
		if (mProgressChangeListener != null) {
			mProgressChangeListener.onProgressChange(currentProgress);
		}
	}

	public static interface OnProgressChangeListener {

		public void onProgressChange(float smoothProgress);
	}
}
