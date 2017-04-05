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

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/3/30
 * My mailbox is 1403241630@qq.com
 */

public class StoreAdapter extends BaseAdapter{

    private List<StoreBean> storeBeanList;
    private Context context;

    public StoreAdapter(Context context, List<StoreBean> list) {
        this.context = context;
        this.storeBeanList = list;
    }
    public void setStoreBeanList(List<StoreBean> storeBean){
        this.storeBeanList = storeBean;
    }

    @Override
    public int getCount() {
        return storeBeanList.size();
    }

    @Override
    public StoreBean getItem(int position) {
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
            final StoreBean storeBean = (StoreBean) storeBeanList.get(position);
            String imag = storeBean.getImage();
//            ImageManager.imageLoader.displayImage(imag,mViewHolder.iv_car_picture,ImageManager.options3);
            viewHolder.text_sales_num.setText("销量" + storeBean.getNum());
            viewHolder.text_store_name.setText(storeBean.getTitle());
            viewHolder.text_store_score.setText(storeBean.getScore());
            viewHolder.text_store_address.setText(storeBean.getAddress());
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv_car_picture;
        TextView text_store_name,text_store_score,text_sales_num,text_store_address;
    }
}
