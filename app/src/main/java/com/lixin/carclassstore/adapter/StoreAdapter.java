package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.StoreBean;
import com.lixin.carclassstore.tools.ImageManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/3/30
 * My mailbox is 1403241630@qq.com
 */

public class StoreAdapter extends BaseAdapter{

    private List<StoreBean.shop> storeBeanList;
    private Context context;

    public StoreAdapter(Context context) {
        this.context = context;
    }
    public void setStoreBeanList(List<StoreBean.shop> storeBean){
        this.storeBeanList = storeBean;
    }

    @Override
    public int getCount() {
        return storeBeanList == null ? 0 : storeBeanList.size();
    }

    @Override
    public StoreBean.shop getItem(int position) {
        return storeBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder ;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_store,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.iv_car_picture = (ImageView) convertView.findViewById(R.id.iv_car_picture);
            viewHolder.text_sales_num = (TextView) convertView.findViewById(R.id.text_sales_num);
            viewHolder.text_store_name = (TextView) convertView.findViewById(R.id.text_store_name);
            viewHolder.text_store_score = (TextView) convertView.findViewById(R.id.text_store_score);
            viewHolder.text_store_address = (TextView) convertView.findViewById(R.id.text_store_address);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            final StoreBean.shop mShopList = storeBeanList.get(position);
            String imag = mShopList.getShopIcon();
            Picasso.with(context).load(imag).into(viewHolder.iv_car_picture);
            viewHolder.text_sales_num.setText("销量" + mShopList.getSellerNum());
            viewHolder.text_store_name.setText(mShopList.getShopName());
            viewHolder.text_store_score.setText(mShopList.getShopCommentNum());
            viewHolder.text_store_address.setText(mShopList.getShopLocaltion());
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv_car_picture;
        TextView text_store_name,text_store_score,text_sales_num,text_store_address;
    }
    public void updateList(List<StoreBean.shop> newList) {
        if (newList == null)
            storeBeanList = new ArrayList<>();
        else
            storeBeanList = newList;
        notifyDataSetChanged();
    }
}
