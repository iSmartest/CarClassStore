package com.lixin.qiaoqixinyuan.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.MyBlackListBean;
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

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.umeng.socialize.Config.dialog;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyBlacklistAdapter
 * 类描述：我的黑名单适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/4 15:06
 */

public class MyBlacklistAdapter extends BaseAdapter {
    private Context context;
    private List<MyBlackListBean.MyBlackBean> myblacklist = new ArrayList<>();
    private int position;
    private String blackuserid;
    private Dialog dialog;
    private String token;
    public MyBlacklistAdapter(Context context, List<MyBlackListBean.MyBlackBean> myblacklist,Dialog dialog) {
        super();
        this.context = context;
        this.myblacklist = myblacklist;
        this.dialog=dialog;
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
    }

    @Override
    public int getCount() {
        return myblacklist.size();
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
            view = View.inflate(context, R.layout.item_prlv_myblacklist, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(myblacklist.get(i).blackusericon, holder.iv_icon, ImageLoaderUtil.DIO());
        holder.tv_name.setText(myblacklist.get(i).blackusernick);
        holder.tv_age.setText(myblacklist.get(i).blackuserage);
        holder.tv_signature.setText(myblacklist.get(i).blackusersignature);
        String blackusersex = myblacklist.get(i).blackusersex;
        if ("男".equals(blackusersex)){
            Drawable drawable= context.getResources().getDrawable(R.mipmap.ic_male);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tv_age.setCompoundDrawables(drawable,null,null,null);
        }else {
            Drawable drawable= context.getResources().getDrawable(R.mipmap.ic_female);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tv_age.setCompoundDrawables(drawable,null,null,null);
        }
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blackuserid = myblacklist.get(i).blackuserid;
                position =i;
                deleteremyblack(blackuserid, MyApplication.getuId());
            }
        });
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_age;
        public ImageView iv_delete;
        public TextView tv_signature;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_icon = (ImageView) rootView.findViewById(R.id.iv_icon);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_age = (TextView) rootView.findViewById(R.id.tv_age);
            this.iv_delete = (ImageView) rootView.findViewById(R.id.iv_delete);
            this.tv_signature = (TextView) rootView.findViewById(R.id.tv_signature);
        }

    }
    /**
     * 删除我的黑名单
     * @param blackuserid
     * @param uid
     */
    private void deleteremyblack(String blackuserid, String uid) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "deleteremyblack");
        params.put("uid", uid);
        params.put("blackuserid", blackuserid);
        params.put("focustype", "0");
        params.put("token", token);*/
        String json="{\"cmd\":\"deleteremyblack\",\"blackuserid\":\"" + blackuserid + "\"," +
                "\"uid\":\"" + uid + "\",\"focustype\":\"" + token + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object=new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("1".equals(result)){
                                ToastUtil.showToast(context,resultNote);
                            }else {
                                myblacklist.remove(position);
                                notifyDataSetChanged();
                                ToastUtil.showToast(context,"删除黑名单成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
