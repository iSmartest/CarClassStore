package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.ForumReplyBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/12
 * My mailbox is 1403241630@qq.com
 */

public class ForumReplyAdapter extends BaseAdapter{
    private Context context;
    private List<ForumReplyBean.replys> list;

    public ForumReplyAdapter(Context context) {
        this.context = context;
    }

    public void setForumReply(Context context, List<ForumReplyBean.replys> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size() ;
    }

    @Override
    public ForumReplyBean.replys getItem(int position) {
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
            viewHolder.iv_user_icon = (ImageView) convertView.findViewById(R.id.iv_user_icon);
            viewHolder.text_user_name = (TextView) convertView.findViewById(R.id.text_user_name);
            viewHolder.text_talk_time = (TextView) convertView.findViewById(R.id.text_talk_time);
            viewHolder.text_user_talk = (TextView) convertView.findViewById(R.id.text_user_talk);
            viewHolder.line_reply = (LinearLayout) convertView.findViewById(R.id.line_reply);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.line_reply.setVisibility(View.GONE);
        ForumReplyBean.replys replysList = list.get(position);
//        Picasso.with(context).load(replysList.getUserIcon()).into(viewHolder.iv_user_icon);
        viewHolder.text_user_name.setText(replysList.getUserName());
        viewHolder.text_talk_time.setText(replysList.getTalkTime());
        viewHolder.text_user_talk.setText(replysList.getUserTalk());
        return convertView;
    }
    class ViewHolder{
        ImageView iv_user_icon;
        TextView text_user_name,text_talk_time,text_user_talk;
        LinearLayout line_reply;
    }
}
