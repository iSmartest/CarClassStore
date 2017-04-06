package com.lixin.carclassstore.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.MyReleaseBean;

import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 */

public class MyReleaseAdapter extends BaseAdapter {
    private Context context;
    private ShoppingCartAdapter.CheckInterface checkInterface;
    private ShoppingCartAdapter.ModifyCountInterface modifyCountInterface;
    private List<MyReleaseBean> myReleaseBeanList;
    public MyReleaseAdapter(Context context) {
        this.context = context;
    }

    public void setMyReleaseBean(List<MyReleaseBean> myReleaseBeanList) {
        this.myReleaseBeanList = myReleaseBeanList;
        notifyDataSetChanged();
    }
    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(ShoppingCartAdapter.CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 改变商品数量接口
     *
     * @param modifyCountInterface
     */
    public void setModifyCountInterface(ShoppingCartAdapter.ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    @Override
    public int getCount() {
        return myReleaseBeanList == null ? 0 : myReleaseBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return myReleaseBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyReleaseAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_release, parent, false);
            holder = new MyReleaseAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyReleaseAdapter.ViewHolder) convertView.getTag();
        }
        final MyReleaseBean myReleaseBean = myReleaseBeanList.get(position);
        holder.text_comment_content.setText(myReleaseBean.getContent());
        holder.text_comment_num.setText(myReleaseBean.getNum());
        holder.text_comment_time.setText( myReleaseBean.getTime());
        //删除弹窗
        holder.text_delete_comment.setOnClickListener(new View.OnClickListener() {
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
        return convertView;
    }

    //初始化控件
    class ViewHolder {

        TextView text_comment_content, text_comment_num, text_comment_time,text_delete_comment;

        public ViewHolder(View itemView) {
            text_comment_content = (TextView) itemView.findViewById(R.id.text_comment_content);
            text_comment_num = (TextView) itemView.findViewById(R.id.text_comment_num);
            text_comment_time = (TextView) itemView.findViewById(R.id.text_comment_time);
            text_delete_comment = (TextView) itemView.findViewById(R.id.text_delete_comment);
        }
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }


    /**
     * 改变数量的接口
     */
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
