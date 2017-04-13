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
import com.lixin.carclassstore.activity.SectionArticleListActivity;
import com.lixin.carclassstore.adapter.SectionAdapter;
import com.lixin.carclassstore.bean.NewsAndFocusBean;
import com.lixin.carclassstore.bean.SectionBean;
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
 * 版块
 */

public class SectionFragment extends BaseFragment{
    private View view;
    private SectionAdapter mAdapter;
    private ListView mListView;
    private List<SectionBean.forumSections> mList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_content,container,false);
        mListView = (ListView) view.findViewById(R.id.lv_news);
        mAdapter = new SectionAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showMessageShort(getActivity(),"你点击了"+ mList.get(position).getSectionTile());
                Intent intent = new Intent(getActivity(),SectionArticleListActivity.class);
                intent.putExtra("plateid",mList.get(position).getPlateid());
                startActivity(intent);
            }
        });
        getdata();
        return view;
    }
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"getForumSectionInfo\"}";
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
                        SectionBean sectionBean = gson.fromJson(response, SectionBean.class);
                        if (sectionBean.result.equals("1")) {
                            ToastUtils.showMessageShort(context, sectionBean.resultNote);
                            return;
                        }
                        List<SectionBean.forumSections> sectionList = sectionBean.forumSections;
                        mList.addAll(sectionList);
                        mAdapter.setSectionList(getActivity(), mList);
                        mListView.setAdapter(mAdapter);
                    }
                });
    }
}
