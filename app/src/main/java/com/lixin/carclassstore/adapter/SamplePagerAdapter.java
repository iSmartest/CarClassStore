package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.Photo;
import com.lixin.carclassstore.photoview.PhotoView;
import com.lixin.carclassstore.photoview.PhotoViewAttacher;
import com.lixin.carclassstore.tools.ImageManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;


public class SamplePagerAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private static DisplayImageOptions options;
    private List<Photo> mPhotoList;
    private boolean isShow;
    private View.OnLongClickListener mLongClickListener;
    private View.OnClickListener mClickListener, mLookOriginalListener;

    public SamplePagerAdapter(Context context, List<Photo> list, boolean isShow, View.OnClickListener clickListener,
                              View.OnLongClickListener longClickListener,
                              View.OnClickListener lookOriginalListener) {
        options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.image_fail_empty)
                .showImageOnFail(R.drawable.image_fail_empty).resetViewBeforeLoading(true).cacheInMemory(true)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        inflater = LayoutInflater.from(context);
        mPhotoList = list;
        this.isShow = isShow;
        mLongClickListener = longClickListener;
        mClickListener = clickListener;
        mLookOriginalListener = lookOriginalListener;
    }

    @Override
    public int getCount() {
        return mPhotoList.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View imageLayout = inflater.inflate(R.layout.item_pager_image, container, false);
        PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image);
        final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
        TextView showOriginal = (TextView) imageLayout.findViewById(R.id.i_image_tv_look_original);

        Photo photo = mPhotoList.get(position);

        //当前不是原图或者是附件时不用显示查看原图按钮
        if (photo.isShowOriginal || !isShow) {
            showOriginal.setVisibility(View.GONE);
        } else {
            showOriginal.setVisibility(View.VISIBLE);
            showOriginal.setOnClickListener(mLookOriginalListener);
            showOriginal.setTag(position);
        }

        String uri = null;
        if (photo.isShowOriginal)
            uri = photo.path;
        else
            uri = photo.path2;

        if (mLongClickListener != null && !TextUtils.isEmpty(uri)) {
            imageView.setOnLongClickListener(mLongClickListener);
            imageView.setTag(uri);
        }
        imageView.setOnSingleClickListener(new PhotoViewAttacher.OnSingleClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null)
                    mClickListener.onClick(view);
            }
        });
        ImageManager.imageLoader.displayImage(uri, imageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                spinner.setVisibility(View.GONE);
            }
        });

        container.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void updateDatas(List<Photo> newList) {
        if (newList == null)
            newList = new ArrayList<Photo>();
        mPhotoList = newList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
