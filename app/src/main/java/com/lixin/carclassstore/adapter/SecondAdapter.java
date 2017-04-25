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
import com.lixin.carclassstore.bean.StoreDetailsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/24
 * My mailbox is 1403241630@qq.com
 */
public class SecondAdapter extends BaseAdapter{

    private List<StoreDetailsBean.shopCommoditys.commoditys> commoditys;
    private Context context;
    public SecondAdapter(List<StoreDetailsBean.shopCommoditys.commoditys> commoditys, Context context) {
        this.commoditys = commoditys;
        this.context = context;
    }

    @Override
    public int getCount() {
        return commoditys == null ? 0 : commoditys.size();
    }

    @Override
    public StoreDetailsBean.shopCommoditys.commoditys getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_store_shop,null);
            viewHolder = new ViewHolder();
            viewHolder.mShoreShopPicture = (ImageView) convertView.findViewById(R.id.iv_store_shop_picture);
            viewHolder.mStoreShopName = (TextView) convertView.findViewById(R.id.text_store_shop_name);
            viewHolder.mStoreShopPrice = (TextView) convertView.findViewById(R.id.text_store_shop_price);
            viewHolder.mStoreShopDec = (TextView) convertView.findViewById(R.id.text_store_shop_dec);
            viewHolder.mStoreShopSeller = (TextView) convertView.findViewById(R.id.text_store_shop_seller);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        StoreDetailsBean.shopCommoditys.commoditys mList = commoditys.get(position);
        if (TextUtils.isEmpty(mList.getCommodityIcon())){
            viewHolder.mShoreShopPicture.setImageResource(R.drawable.image_car_defult);
        }else {
            Picasso.with(context).load(mList.commodityIcon).into(viewHolder.mShoreShopPicture);
        }
        viewHolder.mStoreShopName.setText(mList.getCommodityTitle());
        viewHolder.mStoreShopPrice.setText("￥" + mList.getCommodityNewPrice());
        viewHolder.mStoreShopDec.setText(mList.getCommodityDescription());
        viewHolder.mStoreShopSeller.setText("销量：" + mList.getCommoditysellerNum());
        return convertView;
    }
    class ViewHolder {
        ImageView mShoreShopPicture,mAddShoppingCart;
        TextView mStoreShopName,mStoreShopPrice,mStoreShopDec,mStoreShopSeller;
    }
}
