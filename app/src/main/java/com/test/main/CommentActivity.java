package com.test.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.albert.myapplication.R;

import java.util.List;

import bmobObject.Comment;
import bmobObject.Shop;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

public class CommentActivity extends Activity {

	private TextView head;
	private ListView list;
	
	private Shop temp_user = null;
	
	private CommentAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		
		adapter = new CommentAdapter(null,CommentActivity.this);
		
		head = (TextView)findViewById(R.id.comm_number);
		list = (ListView)findViewById(R.id.comm_content);
		
		list.setAdapter(adapter);
		
		temp_user = new Shop();
		temp_user.setObjectId(getIntent().getStringExtra("objectId"));
		
		findComment();
	}
	
	private void findComment()
	{
		final ProgressDialog progress = new ProgressDialog(
				CommentActivity.this);
		progress.setMessage(getResources().getString(R.string.activity_comment_loading));
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		// pointer¿‡–Õ
		query.addWhereEqualTo("desti",new BmobPointer(temp_user));
		query.include("user");
		query.findObjects(CommentActivity.this, new FindListener<Comment>(){

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				progress.dismiss();
				toast(R.string.activity_comment_load_error,arg1);
			}

			@Override
			public void onSuccess(List<Comment> arg0) {
				// TODO Auto-generated method stub
				progress.dismiss();
				toast(R.string.activity_comment_load_success,null);
				adapter.setComment(arg0);
				adapter.notifyDataSetChanged();
			}});
	}
	
	public void toast(int id,String _text)
	{
		String text = getResources().getString(id) + _text;
		Toast.makeText(CommentActivity.this, text, Toast.LENGTH_LONG).show();
	}
	
	
	
}
