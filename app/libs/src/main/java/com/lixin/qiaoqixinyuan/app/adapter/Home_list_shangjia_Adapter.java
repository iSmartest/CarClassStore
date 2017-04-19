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

public class Home_list_shangjia_Adapter extends BaseAdapter {

    public void setShangjiamodel(List<Home_Bean.Shangjiamodel> shangjiamodel) {
        this.shangjiamodel = shangjiamodel;
    }

    private List<Home_Bean.Shangjiamodel> shangjiamodel;
    private Context context;

    @Override
    public int getCount() {
        return shangjiamodel == null ? 0 : shangjiamodel.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        ViewHolder vh = null;
        if (view == null) {
            view = View.inflate(context, R.layout.shangpin_list_item, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();

        }
        Home_Bean.Shangjiamodel shangjiamodel = this.shangjiamodel.get(i);
        ImageLoader.getInstance().displayImage(shangjiamodel.shangjiaimage, vh.iv_shangpin_list_icon, ImageLoaderUtil.DIO());
        vh.tv_shangpin_list_dianpu.setText(shangjiamodel.shangjianame);
        //ImageLoader.getInstance().displayImage(shangjiamodel.shangjiaicon, vh.iv_shangjie_logo, ImageLoaderUtil.DIO());
        vh.tv_shangpin_list_juli.setText(shangjiamodel.shangjiajuli);
        vh.tv_shangpin_list_message.setText(shangjiamodel.shangjiascribe);
        vh.tv_shangpin_list_price.setText("￥"+shangjiamodel.xianprice);
        vh.tv_shangpin_list_yuanjia.setText("原价"+shangjiamodel.yuanprice);
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
        public TextView tv_shangpin_list_title;
        public TextView tv_shangpin_list_juli;
        public TextView tv_shangpin_list_message;
        public TextView tv_shangpin_list_price;
        public TextView tv_shangpin_list_yuanjia;
        public TextView tv_shangpin_list_dianpu;
        public ImageView iv_shangpin_list_icon;
        //public ImageView iv_shangjie_logo;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_shangpin_list_title = (TextView) rootView.findViewById(R.id.tv_shangpin_list_title);
            this.tv_shangpin_list_juli = (TextView) rootView.findViewById(R.id.tv_shangpin_list_juli);
            this.tv_shangpin_list_message = (TextView) rootView.findViewById(R.id.tv_shangpin_list_message);
            this.tv_shangpin_list_price = (TextView) rootView.findViewById(R.id.tv_shangpin_list_price);
            this.tv_shangpin_list_yuanjia = (TextView) rootView.findViewById(R.id.tv_shangpin_list_yuanjia);
            this.tv_shangpin_list_dianpu = (TextView) rootView.findViewById(R.id.tv_shangpin_list_dianpu);
            this.iv_shangpin_list_icon= (ImageView) rootView.findViewById(R.id.iv_shangpin_list_icon);
            //this.iv_shangjie_logo= (ImageView) rootView.findViewById(R.id.iv_shangjie_logo);

        }

    }
}
