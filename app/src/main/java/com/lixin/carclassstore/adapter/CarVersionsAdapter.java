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
import com.lixin.carclassstore.bean.CarSeries;
import com.squareup.picasso.Picasso;

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
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_car_version,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_car_picture = (ImageView) convertView.findViewById(R.id.iv_car_picture);
            viewHolder.text_new_car_name = (TextView) convertView.findViewById(R.id.text_new_car_name);
            viewHolder.text_new_car_price = (TextView) convertView.findViewById(R.id.text_new_car_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CarSeries.carVersionsList.getCarVersionInfo mList  = getCarVersionInfo.get(position);
        viewHolder.text_new_car_name.setText(mList.getCarVersionName());
        viewHolder.text_new_car_price.setText(mList.getCarPriceZone());
        if (TextUtils.isEmpty(mList.getCarIcon())){
            viewHolder.iv_car_picture.setImageResource(R.drawable.image_car_defult);
        }else
            Picasso.with(context).load(mList.getCarIcon()).into(viewHolder.iv_car_picture);
        return convertView;
    }
    class ViewHolder{
        ImageView iv_car_picture;
        TextView text_new_car_name,text_new_car_price;
    }
}
