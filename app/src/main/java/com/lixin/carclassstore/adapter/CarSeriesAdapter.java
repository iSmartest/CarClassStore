package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    private List<CarSeries> carSeriesList;
    private Context context;
    public CarSeriesAdapter(Context context, List<CarSeries> list) {
        this.carSeriesList = list;
        this.context = context;
    }
    public void setCarSeriesList(List<CarSeries> carSeriesList) {
        this.carSeriesList = carSeriesList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_car_series,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_car_picture = (ImageView) convertView.findViewById(R.id.iv_car_picture);
            viewHolder.text_new_car_name = (TextView) convertView.findViewById(R.id.text_new_car_name);
            viewHolder.text_new_car_price = (TextView) convertView.findViewById(R.id.text_new_car_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            final CarSeries carSeries = (CarSeries) carSeriesList.get(position);
//            String image = carSeries.getImage();
            //获取类别默认图片
//            if (TextUtils.isEmpty(image)) {
//                image = KnowledgeTypeDBManager.getInstance().getTypeDefaultImg(carSeries.getCateId());
//
//            }
//            ImageManager.imageLoader.displayImage(image, viewHolder.iv_car_picture, ImageManager.options3);
//            viewHolder.text_new_car_name.setText(carSeries.getName());
//            viewHolder.text_new_car_price.setText(carSeries.getPrice());
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv_car_picture;
        TextView text_new_car_name ,text_new_car_price;
    }
}
