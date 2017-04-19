package com.lixin.qiaoqixinyuan.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.bean.Shangjia_shangpin_search_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Shangjialiebiao_Bean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class Shangpin_list_Adapter extends BaseAdapter {
    public void setGoodslist(List<Shangjia_shangpin_search_Bean.Goodslist> goodslist) {
        this.goodslist = goodslist;
    }

    private Context context;
    private List<Shangjia_shangpin_search_Bean.Goodslist> goodslist;

    @Override
    public int getCount() {
        return goodslist == null ? 0 : goodslist.size();
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
            view = View.inflate(context, R.layout.shangpin_list_item, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
            Shangjia_shangpin_search_Bean.Goodslist goodslist = this.goodslist.get(i);
            ImageLoader.getInstance().displayImage(goodslist.goodsimage, vh.iv_shangpin_list_icon, ImageLoaderUtil.DIO());
            vh.tv_shangpin_list_dianpu.setText(goodslist.shangjianame);
            vh.tv_shangpin_list_title.setText(goodslist.shangpinname);
            vh.tv_shangpin_list_juli.setText(goodslist.shangjiajuli);
            vh.tv_shangpin_list_message.setText(goodslist.shangjiascribe);
            vh.tv_shangpin_list_price.setText(goodslist.xianprice);
            vh.tv_shangpin_list_yuanjia.setText(goodslist.yuanprice);
        }
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView iv_shangpin_list_icon;
        public TextView tv_shangpin_list_title;
        public TextView tv_shangpin_list_juli;
        public TextView tv_shangpin_list_message;
        public TextView tv_shangpin_list_price;
        public TextView tv_shangpin_list_yuanjia;
        //public ImageView iv_shangjie_logo;
        public TextView tv_shangpin_list_dianpu;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_shangpin_list_icon = (ImageView) rootView.findViewById(R.id.iv_shangpin_list_icon);
            this.tv_shangpin_list_title = (TextView) rootView.findViewById(R.id.tv_shangpin_list_title);
            this.tv_shangpin_list_juli = (TextView) rootView.findViewById(R.id.tv_shangpin_list_juli);
            this.tv_shangpin_list_message = (TextView) rootView.findViewById(R.id.tv_shangpin_list_message);
            this.tv_shangpin_list_price = (TextView) rootView.findViewById(R.id.tv_shangpin_list_price);
            this.tv_shangpin_list_yuanjia = (TextView) rootView.findViewById(R.id.tv_shangpin_list_yuanjia);
            //this.iv_shangjie_logo = (ImageView) rootView.findViewById(R.id.iv_shangjie_logo);
            this.tv_shangpin_list_dianpu = (TextView) rootView.findViewById(R.id.tv_shangpin_list_dianpu);
        }

    }
}
