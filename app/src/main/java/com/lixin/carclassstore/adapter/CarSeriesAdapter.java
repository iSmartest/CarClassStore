package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.CarSeries;

import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/27
 * My mailbox is 1403241630@qq.com
 */

public class CarSeriesAdapter extends BaseAdapter {
    private List<CarSeries.carVersionsList> carSeriesList;
    private Context context;
    public CarSeriesAdapter(Context context) {
        this.context = context;
    }
    public void setCarSeriesList(Context context,List<CarSeries.carVersionsList> carSeriesList) {
        this.context = context;
        this.carSeriesList = carSeriesList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return carSeriesList == null ? 0 : carSeriesList.size();
    }

    @Override
    public CarSeries.carVersionsList getItem(int position) {
        return carSeriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_car_series,null);
            viewHolder = new ViewHolder();
            viewHolder.mListCar = (ListView) convertView.findViewById(R.id.list_car);
            viewHolder.text_car_style_name = (TextView) convertView.findViewById(R.id.text_car_style_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            CarSeries.carVersionsList mList =  carSeriesList.get(position);
            viewHolder.text_car_style_name.setText(mList.getCarVersionName());
            CarVersionsAdapter carVersionsAdapter = new CarVersionsAdapter(mList.getGetCarVersionInfo(),context);
            viewHolder.mListCar.setAdapter(carVersionsAdapter);
        }
        return convertView;
    }
    class ViewHolder{
        ListView mListCar;
        TextView text_car_style_name;
    }
}
