package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.OpinionBean;
import com.lixin.carclassstore.bean.ShopDetailsBean;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/4/20
 * My mailbox is 1403241630@qq.com
 */

public class OpinionAdapter extends BaseAdapter{
    private Context context;
    private List<OpinionBean.commodityCommentLists> commentList;

    public OpinionAdapter(Context context) {
        this.context = context;
    }
    public void setOpinionList(Context context, List<OpinionBean.commodityCommentLists> commentList) {
        this.context = context;
        this.commentList = commentList;
    }
    @Override
    public int getCount() {
        return commentList == null ? 0 : commentList.size();
    }

    @Override
    public OpinionBean.commodityCommentLists getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_opinion,null);
            viewHolder = new ViewHolder();
            viewHolder.criticIcon = (ImageView) convertView.findViewById(R.id.iv_critic_icon);
            viewHolder.opinionStar = (RatingBar) convertView.findViewById(R.id.rab_opinion_star);
            viewHolder.criticName = (TextView) convertView.findViewById(R.id.text_critic_name);
            viewHolder.opinionTime = (TextView) convertView.findViewById(R.id.text_opinion_time);
            viewHolder.opinionContent = (TextView) convertView.findViewById(R.id.text_opinion_content);
            viewHolder.buyTime = (TextView) convertView.findViewById(R.id.text_buy_time);
            viewHolder.opinionPicture = (GridView) convertView.findViewById(R.id.grid_opinion_picture);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        OpinionBean.commodityCommentLists mList = commentList.get(position);
        float starNum = Float.parseFloat(mList.commentStarNum);
        viewHolder.opinionStar.setRating(starNum);
        viewHolder.criticName.setText(mList.getUserName());
        viewHolder.opinionTime.setText(mList.getCommentTime());
        viewHolder.opinionContent.setText(mList.getUserComment());
        viewHolder.buyTime.setText(mList.getBuyTime());
        String uriIcon = mList.getUserIcon();
        if (TextUtils.isEmpty(uriIcon)){
            viewHolder.criticIcon.setImageResource(R.drawable.head_img_default);
        }else {
            Picasso.with(context).load(uriIcon).into(viewHolder.criticIcon);
        }
        String[] commentPics = new String[mList.getCommentPics().size()];
        for (int i = 0; i < mList.getCommentPics().size(); i++) {
            commentPics[i] = mList.getCommentPics().get(i);
        }
        CommentPicsAdapter commentPicsAdapter = new CommentPicsAdapter(commentPics,context);
        viewHolder.opinionPicture.setAdapter(commentPicsAdapter);
        return convertView;
    }
    class ViewHolder
    {
        ImageView criticIcon;
        TextView criticName,opinionTime,opinionContent,buyTime;
        GridView opinionPicture;
        RatingBar opinionStar;
    }

}
