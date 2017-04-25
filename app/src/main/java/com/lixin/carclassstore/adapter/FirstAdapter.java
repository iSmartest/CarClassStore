package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.StoreDetailsBean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/24
 * My mailbox is 1403241630@qq.com
 */

public class FirstAdapter extends BaseAdapter{
    private Context context;
    private List<StoreDetailsBean.shopCommoditys> mList;
    public FirstAdapter(Context context) {
       this.context = context;
    }
    public void setFirst(Context context,List<StoreDetailsBean.shopCommoditys> mList){
        this.context = context;
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList == null ? 0: mList.size();
    }

    @Override
    public StoreDetailsBean.shopCommoditys getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_text,null);
            viewHolder = new ViewHolder();
            viewHolder.mTextStoreShop = (TextView) convertView.findViewById(R.id.text_store_shop);
            viewHolder.mListStoreShop = (ListView) convertView.findViewById(R.id.list_second_shop);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        StoreDetailsBean.shopCommoditys shopCommoditysList = mList.get(position);
        viewHolder.mTextStoreShop.setText(shopCommoditysList.getCommodityType());
        SecondAdapter commodityAdapter = new SecondAdapter(shopCommoditysList.commoditys,context);
        viewHolder.mListStoreShop.setAdapter(commodityAdapter);
        return convertView;
    }
    class ViewHolder{
        TextView mTextStoreShop;
        ListView mListStoreShop;
    }
}
