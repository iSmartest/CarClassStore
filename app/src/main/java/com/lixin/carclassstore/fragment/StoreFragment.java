package com.lixin.carclassstore.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.ShopDetailsActivity;
import com.lixin.carclassstore.activity.StoreDetailsActivity;
import com.lixin.carclassstore.adapter.StoreAdapter;
import com.lixin.carclassstore.bean.StoreBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
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
//        JSONArray array = new JSONArray(Arrays.asList(strs));
//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("fliter", array);
//        json2 = new JSONObject(map2);
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
            public void onClick(View v) {

            }
        });

        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new StoreAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                mList.clear();
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

//    private void showPopupMenu(final TextView text) {
//        ListView contentView = new ListView(context);
//        if (popupWindow!=null){
//            popupWindow.dismiss();
//        }
//        popupWindow = new PopupWindow(contentView,getActivity().getWindowManager().getDefaultDisplay().getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        contentView.setBackgroundColor(0XFFEEEEEE);
//        contentView.setDivider(new ColorDrawable(getResources().getColor(R.color.white_smoke)));
//        contentView.setDividerHeight(getResources().getDimensionPixelSize(R.dimen.high));
//        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context,R.layout.item_main_popup_list, mListType);
//        contentView.setAdapter(adapter);
//        contentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = adapter.getItem(position);
//                text.setText(item);
//                popupWindow.dismiss();
//                nowPage=1;
////                code = 2;
////                fujinId = fujinlist.get(position).fujinId;
////                getjulidata(fujinId);
//            }
//        });
//        popupWindow.setOutsideTouchable(true);// 设置此数获得焦点，否则无法参点击
//        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable());
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
//                lp.alpha = 1f;
//                getActivity().getWindow().setAttributes(lp);
//            }
//
//        });
//        int[] location = new int[2];
//        text.getLocationOnScreen(location);
//        popupWindow.showAtLocation(text, Gravity.NO_GRAVITY, location[0], location[1]-popupWindow.getHeight());
//    }

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
                mAdapter.setStoreBeanList(mList);
                mListView.setAdapter(mAdapter);
            }
        });
    }


}
