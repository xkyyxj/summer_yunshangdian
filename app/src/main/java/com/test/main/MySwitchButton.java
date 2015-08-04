package com.test.main;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.test.albert.myapplication.R;

public class MySwitchButton extends View{
	
	public static final String TAG = "App2:";
	
	private boolean status = true;
	private int view_width,view_height,button_on_left;
	
	private Context context;
	private Bitmap on_bg,off_bg,button = null;
	private Paint paint = null;
	
	public MySwitchButton(Context context, AttributeSet attrs)
	{
		this(context,attrs,0);
	}
	
	public MySwitchButton(Context _context, AttributeSet attrs, int defStyleAttr) {
		super(_context, attrs, defStyleAttr);
		context = _context;
		initView();
		//on_bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.)
		// TODO Auto-generated constructor stub
	}
	
	public void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
	{
		setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
	}
	
	public void onDraw(Canvas canvas)
	{
		if(status)
		{
			canvas.drawBitmap(on_bg, 0, 0, paint);
			canvas.drawBitmap(button, button_on_left, 0, paint);
		}
		else
		{
			canvas.drawBitmap(off_bg, 0, 0, paint);
			canvas.drawBitmap(button, 0, 0, paint);
		}
	}
	
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event)
	{
		int mode = event.getAction();
		switch(mode)
		{
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			status = !status;
			invalidate();
			break;
		}
		return super.onTouchEvent(event);
	}
	
	private int measureWidth(int widthMeasureSpec)
	{
		int result = 0;
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		switch(mode)
		{
		case MeasureSpec.AT_MOST:
			if(width > view_width)
				result = view_width;
			else
				result = width;
			Log.e(TAG, "width AT_MOST: " + view_width + " " + width);
			break;
		case MeasureSpec.EXACTLY:
			result = width;
			Log.e(TAG, "width EXACTLY: " + view_width + " " + width);
			break;
		case MeasureSpec.UNSPECIFIED:
			result = view_width;
			Log.e(TAG, "width UNSPECIFIED: " + view_width + " " + width);
			break;
		}
		return result;
	}
	
	private int measureHeight(int heightMeasureSpec)
	{
		int result = 0;
		int mode = MeasureSpec.getMode(heightMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		switch(mode)
		{
		case MeasureSpec.AT_MOST:
			if(height > view_height)
				result = view_height;
			else
				result = height;
			Log.e(TAG, "height AT_MOST: " + view_height + " " + height);
			break;
		case MeasureSpec.EXACTLY:
			result = height;
			Log.e(TAG, "height EXACTLY: " + view_height + " " + height);
			break;
		case MeasureSpec.UNSPECIFIED:
			result = view_height;
			Log.e(TAG, "height UNSPECIFIED: " + view_height + " " + height);
			break;
		}
		return result;
	}
	
	private void initView()
	{
		on_bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.on);
		off_bg = BitmapFactory.decodeResource(getResources(), R.drawable.off);
		button = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
		paint = new Paint();
		view_width = off_bg.getWidth();
		view_height= off_bg.getHeight();
		button_on_left = view_width - button.getWidth();
	}
	
	public boolean getStatus()
	{
		return status;
	}
	
	public void setStatus(boolean _status)
	{
		if(status != _status)
		{
			status = _status;
			invalidate();
		}
	}

}
