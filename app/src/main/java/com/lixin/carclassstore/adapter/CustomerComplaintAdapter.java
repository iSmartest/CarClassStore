package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.CustomerComplaint;
import com.lixin.carclassstore.bean.ReplyBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/17
 * My mailbox is 1403241630@qq.com
 */

public class CustomerComplaintAdapter extends BaseAdapter {
    private Context context;
    private List<CustomerComplaint.complains> list;
    private String uid = "123";
    public CustomerComplaintAdapter(Context context) {
        this.context = context;
    }
    public void setCustomerComplaint( List<CustomerComplaint.complains> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public CustomerComplaint.complains getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_customer_complaint, null);
            vh = new ViewHolder();
            vh.userIcon = (ImageView) convertView.findViewById(R.id.iv_user_picture);
            vh.shopName = (TextView) convertView.findViewById(R.id.text_shop_name);
            vh.userName = (TextView) convertView.findViewById(R.id.text_user_name);
            vh.complainContent = (TextView) convertView.findViewById(R.id.text_complaint_content);
            vh.replay = (TextView) convertView.findViewById(R.id.text_replay);
            vh.complainTime = (TextView) convertView.findViewById(R.id.text_time);
            vh.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final CustomerComplaint.complains complainsList = list.get(position);
        Picasso.with(context).load(complainsList.getCommodityIcon()).into(vh.userIcon);
        vh.shopName.setText(complainsList.getShopName());
        vh.userName.setText(complainsList.getUserName());
        vh.complainContent.setText(complainsList.getContent());
        vh.replay.setText("商家回复：" + complainsList.getReplay());
        vh.complainTime.setText(complainsList.getComplainTime());
        vh.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                updateList(list);
                getdata(complainsList.getComplainid());
            }
        });
        return convertView;
    }

    private void getdata(String complainid) {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"userComplains\",\"uid\":\"" + uid +"\",\"complainid\":\"" + complainid +"\"}";
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
                        Log.i("CustomerComplaintActivity", "onResponse1: " + response.toString());
                        Gson gson = new Gson();
                        ReplyBean replyBean = gson.fromJson(response, ReplyBean.class);
                        if (replyBean.result.equals("1")) {
                            ToastUtils.showMessageShort(context, replyBean.resultNote);
                            return;
                        }
                        ToastUtils.showMessageShort(context, "删除成功！");
                    }
                });
    }

    class ViewHolder {
        ImageView userIcon,iv_delete;
        TextView shopName, userName, complainContent, replay,complainTime;
    }


    public void updateList(List< CustomerComplaint.complains> mList) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list = mList;
        notifyDataSetChanged();
    }

}
