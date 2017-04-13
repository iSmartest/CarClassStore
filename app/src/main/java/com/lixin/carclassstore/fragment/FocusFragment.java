package com.lixin.carclassstore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.NewsWebActivity;
import com.lixin.carclassstore.adapter.FristNewsAdapter;
import com.lixin.carclassstore.bean.NewsAndFocusBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/11
 * My mailbox is 1403241630@qq.com
 * 关注
 */

public class FocusFragment extends BaseFragment{
    private ListView mListView;
    private FristNewsAdapter mAdapter;
    private List<NewsAndFocusBean.newsList> mList = new ArrayList<>();
    private String newType = "1";
    private String plateid = "";
    private int nowPage = 1;
    private String uid = "123";
    private NewsAndFocusBean info;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_content,container,false);
        mListView = (ListView) view.findViewById(R.id.lv_news);
        mAdapter = new FristNewsAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),NewsWebActivity.class);
                intent.putExtra("forumUrl",mList.get(position).getForumUrl());
                startActivity(intent);
            }
        });
        getdata();
        return view;
    }
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"getForumNewsListInfo\",\"plateid\":\"" + plateid + "\"" +
                ",\"newType\":\"" + newType + "\"" + ",\"nowPage\":\"" + nowPage + "\"" + ",\"uid\":\"" + uid + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showMessageShort(context, e.getMessage());
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("111", "onResponse: " + response.toString());
                        Gson gson = new Gson();
                        dialog.dismiss();
                        NewsAndFocusBean newsAndFocusBean = gson.fromJson(response, NewsAndFocusBean.class);
                        if (newsAndFocusBean.result.equals("1")) {
                            ToastUtils.showMessageShort(context, newsAndFocusBean.resultNote);
                            return;
                        }
                        List<NewsAndFocusBean.newsList> newsList = newsAndFocusBean.newsList;
                        mList.addAll(newsList);
                        Log.i("mList", "mList: " + mList.get(0).getForumIcon());
                        mAdapter.setNewsList(getActivity(), mList);
                        mListView.setAdapter(mAdapter);
                        initData();
                    }
                });
    }

    private void initData() {

    }
}
