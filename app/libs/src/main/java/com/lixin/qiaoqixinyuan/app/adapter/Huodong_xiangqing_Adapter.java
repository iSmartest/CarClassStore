package com.lixin.qiaoqixinyuan.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.activity.Dianpu_xiangqing_Activiyu;
import com.lixin.qiaoqixinyuan.app.activity.Songhuo_shangmen_Activity;
import com.lixin.qiaoqixinyuan.app.bean.Cargoos_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean_gouwuche;
import com.lixin.qiaoqixinyuan.app.bean.Shangjia_feilei_shangpin_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Shangjia_shangpin_search_Bean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.R.attr.theme;
import static android.R.attr.type;
import static android.content.Context.TELEPHONY_SERVICE;
import static com.lixin.qiaoqixinyuan.R.id.tv_qianggou_xiangqing_money;
import static com.lixin.qiaoqixinyuan.R.id.tv_qianggou_xiangqing_nub;
import static com.umeng.socialize.Config.dialog;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class Huodong_xiangqing_Adapter extends BaseAdapter {

    private Context context;
    private Dialog dialog;
    private String token;
    private String uid;
    private String shangjiaid;
    private TextView mnub;
    private TextView mmoney;
    public void setGoods(List<Shangjia_shangpin_search_Bean.Goodslist> goods) {
        this.goods = goods;
    }

    private  List<Shangjia_shangpin_search_Bean.Goodslist> goods ;
   public Huodong_xiangqing_Adapter(String shangjiaid,Dialog dialog,TextView nub,TextView money){
      this.shangjiaid=shangjiaid;
      this.dialog=dialog;
       this.mnub=nub;
       this.mmoney=money;
  }
    @Override
    public int getCount() {
        return goods == null?0:goods.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        uid=SharedPreferencesUtil.getSharePreStr(context,"uid");
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        ViewHolder vh = null;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.qianggou_xiangqing_item, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();

        }
        final Shangjia_shangpin_search_Bean.Goodslist goodslist = goods.get(i);
        ImageLoader.getInstance().displayImage(goodslist.goodsimage, vh.iv_qianggou_xiangqing_imange, ImageLoaderUtil.DIO());
        vh.tv_qianggou_xiangqing_title.setText(goodslist.shangpinname);
        vh.tv_qianggou_xiangqing_nub.setText(goodslist.xianprice);
        vh.tv_qianggou_xiangqing_dianji.setText(goodslist.clicknumber);
        vh.tv_qianggou_xiangqing_message.setText(goodslist.shangjiascribe);
        vh.tv_qianggou_xiangqing_new.setText("￥："+goodslist.xianprice);
        vh.tv_qianggou_xiangqing_old.setText("原价："+goodslist.yuanprice);
        if (goodslist.shgangpintype.equals("0")) {
            vh.iv_qianggou_xiangqing_icon.setVisibility(View.VISIBLE);
        } else {
            vh.iv_qianggou_xiangqing_icon.setVisibility(View.GONE);
        }
        vh.iv_qianggou_xiangqing_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addgouwuche(goodslist.xianprice,goodslist.goodsid);
            }
        });
        return view;
    }

    class ViewHolder {
        public View rootView;
        public ImageView iv_qianggou_xiangqing_imange;
        public ImageView iv_qianggou_xiangqing_icon;
        public TextView tv_qianggou_xiangqing_title;
        public ImageView iv_qianggou_xiangqing_add;
        public TextView tv_qianggou_xiangqing_nub;
        public ImageView iv_qianggou_xiangqing_reduce;
        public TextView tv_qianggou_xiangqing_message;
        public TextView tv_qianggou_xiangqing_new;
        public TextView tv_qianggou_xiangqing_old;
        public TextView tv_qianggou_xiangqing_dianji;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_qianggou_xiangqing_imange = (ImageView) rootView.findViewById(R.id.iv_qianggou_xiangqing_imange);
            this.iv_qianggou_xiangqing_icon = (ImageView) rootView.findViewById(R.id.iv_qianggou_xiangqing_icon);
            this.tv_qianggou_xiangqing_title = (TextView) rootView.findViewById(R.id.tv_qianggou_xiangqing_title);
            this.iv_qianggou_xiangqing_add = (ImageView) rootView.findViewById(R.id.iv_qianggou_xiangqing_add);
            this.tv_qianggou_xiangqing_nub = (TextView) rootView.findViewById(R.id.tv_qianggou_xiangqing_nub);
            this.iv_qianggou_xiangqing_reduce = (ImageView) rootView.findViewById(R.id.iv_qianggou_xiangqing_reduce);
            this.tv_qianggou_xiangqing_message = (TextView) rootView.findViewById(R.id.tv_qianggou_xiangqing_message);
            this.tv_qianggou_xiangqing_new = (TextView) rootView.findViewById(R.id.tv_qianggou_xiangqing_new);
            this.tv_qianggou_xiangqing_old = (TextView) rootView.findViewById(R.id.tv_qianggou_xiangqing_old);
            this.tv_qianggou_xiangqing_dianji = (TextView) rootView.findViewById(R.id.tv_qianggou_xiangqing_dianji);
        }

    }
    private void addgouwuche(String goodsprice,String goodsid) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "''addCartGoods''");
        params.put("uid", "12");
        params.put("shangjiaId", "1");
        params.put("goodsprice", "1");
        params.put("goodsId", "1");*/
        String json="{\"cmd\":\"addCartGoods\",\"shangjiaid\":\"" + shangjiaid + "\",\"goodsprice\":\"" + goodsprice + "\"" +
                ",\"uid\":\"" + uid + "\",\"goodsid\":\"" + goodsid + "\",\"token\":\"" + token + "\"}";
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
                        ToastUtil.showToast(context, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        Resout_Bean_gouwuche resout_bean_gouwuche = gson.fromJson(response, Resout_Bean_gouwuche.class);
                        if (resout_bean_gouwuche.result.equals("0")) {
                            ToastUtil.showToast(context, "添加购物车成功");
                            getgouwuchedata();
                        } else {
                            ToastUtil.showToast(context, resout_bean_gouwuche.resultNote);
                        }
                    }
                });
    }
    private void getgouwuchedata() {
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
                .url(context.getString(R.string.url))//
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
                        Cargoos_Bean cargoos_bean = gson.fromJson(response, Cargoos_Bean.class);
                        if (cargoos_bean.result.equals("1")) {
                            ToastUtil.showToast(context, cargoos_bean.resultNote);
                            return;
                        }
                        int nub=0;
                        List<Cargoos_Bean.CartsGoods> cartsGoods = cargoos_bean.cartsGoods;
                        for (int i = 0; i <cartsGoods.size() ; i++) {
                            int i1 = Integer.parseInt(cartsGoods.get(i).goodsNum);
                            nub+=i1;
                        }
                        mnub.setText(nub+"");
                        mmoney.setText("￥"+cargoos_bean.shangjiaprice);
                    }
                });
    }
}
