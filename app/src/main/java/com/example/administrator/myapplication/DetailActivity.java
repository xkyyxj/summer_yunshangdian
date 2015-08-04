package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.example.administrator.myapplication.bean.AppInfo;
import com.example.administrator.myapplication.holder.DetailBottomHolder;
import com.example.administrator.myapplication.holder.DetailScreenHolder;
import com.example.administrator.myapplication.holder.DetailDesHolder;
import com.example.administrator.myapplication.holder.DetailInfoHolder;
import com.example.administrator.myapplication.holder.DetailSafeHolder;
import com.example.administrator.myapplication.protocol.DetailProtocol;
import com.example.administrator.myapplication.utils.UIUtils;
import com.example.administrator.myapplication.view.LoadingPage;

/**
 * Created by Administrator on 2015/7/14.
 */
public class DetailActivity extends BaseActivity {

    public static String PACKAGENAME = "PACKAGENAME";
    private String packagename;
    private AppInfo appInfo;
    private FrameLayout mInfoLayout;
    private DetailInfoHolder mDetailInfoHolder;
    private HorizontalScrollView mScreenLayout;
    private DetailScreenHolder appScreenHolder;
    private FrameLayout mDesLayout;
    private DetailDesHolder mDetailDesHolder;
    private FrameLayout mSafeLayout;
    private DetailSafeHolder mSafeHolder;
    private FrameLayout mBottomLayout;
    private DetailBottomHolder detailBottomHolder;
    private ActionBar mActionBar;

    @Override
    protected void onDestroy() {
        if (detailBottomHolder != null) {
            detailBottomHolder.stopObserver();
        }
        super.onDestroy();
    }


    @Override
    protected void initActionbar() {
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(R.string.app_detail);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void initView() {
        LoadingPage loadingPage = new LoadingPage(this) {
            @Override
            public View createSuccessView() {
                return DetailActivity.this.createSuccessView();
            }

            @Override
            protected LoadResult load() {
                return DetailActivity.this.load();
            }
        };
        setContentView(loadingPage);
        loadingPage.show();
    }

    private View createSuccessView() {
        View view = UIUtils.inflate(R.layout.activity_detail);

        mInfoLayout = (FrameLayout) view.findViewById(R.id.detail_info);
        mDetailInfoHolder = new DetailInfoHolder();
        mDetailInfoHolder.setData(appInfo);
        mInfoLayout.addView(mDetailInfoHolder.getRootView());

        mSafeLayout = (FrameLayout) view.findViewById(R.id.detail_safe);
        mSafeHolder = new DetailSafeHolder();
        mSafeHolder.setData(appInfo);
        mSafeLayout.addView(mSafeHolder.getRootView());

        mScreenLayout = (HorizontalScrollView) view.findViewById(R.id.detail_screen);
        appScreenHolder = new DetailScreenHolder();
        appScreenHolder.setData(appInfo);
        mScreenLayout.addView(appScreenHolder.getRootView());

        mDesLayout = (FrameLayout) view.findViewById(R.id.detail_des);
        mDetailDesHolder = new DetailDesHolder();
        mDetailDesHolder.setData(appInfo);
        mDesLayout.addView(mDetailDesHolder.getRootView());

        mBottomLayout = (FrameLayout) view.findViewById(R.id.bottom_layout);
        detailBottomHolder = new DetailBottomHolder();
        detailBottomHolder.setData(appInfo);
        mBottomLayout.addView(detailBottomHolder.getRootView());

        return view;
    }

    private LoadingPage.LoadResult load() {
        DetailProtocol detailProtocol = new DetailProtocol();
        detailProtocol.setPackageName(packagename);
        appInfo = detailProtocol.load(0);
        if (appInfo == null){
            return LoadingPage.LoadResult.STATE_ERROR;
        }
        return LoadingPage.LoadResult.STATE_SUCCESS;

    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        if (intent != null){
            packagename = intent.getStringExtra(PACKAGENAME);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
