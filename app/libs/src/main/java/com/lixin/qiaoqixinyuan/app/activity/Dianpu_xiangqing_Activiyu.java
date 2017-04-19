package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.Dianpu_xiangqing_Adapter;
import com.lixin.qiaoqixinyuan.app.adapter.Home_list_qianggou_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Cargoos_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Dianpu_huodong_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Home_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Pay_way_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean_gouwuche;
import com.lixin.qiaoqixinyuan.app.bean.Shangjia_feilei_shangpin_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Shangjia_shangpin_fenlei_Bean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.CircleImageView;
import com.lixin.qiaoqixinyuan.app.view.MyListview;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.lixin.qiaoqixinyuan.R.id.content;
import static com.lixin.qiaoqixinyuan.R.id.iv_pop_xiangqing_add;
import static com.lixin.qiaoqixinyuan.R.id.iv_pop_xiangqing_reduce;
import static com.lixin.qiaoqixinyuan.R.id.iv_qianggou_xiangqing_delate;

public class Dianpu_xiangqing_Activiyu extends BaseActivity implements View.OnClickListener {

    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private ImageView iv_basesearch_shuru;
    private CircleImageView dianpu_xiangqing__icon;
    private TextView dianpu_xiangqing_title;
    private TextView dianpu_xiangqing_time;
    private LinearLayout layout_dianpu_xiangqing;
    private TextView dianpu_xiangqing_all;
    private TextView dianpu_xiangqing_qianggou;
    private LinearLayout layout_dianpu_xiangqing_fenlei;
    private ListView lv_dianpu_left;
    private ImageView iv_dianpu_xiangqing_car;
    private TextView tv_dianpu_xiangqing_nub;
    private TextView tv_dianpu_xiangqing_money;
    private TextView tv_dianpu_xiangqing_jiesuan;
    private RelativeLayout rl3;
    private PopupWindow popuWindow1;
    private PopupWindow popuWindow2;
    private View contentView1;
    private View contentView2;
    private TextView tv_pop_in;
    private TextView tv_pop_out;
    private List<String> mListType = new ArrayList<>();
    private LinearLayout layout_pop_add;
    private List<Cargoos_Bean.CartsGoods> cartsGoods;
    private View view1;
    private View view2;
    private MyListview lv_dianpu_right;
    private int nowPage = 1;
    private PullToRefreshListView prlv_xiangqing_list;
    private RelativeLayout relate;
    private Home_list_qianggou_Adapter home_list_qianggou_adapter;
    private List<Home_Bean.Huodongmodel> huodong = new ArrayList<>();
    private String uid;
    private String token;
    private String shangjiaId;
    private Pay_way_Bean pay_way_bean;
    private List<Shangjia_shangpin_fenlei_Bean.Shangpinfenlei> shangpinfenlei;
    private String shangpinway;
    private LinearLayout layout_qianggou;
    private String shangjianame;
    private String shangjiaicon;
    private PullToRefreshScrollView lv_dianpu_right_scroll;
    private String fenleiId;
    private List<Shangjia_feilei_shangpin_Bean.Shangpinlist> shangpin=new ArrayList<>();
    private ImageView iv_pop_xiangqing_reduce;
    private ImageView iv_qianggou_xiangqing_delate;
    private  ImageView iv_pop_xiangqing_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianpu_xiangqing__activiyu);
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        shangjiaId = getIntent().getStringExtra("shangjiaid");
        shangjianame = getIntent().getStringExtra("shangjianame");
        shangjiaicon = getIntent().getStringExtra("shangjiaicon");
        initView();
        clear();
        getpayway();
    }

    private void initView() {
        home_list_qianggou_adapter = new Home_list_qianggou_Adapter();
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        iv_basesearch_back.setOnClickListener(this);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        et_basesearch.setOnClickListener(this);
        iv_basesearch_shuru = (ImageView) findViewById(R.id.iv_basesearch_shuru);
        iv_basesearch_shuru.setOnClickListener(this);
        dianpu_xiangqing__icon = (CircleImageView) findViewById(R.id.dianpu_xiangqing__icon);
        iv_basesearch_shuru.setOnClickListener(this);
        dianpu_xiangqing_title = (TextView) findViewById(R.id.dianpu_xiangqing_title);
        dianpu_xiangqing_time = (TextView) findViewById(R.id.dianpu_xiangqing_time);
        layout_dianpu_xiangqing = (LinearLayout) findViewById(R.id.layout_dianpu_xiangqing);
        layout_dianpu_xiangqing.setOnClickListener(this);
        dianpu_xiangqing_all = (TextView) findViewById(R.id.dianpu_xiangqing_all);
        dianpu_xiangqing_all.setOnClickListener(this);
        dianpu_xiangqing_qianggou = (TextView) findViewById(R.id.dianpu_xiangqing_qianggou);
        dianpu_xiangqing_qianggou.setOnClickListener(this);
        layout_dianpu_xiangqing_fenlei = (LinearLayout) findViewById(R.id.layout_dianpu_xiangqing_fenlei);
        lv_dianpu_left = (ListView) findViewById(R.id.lv_dianpu_left);
        iv_dianpu_xiangqing_car = (ImageView) findViewById(R.id.iv_dianpu_xiangqing_car);
        iv_dianpu_xiangqing_car.setOnClickListener(this);
        tv_dianpu_xiangqing_nub = (TextView) findViewById(R.id.tv_dianpu_xiangqing_nub);
        tv_dianpu_xiangqing_money = (TextView) findViewById(R.id.tv_dianpu_xiangqing_money);
        tv_dianpu_xiangqing_jiesuan = (TextView) findViewById(R.id.tv_dianpu_xiangqing_jiesuan);
        tv_dianpu_xiangqing_jiesuan.setOnClickListener(this);
        dianpu_xiangqing_title.setText(shangjianame);
        ImageLoader.getInstance().displayImage(shangjiaicon, dianpu_xiangqing__icon, ImageLoaderUtil.DIO());
        rl3 = (RelativeLayout) findViewById(R.id.rl3);
        view1 = (View) findViewById(R.id.view2);
        view1.setOnClickListener(this);
        view2 = (View) findViewById(R.id.view3);
        view2.setOnClickListener(this);
        lv_dianpu_right = (MyListview) findViewById(R.id.lv_dianpu_right);
        prlv_xiangqing_list = (PullToRefreshListView) findViewById(R.id.prlv_xiangqing_list);
        prlv_xiangqing_list.setMode(PullToRefreshBase.Mode.BOTH);
        prlv_xiangqing_list.setAdapter(home_list_qianggou_adapter);
        relate = (RelativeLayout) findViewById(R.id.relate);
        prlv_xiangqing_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                huodong.clear();
                gethuodong();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                gethuodong();
            }
        });
        lv_dianpu_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                huodong.clear();
                nowPage = 1;
                shangpin.clear();
                fenleiId = shangpinfenlei.get(i).fenleiId;
                getshangpindata();
            }
        });
        layout_qianggou = (LinearLayout) findViewById(R.id.layout_qianggou);
        layout_qianggou.setOnClickListener(this);
        prlv_xiangqing_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, qianggou_xiangqing_Activity.class);
                intent.putExtra("shangjiaid", huodong.get(i - 1).shangjiaid);
                intent.putExtra("huodongid", huodong.get(i - 1).huodongid);
                startActivity(intent);
            }
        });
        lv_dianpu_right_scroll = (PullToRefreshScrollView) findViewById(R.id.lv_dianpu_right_scroll);
        lv_dianpu_right_scroll.setMode(PullToRefreshBase.Mode.BOTH);
        lv_dianpu_right_scroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                shangpin.clear();
                nowPage=1;
               getshangpindata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                nowPage++;
                getshangpindata();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_basesearch_back:
                finish();
                break;
            case R.id.tv_dianpu_xiangqing_jiesuan:
                initPopuWindow(view);
                break;
            case R.id.tv_pop_in:
                getgouwuchedata(view, 0);
               /* Intent intent2 = new Intent(Dianpu_xiangqing_Activiyu.this, Songhuo_shangmen_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("pay_way_bean", (Serializable) pay_way_bean);
                bundle.putSerializable("cartsGoods", (Serializable) cartsGoods);
                intent2.putExtra("type", "0");
                intent2.putExtra("shangjiaId", shangjiaId);
                intent2.putExtras(bundle);
                startActivity(intent2);*/
                break;
            case R.id.tv_pop_out:
                getgouwuchedata(view, 1);
                /*Intent intent3 = new Intent(Dianpu_xiangqing_Activiyu.this, Songhuo_shangmen_Activity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("pay_way_bean", (Serializable) pay_way_bean);
                bundle1.putSerializable("cartsGoods", (Serializable) cartsGoods);
                intent3.putExtra("type", "1");
                intent3.putExtra("shangjiaId", shangjiaId);
                intent3.putExtras(bundle1);
                startActivity(intent3);*/
                break;
            case R.id.iv_dianpu_xiangqing_car:
                getgouwuchedata(view, -1);
                break;
            case R.id.layout_dianpu_xiangqing:
                Intent intent = new Intent(Dianpu_xiangqing_Activiyu.this, Shangjia_xiangqing_Activity.class);
                intent.putExtra("shangjiaId", shangjiaId);
                startActivity(intent);
                break;
            case R.id.et_basesearch:
                Intent intent1 = new Intent(Dianpu_xiangqing_Activiyu.this, Search_Activity.class);
                intent1.putExtra("shangjiaId", shangjiaId);
                startActivity(intent1);
                break;
            case R.id.dianpu_xiangqing_qianggou:
                relate.setVisibility(View.INVISIBLE);
                layout_qianggou.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                view1.setVisibility(View.INVISIBLE);
                nowPage = 1;
                huodong.clear();
                gethuodong();
                break;
            case R.id.dianpu_xiangqing_all:
                relate.setVisibility(View.VISIBLE);
                layout_qianggou.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view1.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initPopuWindow(View parent) {
        if (popuWindow1 == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            contentView1 = mLayoutInflater.inflate(R.layout.popuwindow, null);
            popuWindow1 = new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        tv_pop_in = (TextView) contentView1.findViewById(R.id.tv_pop_in);
        tv_pop_in.setText("送货上门");
        tv_pop_in.setOnClickListener(this);
        tv_pop_out = (TextView) contentView1.findViewById(R.id.tv_pop_out);
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

    private void initPopuWindow2(View parent) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        contentView2 = mLayoutInflater.inflate(R.layout.popuwindoe_gouwuche, null);
        layout_pop_add = (LinearLayout) contentView2.findViewById(R.id.layout_pop_add);
        for (int i = 0; i < cartsGoods.size(); i++) {
            View inflate = View.inflate(Dianpu_xiangqing_Activiyu.this, R.layout.popwindow_gouwuche_item, null);
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
                    if (Integer.parseInt(goodsNum)==1) {
                        getdelateshangpin(cartsGoods.get(finalI).goodsid, cartsGoods.get(finalI).goodsNum);
                    } else if (Integer.parseInt(goodsNum)> 1) {
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
            layout_pop_add.addView(inflate);
        }
        popuWindow2 = new PopupWindow(contentView2, ViewGroup.LayoutParams.MATCH_PARENT,
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

    private void gethuodong() {//获取所有活动
       /* cmd:”seachgoodslist”
        shangjiaid: “1”       //商家id
        nowPage:"1"     //当前页 */
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "seachgoodslist");
        params.put("shangjiaid", shangjiaId);
        params.put("nowPage", nowPage + "");*/
        String json = "{\"cmd\":\"seachgoodslist\",\"shangjiaid\":\"" + shangjiaId + "\",\"nowPage\":\""
                + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, e.getLocalizedMessage());
                        dialog.dismiss();
                        prlv_xiangqing_list.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        Dianpu_huodong_Bean dianpu_huodong_bean = gson.fromJson(response, Dianpu_huodong_Bean.class);
                        if (dianpu_huodong_bean.result.equals("1")) {
                            ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, dianpu_huodong_bean.resultNote);
                            dialog.dismiss();
                            prlv_xiangqing_list.onRefreshComplete();
                            return;
                        }
                        if (Integer.parseInt(dianpu_huodong_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, "没有更多了");
                            prlv_xiangqing_list.onRefreshComplete();
                            return;
                        }
                        List<Home_Bean.Huodongmodel> huodongmodel = dianpu_huodong_bean.huodongmodel;
                        huodong.addAll(huodongmodel);
                        home_list_qianggou_adapter.setHuodongmodel(huodong);
                        prlv_xiangqing_list.setAdapter(home_list_qianggou_adapter);
                        prlv_xiangqing_list.onRefreshComplete();
                    }
                });
    }

    private void getfeileidata() {
        /* cmd:”shangjiafenleilist”
        shangjiaid: '1'     //商家id */
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "shangjiafenleilist");
        params.put("shangjiaid", shangjiaId);*/
        String json = "{\"cmd\":\"shangjiafenleilist\",\"shangjiaid\":\"" + shangjiaId + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Shangjia_shangpin_fenlei_Bean shangjia_shangpin_fenlei_bean = gson.fromJson(response, Shangjia_shangpin_fenlei_Bean.class);
                        if (shangjia_shangpin_fenlei_bean.result.equals("1")) {
                            ToastUtil.showToast(context, shangjia_shangpin_fenlei_bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        shangpinfenlei = shangjia_shangpin_fenlei_bean.shangpinfenlei;
                        dianpu_xiangqing_time.setText(shangjia_shangpin_fenlei_bean.shangjiatimetype);
                        if (shangpinfenlei != null) {
                            for (int i = 0; i < shangpinfenlei.size(); i++) {
                                Shangjia_shangpin_fenlei_Bean.Shangpinfenlei shangpinfenlei = shangjia_shangpin_fenlei_bean.shangpinfenlei.get(i);
                                mListType.add(shangpinfenlei.fenleiname);
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<>(Dianpu_xiangqing_Activiyu.this,
                                    R.layout.item_main_popup_list, mListType);
                            lv_dianpu_left.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if (shangpinfenlei.size() != 0) {
                               fenleiId = shangpinfenlei.get(0).fenleiId;
                                getshangpindata();
                            }
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void getshangpindata() {
      /*  cmd:”fenleishangpinlist”
        shangjiaid: '1'     //商家id
        fenleiId: '1'                //分类id
        nowPage:"1"     //当前页   */
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "fenleishangpinlist");
        params.put("shangjiaId", shangjiaId);
        params.put("fenleiId", "1");
        params.put("nowPage", nowPage+"");*/
        String json = "{\"cmd\":\"fenleishangpinlist\",\"shangjiaid\":\"" + shangjiaId + "\"," +
                "\"fenleiId\":\"" + fenleiId + "\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, e.getLocalizedMessage());
                        dialog.dismiss();
                        lv_dianpu_right_scroll.onRefreshComplete();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        lv_dianpu_right_scroll.onRefreshComplete();
                        Shangjia_feilei_shangpin_Bean shangjia_feilei_shangpin_bean = gson.fromJson(response, Shangjia_feilei_shangpin_Bean.class);
                        if (shangjia_feilei_shangpin_bean.result.equals("1")) {
                            ToastUtil.showToast(context, shangjia_feilei_shangpin_bean.resultNote);

                            return;
                        }
                        if (Integer.parseInt(shangjia_feilei_shangpin_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            return;
                        }
                        final List<Shangjia_feilei_shangpin_Bean.Shangpinlist> shangpinlist = shangjia_feilei_shangpin_bean.shangpinlist;
                        Dianpu_xiangqing_Adapter dianpu_xiangqing_adapter = new Dianpu_xiangqing_Adapter(dialog, shangjiaId, tv_dianpu_xiangqing_nub, tv_dianpu_xiangqing_money);
                        shangpin.addAll(shangpinlist);
                        dianpu_xiangqing_adapter.setShangpinlist(shangpin);
                        lv_dianpu_right.setAdapter(dianpu_xiangqing_adapter);
                        dianpu_xiangqing_adapter.notifyDataSetChanged();
                        lv_dianpu_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(Dianpu_xiangqing_Activiyu.this, Shangpin_xiangqing_Activity.class);
                                intent.putExtra("goodsId", shangpinlist.get(i).goodsid);
                                intent.putExtra("shangjiaid", shangjiaId);
                                startActivity(intent);
                            }
                        });
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
        String json = "{\"cmd\":\"addCartGoods\",\"shangjiaid\":\"" + shangjiaId + "\"," +
                "\"uid\":\"" + uid + "\",\"goodsprice\":\"" + goodsprice + "\"" +
                ",\"goodsId\":\"" + goodsId + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()//
                .url(this.getString(R.string.url))
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    private Resout_Bean_gouwuche resout_bean_gouwuche;

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        resout_bean_gouwuche = gson.fromJson(response, Resout_Bean_gouwuche.class);
                        if (resout_bean_gouwuche.result.equals("0")) {
                            ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, "添加购物车成功");
                            getgouwuchedata(iv_dianpu_xiangqing_car, -1);
                            dialog.dismiss();
                        } else {
                            ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, resout_bean_gouwuche.resultNote);
                            dialog.dismiss();
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
        String json = "{\"cmd\":\"deleteCartGoods\",\"shangjiaid\":\"" + shangjiaId + "\"," +
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
                        ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        if (resout_bean.result.equals("0")) {
                            ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, "删除购物车数量成功");
                            getgouwuchedata(iv_dianpu_xiangqing_car, -1);
                            dialog.dismiss();
                        } else {
                            ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, resout_bean.resultNote);
                            dialog.dismiss();
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
        String json = "{\"cmd\":\"deleteGoods\",\"shangjiaid\":\"" + shangjiaId + "\"," +
                "\"uid\":\"" + uid + "\",\"goodsNum\":\"" + goodsNum + "\"" +
                ",\"goodsId\":\"" + goodsId + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        if (resout_bean.result.equals("0")) {
                            ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, "删除购物车商品成功");
                            popuWindow2.dismiss();
                            getgouwuchedata(iv_dianpu_xiangqing_car, -1);
                            dialog.dismiss();
                        } else {
                            ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, resout_bean.resultNote);
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void getgouwuchedata(final View view, final int type) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "''getCarts''");
        params.put("uid", uid);
        params.put("shangjiaId", shangjiaId);
        params.put("token", token);*/
        String json = "{\"cmd\":\"getCarts\",\"shangjiaid\":\"" + shangjiaId + "\"," +
                "\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, e.getLocalizedMessage());
                        dialog.dismiss();
                       iv_pop_xiangqing_reduce.setOnClickListener(Dianpu_xiangqing_Activiyu.this);
                        iv_qianggou_xiangqing_delate.setOnClickListener(Dianpu_xiangqing_Activiyu.this);
                        iv_pop_xiangqing_add.setOnClickListener(Dianpu_xiangqing_Activiyu.this);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        iv_pop_xiangqing_reduce.setOnClickListener(Dianpu_xiangqing_Activiyu.this);
                        iv_qianggou_xiangqing_delate.setOnClickListener(Dianpu_xiangqing_Activiyu.this);
                        iv_pop_xiangqing_add.setOnClickListener(Dianpu_xiangqing_Activiyu.this);
                        Gson gson = new Gson();
                        Cargoos_Bean cargoos_bean = gson.fromJson(response, Cargoos_Bean.class);
                        if (cargoos_bean.result.equals("1")) {
                            ToastUtil.showToast(Dianpu_xiangqing_Activiyu.this, cargoos_bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        cartsGoods = cargoos_bean.cartsGoods;
                        int nub=0;
                        dialog.dismiss();
                            for (int i = 0; i < cartsGoods.size(); i++) {
                                int i1 = Integer.parseInt(cartsGoods.get(i).goodsNum);
                                nub += i1;
                            }
                            tv_dianpu_xiangqing_nub.setText(nub + "");
                        if (cartsGoods.size()==0){
                            tv_dianpu_xiangqing_money.setText("￥" + 0.0);
                        }else {
                            tv_dianpu_xiangqing_money.setText("￥" + cargoos_bean.shangjiaprice);
                        }
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
                            intent2.putExtra("shangjiaId", shangjiaId);
                            intent2.putExtras(bundle);
                            startActivity(intent2);
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
                        pay_way_bean = new Gson().fromJson(response, Pay_way_Bean.class);
                        if (pay_way_bean.result.equals("1")) {
                            ToastUtil.showToast(context, pay_way_bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        getfeileidata();
                        shangpinway = pay_way_bean.shangpinway;
                        dialog.dismiss();
                    }
                });
    }
}
