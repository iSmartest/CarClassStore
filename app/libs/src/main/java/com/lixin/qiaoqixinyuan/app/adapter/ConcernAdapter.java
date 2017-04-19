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
import com.lixin.qiaoqixinyuan.app.bean.MyfocusBean;
import com.lixin.qiaoqixinyuan.app.bean.MyfocuslistBean;
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
 * 类名称：ConcernAdapter
 * 类描述：关注适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/4 14:49
 */

public class ConcernAdapter extends BaseAdapter {
    private Context context;
    private List<MyfocusBean> myfocuslist = new ArrayList<>();
    private int position;
    private String guanzhuid;
    private Dialog dialog;

    public ConcernAdapter(Context context,List<MyfocusBean> myfocuslist,Dialog dialog) {
        super();
        this.context = context;
        this.myfocuslist=myfocuslist;
    }

    @Override
    public int getCount() {
        return myfocuslist.size();
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
            view = View.inflate(context, R.layout.item_prlv_concernandfuns, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(myfocuslist.get(i).focususericon,holder.iv_icon, ImageLoaderUtil.DIO());
        holder.tv_name.setText(myfocuslist.get(i).focususernick);
        holder.tv_age.setText(myfocuslist.get(i).focususerage);
        holder.tv_signature.setText(myfocuslist.get(i).focususersignature);
        String focususersex = myfocuslist.get(i).focususersex;
        if ("男".equals(focususersex)){
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
                guanzhuid = myfocuslist.get(i).focususerid;
                position =i;
                TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
                String token=tm.getDeviceId();
                guanzhupersonal(guanzhuid, MyApplication.getuId(),token);
            }
        });
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_age;
        public TextView tv_signature;
        public ImageView iv_delete;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_icon = (ImageView) rootView.findViewById(R.id.iv_icon);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_age = (TextView) rootView.findViewById(R.id.tv_age);
            this.tv_signature = (TextView) rootView.findViewById(R.id.tv_signature);
            this.iv_delete = (ImageView) rootView.findViewById(R.id.iv_delete);
        }

    }
    /**
     * 取消关注
     * @param guanzhuid
     * @param uid
     * @param token
     */
    private void guanzhupersonal(String guanzhuid, String uid, String token) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "guanzhupersonal");
        params.put("uid", uid);
        params.put("guanzhuid", guanzhuid);
        params.put("focustype", "0");
        params.put("token", token);*/
        String json="{\"cmd\":\"guanzhupersonal\",\"uid\":\"" + uid + "\"," +
                "\"guanzhuid\":\"" + guanzhuid +"\",\"focustype\":\"" + 0 + "\",\"token\":\"" + token + "\"}";
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
                                myfocuslist.remove(position);
                                notifyDataSetChanged();
                                ToastUtil.showToast(context,"取消关注成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
