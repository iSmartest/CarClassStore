package com.lixin.carclassstore.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.lixin.carclassstore.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 * 当前位置
 */
public class MapApiDemoActivity extends Activity implements
     OnGetGeoCoderResultListener {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private GeoCoder mSearch = null;
    private TextView textLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // SDK初始化
        SDKInitializer.initialize(getApplicationContext());

        //当前视图
        setContentView(R.layout.activity_map_api_demo);

		//创建地图对象
		init();
		getLocation();

		textLocation = (TextView) findViewById(R.id.btn_location);
		textLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String content = textLocation.getText().toString().trim();
				Log.i("MapApiDemoActivity", "onClick: " + content);
				Intent intent = new Intent(MapApiDemoActivity.this,ReleaseRescueInformationActivity.class);
                intent.putExtra("result02",content);
				setResult(1003, intent);
                finish();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_map_api_demo, menu);
		return true;
	}

	/**
	 * 初始化方法
	 */
	private void init() {
		//mMapView = (MapView) findViewById(R.id.bmapview);
		mMapView = new MapView(this, new BaiduMapOptions());
		mBaiduMap = mMapView.getMap();
		
		/**添加一个对象*/
		LinearLayout rlly_map = (LinearLayout)findViewById(R.id.rlly_map);
		rlly_map.addView(mMapView);
		
		// 开启定位图层
	    mBaiduMap.setMyLocationEnabled(true);
	    
	    //初始化搜索模块，注册事件监听
	 	mSearch = GeoCoder.newInstance();
	 	mSearch.setOnGetGeoCodeResultListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	private boolean isFirstLoc = true;
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					//此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
			
			String addr = location.getAddrStr();
			if (addr != null) {
				Log.i("Test", addr);
			} else {
				Log.i("Test","error");
			}
			
			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
			if (longitude > 0 && latitude > 0) {
				Log.i("Test",String.format("纬度:%f 经度:%f", latitude,longitude));
				
				LatLng ptCenter = new LatLng(latitude,longitude);
				// 反Geo搜索
				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
						.location(ptCenter));
			} 
			//停止定位
			mLocClient.stop();
		}

		@Override
		public void onConnectHotSpotMessage(String s, int i) {

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	private void getLocation() {
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);//打开gps
		option.setCoorType("bd09ll"); //设置坐标类型
		option.setScanSpan(5000); //定位时间间隔
		mLocClient.setLocOption(option);

		mLocClient.start();
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapApiDemoActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
//		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
//				.icon(BitmapDescriptorFactory
//						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		Toast.makeText(MapApiDemoActivity.this, result.getAddress(),
				Toast.LENGTH_LONG).show();
		Log.i("result", "onGetReverseGeoCodeResult: " +result.getAddress());
		Log.i("result", "onGetReverseGeoCodeResult: " +result.getBusinessCircle());
		Log.i("result", "onGetReverseGeoCodeResult: " +result.getSematicDescription());
		Log.i("result", "onGetReverseGeoCodeResult: " +result.getAddressDetail());
		Log.i("result", "onGetReverseGeoCodeResult: " +result.getLocation());
		Log.i("result", "onGetReverseGeoCodeResult: " +result.getPoiList());

        String province = result.getAddressDetail().province;
        String city = result.getAddressDetail().city;
        if (province != null && city != null) {
            textLocation.setText(result.getAddress() + result.getSematicDescription());
        }
    }
}
