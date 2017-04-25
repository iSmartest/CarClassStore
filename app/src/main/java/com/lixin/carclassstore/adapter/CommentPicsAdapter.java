package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.squareup.picasso.Picasso;

/**
 * Created by 小火
 * Create time on  2017/4/20
 * My mailbox is 1403241630@qq.com
 */

public class CommentPicsAdapter extends BaseAdapter{
    private String[] commentPics;
    private Context context;
    public CommentPicsAdapter(String[] commentPics, Context context) {
        this.commentPics = commentPics;
        this.context = context;
    }

    @Override
    public int getCount() {
        return commentPics.length;
    }

    @Override
    public String[] getItem(int position) {
        return commentPics;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid,null);
            vh = new ViewHolder();
            vh.commentPics = (ImageView) convertView.findViewById(R.id.iv_day_kill_picture);
            vh.textNo01 = (TextView) convertView.findViewById(R.id.text_new_price);
            vh.textNo02 = (TextView) convertView.findViewById(R.id.text_old_price);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.textNo01.setVisibility(View.GONE);
        vh.textNo02.setVisibility(View.GONE);
        String[] tempPic = new String[commentPics.length];
        if (!TextUtils.isEmpty(tempPic[position])){
            Picasso.with(context).load(tempPic[position]).into(vh.commentPics);
        }
        return convertView;
    }
    class ViewHolder {
        ImageView  commentPics;
        TextView textNo01,textNo02;
    }
}
