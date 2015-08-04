package com.test.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.test.albert.myapplication.R;

import java.util.List;

import bmobObject.Shop;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/*
 *author:wqch
 *finish_time:2015.07.13 
 */

public class RegisterActivity extends Activity {

	public static final String TAG = "App2:";
	
	private boolean isFirstLoc = true;
	private boolean choose_new_addr = false;
	
	private Spinner category = null;
	private EditText name,work,addr,tele;
	private ImageButton register_button = null;
	
	private LocationClient client = null;
	private BDLocationListener listener = null;
	private MapView view = null;
	private BaiduMap map = null;
	private LocationMode mode = null;
	private GeoCoder coder = null;//搜索模块
	
	private RegisterButtonClickListener li = null;
	private SpinnerSelectedListener item_li = null;
	
	private Shop user = null;
	private Shop new_user = null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		user = BmobUser.getCurrentUser(getApplicationContext(),Shop.class);
		new_user = new Shop();
		setContentView(R.layout.activity_register);
		view = (MapView)findViewById(R.id.bmapView);
		category = (Spinner)findViewById(R.id.activity_register_category);
		name = (EditText)findViewById(R.id.activity_register_shop_name);
		work = (EditText)findViewById(R.id.activity_register_shop_work);
		addr = (EditText)findViewById(R.id.activity_register_shop_addr);
		tele = (EditText)findViewById(R.id.activity_register_shop_tele);
		register_button = (ImageButton)findViewById(R.id.activity_register_register_button);
		
		li = new RegisterButtonClickListener();
		register_button.setOnClickListener(li);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activity_register_category_arr, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(adapter);
		//MyItemAdapter my = new MyItemAdapter(this);
		//category.setAdapter(my);
		item_li = new SpinnerSelectedListener();
		category.setOnItemSelectedListener(item_li);
		
		//BaiduMap initialize
		map = view.getMap();
		mode = LocationMode.NORMAL;
		
		map.setMyLocationEnabled(true);
		map.setOnMapClickListener(new MyMapClickListener());
		coder = GeoCoder.newInstance();
		coder.setOnGetGeoCodeResultListener(new MyGeoCoderResultListener());
		
		client = new LocationClient(getApplicationContext());
		listener = new MyLocationListener();
		client.registerLocationListener(listener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setIsNeedAddress(true);
		option.setScanSpan(1000);
		client.setLocOption(option);
		client.start();
		map.setMyLocationConfigeration(new MyLocationConfiguration(mode, true, null));
	}
	
	class RegisterButtonClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(user != null)
			{
				String shop_name = name.getText().toString();
				String work_text = work.getText().toString();
				String shop_tele = tele.getText().toString();
				String shop_addr = addr.getText().toString();
				new_user.setShopname(shop_name);
				new_user.setBusiness(work_text);
				new_user.setShopadd(shop_addr);
				new_user.setShoptel(shop_tele);
				BmobQuery<Shop> query = new BmobQuery<Shop>();
				query.addWhereEqualTo("shopname", shop_name);
				query.findObjects(RegisterActivity.this, new FindListener<Shop>()
				{

					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<Shop> arg0) {
						// TODO Auto-generated method stub
						Log.e("RUN:","in finding shop function:" + arg0.size());
						if(arg0.size() > 0)
							toast(R.string.activity_register_shop_name_repetition);
						else
						{
							double send_price = 0;
							new_user.setCheckstate(3);
							new_user.setStarttime("08:30");
							new_user.setEndtime("08:30");
							new_user.setSusername(user.getUsername());
							new_user.setSuserpwd("");
							new_user.setDistributeprice(send_price);
							new_user.setSendprice(send_price);
							new_user.setOperatesta(false);
							new_user.setTotalgood(0);
							new_user.update(RegisterActivity.this, user.getObjectId(), new UpdateListener() {
								@Override
								public void onFailure(int arg0, String arg1) {
									// TODO Auto-generated method stub
									toast(R.string.activity_register_registe_fail, arg1);
								}

								@Override
								public void onSuccess() {
									// TODO Auto-generated method stub
									toast(R.string.activity_register_registe_success);
									finish();
								}

							});
						}
					}
					
				});
				
			}
			else
				toast(R.string.activity_register_login_info);
		}
		
	}
	
	class SpinnerSelectedListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			//String content = (String)parent.getItemAtPosition(position);
			//Log.e(TAG, content);
			if(user != null)
				new_user.setScat((String)parent.getItemAtPosition(position));
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			if(user != null)
				new_user.setScat(null);
		}
		
	}
	
	private class MyLocationListener implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub
			if(arg0 == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(arg0.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(arg0.getLatitude())
					.longitude(arg0.getLongitude()).build();
			if(user != null)
			{
				BmobGeoPoint point = new BmobGeoPoint();
				point.setLatitude(arg0.getLatitude());
				point.setLongitude(arg0.getLongitude());
				new_user.setSlonla(point);
				new_user.setShopadd(arg0.getAddrStr());
			}
			if(!choose_new_addr)
				addr.setText(arg0.getAddrStr());
			map.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(arg0.getLatitude(),
						arg0.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				map.animateMapStatus(u);
			}
		}
		
	}
	
	public void onDestroy()
	{
		client.stop();
		map.setMyLocationEnabled(false);
		view.onDestroy();
		view = null;
		super.onDestroy();
	}
	
	private class MyMapClickListener implements OnMapClickListener
	{

		@Override
		public void onMapClick(LatLng arg0) {
			// TODO Auto-generated method stub
			Log.e(TAG, "onMapClick??");
			coder.reverseGeoCode(new ReverseGeoCodeOption().location(arg0));
		}

		@Override
		public boolean onMapPoiClick(MapPoi arg0) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	private class MyGeoCoderResultListener implements OnGetGeoCoderResultListener
	{

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			// TODO Auto-generated method stub
			Log.e(TAG, "is running???");
			
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				//提示信息，没有搜到位置信息
				return;
			}
			if(user != null)
			{
				BmobGeoPoint point = new BmobGeoPoint();
				LatLng position = result.getLocation();
				point.setLatitude(position.latitude);
				point.setLongitude(position.longitude);
				new_user.setSlonla(point);
				new_user.setShopadd(result.getAddress());
			}
			addr.setText(result.getAddress());
			choose_new_addr = true;
			map.clear();
			map.addOverlay(new MarkerOptions().position(result.getLocation())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.activity_register_locate)));
			map.setMapStatus(MapStatusUpdateFactory.newLatLng(result
					.getLocation()));
			LatLng llText = result.getLocation();
			OverlayOptions ooText = new TextOptions().bgColor(0xAAFFFF00)
					.fontSize(24).fontColor(0xFFFF00FF).text(result.getAddress()).rotate(-30)
					.position(llText);
			map.addOverlay(ooText);
		}
		
	}
	
	private void toast(int id)
	{
		String text = this.getResources().getString(id);
		Toast.makeText(RegisterActivity.this, text, Toast.LENGTH_LONG).show();;
	}
	
	private void toast(int id,String _text)
	{
		String text = this.getResources().getString(id) + "," + _text;
		Toast.makeText(RegisterActivity.this, text, Toast.LENGTH_LONG).show();;
	}
	
}
