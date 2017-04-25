package com.lixin.carclassstore.adapter;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.DecBean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/20
 * My mailbox is 1403241630@qq.com
 */

public class DecAdapter extends BaseAdapter{
    private  List<DecBean.parameterTypes> mList;
    private Context context;
    public DecAdapter(Context context) {
        this.context = context;
    }
    public void setDecList(Context context, List<DecBean.parameterTypes> mList) {
        this.context = context;
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public DecBean.parameterTypes getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dec,null);
            viewHolder = new ViewHolder();
            viewHolder.parameters = (TextView) convertView.findViewById(R.id.text_parameters);
            viewHolder.parameterTypes = (TextView) convertView.findViewById(R.id.text_parameter_types);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DecBean.parameterTypes parameterTypesList = mList.get(position);
        viewHolder.parameterTypes.setText(parameterTypesList.getParameterTypes() + ":");
        viewHolder.parameters.setText(parameterTypesList.getParameters());
        return convertView;
    }

    class ViewHolder {
        TextView parameterTypes,parameters;
    }

}
