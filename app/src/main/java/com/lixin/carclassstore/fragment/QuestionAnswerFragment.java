package com.lixin.carclassstore.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.ReleaseQuestionActivity;
import com.lixin.carclassstore.adapter.QuestionAnswerAdapter;
import com.lixin.carclassstore.bean.QuestionAnswerBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
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
 * 问答
 */

public class QuestionAnswerFragment extends BaseFragment{
    private ListView mListView;
    private QuestionAnswerAdapter mAdapter;
    private List<QuestionAnswerBean.consults> mList = new ArrayList<>();
    private int nowPage = 1 ;//当前页数（10个每页）是第一页需要服务位和广告位信息
    private String uid ;
    private View view;
    private ImageView addQuestion;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_content,container,false);
        mListView = (ListView) view.findViewById(R.id.lv_news);
        addQuestion = (ImageView) view.findViewById(R.id.iv_add_question);
        addQuestion.setVisibility(View.VISIBLE);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ReleaseQuestionActivity.class));
            }
        });
        mAdapter = new QuestionAnswerAdapter(context);
        mListView.setAdapter(mAdapter);
        uid = (String) SPUtils.get(getActivity(),"uid","");
        getdata();
        return view;
    }
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"getForumConsultInfo\",\"nowPage\":\"" + nowPage + "\"" + ",\"uid\":\"" + uid + "\"}";
        params.put("json", json);
        dialog.show();
        Log.i("111", "onResponse: " + json.toString());
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
                        QuestionAnswerBean questionAnswerBean = gson.fromJson(response, QuestionAnswerBean.class);
                        if (questionAnswerBean.result.equals("1")) {
                            ToastUtils.showMessageShort(context, questionAnswerBean.resultNote);
                            return;
                        }
                        List<QuestionAnswerBean.consults> newsList = questionAnswerBean.consults;
                        mList.addAll(newsList);
                        Log.i("mList", "mList: " + mList.get(0).getUserName());
                        mAdapter.setQuestionAnswerList(getActivity(), mList);
                        mListView.setAdapter(mAdapter);
                        initData();
                    }
                });
    }

    private void initData() {

    }
}
