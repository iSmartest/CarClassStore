package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.CarFilesActivity;
import com.lixin.carclassstore.activity.CarModelActivity;
import com.lixin.carclassstore.activity.CarSeriesActivity;
import com.lixin.carclassstore.activity.NewCarDetailsActivity;
import com.lixin.carclassstore.activity.UsedCarListActivity;
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
    private String flag;
    private String carStyle;
    public CarSeriesAdapter(Context context) {
        this.context = context;
    }
    public void setCarSeriesList(Context context,List<CarSeries.carVersionsList> carSeriesList,String flag,String carStyle) {
        this.context = context;
        this.carSeriesList = carSeriesList;
        this.flag = flag;
        this.carStyle = carStyle;
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
            final CarSeries.carVersionsList mList =  carSeriesList.get(position);
            viewHolder.text_car_style_name.setText(mList.getCarVersionName());
            CarVersionsAdapter carVersionsAdapter = new CarVersionsAdapter(mList.getGetCarVersionInfo(),context);
            viewHolder.mListCar.setAdapter(carVersionsAdapter);
            viewHolder.mListCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (flag.equals("1")){
                        Intent intent = new Intent(context,CarModelActivity.class);
                        intent.putExtra("carVersionId",mList.getGetCarVersionInfo().get(position).getCarVersionId());
                        intent.putExtra("carName",mList.getGetCarVersionInfo().get(position).getCarVersionName());
                        intent.putExtra("carIcon",mList.getGetCarVersionInfo().get(position).getCarIcon());
                        context.startActivity(intent);
                    }
                    if (flag.equals("2")){
                        Intent intent = new Intent(context,NewCarDetailsActivity.class);
                        intent.putExtra("carVersionId",mList.getGetCarVersionInfo().get(position).getCarVersionId());
                        intent.putExtra("carName",mList.getGetCarVersionInfo().get(position).getCarVersionName());
                        intent.putExtra("carPrice",mList.getGetCarVersionInfo().get(position).getCarPriceZone());
                        context.startActivity(intent);
                    }
                    if (flag.equals("3")){
                        Intent intent = new Intent(context,UsedCarListActivity.class);
                        intent.putExtra("carVersionId",mList.getGetCarVersionInfo().get(position).getCarVersionId());
                        intent.putExtra("carName",mList.getGetCarVersionInfo().get(position).getCarVersionName());
                        intent.putExtra("carIcon",mList.getGetCarVersionInfo().get(position).getCarIcon());
                        context.startActivity(intent);
                    }
                    if (flag.equals("4")){
                        Intent intent = new Intent();
                        intent.setClass(context,CarFilesActivity.class);
                        intent.putExtra("carStyle",carStyle);
                        intent.putExtra("carName",mList.getGetCarVersionInfo().get(position).getCarVersionName());
                        intent.putExtra("carIcon",mList.getGetCarVersionInfo().get(position).getCarIcon());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        if(CarSeriesActivity.class.isInstance(context)){
                            CarSeriesActivity activity = (CarSeriesActivity)context;
                            activity.finish();
                        }
                        context.startActivity(intent);
                    }
                }
            });
        }
        return convertView;
    }
    class ViewHolder{
        ListView mListCar;
        TextView text_car_style_name;
    }
}
