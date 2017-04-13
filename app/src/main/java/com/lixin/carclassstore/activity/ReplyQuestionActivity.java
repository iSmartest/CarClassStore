package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.ForumReplyAdapter;
import com.lixin.carclassstore.adapter.QuestionAnswerAdapter;
import com.lixin.carclassstore.bean.ForumReplyBean;
import com.lixin.carclassstore.bean.ReplyBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/11
 * My mailbox is 1403241630@qq.com
 */

public class ReplyQuestionActivity extends BaseActivity{
    private String userIcon;
    private String userName;
    private String talkTime;
    private String userTalk;
    private String questionReplyNum;
    private String questionId;
    private String uid = "123";
    private int nowPage = 1;
    ImageView iv_user_icon;
    TextView text_user_name,text_talk_time,text_user_talk,text_question_reply_num;
    private ListView mListView;
    private EditText edi_reply_content;
    private Button btn_reply;
    private List<ForumReplyBean.replys> mList = new ArrayList<>();
    private ForumReplyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_question);
        Intent intent = getIntent();
        userIcon = intent.getStringExtra("userIcon");
        userName = intent.getStringExtra("userName");
        talkTime = intent.getStringExtra("talkTime");
        userTalk = intent.getStringExtra("userTalk");
        questionReplyNum = intent.getStringExtra("questionReplyNum");
        questionId = intent.getStringExtra("questionid");
        hideBack(false);
        initView();
        commentsList();
    }

    private void initView() {
        iv_user_icon = (ImageView) findViewById(R.id.iv_user_icon);
//        Picasso.with(context).load(userIcon).into(iv_user_icon);
        text_user_name = (TextView) findViewById(R.id.text_user_name);
        text_user_name.setText(userName);
        text_talk_time = (TextView) findViewById(R.id.text_talk_time);
        text_talk_time.setText(talkTime);
        text_user_talk = (TextView) findViewById(R.id.text_user_talk);
        text_user_talk.setText(userTalk);
        text_question_reply_num = (TextView) findViewById(R.id.text_question_reply_num);
        text_question_reply_num.setText(questionReplyNum);
        edi_reply_content = (EditText) findViewById(R.id.edi_reply_content);
        btn_reply = (Button) findViewById(R.id.btn_reply);
        btn_reply.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.lv_reply);
        mAdapter = new ForumReplyAdapter(context);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reply:
                submit();
                break;
        }
    }

    private void submit() {
        String comment = edi_reply_content.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            ToastUtils.showMessageShort(context, "请输入回复内容");
            return;
        }
        getdata(comment);
    }

    private void getdata(String replyContent) {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"getForumCommintReplyInfo\",\"questionid\":\"" + questionId + "\"" + ",\"uid\":\"" + uid + "\"" + ",\"reply\":\"" + replyContent + "\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showMessageShort(context, e.getMessage());
                        dialog1.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("111", "onResponse: " + response.toString());
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        ReplyBean rplyBean = gson.fromJson(response, ReplyBean.class);
                        if (rplyBean.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, rplyBean.getResultNote());
                            return;
                        }else {
                            ToastUtils.showMessageShort(context, "回复成功！");
                            edi_reply_content.setText("");
                            mList.clear();
                            commentsList();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void commentsList() {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"getForumReplyInfo\",\"questionid\":\"" + questionId + "\"" + ",\"uid\":\"" + uid + "\"" + ",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showMessageShort(context, e.getMessage());
                        dialog1.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("111", "onResponse: " + response.toString());
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        ForumReplyBean forumReplyBean = gson.fromJson(response, ForumReplyBean.class);
                        if (forumReplyBean.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, forumReplyBean.getResultNote());
                            return;
                        }
                        List<ForumReplyBean.replys> replysList = forumReplyBean.replys;
                        mList.addAll(replysList);
                        mAdapter.setForumReply(context, mList);
                        mListView.setAdapter(mAdapter);
                    }
                });
    }
}
