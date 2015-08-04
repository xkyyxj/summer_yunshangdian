package com.test.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.test.albert.myapplication.R;

import bmobObject.Shop;
import cn.bmob.v3.listener.SaveListener;

/*
 *author:wqch 
 *finish_time:2015.07.13
 */
public class RegisterUserActivity extends Activity{

	private String user_text,password_text,re_pass_text;
	
	private EditText user_name,password,re_pass;
	
	private ImageButton register_button;
	
	private RegisterButtonClickListener li = null;
	
	private Shop user = null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
		
		user_name = (EditText)findViewById(R.id.ac_re_user_name);
		password = (EditText)findViewById(R.id.ac_re_user_password);
		re_pass = (EditText)findViewById(R.id.ac_re_user_re_password);
		register_button = (ImageButton)findViewById(R.id.ac_re_user_ok);
		
		li = new RegisterButtonClickListener();
		register_button.setOnClickListener(li);
		
		
	}
	
	class RegisterButtonClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			user_text = user_name.getText().toString();
			password_text = password.getText().toString();
			re_pass_text = re_pass.getText().toString();
			//Bmob register
			if(password_text.equals(re_pass_text))
			{
				user = new Shop();
				user.setUsername(user_text);
				user.setPassword(password_text);
				user.signUp(RegisterUserActivity.this, new SaveListener()
				{

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						toast(arg1);
					}

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						toast(R.string.activity_register_user_registe_success);
					}

				});
			}
			
		}
		
	}
	
	private void toast(String text)
	{
		Toast.makeText(RegisterUserActivity.this, text, Toast.LENGTH_SHORT).show();;
	}
	
	private void toast(int id)
	{
		String text = getResources().getString(id);
		toast(text);
	}
	
}
