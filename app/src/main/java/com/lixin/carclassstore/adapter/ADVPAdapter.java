package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.AdInfo;
import com.lixin.carclassstore.tools.ImageManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * 广告位自动跳转的ViewPager适配器。主要原理就是在原本的数据中，前端在添加一个最后一个元素，在末端添加一个第一个元素。
 */
public class ADVPAdapter extends PagerAdapter {
    private List<AdInfo> mList;
    private Context mContext;
    private ArrayList<ImageView> mCacheList;
    private View.OnClickListener mOnClick;

    private DisplayImageOptions options;
    public ADVPAdapter(Context context, List<AdInfo> list, View.OnClickListener onClick) {
        this.mList = list;
        mContext = context;
        mCacheList = new ArrayList<>();
        mOnClick = onClick;

        options = ImageManager.getOptions(R.drawable.image_fail_empty);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return (mList == null || mList.isEmpty()) ? 0 : mList.size() + 2;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        ImageView imageView = (ImageView) object;
        imageView.setOnClickListener(null);
        container.removeView(imageView);
        mCacheList.add(imageView);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView;
        if (mCacheList.isEmpty()) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = mCacheList.remove(0);
        }
        AdInfo item;
        if (position == 0) {
            item = mList.get(mList.size() - 1);
        } else {
            if (position == getCount() - 1) {
                item = mList.get(0);
            } else {
                item = mList.get(position - 1);
            }
        }

        if (mOnClick != null) {
            imageView.setTag(item);
            imageView.setOnClickListener(mOnClick);
        }
        container.addView(imageView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        ImageManager.imageLoader.displayImage(item.getImage(), imageView, options);

        return imageView;
    }

    public void update(List<AdInfo> newList) {
        if (newList != null)
            mList = newList;
        else
            mList = new ArrayList<>();

        notifyDataSetChanged();
    }
}
