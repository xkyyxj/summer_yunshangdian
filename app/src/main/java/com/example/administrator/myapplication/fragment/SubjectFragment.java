package com.example.administrator.myapplication.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.administrator.myapplication.adapter.MyBaseAdapter;
import com.example.administrator.myapplication.bean.SubjectInfo;
import com.example.administrator.myapplication.holder.BaseHolder;
import com.example.administrator.myapplication.holder.SubjectHolder;
import com.example.administrator.myapplication.protocol.SubjectProtocol;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.LoadingPage;

import java.util.List;

/**
 * Created by Administrator on 2015/7/6.
 */
public class SubjectFragment extends BaseFragment {

    private List<SubjectInfo> mDatas;
    private SubjectAdapter adapter;
    private ListView mListView;

    @Override
    public LoadingPage.LoadResult load() {
        SubjectProtocol protocol = new SubjectProtocol();
        mDatas = protocol.load(0);
        return checkData(mDatas);
    }

    @Override
    public View createSuccessView() {
        mListView = new ListView(UIUtils.getContext());
        adapter = new SubjectAdapter(mListView,mDatas);
        mListView.setAdapter(adapter);
        return mListView;
    }

    private class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{
        public SubjectAdapter(ListView mListview, List<SubjectInfo> mDatas) {
            super(mListview, mDatas);
        }

        @Override
        public BaseHolder getHolder() {
            return new SubjectHolder();
        }

        @Override
        protected List onLoadMore() {
            SubjectProtocol protocol = new SubjectProtocol();
            return protocol.load(getData().size());
        }
    }
}
