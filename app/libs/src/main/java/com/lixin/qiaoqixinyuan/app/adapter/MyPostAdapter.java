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
import com.lixin.qiaoqixinyuan.app.bean.Home_Bean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
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
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyPostAdapter
 * 类描述：我的帖子适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/4 16:39
 */

public class MyPostAdapter extends BaseAdapter {
    private Context context;
    private String tiebaId;
    private int position;
    private String token;
    private Dialog dialog;
    private int code=-1;
    public MyPostAdapter(Context context, Dialog dialog,int code) {
        super();
        this.context = context;
        this.code=code;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        this.dialog = dialog;
    }

    public void setTieba(List<Home_Bean.Tiebamodel> tieba) {
        this.tieba = tieba;
    }

    private List<Home_Bean.Tiebamodel> tieba;

    @Override
    public int getCount() {
        return tieba == null ? 0 : tieba.size();
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
            view = View.inflate(context, R.layout.item_prlv_mypost, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Home_Bean.Tiebamodel tiebamodel = tieba.get(i);
        ImageLoader.getInstance().displayImage(tiebamodel.tiebaimage, holder.lv_image, ImageLoaderUtil.DIO());
        holder.tv_commentnum.setText(tiebamodel.dianjiliang);
        holder.tv_pingluntnum.setText(tiebamodel.pinglunliang);
        holder.tv_name.setText(tiebamodel.tiebausername);
        holder.tv_message.setText(tiebamodel.tiebascribe);
        holder.tv_time.setText(tiebamodel.tiebatime);
        holder.title_message.setText(tiebamodel.tiebatitle);
        ImageLoader.getInstance().displayImage(tiebamodel.tiebaicon, holder.lv_icon, ImageLoaderUtil.DIO());
        if (code==1) {
            holder.iv_delete.setVisibility(View.VISIBLE);
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tiebaId = tieba.get(i).tiebaId;
                    position = i;
                    deleteremytiezi(tiebaId, MyApplication.getuId());
                }
            });
        }else {
            holder.iv_delete.setVisibility(View.GONE);
        }
        return view;
    }


    /**
     * 删除我的帖子
     *
     * @param tiebaId
     * @param uid
     */
    private void deleteremytiezi(String tiebaId, String uid) {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "deleteremytiezi");
        params.put("tiebaId", tiebaId);
        params.put("uid", uid);*/
        String json = "{\"cmd\":\"deleteremytiezi\",\"tiebaId\":\"" + tiebaId + "\"" +
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
                                return;
                            } else {
                                tieba.remove(position);
                                notifyDataSetChanged();
                                ToastUtil.showToast(context, "删除我的帖子成功");
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    public static class ViewHolder {
        public View rootView;
        public TextView title_message;
        public ImageView lv_image;
        public TextView tv_message;
        public CircleImageView lv_icon;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_commentnum;
        public TextView tv_pingluntnum;
        public ImageView iv_delete;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.title_message = (TextView) rootView.findViewById(R.id.title_message);
            this.lv_image = (ImageView) rootView.findViewById(R.id.lv_image);
            this.tv_message = (TextView) rootView.findViewById(R.id.tv_message);
            this.lv_icon = (CircleImageView) rootView.findViewById(R.id.lv_icon);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_commentnum = (TextView) rootView.findViewById(R.id.tv_commentnum);
            this.tv_pingluntnum = (TextView) rootView.findViewById(R.id.tv_pingluntnum);
            this.iv_delete = (ImageView) rootView.findViewById(R.id.iv_delete);
        }

    }
}
