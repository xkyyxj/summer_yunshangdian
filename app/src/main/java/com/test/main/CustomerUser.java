package com.test.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.List;

import bmobObject.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class CustomerUser extends Activity{

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	private void registe(String user_name,String user_password)
	{
		final User user = new User();
		final Context context = this;
		user.setName(user_name);
		user.setPassword(user_password);
		BmobQuery<User> query = new BmobQuery<User>();
		query.addWhereEqualTo("name", user_name);
		query.findObjects(context, new FindListener<User>()
		{

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				user.save(context, new SaveListener()
				{

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						//注册失败
					}

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						//注册成功
					}
					
				});
			}

			@Override
			public void onSuccess(List<User> arg0) {
				// TODO Auto-generated method stub
				//注册失败，用户名被占用
			}
			
		});
	}
	
	private void login(String user_name,String user_password)
	{
		BmobQuery<User> query = new BmobQuery<User>();
		query.addWhereEqualTo("name", user_name);
		query.addWhereEqualTo("password", user_password);
		query.findObjects(this, new FindListener<User>()
		{

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				//登录失败
			}

			@Override
			public void onSuccess(List<User> arg0) {
				// TODO Auto-generated method stub
				//登陆成功
			}
			
		});
		
	}
	
	private void toast()
	{
		
	}
	
}
