package com.test.albert.testtingbmoblist;

import android.content.Context;
import android.util.Log;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Albert on 2015/7/17.
 */
public class UploadObject {

    public static void uploadShop(Context context,Shop shop)
    {
        shop.save(context, new SaveListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    public static void uploadOrd(Context context,Ord order)
    {
        order.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.e("%%%%%%%%%%%%%%%%%","good");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("%%%%%%%%%%%%%%%%%","bad " + s);
            }
        });
    }

}
