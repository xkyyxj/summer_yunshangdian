package com.test.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.albert.myapplication.R;

import java.util.List;

import bmobObject.Comment;

public class CommentAdapter extends BaseAdapter
{

	List<Comment> comm_list = null;
	LayoutInflater inflate = null;
	
	public CommentAdapter(List<Comment> _list,Context _context)
	{
		comm_list = _list;
		inflate = LayoutInflater.from(_context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(comm_list != null)
			return comm_list.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null)
		{
			holder = new ViewHolder();
			RelativeLayout item = (RelativeLayout)inflate.inflate(R.layout.comment_item, null);
			holder.comm_state = (ImageView)item.findViewById(R.id.comm_state);
			holder.content = (TextView)item.findViewById(R.id.comm_content);
			holder.name = (TextView)item.findViewById(R.id.name);
			holder.send_speed = (TextView)item.findViewById(R.id.send_speed);
			item.setTag(holder);
			convertView = item;
		}
		else
			holder = (ViewHolder)convertView.getTag();
		Comment comm = comm_list.get(position);
		holder.name.setText(comm.getDesti().getShopname());
		if(comm.isState())
			holder.comm_state.setBackgroundResource(R.drawable.good_comm);
		else
			holder.comm_state.setBackgroundResource(R.drawable.bad_comm);
		holder.content.setText(comm.getContent());
		holder.send_speed.setText(comm.getSend_speed());
		return convertView;
	}
	
	public void setComment(List<Comment> _list)
	{
		comm_list = _list;
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView name,send_speed,content;
		ImageView comm_state;
	}
	
}
