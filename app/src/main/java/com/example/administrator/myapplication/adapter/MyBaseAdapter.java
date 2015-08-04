package com.example.administrator.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.administrator.myapplication.holder.BaseHolder;
import com.example.administrator.myapplication.holder.MoreHolder;
import com.example.administrator.myapplication.manager.ThreadManager;
import com.example.administrator.myapplication.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/7.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter implements AbsListView.RecyclerListener, AdapterView.OnItemClickListener {

    public AbsListView mListview;
    public List<T> mDatas;
    private List<BaseHolder> mDisplayedHolders;

    public MyBaseAdapter(AbsListView mListview, List<T> mDatas) {
        mDisplayedHolders = new ArrayList<BaseHolder>();
        this.mListview = mListview;
        if (null != mListview) {
            mListview.setRecyclerListener(this);
            mListview.setOnItemClickListener(this);
        }
        setData(mDatas);

    }

    private void setData(List<T> mDatas) {
        this.mDatas = mDatas;
    }
    protected List<T> getData(){
        return mDatas;
    }
    @Override
    public int getCount() {
        return mDatas.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public final int MORE_ITEM_TYPE = 0;
    public final int NORMAL_ITEM_TYPE = 1;
    @Override
    public int getItemViewType(int position) {

        if (position == getCount() - 1){
            return MORE_ITEM_TYPE;
        }else {
            return getInnerItemViewType(position);
        }
    }
    public int getInnerItemViewType(int position){
        return NORMAL_ITEM_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if(convertView != null){
            holder = (BaseHolder) convertView.getTag();
        }else{
            if(MORE_ITEM_TYPE == getItemViewType(position)){
                holder = getMoreHolder();
            }else{
                holder = getHolder();
            }

        }
        if(MORE_ITEM_TYPE != getItemViewType(position)){
            holder.setData(mDatas.get(position));
        }
        mDisplayedHolders.add(holder);
        return holder.getRootView();
    }
    MoreHolder moreHolder;
    private BaseHolder getMoreHolder() {
        if (moreHolder == null){
            moreHolder = new MoreHolder(this,hasmore());
        }return moreHolder;
    }

    public boolean hasmore() {
        return true;
    }

    protected abstract BaseHolder getHolder() ;

    public boolean isLoading = false;
    public void loadMore() {
        if (!isLoading) {
            isLoading = true;
            ThreadManager.getLongPool().execute(new Runnable() {
                @Override
                public void run() {
                    final List list = onLoadMore();
                    UIUtils.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list == null) {
                                getMoreHolder().setData(MoreHolder.ERROR);
                            } else if (list.size() < 20) {
                                getMoreHolder().setData(MoreHolder.NO_MORE);
                            } else
                                getMoreHolder().setData(MoreHolder.HAS_MORE);
                            if (null != list) {
                                mDatas.addAll(list);
                            }
                            //else {
                                //setData(list);
                            //}
                            notifyDataSetChanged();
                            isLoading=false;
                        }
                    });

                }



            });
        }
    }
    protected abstract List onLoadMore();

    @Override
    public void onMovedToScrapHeap(View view) {
        if (null != view) {
            Object tag = view.getTag();
            if (tag instanceof BaseHolder) {
                BaseHolder holder = (BaseHolder) tag;
                synchronized (mDisplayedHolders) {
                    mDisplayedHolders.remove(holder);
                }
                holder.recycle();
            }
        }
    }

    public List<BaseHolder> getDisplayedHolders() {
        synchronized (mDisplayedHolders) {
            return new ArrayList<BaseHolder>(mDisplayedHolders);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position = position - getHeaderViewCount();
        onItemClickInner(position);
    }
    public int getHeaderViewCount() {
        int count = 0;
        if (mListview != null && mListview instanceof ListView) {
            ListView listView = (ListView) mListview;
            count = listView.getHeaderViewsCount();
        }
        return count;
    }
    public void onItemClickInner(int position) {

    }
}
