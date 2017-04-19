package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.MyOrderBean;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by 小火
 * Create time on  2017/4/14
 * My mailbox is 1403241630@qq.com
 */

public class OrderlistAdapter extends BaseAdapter{
    JSONArray commodityArray;
    Context context;
    public OrderlistAdapter(JSONArray commodityArray, Context context){
        this.commodityArray = commodityArray;
        this.context = context;
    }
    @Override
    public int getCount() {
        return commodityArray.length();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_order_list_head,null);
            viewHolder = new ViewHolder();
            viewHolder.comm_title = (TextView) convertView.findViewById(R.id.comm_title);
            viewHolder.comm_price = (TextView) convertView.findViewById(R.id.comm_price);
            viewHolder.comm_number = (TextView) convertView.findViewById(R.id.comm_number);
            viewHolder.comm_total_price = (TextView) convertView.findViewById(R.id.comm_total_price);
            viewHolder.comm_icon = (ImageView) convertView.findViewById(R.id.comm_icon);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            viewHolder.comm_title.setText(commodityArray.getJSONObject(position).getString("commodityTitle"));
            viewHolder.comm_price.setText(commodityArray.getJSONObject(position).getString("commodityNewPrice"));
            viewHolder.comm_number.setText(commodityArray.getJSONObject(position).getString("commodityBuyNum"));
            int price = Integer.parseInt(commodityArray.getJSONObject(position).getString("commodityNewPrice"));
            int number = Integer.parseInt(commodityArray.getJSONObject(position).getString("commodityBuyNum"));
            int totalPrice = price * number;
            viewHolder.comm_total_price.setText("￥" + totalPrice);

            String url = commodityArray.getJSONObject(position).getString("commodityIcon");
            //暂时没有图片
//            Picasso.with(context).load(url).into(viewHolder.comm_icon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }
    private class ViewHolder{
        TextView comm_title;
        TextView comm_price;
        TextView comm_number;
        TextView comm_total_price;
        ImageView comm_icon;
    }
}
