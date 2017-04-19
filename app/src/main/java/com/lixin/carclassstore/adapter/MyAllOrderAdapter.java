package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;

import com.lixin.carclassstore.R;


import org.json.JSONArray;
import org.json.JSONException;


/**
 * Created by 小火
 * Create time on  2017/4/13
 * My mailbox is 1403241630@qq.com
 */

public class MyAllOrderAdapter extends BaseAdapter{
    JSONArray ordersArray;
    Context context;
    public MyAllOrderAdapter( Context context){
        this.context = context;
    }
    public void setCollection(JSONArray ordersArray) {
        this.ordersArray = ordersArray;
    }
    @Override
    public int getCount() {
        return ordersArray.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_order_list_content,null);
            viewHolder.order_number = (TextView) convertView.findViewById(R.id.order_number);
            viewHolder.order_state = (TextView) convertView.findViewById(R.id.order_state);
            viewHolder.total_price = (TextView) convertView.findViewById(R.id.total_price);
            viewHolder.commodity_lv = (ListView) convertView.findViewById(R.id.commodity_lv);

            viewHolder.bt_pay = (Button) convertView.findViewById(R.id.bt_pay);
            viewHolder.bt_delete = (Button) convertView.findViewById(R.id.bt_delete);
            //这两个按钮肯定是变化的，没付款状态显示的是这，付款之后肯定变成了别的入口，我不清楚你的具体情况，也不好做处理
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            viewHolder.order_number.setText(ordersArray.getJSONObject(position).getString("orderId"));

            viewHolder.total_price.setText(ordersArray.getJSONObject(position).getString("oderTotalPrice"));

            JSONArray commArray = ordersArray.getJSONObject(position).getJSONArray("orderCommodity");
            OrderlistAdapter commodityAdapter = new OrderlistAdapter(commArray,context);
            viewHolder.commodity_lv.setAdapter(commodityAdapter);
            switch (ordersArray.getJSONObject(position).getString("orderState")){
                case "1":
                    viewHolder.order_state.setText("未支付");
                    break; case "2":
                    viewHolder.order_state.setText("未收货");
                    break; case "3":
                    viewHolder.order_state.setText("未评价");
                    break; case "5":
                    viewHolder.order_state.setText("已完成");
                    break;
                //这个地方你的项目要显示什么文字我不清楚，我只是纯粹按照你json标签写的，你自己看着改
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }



    private class ViewHolder{
        TextView order_number;
        TextView order_state;
        TextView total_price;
        ListView commodity_lv;
        Button bt_pay;
        Button bt_delete;
    }
}
