package com.example.administrator.myapplication.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.protocol.RecommendProtocol;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.LoadingPage;
import com.example.administrator.myapplication.view.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/7/6.
 */
public class RecommendFragment extends BaseFragment {

    List<String> mData;
    @Override
    public LoadingPage.LoadResult load() {
        RecommendProtocol protocol = new RecommendProtocol();
        mData = protocol.load(0);
        return checkData(mData);
    }

    @Override
    public View createSuccessView() {

        StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        stellarMap.setRegularity(6,9);
        RecommendAdapter adapter = new RecommendAdapter();
        stellarMap.setAdapter(adapter);
        stellarMap.setGroup(0,true);
        return stellarMap;
    }

    Random random;
    private class RecommendAdapter implements StellarMap.Adapter{
        public RecommendAdapter(){
            random = new Random();
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getCount(int group) {
            return 15;
        }

        @Override
        public View getView(int group, int position, View convertView) {

            TextView textView = new TextView(UIUtils.getContext());
            textView.setText(mData.get(position));
            int color;
            int red = 20 + random.nextInt(200);
            int green = 20 + random.nextInt(200);
            int blue = 20 + random.nextInt(200);
            color = Color.rgb(red,green,blue);
            textView.setTextColor(color);
            int size = 15 + random.nextInt(16);
            textView.setTextSize(size);
            return textView;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
             return (group + 1) % 2;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return (group + 1) % 2;
        }
    }
}
