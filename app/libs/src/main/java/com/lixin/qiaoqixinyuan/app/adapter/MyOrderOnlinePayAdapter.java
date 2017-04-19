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

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyOrderOnlinePayAdapter
 * 类描述：我的订单线上支付适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/4 14:53
 */

public class MyOrderOnlinePayAdapter extends BaseAdapter {
    private Context context;
    public List<MyorderBean.OrderBean> ordersList = new ArrayList<>();

    public MyOrderOnlinePayAdapter(Context context, List<MyorderBean.OrderBean> ordersList) {
        super();
        this.context = context;
        this.ordersList = ordersList;
    }

    @Override
    public int getCount() {
        return ordersList.size();
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
            view = View.inflate(context, R.layout.item_prlv_order, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(ordersList.get(i).shopIcon,
                holder.iv_icon, ImageLoaderUtil.DIO());
        holder.tv_name.setText(ordersList.get(i).shopName);
        holder.tv_num.setText(ordersList.get(i).orderproductnum);
        holder.tv_allprice.setText(ordersList.get(i).orderprice);
        holder.tv_order_num.setText(ordersList.get(i).ordernum);
        holder.tv_time.setText(ordersList.get(i).adTime);
        if (ordersList.get(i).cancelorder.equals("1")) {
            holder.tv_cencle.setVisibility(View.GONE);
        } else {
            holder.tv_cencle.setVisibility(View.VISIBLE);
        }
        if (ordersList.get(i).deliverytype.equals("0")) {
            holder.tv_sending.setText("未送货");
            holder.tv_pay.setVisibility(View.GONE);
        } else {
            holder.tv_sending.setText("已送货");
            holder.tv_pay.setVisibility(View.VISIBLE);
        }
        return view;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_num;
        public TextView tv_allprice;
        public TextView tv_order_num;
        public TextView tv_time;
        public TextView tv_cencle;
        public TextView tv_sending;
        public TextView tv_pay;
        public TextView tv_complete;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_icon = (ImageView) rootView.findViewById(R.id.iv_icon);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.tv_allprice = (TextView) rootView.findViewById(R.id.tv_allprice);
            this.tv_order_num = (TextView) rootView.findViewById(R.id.tv_order_num);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_cencle = (TextView) rootView.findViewById(R.id.tv_cencle);
            this.tv_sending = (TextView) rootView.findViewById(R.id.tv_sending);
            this.tv_pay = (TextView) rootView.findViewById(R.id.tv_pay);
            this.tv_complete = (TextView) rootView.findViewById(R.id.tv_complete);
        }

    }
}

