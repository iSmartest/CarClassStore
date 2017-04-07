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
import com.lixin.carclassstore.http.RequestParameters;
import com.lixin.carclassstore.http.SimpleHttp;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.pulldown.PullToRefreshBase;
import com.lixin.carclassstore.pulldown.PullToRefreshListView;
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
    private PullToRefreshListView pullToRefreshListView;
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
        view = inflater.inflate(R.layout.fragment_store,container,false);
        pullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.pull_to_refresh_list);
        mListView = (ListView) pullToRefreshListView.getRefreshableView();
        mAdapter = new StoreAdapter(getActivity(), mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int type = pullToRefreshListView.getRefreshType();
                if (type == PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH) {
                    mIndex = 1;
                    loadData(ConstantUtil.PULL_DOWN_REFRESH, false);
                } else {
                    if (type == PullToRefreshBase.MODE_PULL_UP_TO_REFRESH) {
                        loadData(ConstantUtil.PULL_UP_MORE, false);
                    }
                }
            }
        });
        if (GlobalMethod.isNetworkAvailable(getActivity())){
            if (mList == null || mList.size() == 0) {
                loadData(ConstantUtil.PULL_DOWN_REFRESH, true);
            }
        }  else {
            ToastUtils.showMessageLong(getActivity(),"网络跑丢了！");
        }
    return view;
    }
    private String noDataTips;
    private void loadData(int pullType, boolean isShowDialog) {
        if (pullType == ConstantUtil.PULL_DOWN_REFRESH) {
            mIndex = 1;
            noDataTips = getString(R.string.no_data);
        } else {
            mIndex++;
            noDataTips = getString(R.string.no_more_data);
        }
        loadSeverData();
    }


    private void loadSeverData() {
////         cmd:"getShopListInfo"
//        serveType:""门店服务id从启动接口中获得，可以为空
//        sort:""//0评分排序，1销量排序，2距离排序
//        filtrate:[@"1",@"3"];//筛选方式数组中是对应的filtrateid
//        nowPage:"1" //当前页数（10个每页）是第一页需要服务位和广告位信息

    /*    cmd:”shangjialiuyan”      //留言
        shangjiaid:"35"         //商家id
        messageid:"65"     //留言用户id
        pingluntype:"0"   // 0.帖子评论；1.房屋信息评论；2.二手信息评论；3.招聘求职信息评论 4：商家
        messagetype:"0"     // 0 普通用户留言   1 客户留言
        nowPage:"1"     //当前页 */
            Map<String, String> params = new HashMap<>();
            String json = "{\"cmd\":\"getShopListInfo\",\"serveType\":\"" + serveType + "\"" +
                    ",\"sort\":\"" + sort + "\",\"filtrate\":\"" + filtrate + "\"" +
                    ",\"nowPage\":\"" + nowPage + "\"}";
            params.put("json", json);
            dialog.show();
            OkHttpUtils.post().url(getString(R.string.url)).params(params).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.showMessageShort(context, e.getMessage());
                    dialog.dismiss();
                    pullToRefreshListView.onRefreshComplete();
                }
                @Override
                public void onResponse(String response, int id) {
                    dialog.dismiss();
                    StoreBean storeBean = new Gson().fromJson(response, StoreBean.class);
                    if (storeBean.result.equals("1")) {
                        ToastUtils.showMessageLong(context, storeBean.resultNote);
                        pullToRefreshListView.onRefreshComplete();
                        return;
                    }
                    if (Integer.parseInt(storeBean.totalPage) < mIndex) {
                        ToastUtils.showMessageShort(context, "没有更多了");
                        pullToRefreshListView.onRefreshComplete();
                        return;
                    }
                    List<StoreBean.shopList> messagelist = storeBean.shop;
                    Log.i("jjjj", "onResponse: " + messagelist);
                    mList.addAll(messagelist);
                    mAdapter.setStoreBeanList(mList);
                    mAdapter.notifyDataSetChanged();
                    pullToRefreshListView.onRefreshComplete();

                    Log.i("kkkkk", "onResponse: " + mList.get(0));
                }
            });
        }

    /**
     * 刷新
     */
    private void refreshView() {

        if (tmpList == null || tmpList.isEmpty()) {
            if (!TextUtils.isEmpty(noDataTips))
                ToastUtils.showMessageShort(getActivity(), noDataTips);
            if (mIndex == 1) {
                if (mList != null)
                    mList.clear();
                mAdapter.notifyDataSetChanged();
            }

        } else {
            if (mList == null)
                mList = new ArrayList<>();
            if (mIndex == 1) {
                mList.clear();
            }
            mList.addAll(tmpList);
            mAdapter.updateList(mList);


        }

    }
    /**
     * 解析列表
     *
     * @param jsonStr
     */
    private void parseResult(Object jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr.toString());
            String body = jsonObject.getString("shop");
            if (TextUtils.isEmpty(body) || "null".equals(body))
                return;
            JSONObject bodyObject = jsonObject.getJSONObject("shop");

            if (bodyObject == null)
                return;

            String jsonList = bodyObject.getString("shop");
            if (TextUtils.isEmpty(jsonList)) {
                return;
            }
            tmpList = new Gson().fromJson(jsonList, new TypeToken<List<StoreBean>>() {
            }.getType());

            refreshView();

        } catch (Exception e) {
            CommonLog.e(e);
        }
    }
}
