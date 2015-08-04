package com.test.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.test.albert.myapplication.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import bmobObject.Shop;
import cn.bmob.v3.BmobUser;

public class ShopInfoActivity extends Activity {

	public static final String TAG = "App2:";
	
	private String file_path = null;
	
	private Bitmap pic_bg = null;
	private ImageView shop_pic = null;
	private TextView good_comm = null,bad_comm = null,sell_count = null,comm_count;
	private ImageButton check_comm = null;
	
	private TextView name,addr,tele,time,acc_mo,send_mo,state;
	private ImageButton log_off;
	
	private Handler handler= null;

	private MyButtonListener li = null;
	private Shop user = null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_info);
		
		handler = new Handler()
		{
			public void handleMessage(Message me)
			{
				if(me.what == 1)
				{

				}
			}
		};
		user = BmobUser.getCurrentUser(getApplicationContext(),Shop.class);
		if(user != null)
		{
			file_path = getFilesDir().getPath() + user.getUsername() + ".png";
		}
		
		//initialize view
		shop_pic = (ImageView)findViewById(R.id.shop_pic);
		good_comm = (TextView)findViewById(R.id.good_comm);
		bad_comm = (TextView)findViewById(R.id.bad_comm);
		sell_count = (TextView)findViewById(R.id.sell_count);
		comm_count = (TextView)findViewById(R.id.comm_count);
		check_comm = (ImageButton)findViewById(R.id.check_comm);
		
		name = (TextView)findViewById(R.id.name);
		addr = (TextView)findViewById(R.id.addr);
		tele = (TextView)findViewById(R.id.tele);
		time = (TextView)findViewById(R.id.time);
		acc_mo = (TextView)findViewById(R.id.acc_mo);
		send_mo = (TextView)findViewById(R.id.send_mo);
		state = (TextView)findViewById(R.id.state);
		log_off = (ImageButton)findViewById(R.id.log_off_button);
		
		li = new MyButtonListener();
		
		log_off.setOnClickListener(li);
		check_comm.setOnClickListener(li);
		
		loadShopPic();
		loadShopInfo();
	}
	
	private void loadShopPic()
	{
		File file = new File(file_path);
		if(file.exists())
		{
			Log.i(TAG, "file exists!");
			pic_bg = BitmapFactory.decodeFile(file_path);
			Message message = handler.obtainMessage();
			message.what = 1;
			handler.sendMessage(message);
			shop_pic.setBackgroundResource(0);
			shop_pic.invalidate();
			shop_pic.setImageBitmap(pic_bg);
			shop_pic.invalidate();
		}
		else
		{
			Log.i(TAG,"downlaoding");
			if(user.getSpicture() != null)
			{
				Log.e(TAG, "downloading??");
				downloadFile(user.getSpicture());
			}
		}
	}
	
	private void loadShopInfo()
	{
		name.setText(user.getShopname());
		addr.setText(user.getShopadd());
		tele.setText(user.getShoptel());
		acc_mo.setText("" + user.getSendprice());
		send_mo.setText("" + user.getDistributeprice());
		time.setText(user.getStarttime() + "--" + user.getEndtime());
		boolean ope_state = false;
		Boolean ope_state_obj = user.getOperatesta();
		if(ope_state_obj != null)
			ope_state = ope_state_obj;
		if(ope_state)
			state.setText(R.string.activity_shop_info_working);
		else
			state.setText(R.string.activity_shop_info_not_working);
	}
	
	private void downloadFile(String file_name)
	{
		BmobProFile.getInstance(ShopInfoActivity.this).download(file_name, new DownloadListener(){

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e(TAG, arg1 +"error1" + arg0);
			}

			@Override
			public void onProgress(String arg0, int arg1) {
				// TODO Auto-generated method stub
				Log.e(TAG,arg0);
			}

			@Override
			public void onSuccess(String full_path) {
				// TODO Auto-generated method stub
				Log.e(TAG,full_path);
				pic_bg = BitmapFactory.decodeFile(full_path);
				shop_pic.setImageBitmap(pic_bg);
				saveShopBitmap(pic_bg);
			}});
			
	}
	
	private void saveShopBitmap(Bitmap photo)
	{
		File file = new File(file_path);
		try {
			if(!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			BufferedOutputStream buff = new BufferedOutputStream(out);
			photo.compress(Bitmap.CompressFormat.PNG, 100, buff);
			buff.flush();
			buff.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void goLoginActivity()
	{
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	private class MyButtonListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			switch(id)
			{
			case R.id.check_comm:
				Intent intent = new Intent(ShopInfoActivity.this,CommentActivity.class);
				intent.putExtra("objectId", user.getObjectId());
				startActivity(intent);
				break;
			case R.id.log_off_button:
				BmobUser.logOut(getApplicationContext());
				goLoginActivity();	;
			}
		}
		
	}
	
}
