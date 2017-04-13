package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.NewsAndFocusBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/11
 * My mailbox is 1403241630@qq.com
 */

public class FristNewsAdapter extends BaseAdapter {
    private Context context;
    private  List<NewsAndFocusBean.newsList> list;
    public void setNewsList(Context context, List<NewsAndFocusBean.newsList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {

        return list == null ? 0 : list.size();
    }

    @Override
    public NewsAndFocusBean.newsList getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news,null);
            viewHolder = new ViewHolder();
            viewHolder.newsPicture = (ImageView) convertView.findViewById(R.id.iv_news_picture);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.text_news_title);
            viewHolder.newsTime = (TextView) convertView.findViewById(R.id.text_news_time);
            viewHolder.newsContent = (TextView) convertView.findViewById(R.id.text_news_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NewsAndFocusBean.newsList newsList = list.get(position);

//        Picasso.with(context).load(newsList.getForumIcon()).into(viewHolder.newsPicture);
        viewHolder.newsTitle.setText(newsList.getForumTitle());
        viewHolder.newsContent.setText(newsList.getForumDetail());
        viewHolder.newsTime.setText(newsList.getForumTime());
        return convertView;
    }
    class ViewHolder{
        ImageView newsPicture;
        TextView newsTitle,newsTime,newsContent;

    }
}
