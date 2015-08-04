package com.example.administrator.myapplication.fragment;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/6.
 */
public class FragmentFactory {

    public static final int TAB_HOME = 0;
    public static final int TAB_APP = 1;
    public static final int TAB_GAME = 2;
    public static final int TAB_SUBJECT = 3;
    public static final int TAB_RECOMMEND = 4;
    public static final int TAB_CATEGORY = 5;
    public static final int TAB_HOT = 6;

    private static BaseFragment fragment;

    private static HashMap<Integer,BaseFragment> mHashMap = new HashMap<>();

    public static BaseFragment createFragment(int position) {

        fragment=mHashMap.get(position);
        if( fragment== null){
            switch (position) {
                case TAB_HOME:
                    fragment = new HomeFragment();
                    break;
                case TAB_APP:
                    fragment = new AppFragment();
                    break;
                case TAB_GAME:
                    fragment = new GameFragment();
                    break;
                case TAB_SUBJECT:
                    fragment = new SubjectFragment();
                    break;
                case TAB_RECOMMEND:
                    fragment = new RecommendFragment();
                    break;
                case TAB_CATEGORY:
                    fragment = new CategoryFragment();
                    break;
                case TAB_HOT:
                    fragment = new HotFragment();
                    break;
            }
            mHashMap.put(position,fragment);
        }

        return fragment;
    }

}
