package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.UsedCarList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/3/28
 * My mailbox is 1403241630@qq.com
 * 二手车列表Adapter
 */

public class UsedCarListAdapter extends BaseAdapter {

    private List<UsedCarList.carModelList> usedCarList;
    private Context context;
    public UsedCarListAdapter(Context context) {
        this.context = context;
    }

    public void setUsedCarBrandList(Context context, List<UsedCarList.carModelList> usedCarList) {
        this.context = context;
        this.usedCarList = usedCarList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {

        return usedCarList == null ? 0 : usedCarList.size();
    }

    @Override
    public Object getItem(int position) {

        return usedCarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_used_car_bran,null);
            mViewHolder = new ViewHolder();
            mViewHolder.iv_car_picture = (ImageView) convertView.findViewById(R.id.iv_car_picture);
            mViewHolder.text_used_car_age = (TextView) convertView.findViewById(R.id.text_used_car_age);
            mViewHolder.text_used_car_name = (TextView) convertView.findViewById(R.id.text_used_car_name);
            mViewHolder.text_used_car_mileage = (TextView) convertView.findViewById(R.id.text_used_car_mileage);
            mViewHolder.text_used_car_business = (TextView) convertView.findViewById(R.id.text_used_car_business);
            mViewHolder.text_used_car_price = (TextView) convertView.findViewById(R.id.text_used_car_price);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();

            UsedCarList.carModelList carModelList = usedCarList.get(position);
            String imag = carModelList.getCarIcon();
            if (TextUtils.isEmpty(imag)){
                mViewHolder.iv_car_picture.setImageResource(R.drawable.image_car_defult);
            }else Picasso.with(context).load(imag).into(mViewHolder.iv_car_picture);
            mViewHolder.text_used_car_name.setText(carModelList.getCarModel());
            mViewHolder.text_used_car_mileage.setText(carModelList.getCarRunKm());
            mViewHolder.text_used_car_age.setText("/" + carModelList.getCarBuyYear());
            mViewHolder.text_used_car_price.setText(carModelList.getCarNowPrice());
            switch (carModelList.getCarNowPrice()){
                case "0":
                    mViewHolder.text_used_car_business.setText("质保");
                   break;
                case "1":
                    mViewHolder.text_used_car_business.setText("非质保");
                    break;
            }
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv_car_picture;
        TextView text_used_car_name,text_used_car_mileage,text_used_car_age,
                text_used_car_business,text_used_car_price;
    }

}