package com.lixin.qiaoqixinyuan.app.adapter;

import android.content.Context;
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

public class Home_list_tieba_Adapter extends BaseAdapter {
    public void setTiebamodel(List<Home_Bean.Tiebamodel> tiebamodel) {
        this.tiebamodel = tiebamodel;
    }

    private List<Home_Bean.Tiebamodel> tiebamodel;
    private Context context;

    @Override
    public int getCount() {
        return tiebamodel == null ? 0 : tiebamodel.size();
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
        ViewHolder vh = null;
        if (view == null) {
            view = View.inflate(context, R.layout.tieba_list_item, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();

        }
        Home_Bean.Tiebamodel tiebamodel = this.tiebamodel.get(i);
        ImageLoader.getInstance().displayImage(tiebamodel.tiebaimage, vh.iv_tieba_image, ImageLoaderUtil.DIO());
        vh.tv_teiba_list_time.setText(tiebamodel.tiebatime);
        vh.tv_tieba_list_message.setText(tiebamodel.tiebascribe);
        vh.tv_tieba_list_nub.setText(tiebamodel.dianjiliang);
        vh.tv_tieba_list_read.setText(tiebamodel.pinglunliang);
        vh.tv_tieba_list_name.setText(tiebamodel.tiebausername);
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView iv_tieba_image;
        public TextView tv_tieba_list_name;
        public TextView tv_tieba_list_message;
        public TextView tv_teiba_list_time;
        public TextView tv_tieba_list_nub;
        public TextView tv_tieba_list_read;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_tieba_image = (ImageView) rootView.findViewById(R.id.iv_tieba_image);
            this.tv_tieba_list_name = (TextView) rootView.findViewById(R.id.tv_tieba_list_name);
            this.tv_tieba_list_message = (TextView) rootView.findViewById(R.id.tv_tieba_list_message);
            this.tv_teiba_list_time = (TextView) rootView.findViewById(R.id.tv_teiba_list_time);
            this.tv_tieba_list_nub = (TextView) rootView.findViewById(R.id.tv_tieba_list_nub);
            this.tv_tieba_list_read = (TextView) rootView.findViewById(R.id.tv_tieba_list_read);
        }

    }
}