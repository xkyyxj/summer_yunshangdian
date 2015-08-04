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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.bmob.btp.callback.UploadListener;
import com.example.project.easyshopping.data.ShopG;
import com.test.albert.myapplication.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by acer on 2015/7/15.
 */
public class EditGoods extends Activity {
    public static final String TAG = "EditGoods";

    //图片处理的类变量
    private String file_path = null, pro_file_path = null, pic_name;
    private FileOutputStream out = null;
    private Bitmap pic_bt = null;
    //两个类对象
    private ShopG mShopG = null;
    private ShopG new_mShopG = null;


    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    public static final String IMAGE_FILE_NAME = "temp_file";
    private static final String PROPERTY_FILE_NAME = "user_info.xml";

    private LinearLayout ll1;
    private ImageButton back;
    private TextView textView0;
    private RelativeLayout rl1;
    private LinearLayout ll2;
    private EditText etBarcode;
    private ImageButton ibScan;
    private ImageButton ibPic;
    private ImageButton ibUpload;
    private LinearLayout ll3;
    private LinearLayout ll4;
    private TextView tvName;
    private EditText dtName;
    private LinearLayout ll5;
    private TextView tvDescribe;
    private EditText dtDescribe;
    private LinearLayout ll6;
    private TextView tvPrice;
    private EditText dtPrice;
    private LinearLayout ll7;
    private TextView tvCat;
    private Spinner spinner;
    private Button submit;

    private static String goodid;
    private static String ShopName;

    private void assignViews() {
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        back = (ImageButton) findViewById(R.id.back);
        textView0 = (TextView) findViewById(R.id.textView0);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        etBarcode = (EditText) findViewById(R.id.et_barcode);
        ibScan = (ImageButton) findViewById(R.id.ib_scan);
        ibPic = (ImageButton) findViewById(R.id.ib_pic);
        ibUpload = (ImageButton) findViewById(R.id.ib_upload);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        tvName = (TextView) findViewById(R.id.tv_name);
        dtName = (EditText) findViewById(R.id.dt_name);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        tvDescribe = (TextView) findViewById(R.id.tv_describe);
        dtDescribe = (EditText) findViewById(R.id.dt_describe);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        dtPrice = (EditText) findViewById(R.id.dt_price);
        ll7 = (LinearLayout) findViewById(R.id.ll7);
        tvCat = (TextView) findViewById(R.id.tv_cat);
        spinner = (Spinner) findViewById(R.id.spinner);
        submit = (Button) findViewById(R.id.submit);
    }

    private ArrayAdapter<String> mAdapter;
    private static String[] cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editgoods);
        assignViews();
        Intent intent = getIntent();
        goodid = intent.getStringExtra("objid");
        ShopName = intent.getStringExtra("shopname");
        Log.e("EditGoods里的onCreat()方法", "里得到的ShopGid是" + goodid);

        file_path = getFilesDir().getPath() + ShopName + ".png";
        pro_file_path = getFilesDir().getPath() + "/" + PROPERTY_FILE_NAME;

        mShopG = new ShopG();
        new_mShopG = new ShopG();
        loadpic();
        initTextView();
        initSpinner();
        initButton();
    }

    private void initTextView() {
        BmobQuery<ShopG> query = new BmobQuery<ShopG>();
        query.getObject(this, goodid, new GetListener<ShopG>() {
            @Override
            public void onSuccess(ShopG shopG) {
                etBarcode.setText(shopG.getObjectId());
                dtName.setText(shopG.getGoodsname());
                dtPrice.setText(shopG.getPrice().toString());
                dtDescribe.setText(shopG.getDescription());
                spinner.setId(0);
               // loadShopPicImpl(ibPic, shopG.getPicture());
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    private void initSpinner() {
        cat = new String[]{"请选择类别", "日用品", "水果", "零食"};
        mAdapter = new ArrayAdapter<String>(this, R.layout.drop_down_item, cat);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("EditGoods", "spinner选择了第" + position + "个item");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void initButton() {
        ibPic.setOnClickListener(new EditGoodsPicClickListener());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    class EditGoodsPicClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            showDialog();
        }

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

    private static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private void loadpic() {

        File file = new File(file_path);
        if (!file.exists())
            try {
                ibPic.setImageDrawable(getResources().getDrawable(R.drawable.nopic));
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("on creating file!", "error!!!!!!!");
                e.printStackTrace();
            }
        else {
            pic_bt = BitmapFactory.decodeFile(file_path);
            ibPic.setImageBitmap(pic_bt);
        }
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e("on creating :", "error!!!");
            e.printStackTrace();
        }

        String file_na = mShopG.getPicture();
        if (file_na != null && pic_bt == null) {

            downloadFile(file_na);
        }
    }

    private void downloadFile(String file_name) {
        BmobProFile.getInstance(this).download(file_name, new DownloadListener() {

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgress(String arg0, int arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onSuccess(String full_path) {
                // TODO Auto-generated method stub

                pic_bt = BitmapFactory.decodeFile(full_path);
                ibPic.setImageBitmap(pic_bt);
                saveShopBitmap(pic_bt);
            }
        });

    }

    private void update() {
        BTPFileResponse response = BmobProFile.getInstance(EditGoods.this).upload(file_path, new UploadListener() {
            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                toast("头像上传失败，请重试！");
            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onSuccess(String file_name, String url) {
                // TODO Auto-generated method stub
                pic_name = file_name;
                try {
                    writeProperty("user_pic", file_name);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (pic_name != null) {
                    new_mShopG.setPicture(pic_name);
                }
                String p = dtPrice.getText().toString();
                String goodsname = dtName.getText().toString();
                String describe = dtDescribe.getText().toString();
                String cat = spinner.getSelectedItem().toString();
                if (p.equals("") || goodsname.equals("") || describe.equals("") || cat.equals("")) {
                    toast("信息不完整，请检查。");
                } else {
                    Log.e("EditGoods里的update()方法", "种类cat为" + cat);
                    new_mShopG.setShopname(ShopName);
                    new_mShopG.setGoodsname(goodsname);
                    new_mShopG.setDescription(describe);
                    new_mShopG.setPrice(p);
                    new_mShopG.setGcat(cat);
                    //上传数据
                    new_mShopG.update(EditGoods.this, goodid, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            toast("商品已更新！快去管理你的小店吧~");
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast("更新商品失败" + s);
                        }
                    });
                }
            }
        });

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
            ibPic.setImageBitmap(photo);
            //photo.
        }
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

    //加载店家图片
    private void loadShopPicImpl(ImageButton shop_pic, String pic_name) {
        Log.d(TAG, "in function loadShopPicImpl:loading");
        final ImageView view = shop_pic;
        Bitmap shop_bg = null;
        File file = new File(pic_name);
        if (file.exists()) {
            Log.i(TAG, "loading pic local?");
            shop_bg = BitmapFactory.decodeFile(pic_name);
            shop_pic.setImageBitmap(shop_bg);
        } else {
            BmobProFile pro_file = BmobProFile.getInstance(EditGoods.this);
            pro_file.download(pic_name, new DownloadListener() {
                @Override
                public void onSuccess(String s) {
                    setImageBitmap(view, s);
                }

                @Override
                public void onProgress(String s, int i) {

                }

                @Override
                public void onError(int i, String s) {
                    Log.e(TAG, "loading pic wrong:" + s);
                }
            });
        }
    }

    private void setImageBitmap(ImageView view, String file_path) {
        Log.i(TAG, "loading success: " + file_path);
        File file = new File(file_path);
        Bitmap bit = null;
        if (view.isShown()) {
            view.setImageResource(0);
            bit = BitmapFactory.decodeFile(file_path);
            if (file.exists())
                Log.e(TAG, "file exists!");
            if (bit == null)
                Log.e(TAG, "WTF NullPointer?");
            else
                Log.i(TAG, "" + bit.getWidth());
            view.setImageBitmap(bit);
            view.invalidate();
            Log.i(TAG, "set to imageView");
        }
        //file.delete();
    }

    private void toast(String text) {
        Toast.makeText(EditGoods.this, text, Toast.LENGTH_SHORT).show();
        ;
    }
}
