package com.lixin.qiaoqixinyuan.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.activity.AddAddressActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.MyAddressBean;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.umeng.socialize.Config.dialog;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyAddressAdapter
 * 类描述：我的地址适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/4 14:40
 */

public class MyAddressAdapter extends BaseAdapter {
    private Context context;
    private Dialog dialog;
    private String token;
    public void setAddressList(List<MyAddressBean.AddressBean> addressList) {
        this.addressList = addressList;
    }

    private List<MyAddressBean.AddressBean> addressList ;
    private String addressId;
    private int position;

    public MyAddressAdapter(Context context,List<MyAddressBean.AddressBean> addressList,Dialog dialog){
        super();
        this.context=context;
        this.addressList=addressList;
        this.dialog=dialog;
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
    }
    @Override
    public int getCount() {
        return addressList.size();
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
            view = View.inflate(context, R.layout.item_prlv_myaddress, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_name.setText(addressList.get(i).userName);
        holder.tv_zipcode.setText(addressList.get(i).cityId);
        holder.tv_phone.setText(addressList.get(i).userPhone);
        holder.tv_address.setText(addressList.get(i).address);
        boolean isDefault = Boolean.parseBoolean(addressList.get(i).isDefault);
        if (isDefault){
            holder.tv_default_address.setText("默认地址");
        }else {
            holder.tv_default_address.setText("");
        }
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressId = addressList.get(i).addressId;
                position =i;
                String registrationID = JPushInterface.getRegistrationID(context);
                deleteMyAddress(addressId, MyApplication.getuId(),registrationID);
            }
        });
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressId = addressList.get(i).addressId;
                Bundle bundle=new Bundle();
                bundle.putString("addressId",addressId);
                MyApplication.openActivity(context,AddAddressActivity.class,bundle);
            }
        });
        return view;
    }

    public class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_zipcode;
        public TextView tv_phone;
        public TextView tv_address;
        public TextView tv_default_address;
        public TextView tv_delete;
        public TextView tv_edit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_zipcode = (TextView) rootView.findViewById(R.id.tv_zipcode);
            this.tv_phone = (TextView) rootView.findViewById(R.id.tv_phone);
            this.tv_address = (TextView) rootView.findViewById(R.id.tv_address);
            this.tv_default_address = (TextView) rootView.findViewById(R.id.tv_default_address);
            this.tv_delete = (TextView) rootView.findViewById(R.id.tv_delete);
            this.tv_edit = (TextView) rootView.findViewById(R.id.tv_edit);
        }
    }
    /**
     * 删除收货地址
     * @param addressId
     * @param uid
     * @param token
     */
    private void deleteMyAddress(String addressId, String uid, String token) {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "deleteMyAddress");
        params.put("addressId", addressId);
        params.put("uid", uid);
        params.put("token", token);*/
        String json="{\"cmd\":\"deleteMyAddress\",\"addressId\":\"" + addressId + "\"," +
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
                            JSONObject object=new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("1".equals(result)){
                                ToastUtil.showToast(context,resultNote);
                            }else {
                                addressList.remove(position);
                                notifyDataSetChanged();
                                ToastUtil.showToast(context,"删除收货地址成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
