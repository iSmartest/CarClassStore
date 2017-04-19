package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.MyAddressAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.MyAddressBean;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

public class MyAddressActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private PullToRefreshListView prlv_myaddress;
    private TextView tv_addaddress;
    private MyAddressAdapter adapter;
    private String uid;
    private String token;
    private List<MyAddressBean.AddressBean> addressList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaddress);
        initView();
        initData();
        initLiserner();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        prlv_myaddress = (PullToRefreshListView) findViewById(R.id.prlv_myaddress);
        tv_addaddress = (TextView) findViewById(R.id.tv_addaddress);
        prlv_myaddress.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    private void initData() {
        tv_title.setText("我的地址");
        uid = MyApplication.getuId();
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        adapter = new MyAddressAdapter(context,addressList,dialog);
        prlv_myaddress.setAdapter(adapter);
        getMyAddress();
    }

    private void initLiserner() {
        iv_turnback.setOnClickListener(this);
        tv_addaddress.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_addaddress:
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("code",0);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==3){
            getMyAddress();
        }
    }

    /**
     * 我的收货地址
     */

    private void getMyAddress() {
        Map<String, String> params = new HashMap<>();
    /*    cmd：”getMyAddress
        uid:”12”  //用户Id
        token:    [JPUSHService registrationID]   //推送token*/
        String json="{\"cmd\":\"getMyAddress\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyAddressBean bean = gson.fromJson(response, MyAddressBean.class);
                        if ("1".equals(bean.result)) {
                            ToastUtil.showToast(context, bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        addressList = bean.addressList;
                        adapter.setAddressList(addressList);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

    }
}
