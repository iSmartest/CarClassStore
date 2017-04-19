package com.lixin.qiaoqixinyuan.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.NearByBean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
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

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：NearByAdapter
 * 类描述：附近的人适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/4 14:45
 */

public class NearByAdapter extends BaseAdapter {
    private Context context;
    private List<NearByBean.NearByUserBean> userlist = new ArrayList<>();
    private String nearuserid;
    private int position;
    private String token;
    private Dialog dialog;
    public NearByAdapter(Context context, List<NearByBean.NearByUserBean> userlist,Dialog dialog) {
        super();
        this.context = context;
        this.userlist = userlist;
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        this.dialog=dialog;
    }

    @Override
    public int getCount() {
        return userlist.size();
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
            view = View.inflate(context, R.layout.item_prlv_nearby, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(userlist.get(i).nearusericon, holder.iv_icon, ImageLoaderUtil.DIO());
        holder.tv_name.setText(userlist.get(i).nearusernick);
        holder.tv_distance.setText(userlist.get(i).nearuserdistance);
        holder.tv_signature.setText(userlist.get(i).nearusersignature);
        String nearusersex = userlist.get(i).nearusersex;
        if ("男".equals(nearusersex)) {
            holder.iv_sex.setImageResource(R.mipmap.ic_male);
        } else {
            holder.iv_sex.setImageResource(R.mipmap.ic_female);
        }
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nearuserid = userlist.get(i).nearuserid;
                position = i;
                deleteremannear(nearuserid, MyApplication.getuId());
            }
        });
        return view;
    }

    public class ViewHolder {
        public View rootView;
        public ImageView iv_icon;
        public TextView tv_name;
        public ImageView iv_sex;
        public TextView tv_distance;
        public TextView tv_signature;
        public ImageView iv_delete;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_icon = (ImageView) rootView.findViewById(R.id.iv_icon);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.iv_sex = (ImageView) rootView.findViewById(R.id.iv_sex);
            this.tv_distance = (TextView) rootView.findViewById(R.id.tv_distance);
            this.tv_signature = (TextView) rootView.findViewById(R.id.tv_signature);
            this.iv_delete = (ImageView) rootView.findViewById(R.id.iv_delete);
        }

    }

    /**
     * 删除附近的人
     * @param nearuserid
     * @param uid
     */
    private void deleteremannear(String nearuserid, String uid) {
        Map<String, String> params = new HashMap<>();
     /*   params.put("cmd", "deleteremannear");
        params.put("nearuserid", nearuserid);
        params.put("uid", uid);*/
        String json="{\"cmd\":\"deleteremannear\",\"nearuserid\":\"" + nearuserid + "\"" +
                ",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
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
                                userlist.remove(position);
                                notifyDataSetChanged();
                                ToastUtil.showToast(context, "删除附近的人成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
