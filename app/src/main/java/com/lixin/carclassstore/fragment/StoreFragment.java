package com.lixin.carclassstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.StoreAdapter;
import com.lixin.carclassstore.bean.StoreBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.CommonLog;
import com.lixin.carclassstore.utils.ConstantUtil;
import com.lixin.carclassstore.utils.GlobalMethod;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import org.json.JSONObject;
import java.util.ArrayList;
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
    private ListView mListView;
    private StoreAdapter mAdapter;
    private int mIndex = 1;
    private String serveType;
    private String sort;
    private String filtrate;
    private String nowPage;
    private List<StoreBean.shopList> mList = new ArrayList<>();
    private List<StoreBean.shopList> tmpList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store, container, false);
        return view;
    }
}
