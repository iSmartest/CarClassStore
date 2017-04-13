package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.SectionBean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/12
 * My mailbox is 1403241630@qq.com
 */

public class SectionAdapter extends BaseAdapter{
    private List<SectionBean.forumSections> mList;
    private Context context;
    public void setSectionList(Context context, List<SectionBean.forumSections> mList) {
        this.context = context;
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public SectionBean.forumSections getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_section,null);
            viewHolder = new ViewHolder();
            viewHolder.mTextectionTitle = (TextView) convertView.findViewById(R.id.text_section_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SectionBean.forumSections forumSectionList = mList.get(position);
        viewHolder.mTextectionTitle.setText(forumSectionList.getSectionTile());
        return convertView;
    }



    class ViewHolder {
        TextView mTextectionTitle;
    }
}
