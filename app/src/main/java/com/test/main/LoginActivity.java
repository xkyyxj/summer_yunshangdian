package com.test.main;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.project.easyshopping.Seller;
import com.test.albert.myapplication.R;

import bmobObject.Shop;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
/*
 * author:wqch
 * finish_time:2015.07.23
 */
public class LoginActivity extends Activity {

	public static final String TAG = "App2:";

	public static String BMOB_API_KEY = "474311be9f312229e370c28ebcac1d75";
	
	private boolean login_success = false;
	
	private ImageButton login,new_user;
	private EditText name,password;
	private Context context = null;
	
	private MyButtonClickListener li = null;
	private Shop user = null;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bmob.initialize(getApplicationContext(), BMOB_API_KEY);
		setContentView(R.layout.activity_login);
		context = getApplicationContext();
		
		name = (EditText)findViewById(R.id.ac_login_user_name);
		password = (EditText)findViewById(R.id.ac_login_user_password);
		login = (ImageButton)findViewById(R.id.login);
		new_user = (ImageButton)findViewById(R.id.register_button);
		
		li = new MyButtonClickListener();
		login.setOnClickListener(li);
		new_user.setOnClickListener(li);
		
		user = new Shop();
	}
	
	private void goRegisteActivity()
	{
		Intent intent = new Intent(this,RegisterUserActivity.class);
		startActivity(intent);
	}
	
	private void login()
	{
		String name_text = name.getText().toString();
		String password_text = password.getText().toString();
		user.setUsername(name_text);
		user.setPassword(password_text);
		user.login(getApplicationContext(), new SaveListener() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				login_success = false;
				toast(R.string.activity_login_fail, arg1);
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				login_success = true;
				showInfoAndGoto();
			}
		});
	}

	private void showInfoAndGoto()
	{
		Intent intent = null;
		user = BmobUser.getCurrentUser(getApplicationContext(), Shop.class);
		if(user == null)
			Log.e(TAG, "LoginActivity:login sucessful?");
		int check_state = 0;
		Integer check_state_object  = user.getCheckstate();
		if(check_state_object != null)
			check_state = check_state_object;
		switch(check_state)
		{
			case 1:
				toast(R.string.activity_login_success,null);
				intent = new Intent(this, Seller.class);
				startActivity(intent);
				finish();
				break;
			case 2:
				showMyDialog(R.string.activity_login_warning2);
				intent = new Intent(this, RegisterActivity.class);
				startActivity(intent);
				break;
			case 3:
				showMyDialog(R.string.activity_login_warning3);
				break;
			default:
				toast(R.string.activity_login_success, null);
				intent = new Intent(this, RegisterActivity.class);
				startActivity(intent);
		}
	}
	
	private void showMyDialog(int id)
	{
		String content = getResources().getString(id);
		new AlertDialog.Builder(this)
		.setTitle(R.string.activity_login_warning_title)
		.setMessage(content)
		.setPositiveButton(R.string.activity_login_sure, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}).show();
	}
	
	class MyButtonClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.login)
			{
				login();
			}
			else
			{
				goRegisteActivity();
			}
		}
		
	}
	
	public void toast(int id,String _text)
	{
		String text = getResources().getString(id);
		if(_text != null)
			text += _text;
		Toast.makeText(LoginActivity.this, text, Toast.LENGTH_LONG).show();
	}
	
}
