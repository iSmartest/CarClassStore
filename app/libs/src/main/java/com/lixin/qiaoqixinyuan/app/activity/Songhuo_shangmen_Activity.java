package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Cargoos_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Carts;
import com.lixin.qiaoqixinyuan.app.bean.Paywayorder;
import com.lixin.qiaoqixinyuan.app.bean.Order_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Pay_way_Bean;
import com.lixin.qiaoqixinyuan.app.util.MyAsyncHttpResponseHandler;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.MyListview;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.lixin.qiaoqixinyuan.app.bean.Paywayorder.*;

public class Songhuo_shangmen_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private TextView songhuo_shangmen_shangjia_phone;
    private TextView songhuo_shangmen_dizhi;
    private EditText songhuo_shangmen_name;
    private TextView songhuo_shangmen_phone;
    private EditText songhuo_shangmen_kehu_dizhi;
    private MyListview songhuo_shangmen_list;
    private TextView songhuo_shangmen_jiazhi;
    private TextView songhuo_shangmen_yunfei;
    private EditText songhuo_shangmen_liuyan;
    private TextView songhuo_shangmen_zongjia;
    private TextView songhuo_shangmen_queren;
    private LinearLayout activity_songhuo_shangmen_;
    private String type;
    private Pay_way_Bean pay_way_bean;
    private String shangjiaId;
    private String uid;
    private String nickName;
    private List<Cargoos_Bean.CartsGoods> cartsGoods;
    private String token;
    private String orderNum;
    private Cargoos_Bean cargoos_bean;
    float price;
    float aFloat1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songhuo_shangmen_);
        initView();
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        nickName = SharedPreferencesUtil.getSharePreStr(context,"nickName");
        type = getIntent().getStringExtra("type");
        uid = SharedPreferencesUtil.getSharePreStr(context,"uid");
        shangjiaId = getIntent().getStringExtra("shangjiaId");
        getpayway();
        /*pay_way_bean = (Pay_way_Bean) getIntent().getExtras().get("pay_way_bean");
        cartsGoods = (List<Cargoos_Bean.CartsGoods>) getIntent().getExtras().get("cartsGoods");*/


    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("确认订单");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        songhuo_shangmen_shangjia_phone = (TextView) findViewById(R.id.songhuo_shangmen_shangjia_phone);
        songhuo_shangmen_dizhi = (TextView) findViewById(R.id.songhuo_shangmen_dizhi);
        songhuo_shangmen_name = (EditText) findViewById(R.id.songhuo_shangmen_name);
        songhuo_shangmen_phone = (EditText) findViewById(R.id.songhuo_shangmen_phone);
        songhuo_shangmen_kehu_dizhi = (EditText) findViewById(R.id.songhuo_shangmen_kehu_dizhi);
        songhuo_shangmen_list = (MyListview) findViewById(R.id.songhuo_shangmen_list);
        songhuo_shangmen_jiazhi = (TextView) findViewById(R.id.songhuo_shangmen_jiazhi);
        songhuo_shangmen_yunfei = (TextView) findViewById(R.id.songhuo_shangmen_yunfei);
        songhuo_shangmen_liuyan = (EditText) findViewById(R.id.songhuo_shangmen_liuyan);
        songhuo_shangmen_zongjia = (TextView) findViewById(R.id.songhuo_shangmen_zongjia);
        songhuo_shangmen_queren = (TextView) findViewById(R.id.songhuo_shangmen_queren);
        songhuo_shangmen_queren.setOnClickListener(this);
        activity_songhuo_shangmen_ = (LinearLayout) findViewById(R.id.activity_songhuo_shangmen_);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.songhuo_shangmen_queren:
                if (pay_way_bean.freightid.equals("1")){
                    if (price<Float.valueOf(pay_way_bean.freightreduction)){
                        ToastUtil.showToast(context,"对不起，您必须购买够"+pay_way_bean.freightreduction+
                        "商品才能配送");
                        return;
                    }
                }
                getdata();
                break;
            case R.id.iv_turnback:
                finish();
                break;
        }
    }
    private void getdata(){
                 /*         cmd:”paywayorder”
    paywayId: '1'           //消费类型id  0 送货上门 1 到店消费
    shangjiaid: “1”       //商家id
    uid:"12"    //用户id
    payAddress:"富田太阳城"                     //下单人地址
    payName:"张三"                             //下单人姓名
    payPhone:"156789654"                       //下单人电话
    payPrice:"366.00"                          //订单总价
    leaveMessage:"天王盖地虎"                     //买家留言
    cartsGoods: [{
        goodsid: 12                     // 购物车商品id
        goodsNum:3                      // 购物车商品的数量
        goodsprice:"12"       //购物车商品价格
        }]
    }
    shopSendMoney：“12”    //运送费
    token:    [JPUSHService registrationID]   //推送token
 } */
        Paywayorder paywayorder = new Paywayorder();
         List<Carts> list=new ArrayList<>();
        for (int i = 0; i <cartsGoods.size() ; i++) {
            Carts carts1 = new Carts();
            carts1.goodsid=cartsGoods.get(i).goodsid;
            carts1.goodsNum=cartsGoods.get(i).goodsNum;
            carts1.goodsprice=cartsGoods.get(i).goodsPrice;
            list.add(carts1);
        }
        paywayorder.cartsGoods=list;
        paywayorder.cmd="paywayorder";
        paywayorder.paywayId=type;
        paywayorder.shangjiaid=shangjiaId;
         paywayorder.uid=uid;
        paywayorder.payAddress=songhuo_shangmen_kehu_dizhi.getText().toString();
        paywayorder.payName= songhuo_shangmen_name.getText().toString();
                paywayorder.payPhone=songhuo_shangmen_phone.getText().toString();
                        paywayorder.payPrice=price+"";
                                paywayorder.leaveMessage=songhuo_shangmen_liuyan.getText().toString();
                                        paywayorder.shopSendMoney=aFloat1+"";
                                                paywayorder.token=token;
        Gson gson = new Gson();
        String s = gson.toJson(paywayorder);
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        /* params.put("cmd", "paywayorder");
        params.put("paywayId", type);
        params.put("shangjiaid", shangjiaId);
        params.put("uid", uid);
        params.put("payAddress", pay_way_bean.shangjiaaddress);
        params.put("payName", nickName);
        params.put("payPhone", pay_way_bean.shangjiaphone);
        params.put("payPrice",price+"");
        params.put("leaveMessage", pay_way_bean.freightreduction);
        params.put("cartsGoods",cartsGoods);
        params.put("shopSendMoney",aFloat1+"");
        params.put("token",token);*/
       /* String json="{\"cmd\":\"paywayorder\",\"paywayId\":\"" + type + "\",\"shangjiaid\":\"" + shangjiaId + "\"" +
                ",\"uid\":\"" + uid + "\",\"payAddress\":\"" + pay_way_bean.shangjiaaddress + "\"" +
                ",\"payName\":\"" + nickName + "\",\"payPhone\":\"" + pay_way_bean.shangjiaphone + "\"" +
                ",\"payPrice\":\"" + price + "\",\"leaveMessage\":\"" +  pay_way_bean.freightreduction + "\"" +
                ",\"cartsGoods\":\"" + charSequence + "\",\"shopSendMoney\":\"" + aFloat1 + "\",\"token\":\"" + token + "\"}";*/
       String json=s;
        params.put("json",json);
        httpClient.post(context, getString(R.string.url), params, new MyAsyncHttpResponseHandler(context) {
            @Override
            public void success(int arg0, Header[] arg1, String s) {
                Gson gson1 = new Gson();
                Order_Bean order_bean = gson1.fromJson(s, Order_Bean.class);
                if (order_bean.result.equals("0")){
                    ToastUtil.showToast(Songhuo_shangmen_Activity.this,"订单提交成功");
                }else {
                    ToastUtil.showToast(Songhuo_shangmen_Activity.this,order_bean.resultNote);
                    return;
                }
                orderNum = order_bean.orderNum;
                Intent intent = new Intent(context, Queren_dingdan_Activity.class);
                intent.putExtra("shangjiaId",shangjiaId);
                intent.putExtra("orderId",orderNum);
                intent.putExtra("amount",price+"");
                startActivity(intent);
            }
        });
    }
    private void getpayway() {
        /*cmd:”payway”
        shangjiaid: “1”       //商家id
        uid:"12"  //用户id
        token:    [JPUSHService registrationID]   //推送token   */
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "''payway''");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("token", token);*/
        String json = "{\"cmd\":\"payway\",\"shangjiaid\":\"" + shangjiaId + "\"," +
                "\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("zzc",response);
                        pay_way_bean = new Gson().fromJson(response, Pay_way_Bean.class);
                        if (pay_way_bean.result.equals("1")) {
                            ToastUtil.showToast(context, pay_way_bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        getgouwuchedata();
                        songhuo_shangmen_shangjia_phone.setText(pay_way_bean.shangjiaphone);
                        songhuo_shangmen_dizhi.setText(pay_way_bean.shangjiaaddress);
                        songhuo_shangmen_phone.setText(pay_way_bean.shangjiaphone);
                        songhuo_shangmen_yunfei.setText(pay_way_bean.shangjiaprice);
                        songhuo_shangmen_liuyan.setText(pay_way_bean.shangpinway);
                        songhuo_shangmen_name.setText(nickName);
                        dialog.dismiss();
                    }
                });
    }
    private void getgouwuchedata() {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "''getCarts''");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("token", token);*/
        String json="{\"cmd\":\"getCarts\",\"shangjiaid\":\"" + shangjiaId + "\"," +
                "\"uid\":\"" + uid +"\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        cargoos_bean = gson.fromJson(response, Cargoos_Bean.class);
                        if (cargoos_bean.result.equals("1")) {
                            ToastUtil.showToast(context, cargoos_bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        cartsGoods = cargoos_bean.cartsGoods;
                        songhuo_shangmen_jiazhi.setText(cargoos_bean.shangjiaprice);
                        float aFloat = Float.valueOf(cargoos_bean.shangjiaprice);
                         aFloat1 = Float.valueOf(pay_way_bean.shangjiaprice);
                        price=aFloat+aFloat1;
                        if (aFloat>=Float.valueOf(pay_way_bean.freightreduction)){
                            songhuo_shangmen_yunfei.setText("0");
                            price=aFloat;
                        }
                        songhuo_shangmen_zongjia.setText("￥"+price);
                        dialog.dismiss();
                    }
                });
    }
}
