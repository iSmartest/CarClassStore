package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.ExchangeZone;
import com.lixin.carclassstore.bean.ReplyBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/28
 * My mailbox is 1403241630@qq.com
 */

public class ExchangeZoneAdapter extends BaseAdapter{
    private Context context;
    private List<ExchangeZone.commoditys> mList;
    public ExchangeZoneAdapter(Context context) {
        this.context = context;
    }
    public void setExchangeZone(List<ExchangeZone.commoditys> mList) {
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ExchangeZone.commoditys getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_exchange_zone,null);
            viewHolder = new ViewHolder();
            viewHolder.exchange_icon = (ImageView) convertView.findViewById(R.id.exchange_icon);
            viewHolder.exchange_name = (TextView) convertView.findViewById(R.id.exchange_name);
            viewHolder.exchange_integral = (TextView) convertView.findViewById(R.id.exchange_integral);
            viewHolder.btn_exchange = (Button) convertView.findViewById(R.id.btn_exchange);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ExchangeZone.commoditys commoditysList = mList.get(position);
        viewHolder.exchange_name.setText(commoditysList.getCommodityTitle());
        viewHolder.exchange_integral.setText(commoditysList.getCommoditycreditsNum());
        Picasso.with(context).load(commoditysList.getCommodityIcon()).placeholder(R.drawable.image_fail_empty).into(viewHolder.exchange_icon);
        viewHolder.btn_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = (String) SPUtils.get(context,"uid","");
                getdata(commoditysList.getCommodityid(),uid);
            }
        });
        return null;
    }

    private void getdata(String commodityid,String uid) {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"creditsExchangeCenter\",\"commodityid\":\"" + commodityid + "\",\"uid\":\"" + uid +"\"}";
        params.put("json", json);
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showMessageShort(context, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Log.i("ExchangeZoneActivity", "onResponse: " + response.toString());
                        ReplyBean rplyBean = gson.fromJson(response, ReplyBean.class);
                        if (rplyBean.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, rplyBean.getResultNote());
                            return;
                        } else {
                            ToastUtils.showMessageShort(context, "兑换成功！");
                        }
                    }
                });
    }

    class ViewHolder {
        ImageView exchange_icon;
        TextView exchange_name,exchange_integral;
        Button btn_exchange;
    }
}
