package com.lixin.qiaoqixinyuan.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.bean.IncomedetailsListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：BalanceMingXiAdapter
 * 类描述：提现明细适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/14 16:52
 */

public class BalanceMingXiAdapter extends BaseAdapter {
    private Context context;
    private List<IncomedetailsListBean.incomedetailsBean> incomedetailsList=new ArrayList<>();
    public BalanceMingXiAdapter(Context context, List<IncomedetailsListBean.incomedetailsBean> incomedetailsList) {
        super();
        this.context = context;
        this.incomedetailsList=incomedetailsList;
    }

    @Override
    public int getCount() {
        return incomedetailsList.size();
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
            view = View.inflate(context, R.layout.item_prlv_casher, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        holder.tv_casher_type.setText(incomedetailsList.get(i).incometitle);
        holder.tv_casher_money.setText(incomedetailsList.get(i).incomenum);
        holder.tv_casher_time.setText(incomedetailsList.get(i).incometime);
        holder.tv_casher_restmoney.setText(incomedetailsList.get(i).incomebalance);
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_casher_type;
        public TextView tv_casher_money;
        public TextView tv_casher_time;
        public TextView tv_casher_restmoney;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_casher_type = (TextView) rootView.findViewById(R.id.tv_casher_type);
            this.tv_casher_money = (TextView) rootView.findViewById(R.id.tv_casher_money);
            this.tv_casher_time = (TextView) rootView.findViewById(R.id.tv_casher_time);
            this.tv_casher_restmoney = (TextView) rootView.findViewById(R.id.tv_casher_restmoney);
        }

    }
}
