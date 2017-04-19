package com.lixin.qiaoqixinyuan.app.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.XListView.XListView;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.lixin.qiaoqixinyuan.app.base.MyApplication.mLocationClient;
import static com.lixin.qiaoqixinyuan.app.base.MyApplication.myListener;

/**
 * 地图选择位置、经纬度
 * Created by Administrator on 2016/8/25 0025.
 */
public class SelectMapAddressActivity extends BaseActivity implements OnGetPoiSearchResultListener, BaiduMap.OnMapStatusChangeListener, XListView.IXListViewListener, View.OnClickListener {

    private XListView listView;
    private List<PoiInfo> dataList = new ArrayList<>();
    private int dataListLength = 0;//检索地址集合个数
    private MapAddressAdapter addressAdapter;
    // 定位相关
    private BaiduMap mBaiduMap;
    private MapView mapView;
    boolean isFirstLoc = true; // 是否首次定位
    private LatLng latLng;
    private PoiSearch mPoiSearch;
    private Handler mHandler;
    private TextView tv_nearby;
    private int nowPage=1;
    private int pageSize = 15, pageIndex = 0;
    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private View view;
    private XListView lv_address;
    private LocationClient mLocClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map_address);
        initView();
        init();
    }

    private void init() {
        mHandler = new Handler();
        tv_title.setText("地址选择");
        iv_turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listView = (XListView) findViewById(R.id.lv_address);
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                PoiInfo info = dataList.get(i - 1);
                intent.putExtra("address", info.address + "");
                intent.putExtra("lat", info.location.latitude + "");
                intent.putExtra("lon", info.location.longitude + "");
                intent.putExtra("city", info.city);
                ToastUtil.showToast(context,info.uid);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        addressAdapter = new MapAddressAdapter(dataList);
        listView.setAdapter(addressAdapter);
        tv_nearby = (TextView) findViewById(R.id.tv_nearby);
        tv_nearby.setOnClickListener(this);
        mapView = (MapView) findViewById(R.id.mapView);
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        //地图上比例尺
        mapView.showScaleControl(false);
        // 隐藏缩放控件
        mapView.showZoomControls(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLocation();
    }

    public void initLocation() {
        //POI搜索初始化
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        // 开启定位图层
        mBaiduMap = mapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setOnMapStatusChangeListener(this);
        mBaiduMap.setMyLocationEnabled(true);

        UiSettings settings = mBaiduMap.getUiSettings();
        settings.setAllGesturesEnabled(true);
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    /**
     * 检索周边地址
     */
    @Override
    public void onGetPoiResult(PoiResult poiResult) {

        mLocClient.start();
        List<PoiInfo> poiInfos = new ArrayList<>();
        if (poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
            mPoiSearch.searchNearby(new PoiNearbySearchOption().location(latLng)
                    .pageCapacity(pageSize).pageNum(pageIndex).keyword("村")
                    .radius(1000).sortType(PoiSortType.distance_from_near_to_far));
        } else {
            poiInfos.clear();
            poiInfos = poiResult.getAllPoi();
            if (poiInfos != null && poiInfos.size() > 0) {
                dataList.addAll(poiInfos);
                if (poiInfos.size() < pageSize) {
                    listView.setPullLoadEnable(false);
                } else {
                    listView.setPullLoadEnable(true);
                }
                addressAdapter = new MapAddressAdapter(dataList);
                listView.setAdapter(addressAdapter);
                listView.setSelection(dataListLength);
                dataListLength = dataList.size();
            }
        }
    }

    private void getLocation() {
        mPoiSearch.searchNearby(new PoiNearbySearchOption().location(latLng)
                .pageCapacity(pageSize).pageNum(pageIndex).keyword("小区")
                .radius(1000).sortType(PoiSortType.distance_from_near_to_far));
        //geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nowPage = 1;
                getLocation();
                listView.stopRefresh();
            }
        }, 1500);
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex++;
                nowPage++;
                getLocation();

                listView.stopLoadMore();
            }
        }, 1500);
    }

    /**
     * 地图移动监听
     */
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {
        if (dataList != null && dataList.size() > 0) {
            dataList.clear();
            dataListLength = 0;
            addressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {
        if (dataList != null && dataList.size() > 0) {
            dataList.clear();
            dataListLength = 0;
            addressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        latLng = mapStatus.target;
        pageIndex = 0;
        getLocation();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        view = (View) findViewById(R.id.view);
        lv_address = (XListView) findViewById(R.id.lv_address);
    }


    private class MapAddressAdapter extends BaseAdapter {
        private List<PoiInfo> dataList;

        public MapAddressAdapter(List<PoiInfo> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(SelectMapAddressActivity.this).inflate(R.layout.item_search_address_lv, viewGroup, false);
                holder = new ViewHolder(view);
                holder.id_address_name = (TextView) view.findViewById(R.id.id_address_name);
                holder.id_address_details = (TextView) view.findViewById(R.id.id_address_details);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            PoiInfo info = dataList.get(i);
            holder.id_address_name.setText(info.name);
            holder.id_address_details.setText(info.address);

            return view;
        }

        public class ViewHolder {
            public View rootView;
            public TextView id_address_name;
            public TextView id_address_details;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.id_address_name = (TextView) rootView.findViewById(R.id.id_address_name);
                this.id_address_details = (TextView) rootView.findViewById(R.id.id_address_details);
            }

        }
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
//        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        super.onDestroy();
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mPoiSearch.destroy();
        mapView = null;
    }


}
