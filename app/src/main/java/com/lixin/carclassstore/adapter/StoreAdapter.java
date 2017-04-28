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

    private List<StoreBean.shop> storeBean;
    private Context context;

    public StoreAdapter(Context context) {
        this.context = context;
    }
    public void setStoreBeanList(Context context,List<StoreBean.shop> storeBean){
        this.context = context;
        this.storeBean = storeBean;
    }

    @Override
    public int getCount() {
        return storeBean == null ? 0 : storeBean.size();
    }

    @Override
    public StoreBean.shop getItem(int position) {
        return storeBean.get(position);
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
            viewHolder.iv_car_picture = (ImageView) convertView.findViewById(R.id.iv_car_picture1);
            viewHolder.text_sales_num = (TextView) convertView.findViewById(R.id.text_sales_num);
            viewHolder.text_store_name = (TextView) convertView.findViewById(R.id.text_store_name1);
            viewHolder.text_store_score = (TextView) convertView.findViewById(R.id.text_store_score);
            viewHolder.text_store_address = (TextView) convertView.findViewById(R.id.text_store_address1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            final StoreBean.shop mShopList = storeBean.get(position);
            String imag = mShopList.getShopIcon();
            Picasso.with(context).load(imag).placeholder(R.drawable.image_car_defult).into(viewHolder.iv_car_picture);
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
            storeBean = new ArrayList<>();
        else
            storeBean = newList;
        notifyDataSetChanged();
    }
}
