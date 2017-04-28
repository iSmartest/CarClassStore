package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.NewCarDetails;

import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/4/26
 * My mailbox is 1403241630@qq.com
 */

public class NewCarAdapter extends BaseAdapter{
    private Context context;
    private List<NewCarDetails.salesCars> mList;
    public NewCarAdapter(Context context) {
        this.context = context;
    }
    public void setNewCarList(Context context, List<NewCarDetails.salesCars> mList) {
        this.context = context;
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size() ;
    }

    @Override
    public NewCarDetails.salesCars getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_new_car,null);
            viewHolder = new ViewHolder();
            viewHolder.btn_wen_dijia = (Button) convertView.findViewById(R.id.btn_wen_dijia);
            viewHolder.linear_car_model = (LinearLayout) convertView.findViewById(R.id.linear_car_model);
            viewHolder.car_model = (TextView) convertView.findViewById(R.id.car_model);
            viewHolder.text_cankao_price = (TextView) convertView.findViewById(R.id.text_cankao_price);
            viewHolder.text_zhidao_price = (TextView) convertView.findViewById(R.id.text_zhidao_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final NewCarDetails.salesCars salesCarList = mList.get(position);
        viewHolder.car_model.setText(salesCarList.getCarModel());
        viewHolder.text_cankao_price.setText(salesCarList.getCarNewPrice());
        viewHolder.text_zhidao_price.setText(salesCarList.getCarFactoryPrice());
        viewHolder.btn_wen_dijia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:" + salesCarList.get));
//                context.startActivity(intent);
            }
        });
        viewHolder.linear_car_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                Intent intent = new Intent(context,NewCarModelDetailsActivitry.class);
//                intent.putExtra("carId",salesCarList.getCarId());
//                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder {
        LinearLayout linear_car_model;
        TextView text_zhidao_price,text_cankao_price,car_model;
        Button btn_wen_dijia;
    }
}
