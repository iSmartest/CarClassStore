package com.lixin.carclassstore.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.StoreDetailsActivity;
import com.lixin.carclassstore.adapter.StoreAdapter;
import com.lixin.carclassstore.bean.ContentBean;
import com.lixin.carclassstore.bean.JavaBean;
import com.lixin.carclassstore.bean.StoreBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */
public class StoreFragment extends BaseFragment {
    private View view;
    private PullToRefreshListView mListView;
    private StoreAdapter mAdapter;
    private String serveType;
    private String sort;
    private int nowPage = 1;
    private List<StoreBean.shop> mList = new ArrayList<>();
    private TextView textClass,textMethod,textSort;
    private PopupWindow popupWindow;
    private ListView lv_group;
    private List<String> mListType = new ArrayList<String>();  //类型列表
    String[] strs = {};
    JSONObject json2;
    private List<JavaBean.filtrate> filtrate = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store, container, false);
        textClass = (TextView) view.findViewById(R.id.text_service_class);
        textMethod = (TextView) view.findViewById(R.id.text_screening_method);
        textSort = (TextView) view.findViewById(R.id.text_sort_method);
        mListView = (PullToRefreshListView) view.findViewById(R.id.pull_to_refresh_list);
        if (getArguments() != null) {
            String param1 = getArguments().getString("param");
            ToastUtils.showMessageShort(getActivity(),param1);
        }
        sort = "1";
        serveType = "0";
        getdata();
        textClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow(view);
            }
        });
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new StoreAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mList.clear();
                nowPage = 1;
                getdata();
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                getdata();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), StoreDetailsActivity.class);
                intent.putExtra("shopid",mList.get(position-1).getShopid());
                startActivity(intent);
            }
        });
        return view;
    }

    public static StoreFragment newInstance(String text) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        args.putString("param", text);
        fragment.setArguments(args);
        return fragment;
    }

    private void getdata() {
        Map<String, String> params = new HashMap<>();
        JSONArray array = new JSONArray(Arrays.asList(strs));
        Map<String, Object> map2 = new HashMap<>();
        map2.put("fliter", array);
        map2.put("cmd", "getShopListInfo");
        map2.put("serveType", serveType);
        map2.put("sort", sort);
        map2.put("nowPage", nowPage);
        json2 = new JSONObject(map2);
        params.put("json", json2.toString());
        Log.i("shopList", "getdata: "+json2.toString());
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageLong(context, "网络异常");
                dialog.dismiss();
                mListView.onRefreshComplete();
            }
            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                dialog.dismiss();
                mListView.onRefreshComplete();
                Log.i("shopList", "onResponse: " + response.toString());
                StoreBean storeBean = gson.fromJson(response, StoreBean.class);
                if (storeBean.getResult().equals("1")){
                    ToastUtils.showMessageLong(getActivity(),storeBean.getResultNote());
                }
                if (storeBean.getTotalPage() < nowPage) {
                    ToastUtils.showMessageShort(context, "没有更多了");
                    return;
                }
                List<StoreBean.shop> shopList = storeBean.shop;
                mList.addAll(shopList);
                Log.i("shopList", "onResponse: " + mList.get(0).getShopName());
                mAdapter.setStoreBeanList(context,mList);
                mListView.setAdapter(mAdapter);
            }
        });
    }
    private void showPopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_window, null);
        // 设置按钮的点击事件
        final TextView text1 = (TextView) contentView.findViewById(R.id.text_default_sort);
        final TextView text2 = (TextView) contentView.findViewById(R.id.source_good);
        final TextView text3 = (TextView) contentView.findViewById(R.id.selas_height);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = "0";
                textSort.setText(text1.getText().toString().trim());
                getdata();
            }
        });text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = "1";
                textSort.setText(text2.getText().toString().trim());
                getdata();
            }
        });text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = "2";
                textSort.setText(text3.getText().toString().trim());
                getdata();
            }
        });
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
//        popupWindow.setBackgroundDrawable(getResources().get(R.color.text_color_choose));
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }
}
