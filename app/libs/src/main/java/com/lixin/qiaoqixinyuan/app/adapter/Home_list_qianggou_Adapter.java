package com.lixin.qiaoqixinyuan.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.bean.Home_Bean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class Home_list_qianggou_Adapter extends BaseAdapter {
    public void setHuodongmodel(List<Home_Bean.Huodongmodel> huodongmodel) {
        this.huodongmodel = huodongmodel;
    }

    private List<Home_Bean.Huodongmodel> huodongmodel;
    private Context context;

    @Override
    public int getCount() {
        return huodongmodel == null?0:huodongmodel.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        ViewHolder vh=null;
        if (view==null) {
            view = View.inflate(context, R.layout.home_list_qianggou_item, null);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }
        Home_Bean.Huodongmodel huodongmodel = this.huodongmodel.get(i);
        ImageLoader.getInstance().displayImage(huodongmodel.huodongimage,vh.iv_home_list_image, ImageLoaderUtil.DIO());
        vh.tv_home_list_message.setText(huodongmodel.huodongdescribe);
        vh.tv_home_list_title.setText(huodongmodel.huodongtitle);

        if (huodongmodel.huodongtype.equals("0")){
            vh.tv_home_list_time.setText("距离活动结束时间还有"+huodongmodel.huodongtime+"天");
        }else if (huodongmodel.huodongtype.equals("1")){
            vh.tv_home_list_time.setText("距离活动开始时间还有"+huodongmodel.huodongtime+"天");
            vh.tv_home_list_time.setTextColor(Color.GREEN);
        }else {
            vh.tv_home_list_time.setText("活动已结束");
        }
        return view;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView iv_home_list_image;
        public TextView tv_home_list_title;
        public TextView tv_home_list_message;
        public TextView tv_home_list_time;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_home_list_image = (ImageView) rootView.findViewById(R.id.iv_home_list_image);
            this.tv_home_list_title = (TextView) rootView.findViewById(R.id.tv_home_list_title);
            this.tv_home_list_message = (TextView) rootView.findViewById(R.id.tv_home_list_message);
            this.tv_home_list_time = (TextView) rootView.findViewById(R.id.tv_home_list_time);
        }

    }
}
