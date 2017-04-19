package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.Huodong_xiangqing_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Cargoos_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Huodong_xiangqing_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Pay_way_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean_gouwuche;
import com.lixin.qiaoqixinyuan.app.bean.Shangjia_shangpin_search_Bean;
import com.lixin.qiaoqixinyuan.app.util.AppUtil;
import com.lixin.qiaoqixinyuan.app.util.LogUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.GlideImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
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

import static android.R.attr.id;
import static com.lixin.qiaoqixinyuan.R.id.iv_pop_xiangqing_add;
import static com.lixin.qiaoqixinyuan.R.id.iv_pop_xiangqing_reduce;
import static com.lixin.qiaoqixinyuan.R.id.iv_pop_xiangqing_title;
import static com.lixin.qiaoqixinyuan.R.id.iv_qianggou_xiangqing_delate;
import static com.lixin.qiaoqixinyuan.R.id.layout_gouwuche;

public class qianggou_xiangqing_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private Banner qianggou_xiangqing_image;
    private PullToRefreshListView prlv_qianggou_xiangqing_list;
    private ImageView iv_qianggou_xiangqing_car;
    private TextView tv_qianggou_xiangqing_nub;
    private TextView tv_qianggou_xiangqing_money;
    private TextView tv_qianggou_xiangqing_jiesuan;
    private LinearLayout activity_qianggou_xiangqing_;
    private int nowPage = 1;
    private TextView tv_qianggou_xiangqing_title;
    private TextView tv_qianggou_xiangqing_jinru;
    private TextView tv_qianggou_xiangqing_time;
    private TextView tv_qianggou_xiangqing_shengyu;
    private TextView tv_qianggou_xiangqing_xiangqing;
    private Huodong_xiangqing_Adapter dianpu_shangpin_adapter;
    private String shangjiaid;
    private String huodongid;
    private List<Huodong_xiangqing_Bean.ImagesList> imagesList;
    private List<Shangjia_shangpin_search_Bean.Goodslist> goods = new ArrayList<>();
    private Huodong_xiangqing_Bean huodong_xiangqing_bean;
    private PopupWindow popuWindow1;
    private PopupWindow popuWindow2;
    private View contentView;
    private TextView tv_pop_in;
    private TextView tv_pop_out;
    private String uid;
    private String token;
    private Pay_way_Bean pay_way_bean;
    private String shangpinway;
    private List<Cargoos_Bean.CartsGoods> cartsGoods;
    private LinearLayout layout_pop_add;
    private RelativeLayout rl_gouwuche;
    private String lat;
    private String lon;
    private List<String> images = new ArrayList<>();
    private TextView tv_juli_time;
    private TextView tv_jubao;
    ImageView iv_pop_xiangqing_add ;
    ImageView iv_pop_xiangqing_reduce;
    ImageView iv_qianggou_xiangqing_delate ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qianggou_xiangqing_);
        shangjiaid = getIntent().getStringExtra("shangjiaid");
        huodongid = getIntent().getStringExtra("huodongid");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
        lat = SharedPreferencesUtil.getSharePreStr(context, "lat");
        lon = SharedPreferencesUtil.getSharePreStr(context, "lon");
        clear();
        initView();
        getdata();
        getpayway();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("活动详情");
        qianggou_xiangqing_image = (Banner) findViewById(R.id.qianggou_xiangqing_image);
        prlv_qianggou_xiangqing_list = (PullToRefreshListView) findViewById(R.id.prlv_qianggou_xiangqing_list);
        iv_qianggou_xiangqing_car = (ImageView) findViewById(R.id.iv_qianggou_xiangqing_car);
        tv_qianggou_xiangqing_nub = (TextView) findViewById(R.id.tv_qianggou_xiangqing_nub);
        tv_qianggou_xiangqing_money = (TextView) findViewById(R.id.tv_qianggou_xiangqing_money);
        tv_qianggou_xiangqing_jiesuan = (TextView) findViewById(R.id.tv_qianggou_xiangqing_jiesuan);
        tv_qianggou_xiangqing_jiesuan.setOnClickListener(this);
        activity_qianggou_xiangqing_ = (LinearLayout) findViewById(R.id.activity_qianggou_xiangqing_);
        tv_qianggou_xiangqing_title = (TextView) findViewById(R.id.tv_qianggou_xiangqing_title);
        tv_qianggou_xiangqing_jinru = (TextView) findViewById(R.id.tv_qianggou_xiangqing_jinru);
        tv_qianggou_xiangqing_jinru.setOnClickListener(this);
        tv_qianggou_xiangqing_time = (TextView) findViewById(R.id.tv_qianggou_xiangqing_time);
        tv_qianggou_xiangqing_shengyu = (TextView) findViewById(R.id.tv_qianggou_xiangqing_shengyu);
        tv_qianggou_xiangqing_xiangqing = (TextView) findViewById(R.id.tv_qianggou_xiangqing_xiangqing);
        prlv_qianggou_xiangqing_list.setMode(PullToRefreshBase.Mode.BOTH);
        dianpu_shangpin_adapter = new Huodong_xiangqing_Adapter(shangjiaid, dialog, tv_qianggou_xiangqing_nub,
                tv_qianggou_xiangqing_money);
        prlv_qianggou_xiangqing_list.setAdapter(dianpu_shangpin_adapter);
        prlv_qianggou_xiangqing_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                goods.clear();
                getdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                getdata();
            }
        });
        rl_gouwuche = (RelativeLayout) findViewById(R.id.rl_gouwuche);
        rl_gouwuche.setOnClickListener(this);
        prlv_qianggou_xiangqing_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, Shangpin_xiangqing_Activity.class);
                intent.putExtra("shangjiaid", shangjiaid);
                intent.putExtra("goodsId", goods.get(i - 1).goodsid);
                startActivity(intent);
            }
        });
        tv_juli_time = (TextView) findViewById(R.id.tv_juli_time);
        tv_juli_time.setOnClickListener(this);
        tv_jubao = (TextView) findViewById(R.id.tv_jubao);
        tv_jubao.setOnClickListener(this);
        tv_jubao.setText("");
        tv_jubao.setBackgroundResource(R.mipmap.share);
    }

    private void setbanner() {
    /* images.add("http://www.pconline.com.cn/pcedu/sj/pm/photoshop/0412/pic/23psenlarge01.jpg");
     images.add("http://www.pconline.com.cn/pcedu/sj/pm/photoshop/0412/pic/23psenlarge01.jpg");
     images.add("http://www.pconline.com.cn/pcedu/sj/pm/photoshop/0412/pic/23psenlarge01.jpg");
     images.add("http://www.pconline.com.cn/pcedu/sj/pm/photoshop/0412/pic/23psenlarge01.jpg");*/
        for (int i = 0; i < imagesList.size(); i++) {
            images.add(imagesList.get(i).imageUrl);
        }
        DisplayMetrics displayMetrics = AppUtil.getDisplayMetrics(context);
        int widthPixels = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = qianggou_xiangqing_image.getLayoutParams();
        layoutParams.height = widthPixels / 2;
        qianggou_xiangqing_image.setLayoutParams(layoutParams);
        qianggou_xiangqing_image.setImageLoader(new GlideImageLoader());
        //设置图片集合
        qianggou_xiangqing_image.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        qianggou_xiangqing_image.start();
        qianggou_xiangqing_image.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(context, Dianpu_xiangqing_Activiyu.class);
            }
        });
    }

    private void getdata() {
        /*cmd:”huodongdetails”      //搜索活动
        huodongid: '1'                    //活动id
        shangjiaid:"35"              //商家id
        nowPage:"1"     //当前页 */
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "huodongdetails");
        params.put("huodongid", huodongid);
        params.put("shangjiaid", shangjiaid);
        params.put("nowPage", nowPage + "");*/
        String json = "{\"cmd\":\"huodongdetails\",\"huodongid\":\"" + huodongid + "\",\"shangjiaid\":\"" +
                shangjiaid + "\" ,\"nowPage\":\"" + nowPage + "\",\"lat\":\"" + lat + "\"" +
                ",\"lon\":\"" + lon + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(qianggou_xiangqing_Activity.this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(qianggou_xiangqing_Activity.this, e.getMessage());
                        prlv_qianggou_xiangqing_list.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        prlv_qianggou_xiangqing_list.onRefreshComplete();
                        dialog.dismiss();
                        huodong_xiangqing_bean = gson.fromJson(response, Huodong_xiangqing_Bean.class);
                        if (huodong_xiangqing_bean.result.equals("1")) {
                            ToastUtil.showToast(qianggou_xiangqing_Activity.this, huodong_xiangqing_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(huodong_xiangqing_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(qianggou_xiangqing_Activity.this, "没有更多了");
                            return;
                        }
                        List<Shangjia_shangpin_search_Bean.Goodslist> goodslist = huodong_xiangqing_bean.goodslist;
                        imagesList = huodong_xiangqing_bean.imagesList;
                        tv_qianggou_xiangqing_title.setText(huodong_xiangqing_bean.huodongtitle);
                        tv_qianggou_xiangqing_time.setText(huodong_xiangqing_bean.startTime + huodong_xiangqing_bean.endTime);
                        tv_qianggou_xiangqing_xiangqing.setText(huodong_xiangqing_bean.huodongdescribe);
                        if (huodong_xiangqing_bean.huodongtype.equals("0")) {
                            tv_juli_time.setText("距离活动结束时间" + huodong_xiangqing_bean.huodongtime + "天");
                        } else if (huodong_xiangqing_bean.huodongtype.equals("1")) {
                            tv_juli_time.setText("距离活动开始时间" + huodong_xiangqing_bean.huodongtime + "天");
                        } else {
                            tv_juli_time.setText("活动已结束");
                        }
                        goods.addAll(goodslist);
                        dianpu_shangpin_adapter.setGoods(goods);
                        dianpu_shangpin_adapter.notifyDataSetChanged();
                        for (int i = 0; i < imagesList.size(); i++) {
                            images.add(imagesList.get(i).imageUrl);
                        }
                        setbanner();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_qianggou_xiangqing_jinru:
                Intent intent = new Intent(context, Dianpu_xiangqing_Activiyu.class);
                intent.putExtra("shangjiaid", shangjiaid);
                startActivity(intent);
                break;
            case R.id.tv_qianggou_xiangqing_jiesuan:
                initPopuWindow(view);
                break;
            case R.id.tv_pop_in:
                getgouwuchedata(view, 0);
                break;
            case R.id.tv_pop_out:
                getgouwuchedata(view, 1);
               /* Intent intent3 = new Intent(context, Songhuo_shangmen_Activity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("pay_way_bean", (Serializable) pay_way_bean);
                bundle1.putSerializable("cartsGoods", (Serializable) cartsGoods);
                intent3.putExtra("type", "1");
                intent3.putExtra("shangjiaId", shangjiaid);
                intent3.putExtras(bundle1);
                startActivity(intent3);*/
                break;
            case R.id.rl_gouwuche:
                getgouwuchedata(view, -1);
                break;
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_jubao:
                getshareuri();
                break;
        }
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
                        dialog.dismiss();
                        pay_way_bean = new Gson().fromJson(response, Pay_way_Bean.class);
                        if (pay_way_bean.result.equals("1")) {
                            ToastUtil.showToast(context, pay_way_bean.resultNote);
                            return;
                        }
                        shangpinway = pay_way_bean.shangpinway;

                    }
                });
    }

    private void initPopuWindow(View parent) {
        if (popuWindow1 == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            contentView = mLayoutInflater.inflate(R.layout.popuwindow, null);
            popuWindow1 = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        tv_pop_in = (TextView) contentView.findViewById(R.id.tv_pop_in);
        tv_pop_in.setText("送货上门");
        tv_pop_in.setOnClickListener(this);
        tv_pop_out = (TextView) contentView.findViewById(R.id.tv_pop_out);
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

    private void getgouwuchedata(final View view, final int type) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "''getCarts''");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("token", token);*/
        String json = "{\"cmd\":\"getCarts\",\"shangjiaid\":\"" + shangjiaid + "\"," +
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
                        ToastUtil.showToast(context, e.getLocalizedMessage());
                        dialog.dismiss();
                         iv_pop_xiangqing_add.setOnClickListener(qianggou_xiangqing_Activity.this);
                        iv_pop_xiangqing_reduce.setOnClickListener(qianggou_xiangqing_Activity.this);
                        iv_qianggou_xiangqing_delate.setOnClickListener(qianggou_xiangqing_Activity.this);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        iv_pop_xiangqing_add.setOnClickListener(qianggou_xiangqing_Activity.this);
                        iv_pop_xiangqing_reduce.setOnClickListener(qianggou_xiangqing_Activity.this);
                        iv_qianggou_xiangqing_delate.setOnClickListener(qianggou_xiangqing_Activity.this);
                        Gson gson = new Gson();
                        Log.e("gouwuche",response);
                        dialog.dismiss();
                        Cargoos_Bean cargoos_bean = gson.fromJson(response, Cargoos_Bean.class);
                        if (cargoos_bean.result.equals("1")) {
                            ToastUtil.showToast(context, cargoos_bean.resultNote);
                            return;
                        }
                        int nub = 0;
                        cartsGoods = cargoos_bean.cartsGoods;
                        for (int i = 0; i < cartsGoods.size(); i++) {
                            int i1 = Integer.parseInt(cartsGoods.get(i).goodsNum);
                            nub += i1;
                        }
                        tv_qianggou_xiangqing_nub.setText(nub + "");
                        tv_qianggou_xiangqing_money.setText("￥" + cargoos_bean.shangjiaprice);
                        if (type == -1) {
                            initPopuWindow2(view);
                        } else {
                            if (cargoos_bean.cartsGoods.size()==0){
                                ToastUtil.showToast(context,"您还没有购买商品");
                                return;
                            }
                            Intent intent2 = new Intent(context, Songhuo_shangmen_Activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("pay_way_bean", (Serializable) pay_way_bean);
                            bundle.putSerializable("cartsGoods", (Serializable) cartsGoods);
                            intent2.putExtra("type", type + "");
                            intent2.putExtra("shangjiaId", shangjiaid);
                            intent2.putExtras(bundle);
                            startActivity(intent2);
                        }
                    }
                });
    }

    private void initPopuWindow2(View parent) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        contentView = mLayoutInflater.inflate(R.layout.popuwindoe_gouwuche, null);
        layout_pop_add = (LinearLayout) contentView.findViewById(R.id.layout_pop_add);
        for (int i = 0; i < cartsGoods.size(); i++) {
            View inflate = View.inflate(context, R.layout.popwindow_gouwuche_item, null);
            LinearLayout viewById = (LinearLayout) inflate.findViewById(R.id.layout_gouwuche);
            TextView iv_pop_xiangqing_title = (TextView) inflate.findViewById(R.id.iv_pop_xiangqing_title);
            TextView iv_pop_xiangqing_price = (TextView) inflate.findViewById(R.id.iv_pop_xiangqing_price);
            TextView tv_pop_xiangqing_nub = (TextView) inflate.findViewById(R.id.tv_pop_xiangqing_nub);
            iv_pop_xiangqing_add = (ImageView) inflate.findViewById(R.id.iv_pop_xiangqing_add);
            iv_pop_xiangqing_reduce = (ImageView) inflate.findViewById(R.id.iv_pop_xiangqing_reduce);
            iv_qianggou_xiangqing_delate = (ImageView) inflate.findViewById(R.id.iv_qianggou_xiangqing_delate);
            iv_pop_xiangqing_title.setText(cartsGoods.get(i).goodsName);
            iv_pop_xiangqing_price.setText(cartsGoods.get(i).goodsPrice);
            tv_pop_xiangqing_nub.setText(cartsGoods.get(i).goodsNum);
            final int finalI1 = i;
            iv_pop_xiangqing_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_pop_xiangqing_add.setOnClickListener(null);
                    addgouwuche(cartsGoods.get(finalI1).goodsid, cartsGoods.get(finalI1).goodsPrice);
                }
            });
            final int finalI = i;
            iv_pop_xiangqing_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_pop_xiangqing_reduce.setOnClickListener(null);
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
                    iv_qianggou_xiangqing_delate.setOnClickListener(null);
                    getdelateshangpin(cartsGoods.get(finalI).goodsid, cartsGoods.get(finalI).goodsNum);
                }
            });
            layout_pop_add.addView(viewById);
        }
        popuWindow2 = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popuWindow2.setBackgroundDrawable(cd);
        //产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popuWindow2.setOutsideTouchable(true);
        popuWindow2.setFocusable(true);
        popuWindow2.showAtLocation((View) parent.getParent(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popuWindow2.update();
        popuWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void addgouwuche(String goodsId, String goodsprice) {
        if (popuWindow2!=null){
            popuWindow2.dismiss();
        }
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "''addCartGoods''");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("goodsprice", "1");
        params.put("goodsId", "1");
        params.put("token", token);*/
        String json = "{\"cmd\":\"addCartGoods\",\"shangjiaid\":\"" + shangjiaid + "\"," +
                "\"uid\":\"" + uid + "\",\"goodsprice\":\"" + goodsprice + "\"" +
                ",\"goodsId\":\"" + goodsId + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()//
                .url(this.getString(R.string.url))//
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
                        dialog.dismiss();
                        resout_bean_gouwuche = gson.fromJson(response, Resout_Bean_gouwuche.class);
                        if (resout_bean_gouwuche.result.equals("0")) {
                            ToastUtil.showToast(context, "添加购物车成功");
                            getgouwuchedata(iv_qianggou_xiangqing_car, -1);
                        } else {
                            ToastUtil.showToast(context, resout_bean_gouwuche.resultNote);
                        }
                    }
                });
    }

    private void getdelatenub(String goodsId, String goodsprice) {
        popuWindow2.dismiss();
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "'deleteCartGoods'");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("goodsId", "1");
        params.put("token", token);*/
        String json = "{\"cmd\":\"deleteCartGoods\",\"shangjiaid\":\"" + shangjiaid + "\"," +
                "\"uid\":\"" + uid + "\",\"goodsprice\":\"" + goodsprice + "\"" +
                ",\"goodsId\":\"" + goodsId + "\",\"token\":\"" + token + "\"}";
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
                        ToastUtil.showToast(context, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        if (resout_bean.result.equals("0")) {
                            ToastUtil.showToast(context, "删除购物车数量成功");
                            getgouwuchedata(iv_qianggou_xiangqing_car, -1);
                        } else {
                            ToastUtil.showToast(context, resout_bean.resultNote);
                        }
                    }
                });
    }

    private void getdelateshangpin(String goodsId, String goodsNum) {
        popuWindow2.dismiss();
        Map<String, String> params = new HashMap<>();
      /*  params.put("cmd", "'deleteGoods'");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("goodsId", "1");
        params.put("goodsNum", "1");
        params.put("token", token);*/
        String json = "{\"cmd\":\"deleteGoods\",\"shangjiaid\":\"" + shangjiaid + "\"," +
                "\"uid\":\"" + uid + "\",\"goodsNum\":\"" + goodsNum + "\"" +
                ",\"goodsId\":\"" + goodsId + "\",\"token\":\"" + token + "\"}";
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
                        ToastUtil.showToast(context, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        if (resout_bean.result.equals("0")) {
                            ToastUtil.showToast(context, "删除购物车商品成功");
                            popuWindow2.dismiss();
                            getgouwuchedata(iv_qianggou_xiangqing_car, -1);
                        } else {
                            ToastUtil.showToast(context, resout_bean.resultNote);

                        }
                    }
                });
    }

    private void clear() {
       /* cmd：'clearCartGoods'
        uid: 12*/
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"clearCartGoods\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
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
                        dialog.dismiss();
                    }
                });
    }

    private void getshareuri() {
      /*  cmd:”sixshare”
        type:"0"  //0贴吧 1二手出租 2二手求购 3招聘 4求职 5房屋出租 6房屋求购 7活动 8商品 9商家
        contentid:"12"   //内容id
        nowPage:"1"		//当前页(分享活动使用)
        lat:""			//纬度(分享活动使用)
        lon:""			//经度(分享活动使用) */
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"sixshare\",\"type\":\"" + 7 + "\"," +
                "\"contentid\":\"" + huodongid + "\",\"nowPage\":\"" + nowPage + "\",\"lat\":\"" + lat + "\"" +
                ",\"lon\":\"" + lon + "\"}";
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
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            String sixshareone = object.getString("sixshareone");
                            if ("0".equals(result)) {
                                new ShareAction(qianggou_xiangqing_Activity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                        .withTitle("撬动沁源")
                                        .withMedia(new UMImage(context, images.get(0)))
                                        .withTargetUrl(sixshareone)
                                        .withText(huodong_xiangqing_bean.huodongtitle)
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
            LogUtil.d("plat", "platform" + platform);
            ToastUtil.showToast(context, platform + " 分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToast(context, platform + " 分享失败啦");
            if (t != null) {
                LogUtil.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToast(context, platform + " 分享取消了");
        }
    };
}
