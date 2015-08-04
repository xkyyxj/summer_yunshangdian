package com.example.administrator.myapplication.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.MyBaseAdapter;
import com.example.administrator.myapplication.utils.UIUtils;

/**
 * Created by Administrator on 2015/7/8.
 */
public class MoreHolder extends BaseHolder<Integer> implements View.OnClickListener {
    private MyBaseAdapter mAdapter;

    public static final int HAS_MORE = 0;
    public static final int NO_MORE = 1;
    public static final int ERROR = 2;
    private RelativeLayout rl_more_loading;
    private RelativeLayout rl_more_error;

    @Override
    public View getRootView() {
        if (getData() == HAS_MORE) {
            loadMore();
        }
        return super.getRootView();
    }

    public  MoreHolder(MyBaseAdapter tMyBaseAdapter,boolean hasmore) {
        setData(hasmore?HAS_MORE:NO_MORE);
        mAdapter = tMyBaseAdapter;
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_more_loading);
        rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
        rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
        rl_more_error.setOnClickListener(this);
        return view;
    }

    @Override
    public void refreshView() {
        Integer data = getData();
        rl_more_loading.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE);
        rl_more_error.setVisibility(data == ERROR ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        loadMore();
    }

    private void loadMore() {
        mAdapter.loadMore();
    }
}
