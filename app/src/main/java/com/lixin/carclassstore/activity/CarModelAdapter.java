package com.lixin.carclassstore.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.CarModel;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/25
 * My mailbox is 1403241630@qq.com
 */

public class CarModelAdapter extends BaseAdapter{
    private Context context;
    private List<CarModel.carModelList> mList;
    public void setModelList(Context context, List<CarModel.carModelList> mList) {
        this.context = context;
        this.mList = mList;
    }
    public CarModelAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CarModel.carModelList getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_car_model,null);
            viewHolder = new ViewHolder();
            viewHolder.text_car_model = (TextView) convertView.findViewById(R.id.text_car_model);
            convertView.setTag(viewHolder);
        }else {
           viewHolder = (ViewHolder) convertView.getTag();
        }
        CarModel.carModelList carModelList = mList.get(position);
        viewHolder.text_car_model.setText(carModelList.getCarModel());
        return convertView;
    }
    class ViewHolder {
        TextView text_car_model;
    }
}
