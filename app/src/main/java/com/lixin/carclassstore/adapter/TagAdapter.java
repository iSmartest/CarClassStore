package com.lixin.carclassstore.adapter;

import android.view.View;

import com.lixin.carclassstore.view.Attribute;
import com.lixin.carclassstore.view.FlowLayout;
import com.lixin.carclassstore.view.TagFlowLayout;

import java.util.HashSet;

public abstract class TagAdapter<T> {
    private Attribute mTagDatas;
    private TagFlowLayout mOnDataChangedListener;
    private HashSet<Integer> mCheckedPosList = new HashSet<>();

    public TagAdapter(Attribute ab) {
        mTagDatas = ab;
    }


    public static interface OnDataChangedListener {
        void onChanged();
    }

    public void setOnDataChangedListener(TagFlowLayout tagFlowLayout) {
        mOnDataChangedListener = tagFlowLayout;
    }

    public void setSelectedList(int... pos) {
        for (int i = 0; i < pos.length; i++)
            mCheckedPosList.add(pos[i]);
        notifyDataChanged();
    }

    public HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }

    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.aliasName.size();
    }

    public void notifyDataChanged() {
        mOnDataChangedListener.onChanged();
    }

    public Attribute getItem(int position) {
        return mTagDatas;
    }

    public abstract View getView(FlowLayout parent, int position, Attribute t);

}