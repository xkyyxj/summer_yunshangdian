package com.example.project.easyshopping;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class MiniActivity extends Activity {

    public static final String TAG = "App2 Map testing:";

    private LocationClient client = null;
    private BDLocationListener listener = null;
    private MapView view = null;
    private BaiduMap map = null;
    private LocationMode mode = null;
    private GeoCoder coder = null;//����ģ��

    private TextView info = null;

    private boolean isFirstLoc = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.baidu_map_test);
        view = (MapView) findViewById(R.id.bmapView);
        info = (TextView) findViewById(R.id.show_info);
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
        option.setOpenGps(true);// ��gps
        option.setCoorType("bd09ll"); // ������������
        option.setIsNeedAddress(true);
        option.setScanSpan(1000);
        client.setLocOption(option);
        client.start();
        map.setMyLocationConfigeration(new MyLocationConfiguration(mode, true, null));
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation arg0) {
            // TODO Auto-generated method stub
            if (arg0 == null)
                return;
            double longitude = arg0.getLongitude();
            double latitude = arg0.getLatitude();
            String province = arg0.getProvince();
            String city = arg0.getCity();
            String district = arg0.getDistrict();
            String street = arg0.getStreet();
            String street_number = arg0.getStreetNumber();
            info.setText(longitude + " " + latitude + " " + province + " " + city + " " + district + " " + street + " " + street_number + " ");
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(arg0.getRadius())
                            // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
                    .direction(100).latitude(arg0.getLatitude())
                    .longitude(arg0.getLongitude()).build();
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

    public void onDestroy() {
        map.setMyLocationEnabled(false);
        view.onDestroy();
        view = null;
        client.unRegisterLocationListener(listener);
        client.stop();
    }

    private class MyMapClickListener implements OnMapClickListener {

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

    private class MyGeoCoderResultListener implements OnGetGeoCoderResultListener {

        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            // TODO Auto-generated method stub
            Log.e(TAG, "is running???");

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //��ʾ��Ϣ��û���ѵ�λ����Ϣ
                return;
            }
            map.clear();
            map.addOverlay(new MarkerOptions().position(result.getLocation())
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.user)));
            map.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                    .getLocation()));
            LatLng llText = result.getLocation();
            OverlayOptions ooText = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24).fontColor(0xFFFF00FF).text(result.getAddress()).rotate(-30)
                    .position(llText);
            map.addOverlay(ooText);
        }

    }

}
