package com.lixin.qiaoqixinyuan.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/25 0025.
 */

public class Home_glid_adapter extends BaseAdapter {
    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    List<String> title;

    public void setResorce(List<Integer> resorce) {
        this.resorce = resorce;
    }

    List<Integer> resorce;
    @Override
    public int getCount() {
        return title == null ? 0 : title.size();
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
        ViewHolder vh = null;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.home_glid_item, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.tv_glid.setText(title.get(i));
        vh.iv_glid.setImageResource(resorce.get(i));
        return view;
    }
    class ViewHolder {
        public View rootView;
        public ImageView iv_glid;
        public TextView tv_glid;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_glid = (ImageView) rootView.findViewById(R.id.iv_glid);
            this.tv_glid = (TextView) rootView.findViewById(R.id.tv_glid);
        }

    }
}
