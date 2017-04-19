package com.lixin.qiaoqixinyuan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.activity.Huifu_pinglun_Activity;
import com.lixin.qiaoqixinyuan.app.bean.Pinglun_Bean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.view.MyGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class Pinglun_Adapter extends BaseAdapter {
    public void setMessage(List<Pinglun_Bean.Messagelist> pinglun) {
        this.pinglun = pinglun;
    }
    private Context context;
    private List<Pinglun_Bean.Messagelist> pinglun;

    public void setTiebaId(String tiebaId) {
        this.tiebaId = tiebaId;
    }

    private String tiebaId;
    @Override
    public int getCount() {
        return pinglun == null ? 0 : pinglun.size();
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
            view = View.inflate(context, R.layout.item_pinglun_list, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        final Pinglun_Bean.Messagelist messagelist = pinglun.get(i);
        ImageLoader.getInstance().displayImage(messagelist.messageusericon, vh.iv_pinglun_icon, ImageLoaderUtil.DIO());
        vh.iv_pinglun_message.setText(messagelist.messageusertext);
        vh.iv_pinglun_time.setText(messagelist.messageusertime);
        vh.iv_pinglun_name.setText(messagelist.messageid);
        PhotosAdapter photosAdapter = new PhotosAdapter(context,messagelist.messageimage);
        vh.grid_liuyan_image.setAdapter(photosAdapter);
        if (messagelist.huifu!=null) {
            for (int j = 0; j < messagelist.huifu.size(); j++) {
                View inflate = View.inflate(context, R.layout.item_textview, null);
                TextView tv_item_text = (TextView) inflate.findViewById(R.id.tv_item_text);
                tv_item_text.setText(messagelist.huifu.get(i).huifutext);
                vh.layout_huifu.addView(tv_item_text);
            }
        }
        vh.iv_pinglun_huifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Huifu_pinglun_Activity.class);
                intent.putExtra("messageid",messagelist.messageid);
                intent.putExtra("messagetype", "0");
                intent.putExtra("pingluntype", "0");
                intent.putExtra("orderNum", "0");
                intent.putExtra("shangjiaid", tiebaId);
                context.startActivity(intent);
            }
        });

        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView iv_pinglun_icon;
        public TextView iv_pinglun_name;
        public MyGridView grid_liuyan_image;
        public TextView iv_pinglun_message;
        public TextView iv_pinglun_time;
        public TextView iv_pinglun_huifu;
        public LinearLayout layout_huifu;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pinglun_icon = (ImageView) rootView.findViewById(R.id.iv_pinglun_icon);
            this.iv_pinglun_name = (TextView) rootView.findViewById(R.id.iv_pinglun_name);
            this.grid_liuyan_image = (MyGridView) rootView.findViewById(R.id.grid_liuyan_image);
            this.iv_pinglun_message = (TextView) rootView.findViewById(R.id.iv_pinglun_message);
            this.iv_pinglun_time = (TextView) rootView.findViewById(R.id.iv_pinglun_time);
            this.iv_pinglun_huifu = (TextView) rootView.findViewById(R.id.iv_pinglun_huifu);
            this.layout_huifu = (LinearLayout) rootView.findViewById(R.id.layout_huifu);
        }

    }
}
