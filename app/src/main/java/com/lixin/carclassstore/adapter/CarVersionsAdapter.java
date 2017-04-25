package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.CarSeries;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/25
 * My mailbox is 1403241630@qq.com
 */

public class CarVersionsAdapter extends BaseAdapter{
    private List<CarSeries.carVersionsList.getCarVersionInfo> getCarVersionInfo;
    private Context context;
    public CarVersionsAdapter(List<CarSeries.carVersionsList.getCarVersionInfo> getCarVersionInfo, Context context) {
        this.getCarVersionInfo = getCarVersionInfo;
        this.context = context;
    }

    @Override
    public int getCount() {
        return getCarVersionInfo == null ? 0 : getCarVersionInfo.size() ;
    }

    @Override
    public CarSeries.carVersionsList.getCarVersionInfo getItem(int position) {
        return getCarVersionInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_car_version,null);
        }
        return null;
    }
}
