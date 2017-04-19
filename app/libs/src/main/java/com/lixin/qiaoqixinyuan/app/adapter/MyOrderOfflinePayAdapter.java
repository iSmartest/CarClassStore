package com.lixin.qiaoqixinyuan.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.activity.Qianggou_Activity;
import com.lixin.qiaoqixinyuan.app.bean.MyorderBean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.lixin.qiaoqixinyuan.R.id.tv_cencle;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyOrderOfflinePayAdapter
 * 类描述：我的订单见面付款适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/4 14:54
 */

public class MyOrderOfflinePayAdapter extends BaseAdapter {
    private String uid;
    private Context context;
    private List<MyorderBean.OrderBean> ordersList = new ArrayList<>();
    private String token;
    private Dialog dialog;

    public MyOrderOfflinePayAdapter(Context context, List<MyorderBean.OrderBean> ordersList, Dialog dialog) {
        super();
        this.context = context;
        this.ordersList = ordersList;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        uid = SharedPreferencesUtil.getSharePreStr(context,"uid");
        this.dialog = dialog;
    }

    @Override
    public int getCount() {
        return ordersList.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.item_prlv_order, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(ordersList.get(i).shopIcon,
                holder.iv_icon, ImageLoaderUtil.DIO());
        holder.tv_name.setText(ordersList.get(i).shopName);
        holder.tv_num.setText(ordersList.get(i).orderproductnum);
        holder.tv_allprice.setText(ordersList.get(i).orderprice);
        holder.tv_order_num.setText(ordersList.get(i).ordernum);
        holder.tv_time.setText(ordersList.get(i).adTime);
        if (ordersList.get(i).cancelorder.equals("1")){
            holder.tv_cencle.setVisibility(View.GONE);
        }else {
            holder.tv_cencle.setVisibility(View.VISIBLE);
        }
        if (ordersList.get(i).deliverytype.equals("0")){
            holder.tv_sending.setText("未送货");
            holder.tv_pay.setVisibility(View.GONE);
        }else {
            holder.tv_sending.setText("已送货");
            holder.tv_pay.setVisibility(View.VISIBLE);
        }
        holder.tv_cencle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancleOrder(ordersList.get(i).ordernum);
            }
        });
        holder.tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Qianggou_Activity.class);
                context.startActivity(intent);
            }
        });
        holder.tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardGetDistance(ordersList.get(i).ordernum);
            }
        });
        return view;
    }


    /**
     * 取消订单
     *
     * @param orderNum
     */
    private void cancleOrder( String orderNum) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "cancleOrder");
        params.put("uid", uid);
        params.put("orderNum", orderNum);*/
        String json = "{\"cmd\":\"cancleOrder\",\"orderNum\":\"" + orderNum + "\"," +
                "\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("1".equals(result)) {
                                ToastUtil.showToast(context, resultNote);
                            } else {
                                ordersList.remove(0);//这里需要把0改为选择的条目的位置序号
                                notifyDataSetChanged();
                                ToastUtil.showToast(context, "取消订单成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 确认收货
     * @param orderNum
     */
    private void guardGetDistance( String orderNum) {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "guardGetDistance");
        params.put("uid", uid);
        params.put("orderNum", orderNum);*/
        String json = "{\"cmd\":\"guardGetDistance\",\"orderNum\":\"" + orderNum + "\"," +
                "\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("1".equals(result)) {
                                ToastUtil.showToast(context, resultNote);
                            } else {
                                ordersList.remove(0);//这里需要把0改为选择的条目的位置序号
                                notifyDataSetChanged();
                                ToastUtil.showToast(context, "已确认收货完成");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 退货申请
     *
     * @param uid
     * @param orderNum
     */
    private void applyReturnGoods(String uid, String orderNum) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "applyReturnGoods");
        params.put("uid", uid);
        params.put("orderNum", orderNum);*/
        String json = "{\"cmd\":\"applyReturnGoods\",\"orderNum\":\"" + orderNum + "\"," +
                "\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("1".equals(result)) {
                                ToastUtil.showToast(context, resultNote);
                            } else {
                                ToastUtil.showToast(context, "退货申请提交成功，请等待管理员审核");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    public static class ViewHolder {
        public View rootView;
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_num;
        public TextView tv_allprice;
        public TextView tv_order_num;
        public TextView tv_time;
        public TextView tv_cencle;
        public TextView tv_sending;
        public TextView tv_pay;
        public TextView tv_complete;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_icon = (ImageView) rootView.findViewById(R.id.iv_icon);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.tv_allprice = (TextView) rootView.findViewById(R.id.tv_allprice);
            this.tv_order_num = (TextView) rootView.findViewById(R.id.tv_order_num);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_cencle = (TextView) rootView.findViewById(R.id.tv_cencle);
            this.tv_sending = (TextView) rootView.findViewById(R.id.tv_sending);
            this.tv_pay = (TextView) rootView.findViewById(R.id.tv_pay);
            this.tv_complete = (TextView) rootView.findViewById(R.id.tv_complete);
        }

    }
}
