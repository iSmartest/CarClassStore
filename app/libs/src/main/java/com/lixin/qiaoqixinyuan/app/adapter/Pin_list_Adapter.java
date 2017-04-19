package com.lixin.qiaoqixinyuan.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.bean.Ping_data_Bean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class Pin_list_Adapter extends BaseAdapter {
    public void setCarpooling(List<Ping_data_Bean.Carpoolinglist> carpooling) {
        this.carpooling = carpooling;
    }

    private Context context;
    private List<Ping_data_Bean.Carpoolinglist> carpooling;

    @Override
    public int getCount() {
        return carpooling == null ?0:carpooling.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.pin_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }
        Ping_data_Bean.Carpoolinglist carpoolinglist = carpooling.get(i);
        ImageLoader.getInstance().displayImage(carpoolinglist.carpoolingicon, holder.iv_pin_list_icon, ImageLoaderUtil.DIO());
        holder.tv_pin_list_name.setText(carpoolinglist.carpoolingname);
        holder.tv_pin_list_time.setText(carpoolinglist.carpoolingtime);
        if (carpoolinglist.carpoolingtype.equals("0")){
            holder.tv_pin_list_biaoqian.setText("车找人");
        }else {
            holder.tv_pin_list_biaoqian.setText("人找车");
        }
        holder.tv_pin_list_message.setText(carpoolinglist.carpoolingaddress + "，" + carpoolinglist.carpoolingdate + "，"
                + carpoolinglist.carpoolingnewstime + "，" + carpoolinglist.carpoolingphone + "" + carpoolinglist.carpoolingnote + "。");
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public CircleImageView iv_pin_list_icon;
        public TextView tv_pin_list_name;
        public TextView tv_pin_list_time;
        public TextView tv_pin_list_biaoqian;
        public TextView tv_pin_list_message;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pin_list_icon = (CircleImageView) rootView.findViewById(R.id.iv_pin_list_icon);
            this.tv_pin_list_name = (TextView) rootView.findViewById(R.id.tv_pin_list_name);
            this.tv_pin_list_time = (TextView) rootView.findViewById(R.id.tv_pin_list_time);
            this.tv_pin_list_biaoqian = (TextView) rootView.findViewById(R.id.tv_pin_list_biaoqian);
            this.tv_pin_list_message = (TextView) rootView.findViewById(R.id.tv_pin_list_message);
        }

    }
}
