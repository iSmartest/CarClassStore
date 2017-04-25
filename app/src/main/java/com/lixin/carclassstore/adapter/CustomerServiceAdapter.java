package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.CustomerServiceActivity;
import com.lixin.carclassstore.bean.CustomerServiceBean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/13
 * My mailbox is 1403241630@qq.com
 */

public class CustomerServiceAdapter extends BaseAdapter{
    private List<CustomerServiceBean.service> mList;
    private Context context;

    public CustomerServiceAdapter(Context context) {
        this.context = context;
    }
    public void setCustomerService(Context context,List<CustomerServiceBean.service> servicesList) {
        this.context = context;
        this.mList = servicesList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CustomerServiceBean.service getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_customer_service,null);
            viewHolder = new ViewHolder();
            viewHolder.serviceName = (TextView) convertView.findViewById(R.id.text_service_name);
            viewHolder.serviceQQ = (TextView) convertView.findViewById(R.id.text_service_qq);
            viewHolder.serviceWX = (TextView) convertView.findViewById(R.id.text_service_wx);
            viewHolder.serviceTelephone = (TextView) convertView.findViewById(R.id.text_service_phone);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CustomerServiceBean.service serviceList = mList.get(position);
        viewHolder.serviceName.setText(serviceList.getServiceName());
        viewHolder.serviceQQ.setText(serviceList.getServiceQQ());
        viewHolder.serviceWX.setText(serviceList.getServiceWX());
        viewHolder.serviceTelephone.setText(serviceList.getServiceTelephone());
        return convertView;
    }

    class ViewHolder {
        private TextView serviceName,serviceQQ,serviceWX,serviceTelephone;
    }

}
