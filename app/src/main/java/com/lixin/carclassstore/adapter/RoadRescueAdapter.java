package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.RoadRescue;

import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 */

public class RoadRescueAdapter extends BaseAdapter {
    private Context context;
    private List<RoadRescue> mList;


    public RoadRescueAdapter(Context context, List<RoadRescue> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public RoadRescue getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_road_rescue,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.iv_car_image = (ImageView) convertView.findViewById(R.id.iv_car_image);
            viewHolder.text_car_question = (TextView) convertView.findViewById(R.id.text_car_question);
            viewHolder.text_state = (TextView) convertView.findViewById(R.id.text_state);
            viewHolder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RoadRescue roadRescue = mList.get(position);
        viewHolder.text_time.setText(roadRescue.getDate());
        viewHolder.text_state.setText(roadRescue.getState());
        viewHolder.text_car_question.setText(roadRescue.getQuestion());
        return convertView;
    }
    class ViewHolder{
        ImageView iv_car_image;
        TextView text_car_question,text_state,text_time;
    }
}
