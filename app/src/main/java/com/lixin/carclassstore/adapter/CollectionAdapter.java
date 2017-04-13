package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.ForumReplyBean;
import com.lixin.carclassstore.bean.ShoppingCollectionFootBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class CollectionAdapter extends BaseAdapter {
    private Context context;
    private List<ShoppingCollectionFootBean.commoditys> list;
    public void setCollection(List<ShoppingCollectionFootBean.commoditys> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public ShoppingCollectionFootBean.commoditys getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_store_detail, null);
            vh = new ViewHolder();
            vh.iv_show_pic1 = (ImageView) convertView.findViewById(R.id.iv_show_pic1);
            vh.tv_commodity_name1 = (TextView) convertView.findViewById(R.id.tv_commodity_name1);
            vh.tv_fabric1 = (TextView) convertView.findViewById(R.id.tv_fabric1);
            vh.tv_pants1 = (TextView) convertView.findViewById(R.id.tv_pants1);
            vh.tv_price1 = (TextView) convertView.findViewById(R.id.tv_price1);
            vh.tv_delete1 = (TextView) convertView.findViewById(R.id.tv_delete1);
            convertView.setTag(vh);
        } else
            vh = (ViewHolder) convertView.getTag();

        ShoppingCollectionFootBean.commoditys commoditysList = list.get(position);
        Picasso.with(context).load(commoditysList.getCommodityIcon());
        vh.tv_commodity_name1.setText(commoditysList.getCommodityTitle());
//        vh.tv_fabric1.setText(commoditysList.getAuthor());
        vh.tv_pants1.setText(commoditysList.getCommoditysellerNum());
        vh.tv_price1.setText(commoditysList.getCommodityCommendNum());
        return convertView;
    }

    class ViewHolder {
        ImageView iv_show_pic1;
        TextView tv_commodity_name1, tv_fabric1, tv_pants1, tv_price1,tv_delete1;
    }


    public void updateList(List<ShoppingCollectionFootBean.commoditys> mList) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list = mList;
        notifyDataSetChanged();
    }
}
