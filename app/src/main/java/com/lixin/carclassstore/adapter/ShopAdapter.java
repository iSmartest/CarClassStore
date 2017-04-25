package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.ShopBean;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/4/19
 * My mailbox is 1403241630@qq.com
 */

public class ShopAdapter extends BaseAdapter{
    private Context context;
    private List<ShopBean.commoditys> mList;
    public ShopAdapter(Context context) {
       this.context = context;
    }
    public void setShopBeanList(Context context,List<ShopBean.commoditys> mList) {
        this.context = context;
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ShopBean.commoditys getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop, null);
            viewHolder = new ViewHolder();
            viewHolder.shopName = (TextView) convertView.findViewById(R.id.text_shop_title);
            viewHolder.shopDec = (TextView) convertView.findViewById(R.id.text_shop_dec);
            viewHolder.shopRecommend = (TextView) convertView.findViewById(R.id.text_shop_recommend);
            viewHolder.shopPrice = (TextView) convertView.findViewById(R.id.text_shop_price);
            viewHolder.shopSoldNum = (TextView) convertView.findViewById(R.id.text_shop_sold_num);
            viewHolder.shopCommentNum = (TextView) convertView.findViewById(R.id.text_comment_num01);
            viewHolder.shopPicture = (ImageView) convertView.findViewById(R.id.iv_shop_picture);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ShopBean.commoditys commoditysList = mList.get(position);
        Log.i("getView", "getView: " + commoditysList.getCommodityDescription());
        if (TextUtils.isEmpty(commoditysList.getCommodityIcon())){
            viewHolder.shopPicture.setImageResource(R.drawable.image_fail_empty);
        }else {
            Picasso.with(context).load(commoditysList.getCommodityIcon()).into(viewHolder.shopPicture);
        }

        viewHolder.shopName.setText(commoditysList.getCommodityTitle());
        viewHolder.shopDec.setText(commoditysList.getCommodityDescription());
        viewHolder.shopPrice.setText(commoditysList.getCommodityNewPrice());
        viewHolder.shopSoldNum.setText("已售" + commoditysList.getCommoditysellerNum() + "件");
        viewHolder.shopCommentNum.setText(commoditysList.getCommodityCommendNum() + "人评论");
//        if (commoditysList.getCommodityRecommend().equals("0")) {
//            viewHolder.shopRecommend.setVisibility(View.VISIBLE);
//            viewHolder.shopRecommend.setText("推荐");
//        } else{
//            viewHolder.shopRecommend.setVisibility(View.GONE);
//        }
        return convertView;
    }
    class ViewHolder {
        TextView shopName,shopDec,shopRecommend,shopPrice,shopSoldNum,shopCommentNum;
        ImageView shopPicture;
    }
}
