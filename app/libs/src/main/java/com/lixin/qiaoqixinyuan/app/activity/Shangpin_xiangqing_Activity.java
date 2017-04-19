package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Cargoos_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Pay_way_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean_gouwuche;
import com.lixin.qiaoqixinyuan.app.bean.Shangpin_xiangqing_Bean;
import com.lixin.qiaoqixinyuan.app.util.AppUtil;
import com.lixin.qiaoqixinyuan.app.util.LogUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.GlideImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.youth.banner.Banner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.R.attr.type;
import static com.lixin.qiaoqixinyuan.R.id.dianpu_xiangqing_time;
import static com.lixin.qiaoqixinyuan.R.id.dianpu_xiangqing_title;
import static com.lixin.qiaoqixinyuan.R.id.iv_dianpu_xiangqing_car;
import static com.lixin.qiaoqixinyuan.R.id.layout_pop_add;
import static com.lixin.qiaoqixinyuan.R.id.tv_dianpu_xiangqing_money;
import static com.lixin.qiaoqixinyuan.R.id.tv_dianpu_xiangqing_nub;
import static com.lixin.qiaoqixinyuan.R.id.tv_pop_in;
import static com.lixin.qiaoqixinyuan.R.id.tv_pop_out;
import static com.lixin.qiaoqixinyuan.R.string.url;

public class Shangpin_xiangqing_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private TextView shangpin_xiangqing_title;
    private ImageView shangpin_xiangqing_fenxiang;
    private TextView shangpin_xiangqing_message;
    private TextView shangpin_xiangqing_jiage;
    private ImageView iv_shangpin_xiangqing_add;
    private TextView tv_shangpin_xiangqing_nub;
    private TextView iv_shangpin_xiangqing_kehu;
    private ImageView iv_shangpin_xiangqing_car;
    private TextView tv_shangpin_xiangqing_nub1;
    private TextView tv_shangpin_xiangqing_money;
    private TextView tv_shangpin_xiangqing_jiesuan;
    private LinearLayout activity_ershou_chushou_;
    private String shangjiaid;
    private Banner shangpin_xiangqing_image;
    private List<String> images = new ArrayList<>();
    private int code;
    private String goodsid;
    private String uid;
    private String token;
    private Shangpin_xiangqing_Bean.goodDetail detail;
    private List<Cargoos_Bean.CartsGoods> cartsGoods;
    private PopupWindow popuWindow1;
    private View inflate1;
    private TextView tv_pop_in;
    private TextView tv_pop_out;
    private Pay_way_Bean pay_way_bean;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangpin_xiangqing_);
        uid = SharedPreferencesUtil.getSharePreStr(context,"uid");
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        Intent intent = getIntent();
        shangjiaid = intent.getStringExtra("shangjiaid");
        goodsid = intent.getStringExtra("goodsId");
        initView();
        getdata();
        getpayway();
        getgouwuchedata(iv_shangpin_xiangqing_car,-1);
    }
    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("商品详情");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        shangpin_xiangqing_title = (TextView) findViewById(R.id.shangpin_xiangqing_title);
        shangpin_xiangqing_fenxiang = (ImageView) findViewById(R.id.shangpin_xiangqing_fenxiang);
        shangpin_xiangqing_fenxiang.setOnClickListener(this);
        shangpin_xiangqing_message = (TextView) findViewById(R.id.shangpin_xiangqing_message);
        shangpin_xiangqing_jiage = (TextView) findViewById(R.id.shangpin_xiangqing_jiage);
        iv_shangpin_xiangqing_add = (ImageView) findViewById(R.id.iv_shangpin_xiangqing_add);
        iv_shangpin_xiangqing_add.setOnClickListener(this);
        tv_shangpin_xiangqing_nub = (TextView) findViewById(R.id.tv_shangpin_xiangqing_nub);
        iv_shangpin_xiangqing_kehu = (TextView) findViewById(R.id.iv_shangpin_xiangqing_kehu);
        iv_shangpin_xiangqing_car = (ImageView) findViewById(R.id.iv_shangpin_xiangqing_car);
        iv_shangpin_xiangqing_car.setOnClickListener(this);
        tv_shangpin_xiangqing_nub1 = (TextView) findViewById(R.id.tv_shangpin_xiangqing_nub1);
        tv_shangpin_xiangqing_money = (TextView) findViewById(R.id.tv_shangpin_xiangqing_money);
        tv_shangpin_xiangqing_jiesuan = (TextView) findViewById(R.id.tv_shangpin_xiangqing_jiesuan);
        tv_shangpin_xiangqing_jiesuan.setOnClickListener(this);
        shangpin_xiangqing_image = (Banner) findViewById(R.id.shangpin_xiangqing_image);
    }

    private void setbanner() {
        DisplayMetrics displayMetrics = AppUtil.getDisplayMetrics(Shangpin_xiangqing_Activity.this);
        int widthPixels = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = shangpin_xiangqing_image.getLayoutParams();
        layoutParams.height = widthPixels / 2;
        shangpin_xiangqing_image.setLayoutParams(layoutParams);
        shangpin_xiangqing_image.setImageLoader(new GlideImageLoader());
        //设置图片集合
        shangpin_xiangqing_image.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        shangpin_xiangqing_image.start();
    }

    private void getdata() {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "''getGoodsDetail''");
        params.put("shangjiaId", "1");
        params.put("goodsId", "1");*/
        String json = "{\"cmd\":\"getGoodsDetail\",\"shangjiaid\":\"" + shangjiaid + "\",\"goodsid\":\""
                + goodsid + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(this.getString(url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Shangpin_xiangqing_Activity.this, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        Shangpin_xiangqing_Bean shangpin_xiangqing_bean = gson.fromJson(response, Shangpin_xiangqing_Bean.class);
                        if (shangpin_xiangqing_bean.result.equals("1")) {
                               ToastUtil.showToast(context,shangpin_xiangqing_bean.resultNote);
                            return;
                               }
                            ToastUtil.showToast(Shangpin_xiangqing_Activity.this, shangpin_xiangqing_bean.resultNote);
                            List<Shangpin_xiangqing_Bean.Images> imagesList = shangpin_xiangqing_bean.imagesList;
                            for (int i = 0; i < imagesList.size(); i++) {
                                images.add(imagesList.get(i).imageUrl);
                            }
                            setbanner();
                            detail = shangpin_xiangqing_bean.goodDetail;
                            shangpin_xiangqing_title.setText(detail.goodsName);
                            shangpin_xiangqing_message.setText(detail.goodIntroduce);
                            shangpin_xiangqing_jiage.setText(detail.goodsPrice);
                            iv_shangpin_xiangqing_kehu.setText(detail.goodrequirements);
                        }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_shangpin_xiangqing_add:
                addgouwuche(goodsid,detail.goodsPrice);
                break;
            case R.id.iv_shangpin_xiangqing_car:
                getgouwuchedata(iv_shangpin_xiangqing_car,-1);
                break;
            case R.id.tv_shangpin_xiangqing_jiesuan:
                initPopuWindow(view);
                break;
            case R.id.tv_pop_in:
                getgouwuchedata(view,0);
                break;
            case R.id.tv_pop_out:
                getgouwuchedata(view,1);
                break;
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.shangpin_xiangqing_fenxiang:
                getshareuri();
                break;
        }
    }
    private void getshareuri(){
      /*  cmd:”sixshare”
        type:"0"  //0贴吧 1二手出租 2二手求购 3招聘 4求职 5房屋出租 6房屋求购 7活动 8商品 9商家
        contentid:"12"   //内容id
        nowPage:"1"		//当前页(分享活动使用)
        lat:""			//纬度(分享活动使用)
        lon:""			//经度(分享活动使用) */
        Map<String, String> params = new HashMap<>();
        String json="{\"cmd\":\"sixshare\",\"type\":\"" + 8 + "\"," +
                "\"contentid\":\"" + goodsid +"\"}";
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
                                ToastUtil.showToast(context,e.getMessage());
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            String sixshareone = object.getString("sixshareone");
                            if ("0".equals(result)) {
                                new ShareAction(Shangpin_xiangqing_Activity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                        .withTitle("撬动沁源")
                                        .withMedia(new UMImage(context, images.get(0)))
                                        .withTargetUrl(sixshareone)
                                        .withText(detail.goodsName)
                                        .setCallback(umShareListener)
                                        .open();
                            } else {
                                ToastUtil.showToast(context, resultNote);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtil.d("plat","platform"+platform);
            ToastUtil.showToast(context,platform + " 分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToast(context,platform + " 分享失败啦");
            if(t!=null){
                LogUtil.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToast(context,platform + " 分享取消了");
        }
    };
    private void initPopuWindow(View parent) {
        if (popuWindow1 == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            inflate1 = mLayoutInflater.inflate(R.layout.popuwindow, null);
            popuWindow1 = new PopupWindow(inflate1, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        tv_pop_in = (TextView) inflate1.findViewById(R.id.tv_pop_in);
        tv_pop_in.setText("送货上门");
        tv_pop_in.setOnClickListener(this);
        tv_pop_out = (TextView) inflate1.findViewById(R.id.tv_pop_out);
        tv_pop_out.setText("到店消费或到店自取");
        tv_pop_out.setOnClickListener(this);
        if (pay_way_bean.shangpinway.equals("0")) {
            tv_pop_in.setVisibility(View.VISIBLE);
            tv_pop_out.setVisibility(View.GONE);
        } else if (pay_way_bean.shangpinway.equals("1")) {
            tv_pop_in.setVisibility(View.GONE);
            tv_pop_out.setVisibility(View.VISIBLE);
        } else if (pay_way_bean.shangpinway.equals("2")) {
            tv_pop_in.setVisibility(View.VISIBLE);
            tv_pop_out.setVisibility(View.VISIBLE);
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popuWindow1.setBackgroundDrawable(cd);
        //产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popuWindow1.setOutsideTouchable(true);
        popuWindow1.setFocusable(true);
        popuWindow1.showAtLocation((View) parent.getParent(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popuWindow1.update();
        popuWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void addgouwuche(String goodsId,String goodsprice) {
        if (popupWindow!=null){
            popupWindow.dismiss();
        }
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "''addCartGoods''");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("goodsprice", "1");
        params.put("goodsId", "1");
        params.put("token", token);*/
        String json="{\"cmd\":\"addCartGoods\",\"shangjiaid\":\"" + shangjiaid + "\"," +
                "\"uid\":\"" + uid +"\",\"goodsprice\":\"" + goodsprice + "\"" +
                ",\"goodsId\":\"" + goodsId + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()//
                .url(this.getString(url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    private Resout_Bean_gouwuche resout_bean_gouwuche;

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        resout_bean_gouwuche = gson.fromJson(response, Resout_Bean_gouwuche.class);
                        if (resout_bean_gouwuche.result.equals("0")) {
                            ToastUtil.showToast(context, "添加购物车成功");
                            dialog.dismiss();
                            getgouwuchedata(iv_shangpin_xiangqing_car,-1);
                        } else {
                            ToastUtil.showToast(context, resout_bean_gouwuche.resultNote);
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void getdelatenub(String goodsId,String goodsprice) {
        if (popupWindow!=null){
            popupWindow.dismiss();
        }
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "'deleteCartGoods'");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("goodsId", "1");
        params.put("token", token);*/
        String json="{\"cmd\":\"deleteCartGoods\",\"shangjiaid\":\"" + shangjiaid + "\"," +
                "\"uid\":\"" + uid +"\",\"goodsprice\":\"" + goodsprice + "\"" +
                ",\"goodsId\":\"" + goodsId + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()//
                .url(this.getString(url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Shangpin_xiangqing_Activity.this, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        if (resout_bean.result.equals("0")) {
                            ToastUtil.showToast(context, "删除购物车数量成功");
                            dialog.dismiss();
                            getgouwuchedata(iv_shangpin_xiangqing_car,-1);
                        } else {
                            ToastUtil.showToast(context, resout_bean.resultNote);
                            dialog.dismiss();
                        }
                    }
                });
    }
    private void getgouwuchedata(final View view,final int type) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "''getCarts''");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("token", token);*/
        String json="{\"cmd\":\"getCarts\",\"shangjiaid\":\"" + shangjiaid + "\"," +
                "\"uid\":\"" + uid +"\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post()//
                .url(this.getString(url))//
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
                        Cargoos_Bean cargoos_bean = gson.fromJson(response, Cargoos_Bean.class);
                        if (cargoos_bean.result.equals("1")) {
                            ToastUtil.showToast(context, cargoos_bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        cartsGoods = cargoos_bean.cartsGoods;
                        dialog.dismiss();
                        int nub=0;
                        if(cartsGoods.size()==0){
                            return;
                        }
                        for (int i = 0; i <cartsGoods.size() ; i++) {
                            int i1 = Integer.parseInt(cartsGoods.get(i).goodsNum);
                            nub+=i1;
                        }
                        tv_shangpin_xiangqing_nub1.setText(nub+"");
                        tv_shangpin_xiangqing_money.setText("￥"+cargoos_bean.shangjiaprice);
                        if (type==-1) {
                            initPopuWindow2(view);
                        }else {
                            if (cargoos_bean.cartsGoods.size()==0){
                                ToastUtil.showToast(context,"您还没有购买商品");
                                return;
                            }
                            Intent intent2 = new Intent(context, Songhuo_shangmen_Activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("pay_way_bean", (Serializable) pay_way_bean);
                            bundle.putSerializable("cartsGoods", (Serializable) cartsGoods);
                            intent2.putExtra("type", type+"");
                            intent2.putExtra("shangjiaId", shangjiaid);
                            intent2.putExtras(bundle);
                            startActivity(intent2);
                        }

                    }
                });
    }
    private void initPopuWindow2(View parent) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View inflate1 = mLayoutInflater.inflate(R.layout.popuwindoe_gouwuche, null);
        LinearLayout layout_pop_add = (LinearLayout) inflate1.findViewById(R.id.layout_pop_add);
        for (int i = 0; i < cartsGoods.size(); i++) {
            View inflate = View.inflate(context, R.layout.popwindow_gouwuche_item, null);
            TextView iv_pop_xiangqing_title = (TextView) inflate.findViewById(R.id.iv_pop_xiangqing_title);
            TextView iv_pop_xiangqing_price = (TextView) inflate.findViewById(R.id.iv_pop_xiangqing_price);
            TextView tv_pop_xiangqing_nub = (TextView) inflate.findViewById(R.id.tv_pop_xiangqing_nub);
            ImageView iv_pop_xiangqing_add = (ImageView) inflate.findViewById(R.id.iv_pop_xiangqing_add);
            ImageView iv_pop_xiangqing_reduce = (ImageView) inflate.findViewById(R.id.iv_pop_xiangqing_reduce);
            ImageView iv_qianggou_xiangqing_delate = (ImageView) inflate.findViewById(R.id.iv_qianggou_xiangqing_delate);
            iv_pop_xiangqing_title.setText(cartsGoods.get(i).goodsName);
            iv_pop_xiangqing_price.setText(cartsGoods.get(i).goodsPrice);
            tv_pop_xiangqing_nub.setText(cartsGoods.get(i).goodsNum);
            final int finalI1 = i;
            iv_pop_xiangqing_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addgouwuche(cartsGoods.get(finalI1).goodsid,cartsGoods.get(finalI1).goodsPrice);
                }
            });
            final int finalI = i;
            iv_pop_xiangqing_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String goodsNum = cartsGoods.get(finalI).goodsNum;
                    if (Integer.parseInt(goodsNum) == 1) {
                        getdelateshangpin(cartsGoods.get(finalI).goodsid, cartsGoods.get(finalI).goodsNum);
                    } else if (Integer.parseInt(goodsNum) > 1) {
                        getdelatenub(cartsGoods.get(finalI).goodsid, cartsGoods.get(finalI).goodsPrice);
                    }
                }
            });
            iv_qianggou_xiangqing_delate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getdelateshangpin(cartsGoods.get(finalI).goodsid,cartsGoods.get(finalI).goodsNum);
                }
            });
            layout_pop_add.addView(inflate);
        }
        popupWindow = new PopupWindow(inflate1, ViewGroup.LayoutParams.MATCH_PARENT,
                 ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        //产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation((View) parent.getParent(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }
    private void getdelateshangpin(String goodsId,String goodsNum) {
        if (popupWindow!=null){
            popupWindow.dismiss();
        }
        Map<String, String> params = new HashMap<>();
      /*  params.put("cmd", "'deleteGoods'");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("goodsId", "1");
        params.put("goodsNum", "1");
        params.put("token", token);*/
        String json="{\"cmd\":\"deleteGoods\",\"shangjiaid\":\"" + shangjiaid + "\"," +
                "\"uid\":\"" + uid +"\",\"goodsNum\":\"" + goodsNum + "\"" +
                ",\"goodsId\":\"" + goodsId + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post()//
                .url(this.getString(url))//
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
                        dialog.dismiss();
                        Gson gson = new Gson();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        if (resout_bean.result.equals("0")) {
                            ToastUtil.showToast(context, "删除购物车商品成功");
                            popuWindow1.dismiss();
                            getgouwuchedata(iv_shangpin_xiangqing_car,-1);
                        } else {
                            ToastUtil.showToast(context, resout_bean.resultNote);


                        }
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
        String json = "{\"cmd\":\"payway\",\"shangjiaid\":\"" + shangjiaid + "\"," +
                "\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()//
                .url(this.getString(url))//
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
                        pay_way_bean = new Gson().fromJson(response, Pay_way_Bean.class);
                        if (pay_way_bean.result.equals("1")) {
                            ToastUtil.showToast(context, pay_way_bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        dialog.dismiss();
                    }
                });
    }

}
