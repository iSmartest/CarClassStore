package com.lixin.carclassstore.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.StoreDetailBean;

import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/30
 * My mailbox is 1403241630@qq.com
 */

public class StoreDetailAdapter extends BaseAdapter {
    private List<StoreDetailBean> storeDetailBeanList;
    private ModifyCountInterface modifyCountInterface;
    private Context context;
    public StoreDetailAdapter(Context context, List<StoreDetailBean> list) {
        this.context = context;
        this.storeDetailBeanList = list;
        notifyDataSetChanged();
    }
    public void setStoreDetailBean(List<StoreDetailBean> storeDetailBean){
        this.storeDetailBeanList = storeDetailBean;

    }
    @Override
    public int getCount() {
        return storeDetailBeanList.size();
    }

    @Override
    public StoreDetailBean getItem(int position) {
        return storeDetailBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_store_detail,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.iv_show_pic = (ImageView) convertView.findViewById(R.id.iv_show_pic1);
            viewHolder.tv_commodity_name = (TextView) convertView.findViewById(R.id.tv_commodity_name1);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price1);
            viewHolder.tv_fabric = (TextView) convertView.findViewById(R.id.tv_fabric1);
            viewHolder.tv_pants = (TextView) convertView.findViewById(R.id.tv_pants1);
            viewHolder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            final StoreDetailBean storeDetailBean = storeDetailBeanList.get(position);
            String image = storeDetailBean.getImageUrl();
//            ImageManager.imageLoader.displayImage(image,viewHolder.iv_show_pic, ImageManager.options3);
            viewHolder.tv_commodity_name.setText(storeDetailBean.getShoppingName());
            viewHolder.tv_fabric.setText("" + storeDetailBean.getSize());
            viewHolder.tv_pants.setText("销量" + storeDetailBean.getSalesvolume());
            viewHolder.tv_price.setText("￥" + storeDetailBean.getPrice());
            //删除弹窗
            viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alert = new AlertDialog.Builder(context).create();
                    alert.setTitle("操作提示");
                    alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                    alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    modifyCountInterface.childDelete(position);//删除 目前只是从item中移除
                                }
                            });
                    alert.show();
                }
            });
        }
        return convertView;
    }

    class ViewHolder{
        ImageView iv_show_pic;
        TextView tv_commodity_name,tv_price,tv_fabric,tv_pants,tv_delete;
    }

    //改变商品数量接口
    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }
    //改变数量的接口
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      组元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int position, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param position      组元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int position, View showCountView, boolean isChecked);

        /**
         * 删除子item
         *
         * @param position
         */
        void childDelete(int position);
    }
}
