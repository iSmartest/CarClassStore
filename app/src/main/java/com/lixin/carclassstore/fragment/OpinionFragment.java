package com.lixin.carclassstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.ShopActivity;
import com.lixin.carclassstore.adapter.OpinionAdapter;
import com.lixin.carclassstore.adapter.ShopAdapter;
import com.lixin.carclassstore.bean.OpinionBean;
import com.lixin.carclassstore.bean.ShopBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.lixin.carclassstore.R.id.list_store;

/**
 * Created by 小火
 * Create time on  2017/4/19
 * My mailbox is 1403241630@qq.com
 *
 * 评价
 */

public class OpinionFragment extends BaseFragment{
    private View view;
    private PullToRefreshListView list_opinion;
    private OpinionAdapter mAdapter;
    private int nowPage = 1;
    private String commodityid;
    private List<OpinionBean.commodityCommentLists> mList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_opinion,null);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle != null){
            commodityid = bundle.getString("commodityid");
        }
        list_opinion = (PullToRefreshListView) view.findViewById(R.id.list_opinion);
        list_opinion.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new OpinionAdapter(getActivity());
        list_opinion.setAdapter(mAdapter);
        getdata();
        list_opinion.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                getdata();
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                getdata();
            }
        });
        return view;
    }
    //请求参数
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getCommoditysCommentsInfo\",\"nowPage\":\"" + nowPage +"\",\"commodityid\":\""
                + commodityid + "\"}";
        params.put("json", json);
        Log.i("OpinionFragment", "onResponse: " + json.toString());
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageShort(context, e.getMessage());
                dialog.dismiss();
                list_opinion.onRefreshComplete();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("OpinionFragment", "onResponse: " + response.toString());
                Gson gson = new Gson();
                dialog.dismiss();
                list_opinion.onRefreshComplete();
                OpinionBean opinionBean = gson.fromJson(response, OpinionBean.class);
                if (opinionBean.result.equals("1")) {
                    ToastUtils.showMessageShort(context, opinionBean.resultNote);
                    return;
                }
                if (Integer.parseInt(opinionBean.totalPage) < nowPage) {
                    ToastUtils.showMessageShort(context, "没有更多了");
                    return;
                }
                List<OpinionBean.commodityCommentLists> commodityslist = opinionBean.commodityCommentLists;
                Log.i("commodityslist", "commodityslist: " + commodityslist.get(0).getUserName());
                mList.addAll(commodityslist);
                mAdapter.setOpinionList(getActivity(),mList);
                list_opinion.setAdapter(mAdapter);
                list_opinion.onRefreshComplete();
            }
        });
    }
}
