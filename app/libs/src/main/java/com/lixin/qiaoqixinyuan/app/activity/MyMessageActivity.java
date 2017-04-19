package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.MyMessageAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.MyMessageListBean;
import com.lixin.qiaoqixinyuan.app.bean.UserLoginBean;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
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

import static com.alipay.sdk.app.statistic.c.p;
import static com.alipay.sdk.app.statistic.c.y;
import static com.lixin.qiaoqixinyuan.R.id.prlv_myblacklist;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyMessageActivity
 * 类描述：站内信
 * 创建人：Tiramisu
 * 创建时间：2017/2/6 14:02
 */
public class MyMessageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private PullToRefreshListView prlv_mymessage;
    private MyMessageAdapter adapter;
    private int nowPage=1;
    private String uid;
    private String token;
    private List<MyMessageListBean.MessageBean> messageList = new ArrayList<>();
    private int position;
    private String messageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        initView();
        initData();
        initLiserner();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        prlv_mymessage = (PullToRefreshListView) findViewById(R.id.prlv_mymessage);
        prlv_mymessage.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initData() {
        tv_title.setText("我的消息");
        uid = MyApplication.getuId();
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        adapter = new MyMessageAdapter(context, messageList,dialog);
        prlv_mymessage.setAdapter(adapter);
        getSystemMessage();
    }

    private void initLiserner() {
        iv_turnback.setOnClickListener(this);
        prlv_mymessage.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                messageList.clear();
                getSystemMessage();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                getSystemMessage();
            }
        });
        prlv_mymessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                messageId = messageList.get(i-1).messageId;
                position = i-1;
                readMessage(messageId, uid, token);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
        }
    }

    /**
     * 我的消息
     */
    private void getSystemMessage() {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "getSystemMessage");
        params.put("uid", uid);
        params.put("nowPage", String.valueOf(nowPage));
        params.put("token", token);*/
        String json="{\"cmd\":\"getSystemMessage\",\"nowPage\":\"" + nowPage + "\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        prlv_mymessage.onRefreshComplete();
                        dialog.dismiss();
                        prlv_mymessage.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyMessageListBean bean = gson.fromJson(response, MyMessageListBean.class);
                        prlv_mymessage.onRefreshComplete();
                        if ("1".equals(bean.result)) {
                            ToastUtil.showToast(context, bean.resultNote);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            prlv_mymessage.onRefreshComplete();
                            return;
                        }
                        if (Integer.parseInt(bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        messageList.addAll(bean.messageList);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                        prlv_mymessage.onRefreshComplete();
                    }
                });

    }

    /**
     * 阅读我的消息
     * @param messageId
     * @param uid
     * @param token
     */
    private void readMessage(String messageId, String uid, String token) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "readMessage");
        params.put("messageId", messageId);
        params.put("uid", uid);
        params.put("token", token);*/
        String json="{\"cmd\":\"readMessage\",\"messageId\":\"" + messageId + "\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("1".equals(result)) {
                                ToastUtil.showToast(context, resultNote);
                            } else {
                                messageList.get(position).type="1";
                                adapter.notifyDataSetChanged();
                                ToastUtil.showToast(context, "消息已阅读");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
