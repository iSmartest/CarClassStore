package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.ContentBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/18
 * My mailbox is 1403241630@qq.com
 */

public class GridAdapter extends BaseAdapter{
    Context context;
    List<ContentBean.commoditysList.commoditys> commoditys;
    public GridAdapter(Context context) {
        this.context = context;
    }

    public void setGrid(List<ContentBean.commoditysList.commoditys> commoditys) {
       this.commoditys = commoditys;
    }
    @Override
    public int getCount() {
        return commoditys == null ? 0 : commoditys.size();
    }

    @Override
    public ContentBean.commoditysList.commoditys getItem(int position) {
        return commoditys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_day_kill_picture = (ImageView) convertView.findViewById(R.id.iv_day_kill_picture);
            viewHolder.text_new_price = (TextView) convertView.findViewById(R.id.text_new_price);
            viewHolder.text_old_price = (TextView) convertView.findViewById(R.id.text_old_price);
            convertView.setTag(viewHolder);
            convertView.setPadding(15, 4, 15, 4);  //每格的间距
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ContentBean.commoditysList.commoditys commoditysList = commoditys.get(position);
        Picasso.with(context).load(commoditysList.commodityIcon);
        viewHolder.text_new_price.setText(commoditysList.commodityNewPrice);
        viewHolder.text_old_price.setText(commoditysList.commodityOriginalPrice);
        return convertView;
    }
    class ViewHolder{
        ImageView iv_day_kill_picture;
        TextView text_new_price,text_old_price;
    }
}
