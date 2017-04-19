package com.lixin.qiaoqixinyuan.app.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.bean.MyorderBean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan_Merchant
 * 类名称：OrderGoodsAdapter
 * 类描述：订单物品适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/7 11:36
 */

public class OrderGoodsAdapter extends BaseAdapter {
    private Context context;

    public void setProductList(List<MyorderBean.OrderBean.ProductBean> productList) {
        this.productList = productList;
    }

    private List<MyorderBean.OrderBean.ProductBean> productList;
    public OrderGoodsAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return productList==null?0:productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.item_lv_order_goods, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(productList.get(i).productImage, holder.iv_icon, ImageLoaderUtil.DIO());
        holder.tv_name.setText(productList.get(i).productName);
        holder.tv_count.setText(productList.get(i).productNum);
        holder.tv_price.setText(productList.get(i).productprice);
        return view;
    }
    public static class ViewHolder {
        public View rootView;
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_count;
        public TextView tv_price;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_icon = (ImageView) rootView.findViewById(R.id.iv_icon);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_count = (TextView) rootView.findViewById(R.id.tv_count);
            this.tv_price = (TextView) rootView.findViewById(R.id.tv_price);
        }

    }
}
