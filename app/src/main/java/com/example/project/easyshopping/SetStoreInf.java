package com.example.project.easyshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.bmob.btp.callback.UploadListener;
import com.example.project.easyshopping.data.Shop;
import com.test.albert.myapplication.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by acer on 2015/7/10.
 */
public class SetStoreInf extends Activity {

    public static final String TAG = "EasyShopping:";

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    public static final String IMAGE_FILE_NAME = "temp_file";
    private static final String PROPERTY_FILE_NAME = "user_info.xml";

    private Shop user = null;
    private Shop new_user = null;

    private String file_path = null, pro_file_path = null, pic_name;
    private FileOutputStream out = null;

    private Bitmap pic_bt = null;

    private String ShopName;

    private TextView start, end;
    // private boolean is_running;
    private boolean shop_state = false;//营业状态
    private TextView tvShopName, tvShopAdd, tvShopTel;
    private EditText etSendPrice, etDistributePrice;
    private ImageButton head,back;
    private Button submit;
    private ToggleButton btn_state;
    private static int start_hour, start_min, end_hour, end_min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setstoreinf);
        Intent intent = getIntent();
        user = (Shop) intent.getSerializableExtra("Shop");
        //ShopName = intent.getStringExtra("shopname");


        //   Bmob.initialize(this, "474311be9f312229e370c28ebcac1d75");

        //  user = new Shop();
        //  user.setShopname(ShopName);
        Log.e("ShopName是", user.getShopname());
        new_user = new Shop();
        file_path = getFilesDir().getPath() + user.getSusername() + ".png";
        //file_path = getFilesDir().getPath() + "/user" + count + ".png";
        pro_file_path = getFilesDir().getPath() + "/" + PROPERTY_FILE_NAME;
        initTextView();
        initImageView();
        initButton();
        loadpic();
    }

    private void initImageView() {

    }

    private void initTextView() {
        tvShopName = (TextView) findViewById(R.id.textView9);
        tvShopAdd = (TextView) findViewById(R.id.textView10);
        tvShopTel = (TextView) findViewById(R.id.editText1);
        start = (TextView) findViewById(R.id.textView11);
        end = (TextView) findViewById(R.id.textView13);
        etSendPrice = (EditText) findViewById(R.id.editText2);
        etDistributePrice = (EditText) findViewById(R.id.editText3);
        btn_state = (ToggleButton) findViewById(R.id.mTogBtn);


        tvShopName.setText(user.getShopname());
        tvShopAdd.setText(user.getShopadd());
        tvShopTel.setText(user.getShoptel());
        start.setText(user.getStarttime().toString());
        end.setText(user.getEndtime().toString());
        etDistributePrice.setText(user.getDistributeprice().toString());
        etSendPrice.setText(user.getSendprice().toString());
        shop_state = user.getOperatesta();
        btn_state.setChecked(shop_state);

/*

        // BmobProFile.getInstance(context).getAccessURL
        BmobQuery<Shop> query = new BmobQuery<Shop>();
        query.addWhereEqualTo("shopname", ShopName);
        query.findObjects(this, new FindListener<Shop>() {
            @Override
            public void onSuccess(List<Shop> list) {
                Toast.makeText(SetStoreInf.this, "查询成功!共" + list.size() + "条数据。", Toast.LENGTH_LONG).show();
                for (Shop s : list) {
                    // shoplist = new ArrayList<Shop>();
                    // shoplist = (ArrayList<Shop>) list;
                    user = s;
                    tvShopName.setText(user.getShopname());
                    tvShopAdd.setText(user.getShopadd());
                    tvShopTel.setText(user.getShoptel());
                    start.setText(user.getstarttime());
                    end.setText(user.getendtime());
                    shop_state = user.getOperatesta();
                    btn_state.setChecked(shop_state);
                }
                if (user.getSendprice() == null)
                    etSendPrice.setText("0");
                else
                    etSendPrice.setText(user.getSendprice().toString());
                if (user.getDistributeprice() == null)
                    etDistributePrice.setText("0");
                else
                    etDistributePrice.setText(user.getDistributeprice().toString());


            }


            @Override
            public void onError(int i, String s) {
                Toast.makeText(SetStoreInf.this, "查询失败!", Toast.LENGTH_LONG).show();
            }
        });
*/
    }

    /**
     * 弹出选择图片进行上传的dialog
     */
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.activity_edit_user_info_pic_source_cho)
                .setItems(R.array.face_image_choice_item,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent intentFromGallery = new Intent();
                                        intentFromGallery.setType("image/*");
                                        intentFromGallery
                                                .setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(intentFromGallery,
                                                IMAGE_REQUEST_CODE);
                                        break;
                                    case 1:
                                        Intent intentFromCapture = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                        if (hasSdcard()) {
                                            intentFromCapture.putExtra(
                                                    MediaStore.EXTRA_OUTPUT,
                                                    Uri.fromFile(new File(
                                                            Environment.getExternalStorageDirectory(),
                                                            IMAGE_FILE_NAME)));
                                        }

                                        startActivityForResult(intentFromCapture,
                                                CAMERA_REQUEST_CODE);
                                        break;
                                }
                            }
                        })
                .setNegativeButton(R.string.activity_edit_user_info_cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }

    private void saveShopBitmap(Bitmap photo) {
        BufferedOutputStream buffer_out = new BufferedOutputStream(out);
        photo.compress(Bitmap.CompressFormat.PNG, 100, buffer_out);
        try {
            buffer_out.flush();
            buffer_out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("write bitmap:", "error!!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }

    private boolean compareTime(int hour, int minute, int hour2, int minute2) {
        boolean result = false;
        if (hour < hour2)
            result = true;
        else if (hour > hour2)
            result = false;
        else {
            if (minute < minute2)
                result = true;
            else
                result = false;
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            Log.i("onActivityResult ：", "is running ???");
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (hasSdcard()) {
                        File tempFile = new File(
                                Environment.getExternalStorageDirectory() + "/"
                                        + IMAGE_FILE_NAME);
                        // Log.e("is the file","");
                        Log.i("onActivityResult:", "is running ???2");
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        //Toast.makeText(UserInfoActivity.this,
                        //		"", Toast.LENGTH_LONG).show();
                    }

                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            saveShopBitmap(photo);
            // Drawable drawable = new BitmapDrawable(photo);
            // face_image.setImageDrawable(drawable);
            head.setImageBitmap(photo);
            //photo.
        }
    }

    class EditUserPicClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            showDialog();
        }

    }

    //内部类完成timepicker的初始化以及和dialog的绑定
    class TimePickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            View view = View.inflate(getApplicationContext(), R.layout.timepicker, null);
            final TimePicker timePicker = (TimePicker) view.findViewById(R.id.new_act_time_picker);

            //初始化时间采集器
            int hour;
            int min;
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);

            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(min);

            //绑定时间dialog
            AlertDialog.Builder builer = new AlertDialog.Builder(SetStoreInf.this);
            builer.setView(view);
            builer.setTitle("请设置时间");
            builer.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (v == findViewById(R.id.textView11)) {
                        start_hour = timePicker.getCurrentHour();
                        start_min = timePicker.getCurrentMinute();
                        String timeStr = ((start_hour < 10) ? "0" + start_hour : "" + start_hour) + ":" + ((start_min < 10) ? "0" + start_min : "" + start_min);
                        // String timeStr = start_hour + ":" + start_min;
                        start.setText(timeStr);
                    } else if (v == findViewById(R.id.textView13)) {
                        end_hour = timePicker.getCurrentHour();
                        end_min = timePicker.getCurrentMinute();
                        String timeStr = ((end_hour < 10) ? "0" + end_hour : "" + end_hour) + ":" + ((end_min < 10) ? "0" + end_min : "" + end_min);
                        // String timeStr = end_hour + ":" + end_min;
                        end.setText(timeStr);
                    }
                }
            });
            builer.show();
        }
    }

    private void uploadUserInfo() {

        final double send_pri = Double.parseDouble(etSendPrice.getText().toString());
        final double dis_pri = Double.parseDouble(etDistributePrice.getText().toString());
        BTPFileResponse response = BmobProFile.getInstance(SetStoreInf.this).upload(file_path, new UploadListener() {

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onSuccess(String file_name, String url) {
                // TODO Auto-generated method stub
                Log.e(TAG, file_name + " " + url);
                pic_name = file_name;
                Log.e(TAG, "in upload function: " + pic_name + "  " + file_name);
                try {
                    writeProperty("user_pic", file_name);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (pic_name != null) {
                    Log.i(TAG, "upload file: " + pic_name);
                    new_user.setPicture(pic_name);
                }
                new_user.setShoptel(tvShopTel.getText().toString());
                if (!compareTime(start_hour, start_min, end_hour, end_min)) {
                    Log.i(TAG, "time: " + start_hour + " " + start_min + " " + end_hour + " " + end_min);
                    toast("开始/结束营业时间不合理，请重新设置！");
                } else {
                    new_user.setStarttime(start.getText().toString());
                    new_user.setEndtime(end.getText().toString());
                    new_user.setSendprice(send_pri);
                    new_user.setDistributeprice(dis_pri);
                    new_user.setOperatesta(shop_state);
                    new_user.update(SetStoreInf.this, user.getObjectId(), new UpdateListener() {

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            toast("SetStoreInf" + arg1);
                        }

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            toast("店铺信息更新成功！");
                            finish();
                        }
                    });
                }
            }
        });
        Log.i(TAG, "out of upload function: " + pic_name);

    }

    private void writeProperty(String pro_name, String pro_value) throws IOException {
        File file = new File(pro_file_path);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream output = new FileOutputStream(file);
        Properties pro = new Properties();
        pro.put(pro_name, pro_value);
        pro.store(output, null);
    }

    private void initButton() {
        back= (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        head = (ImageButton) findViewById(R.id.ib_head);
        head.setOnClickListener(new EditUserPicClickListener());

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new ConfirmChangeClickListener());

        //营业开始和结束时间的文本的点击监听
        start = (TextView) findViewById(R.id.textView11);
        end = (TextView) findViewById(R.id.textView13);
        start.setOnClickListener(new TimePickListener());
        end.setOnClickListener(new TimePickListener());

        //店铺状态按钮的设置和监听
        ToggleButton mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn); // 获取到控件

        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                shop_state = false;
                if (isChecked) {
                    //选中
                    shop_state = true;
                } else {
                    //未选中
                    shop_state = false;
                }
            }
        });// 添加监听事件

    }

    class ConfirmChangeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            uploadUserInfo();
        }

    }


    private void loadpic() {

        File file = new File(file_path);
        if (!file.exists())
            try {
                head.setImageDrawable(getResources().getDrawable(R.drawable.head1));
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("on creating file!", "error!!!!!!!");
                e.printStackTrace();
            }
        else {
            pic_bt = BitmapFactory.decodeFile(file_path);
            head.setImageBitmap(pic_bt);
        }
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e("on creating :", "error!!!");
            e.printStackTrace();
        }

        String file_na = user.getPicture();
        if (file_na != null && pic_bt == null) {
            Log.e(TAG, file_na);
            downloadFile(file_na);
        }
    }

    private void downloadFile(String file_name) {
        BmobProFile.getInstance(this).download(file_name, new DownloadListener() {

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.e(TAG, arg1 + "error1" + arg0);
            }

            @Override
            public void onProgress(String arg0, int arg1) {
                // TODO Auto-generated method stub
                Log.e(TAG, arg0);
            }

            @Override
            public void onSuccess(String full_path) {
                // TODO Auto-generated method stub
                Log.e(TAG, "downlaodFile: " + full_path);
                pic_bt = BitmapFactory.decodeFile(full_path);
                head.setImageBitmap(pic_bt);
                saveShopBitmap(pic_bt);
            }
        });

    }

    private static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

    }

}
