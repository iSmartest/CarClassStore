package com.lixin.qiaoqixinyuan.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.bean.ObtainselfdataBean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan_Merchant
 * 类名称：PhotosAdapter
 * 类描述：八张图片的gridview适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/8 14:35
 */

public class PhotosAdapter extends BaseAdapter {
    private final int dwidth;
    private final int dheight;
    private Context context;
    private List<ObtainselfdataBean.Obtainselfdata.MydataImagesBean> mydataImages=new ArrayList<>();

    public PhotosAdapter(Context context,List<ObtainselfdataBean.Obtainselfdata.MydataImagesBean> mydataImages) {
        super();
        this.context = context;
        this.mydataImages=mydataImages;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        dwidth = wm.getDefaultDisplay().getWidth();
        dheight = wm.getDefaultDisplay().getHeight();
    }

    @Override
    public int getCount() {
        return mydataImages.size();
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
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_gv_photos, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ViewGroup.LayoutParams params = holder.iv_image.getLayoutParams();
        params.width = (dwidth - 20) / 4;
        params.height = (dwidth - 20) / 4;
        holder.iv_image.setLayoutParams(params);
        ImageLoader.getInstance().displayImage(mydataImages.get(i).mydataImage,holder.iv_image, ImageLoaderUtil.DIO());
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView iv_image;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_image = (ImageView) rootView.findViewById(R.id.iv_image);
        }

    }
}
