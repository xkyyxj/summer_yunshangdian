package com.test.albert.testtingbmoblist;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Albert on 2015/7/16.
 */
public class BmobWhat extends BmobObject {

    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
