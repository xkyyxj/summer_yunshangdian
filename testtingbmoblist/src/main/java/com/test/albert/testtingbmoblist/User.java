package com.test.albert.testtingbmoblist;

import cn.bmob.v3.BmobObject;

/**
 * Created by Albert on 2015/7/15.
 */
public class User extends BmobObject {

    private String password;
    private String name;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
