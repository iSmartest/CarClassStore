package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.ReplyQuestionActivity;
import com.lixin.carclassstore.bean.QuestionAnswerBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/11
 * My mailbox is 1403241630@qq.com
 */

public class QuestionAnswerAdapter extends BaseAdapter {
    private Context context;
    private List<QuestionAnswerBean.consults> list;
    public QuestionAnswerAdapter(Context context){
        this.context = context;
    }
    public void setQuestionAnswerList(Context context, List<QuestionAnswerBean.consults> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public QuestionAnswerBean.consults getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_question_answer,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_reply = (ImageView) convertView.findViewById(R.id.iv_reply);
            viewHolder.iv_user_icon = (ImageView) convertView.findViewById(R.id.iv_user_icon);

            viewHolder.text_user_name = (TextView) convertView.findViewById(R.id.text_user_name);
            viewHolder.text_talk_time = (TextView) convertView.findViewById(R.id.text_talk_time);
            viewHolder.text_user_talk = (TextView) convertView.findViewById(R.id.text_user_talk);
            viewHolder.text_question_reply_num = (TextView) convertView.findViewById(R.id.text_question_reply_num);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final QuestionAnswerBean.consults consults = list.get(position);
//        Picasso.with(context).load(consults.getUserIcon()).into(viewHolder.iv_user_icon);
        viewHolder.text_user_name.setText(consults.getUserName());
        viewHolder.text_talk_time.setText(consults.getTalkTime());
        viewHolder.text_user_talk.setText(consults.getUserTalk());
        viewHolder.text_question_reply_num.setText(consults.getQuestionReplyNum());
        viewHolder.iv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ReplyQuestionActivity.class);
//                intent.putExtra("userIcon",consults.getUserIcon());
                intent.putExtra("userName",consults.getUserName());
                intent.putExtra("userTalk",consults.getUserTalk());
                intent.putExtra("questionReplyNum",consults.getQuestionReplyNum());
                intent.putExtra("talkTime",consults.getTalkTime());
                intent.putExtra("questionid",consults.getQuestionid());
                context.startActivity(intent);
            }
        });
        return convertView;
    }



    class ViewHolder{
        ImageView iv_user_icon,iv_reply;
        TextView text_user_name,text_talk_time,text_user_talk,text_question_reply_num;

    }
}
