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
import com.lixin.qiaoqixinyuan.app.bean.MyMessageListBean;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
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
import static com.lixin.qiaoqixinyuan.R.id.iv_delete;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyMessageAdapter
 * 类描述：站内信适配器
 * 创建人：Tiramisu
 * 创建时间：2017/2/6 14:04
 */

public class MyMessageAdapter extends BaseAdapter {
    private Context context;
    private List<MyMessageListBean.MessageBean> messageList = new ArrayList<>();
    private String messageId;
    private int position;
     private String token;
    private String uid;
    private Dialog dialog;
    public MyMessageAdapter(Context context, List<MyMessageListBean.MessageBean> messageList,Dialog dialog) {
        super();
        this.context = context;
        this.messageList = messageList;
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        uid= SharedPreferencesUtil.getSharePreStr(context,"uid");
        this.dialog=dialog;
    }

    @Override
    public int getCount() {
        return messageList.size();
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
            view = View.inflate(context, R.layout.item_prlv_mymessage, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_time.setText(messageList.get(i).messageTime);
        holder.tv_content.setText(messageList.get(i).messageContent);
        String type = messageList.get(i).type;
        if ("0".equals(type)) {
            holder.tv_read.setText("未读");
        } else {
            holder.tv_read.setText("已读");
        }
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageId = messageList.get(i).messageId;
                position =i;
                String registrationID = JPushInterface.getRegistrationID(context);
                deleteMessage(messageId);
            }
        });
        return view;
    }
    public static class ViewHolder {
        public View rootView;
        public TextView tv_read;
        public TextView tv_time;
        public TextView tv_content;
        public ImageView iv_delete;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_read = (TextView) rootView.findViewById(R.id.tv_read);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.iv_delete = (ImageView) rootView.findViewById(R.id.iv_delete);

        }

    }
    /**
     * 删除我的消息
     * @param messageId
     */
    private void deleteMessage(String messageId) {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "deleteMessage");
        params.put("uid", uid);
        params.put("messageId", messageId);
        params.put("token", token);*/
        String json="{\"cmd\":\"deleteMessage\",\"uid\":\"" + uid + "\",\"messageId\":\"" + messageId + "\",\"token\":\"" + token + "\"}";
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
                                messageList.remove(position);
                                notifyDataSetChanged();
                                ToastUtil.showToast(context,"删除消息成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
