package com.example.administrator.myapplication.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.myapplication.adapter.MyBaseAdapter;
import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.bean.CategoryInfo;
import com.example.administrator.myapplication.holder.BaseHolder;
import com.example.administrator.myapplication.holder.CategoryHolder;
import com.example.administrator.myapplication.holder.CategoryTitleHolder;
import com.example.administrator.myapplication.protocol.CategoryProtocol;
import com.example.administrator.myapplication.protocol.HomeProtocol;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/6.
 */
public class CategoryFragment extends BaseFragment {

    private List<CategoryInfo> mDatas;
    @Override
    public LoadingPage.LoadResult load() {
        mDatas = new ArrayList<>();
        CategoryProtocol protocol = new CategoryProtocol();
        mDatas = protocol.load(0);
        return checkData(mDatas);
    }

    @Override
    public View createSuccessView() {
        ListView mListView = new ListView(UIUtils.getContext());

        CategoryAdapter adapter = new CategoryAdapter(mListView,mDatas);

        mListView.setAdapter(adapter);
        return mListView;
    }

    private class CategoryAdapter extends MyBaseAdapter<CategoryInfo> {
        public CategoryAdapter(ListView mListView, List<CategoryInfo> mDatas) {
            super(mListView,mDatas);
        }
        int position = 0;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            this.position = position;
            return super.getView(position, convertView, parent);
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;
        }
        @Override
        public int getInnerItemViewType(int position) {
            CategoryInfo categoryInfo = getData().get(position);
            if (categoryInfo.isTitle()) {
                return super.getInnerItemViewType(position) + 1;
            } else {
                return super.getInnerItemViewType(position);
            }
        }

        @Override
        public BaseHolder getHolder() {
            CategoryInfo categoryInfo = getData().get(position);
            if (categoryInfo.isTitle()){
                return new CategoryTitleHolder();
            }else{
                return new CategoryHolder();
            }

        }
        public boolean hasmore(){
            return false;
        }
        @Override
        protected List onLoadMore() {
            return null;
        }
    }
}
