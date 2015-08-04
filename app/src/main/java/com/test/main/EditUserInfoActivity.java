package com.test.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.bmob.btp.callback.UploadListener;
import com.test.albert.myapplication.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import bmobObject.Shop;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class EditUserInfoActivity extends Activity {
	
	public static final String TAG = "App2:";
	
	public static String BMOB_API_KEY = "5eb7b9f34bd4f213f1b9da322b9e5f37";
	
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	public static final String IMAGE_FILE_NAME = "temp_file";
	private static final String PROPERTY_FILE_NAME = "user_info.xml";
	
	private boolean shop_state = false;
	private boolean check_time = false;
	private int hour1,minute1,hour2,minute2;
	
	private String file_path = null,pro_file_path = null,pic_name;
	private FileOutputStream out = null;
	
	private Bitmap pic_bt = null;
	
	private ImageView user_pic = null;
	private ImageButton change_pic = null,confirm = null;
	private EditText tele,acc_mo,send_mo;
	private TextView start_time,end_time,shop_name_view,shop_addr_view;
	private MySwitchButton still_work = null;
	private TextView selected_view = null;
	
	private EditUserPicClickListener pic_but_li = null;
	private ConfirmChangeClickListener con_li = null;
	private MySwitchButtonClickListener switch_li = null;
	private TimeClickListener time_li = null;
	
	private Shop user = null;
	private Shop new_user = null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bmob.initialize(getApplicationContext(), BMOB_API_KEY);
		
		setContentView(R.layout.activity_edit_user_info);
		//=====================================================================
		//测试用代码
		//Random ran = new Random();
		//int count = ran.nextInt();
		//=====================================================================
		
		user = BmobUser.getCurrentUser(getApplicationContext(),Shop.class);
		new_user = new Shop();
		
		if(user != null) {
			Boolean ope_state = user.getOperatesta();
			if(ope_state != null)
				shop_state = user.getOperatesta();
		}
		
		file_path = getFilesDir().getPath() + user.getUsername() + ".png";
		//file_path = getFilesDir().getPath() + "/user" + count + ".png";
		pro_file_path = getFilesDir().getPath() + "/" + PROPERTY_FILE_NAME;
		
		user_pic = (ImageView)findViewById(R.id.user_pic);
		change_pic = (ImageButton)findViewById(R.id.change_pic);
		shop_name_view = (TextView)findViewById(R.id.ac_edit_name);
		shop_addr_view = (TextView)findViewById(R.id.ac_edit_addr);
		start_time = (TextView)findViewById(R.id.ac_edit_time1);
		end_time = (TextView)findViewById(R.id.ac_edit_time2);
		tele = (EditText)findViewById(R.id.ac_edit_tele);
		acc_mo = (EditText)findViewById(R.id.ac_edit_acc_mo);
		send_mo = (EditText)findViewById(R.id.ac_edit_send_mo);
		confirm = (ImageButton)findViewById(R.id.ac_ok);
		still_work = (MySwitchButton)findViewById(R.id.ac_edit_still_work);
		
		still_work.setStatus(shop_state);
		
		pic_but_li = new EditUserPicClickListener();
		con_li = new ConfirmChangeClickListener();
		switch_li = new MySwitchButtonClickListener();
		time_li = new TimeClickListener();
		
		change_pic.setOnClickListener(pic_but_li);
		confirm.setOnClickListener(con_li);
		still_work.setOnClickListener(switch_li);
		start_time.setOnClickListener(time_li);
		end_time.setOnClickListener(time_li);
		
		loadShopInfo();
		
		File file = new File(file_path); 
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("on creating file!","error!!!!!!!");
				e.printStackTrace();
			}
		else
		{
			//pic_bt = BitmapFactory.decodeFile(file_path);
			//user_pic.setImageBitmap(pic_bt);
		}
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String file_na = null;
		
		Log.e(TAG, "WTF" + file_na);
		file_na = user.getSpicture();
		if(file_na != null && pic_bt == null)
		{
			Log.e(TAG, file_na);
			downloadFile(file_na);
		}
		
	}
	
	private void loadShopInfo()
	{
		String shop_name,shop_addr,shop_tele,start_time_text,end_time_text,send_mo_text,acc_mo_text;
		shop_name = user.getShopname();
		shop_addr = user.getShopadd();
		shop_tele = user.getShoptel();
		start_time_text = user.getStarttime() + "";
		end_time_text = user.getEndtime() + "";
		send_mo_text = user.getDistributeprice() + "";
		acc_mo_text = user.getSendprice() + "";
		shop_name_view.setText(shop_name);
		shop_addr_view.setText(shop_addr);
		tele.setText(shop_tele);
		if(start_time_text != null && !start_time_text.equals("null") && !start_time_text.equals(""))
		{	
			start_time.setTextColor(getResources().getColor(R.color.ac_edit_user_info_time_color));
			start_time.setText(start_time_text);
		}
		if(end_time_text != null && !end_time_text.equals("null") && !end_time_text.equals(""))
		{
			end_time.setTextColor(getResources().getColor(R.color.ac_edit_user_info_time_color));
			end_time.setText(end_time_text);
		}
		send_mo.setText(send_mo_text);
		acc_mo.setText(acc_mo_text);
	}
	
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle(R.string.activity_edit_user_info_pic_source_cho)
				.setItems(R.array.face_image_choice_item,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:
									Intent intentFromGallery = new Intent();
									intentFromGallery.setType("image/*");
									intentFromGallery
											.setAction(Intent.ACTION_GET_CONTENT);
									startActivityForResult(intentFromGallery,
											IMAGE_REQUEST_CODE);
									break;
								case 1:

									Intent intentFromCapture = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									if (hasSdcard()) {

										intentFromCapture.putExtra(
												MediaStore.EXTRA_OUTPUT,
												Uri.fromFile(new File(
														Environment
																.getExternalStorageDirectory(),
														IMAGE_FILE_NAME)));
									}

									startActivityForResult(intentFromCapture,
											CAMERA_REQUEST_CODE);
									break;
								}
							}
						})
				.setNegativeButton(R.string.activity_edit_user_info_cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory() + "/"
									+ IMAGE_FILE_NAME);
					// Log.e("is the file","");
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					//Toast.makeText(UserInfoActivity.this,
					//		"", Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}
	
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			saveShopBitmap(photo);
			// Drawable drawable = new BitmapDrawable(photo);
			// face_image.setImageDrawable(drawable);
			user_pic.setImageBitmap(photo);
			//photo.
		}
	}
	
	private void saveShopBitmap(Bitmap photo)
	{
		BufferedOutputStream buffer_out = new BufferedOutputStream(out);
		photo.compress(Bitmap.CompressFormat.PNG, 100, buffer_out);
		try {
			buffer_out.flush();
			buffer_out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("write bitmap:", "error!!!!!!!!!!!!!!!!!!");
			e.printStackTrace();
		}
	}
	
	private static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
/*
	private void makeUpLoadFileRequest() {
		final BmobFile bmobFile = new BmobFile(new File(file_path));
		bmobFile.upload(this, new UploadFileListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub

				user.setPic(bmobFile);
				user.save(UserInfoActivity.this, new SaveListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Log.e("UserInfoActivity class","successful!!!!!!!!!!!!!!!!!!!!!!!!!");
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						Log.e("UserInfoActivity class","successful^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					}
				});
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				Log.e("waht????","&&&&&&&&&&&&&&&&&&&&&&&&&&" + msg);
			}

			@Override
			public void onProgress(Integer arg0) {
				// TODO Auto-generated method stub
				// 上传进度 arg0
				Log.e("progress","" + arg0);
			}
		});
	}
	*/
	
	private void downloadFile(String file_name)
	{
		BmobProFile.getInstance(EditUserInfoActivity.this).download(file_name, new DownloadListener(){

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
				Log.e(TAG,"downlaodFile: " + full_path);
				pic_bt = BitmapFactory.decodeFile(full_path);
				user_pic.setImageBitmap(pic_bt);
				saveShopBitmap(pic_bt);
			}});
			
	}
	
	private void showTimePicker(int hour,int minute)
	{
		new TimePickerDialog(EditUserInfoActivity.this, new OnTimeSetListener(){

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				Log.i(TAG,"first run??");
				check_time = true;
				if(selected_view != null)
				{
					String time = ((hourOfDay < 10)?"0" + hourOfDay:"" + hourOfDay) + ":" +((minute < 10)?"0" + minute:"" + minute);
					selected_view.setText(time);
					if(selected_view.getId() == start_time.getId())
					{
						Log.e(TAG, "start_time");
						hour1 = hourOfDay;
						minute1 = minute;
					}
					else
					{
						Log.e(TAG, "end_time");
						hour2 = hourOfDay;
						minute2 = minute;
					}
				}
			}}, hour, minute, true).show();
	}
	
	//
	private void uploadUserInfo()
	{
		/*
		File pic_file = new File(file_path);
		BmobFile pic = new BmobFile(pic_file);
		Log.e(TAG,"name"+ pic.getFilename());
		pic.upload(EditUserInfoActivity.this, new UploadFileListener(){

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e(TAG, arg1);
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.e(TAG, "good!");
			}});
		*/
		final double send_pri = Double.parseDouble(acc_mo.getText().toString());
		final double dis_pri = Double.parseDouble(send_mo.getText().toString());
		BTPFileResponse response = BmobProFile.getInstance(EditUserInfoActivity.this).upload(file_path, new UploadListener(){

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProgress(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String file_name, String url) {
				// TODO Auto-generated method stub
				Log.e(TAG, file_name +" " + url);
				pic_name = file_name;
				Log.e(TAG, "in upload function: " + pic_name + "  " + file_name);
				try {
					writeProperty("user_pic",file_name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(pic_name != null)
				{
					Log.i(TAG, "upload file: " + pic_name);
					new_user.setSpicture(pic_name);
				}
				new_user.setShoptel(tele.getText().toString());
				if(check_time && !compareTime(hour1,minute1,hour2,minute2))
				{
					Log.i(TAG, "time: " + hour1 + " " + minute1 + " " + hour2 + " " + minute2);
					toast(R.string.activity_edit_user_info_time_wrong,null);
				}
				new_user.setStarttime(start_time.getText().toString());
				new_user.setEndtime(end_time.getText().toString());
				new_user.setSendprice(send_pri);
				new_user.setDistributeprice(dis_pri);
				new_user.setOperatesta(shop_state);
				new_user.update(EditUserInfoActivity.this,user.getObjectId(),new UpdateListener(){

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						toast(R.string.activity_edit_user_info_info_up_wr,arg1);
					}

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						toast(R.string.activity_edit_user_info_info_up_sc,null);
					}});
			}});
		Log.i(TAG, "out of upload function: " + pic_name);
		
	}
	
	private String readProperty(String pro_name) throws IOException
	{
		String result = null;
		File file = new File(pro_file_path);
		if(!file.exists())
		{
			return null;
		}
		else
		{
			Properties pro = new Properties();
			FileInputStream input = new FileInputStream(pro_file_path);
			pro.load(input);
			result = pro.getProperty(pro_name);
		}
		return result;
	}
	
	private void writeProperty(String pro_name,String pro_value) throws IOException
	{
		File file = new File(pro_file_path);
		if(!file.exists())
		{
			file.createNewFile();
		}
		FileOutputStream output = new FileOutputStream(file);
		Properties pro = new Properties();
		pro.put(pro_name, pro_value);
		pro.store(output, null);
	}
	
	private void changeTimeColor(TextView v,int id)
	{
		v.setTextColor(getResources().getColor(id));
	}
	
	//第一个时间小于第二个时间则返回true，否则返回false。
	private boolean compareTime(int hour,int minute,int hour2,int minute2)
	{
		boolean result = false;
		if(hour < hour2)
			result = true;
		else if(hour > hour2)
			result = false;
		else
		{
			if(minute < minute2)
				result = true;
			else
				result = false;
		}
		return result;
	}
	
	public void toast(int id,String _text)
	{
		String text = getResources().getString(id);
		if(_text != null)
			text += _text;
		Toast.makeText(EditUserInfoActivity.this, text, Toast.LENGTH_LONG).show();
	}
	
	class EditUserPicClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//完善修改图片的功能
			showDialog();
		}
		
	}
	
	class ConfirmChangeClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//修改完成之后上传数据
			uploadUserInfo();
		}
		
	}
	
	class MySwitchButtonClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.e(TAG, "still_work?" + still_work.getStatus());
			shop_state = ((MySwitchButton)v).getStatus();
		}
		
	}
	
	class TimeClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.e(TAG,"TextView onclick?");
			showTimePicker(8,30);
			TextView view = (TextView)v;
			changeTimeColor(view,R.color.ac_edit_user_info_time_color);
			selected_view = view;
		}
		
	}
	
}
