package com.lixin.qiaoqixinyuan.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.activity.MainActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.Fangwu_Bean;
import com.lixin.qiaoqixinyuan.app.bean.UserLoginBean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class Home_fangwu_Adapter extends BaseAdapter {

    private String housinginnewsid;
    private int position;
    private String token;
    private Dialog dialog;
    private int code;
    public void setHousinginnews(List<Fangwu_Bean.Housinginnewslist> housinginnews) {
        this.housinginnews = housinginnews;
    }

    private Context context;
    private List<Fangwu_Bean.Housinginnewslist> housinginnews;
    public Home_fangwu_Adapter(Dialog dialog,int code){
       this.dialog=dialog;
        this.code=code;
   }
    @Override
    public int getCount() {
        return housinginnews == null ? 0 : housinginnews.size();
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
        context = viewGroup.getContext();
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        ViewHolder vh = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_prlv_mypublish, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(housinginnews.get(i).housingnewsimage,vh.iv_icon, ImageLoaderUtil.DIO());
        vh.tv_name.setText(housinginnews.get(i).housinginnewsitem);
        vh.tv_instruction.setText(housinginnews.get(i).housinginnewsdetail);
        vh.tv_time.setText(housinginnews.get(i).housinginnewstime);
        if (code==1) {
            vh.iv_delete.setVisibility(View.VISIBLE);
            vh.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    housinginnewsid = housinginnews.get(i).housinginnewsid;
                    position = i;
                    deleterefundorder("0", housinginnews.get(i).housinginnewsid,
                            MyApplication.getuId());
                }
            });
        }else {
            vh.iv_delete.setVisibility(View.GONE);
        }
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView iv_icon;
        public TextView tv_name;
        public ImageView iv_delete;
        public TextView tv_instruction;
        public TextView tv_time;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_icon = (ImageView) rootView.findViewById(R.id.iv_icon);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.iv_delete = (ImageView) rootView.findViewById(R.id.iv_delete);
            this.tv_instruction = (TextView) rootView.findViewById(R.id.tv_instruction);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        }

    }
    /**
     * 删除我的发布信息
     * @param type
     * @param newsid
     * @param uid
     */
    private void deleterefundorder(String type, final String newsid, String uid) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "deleterefundorder");
        params.put("type", type);
        params.put("newsid", newsid);
        params.put("uid", uid);*/
        String json="{\"cmd\":\"deleterefundorder\",\"type\":\"" + type + "\"" +
                ",\"newsid\":\"" + newsid + "\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
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
                                housinginnews.remove(position);
                                notifyDataSetChanged();
                                ToastUtil.showToast(context,"删除房屋信息成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
