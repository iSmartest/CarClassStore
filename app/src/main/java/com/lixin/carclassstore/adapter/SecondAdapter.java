package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.LoginActivity;
import com.lixin.carclassstore.bean.ShopDetailsBean;
import com.lixin.carclassstore.bean.StoreDetailsBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/24
 * My mailbox is 1403241630@qq.com
 */
public class SecondAdapter extends BaseAdapter {
    private List<StoreDetailsBean.shopCommoditys.commoditys> commoditys;
    private Context context;
    private String shopId;
    private int num = 0;
    private OnNumClickListener onNumClickListener;
    public SecondAdapter(Context context) {

        this.context = context;
    }
    public void setSecond(Context context,List<StoreDetailsBean.shopCommoditys.commoditys> commoditys,String shopId){
        this.context = context;
        this.commoditys = commoditys;
        this.shopId = shopId;
    }
    @Override
    public int getCount() {
        return commoditys == null ? 0 : commoditys.size();
    }

    @Override
    public StoreDetailsBean.shopCommoditys.commoditys getItem(int position) {
        return commoditys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_store_shop,null);
            viewHolder = new ViewHolder();
            viewHolder.mShoreShopPicture = (ImageView) convertView.findViewById(R.id.iv_store_shop_picture);
            viewHolder.mStoreShopName = (TextView) convertView.findViewById(R.id.text_store_shop_name);
            viewHolder.mStoreShopPrice = (TextView) convertView.findViewById(R.id.text_store_shop_price);
            viewHolder.mStoreShopDec = (TextView) convertView.findViewById(R.id.text_store_shop_dec);
            viewHolder.mStoreShopSeller = (TextView) convertView.findViewById(R.id.text_store_shop_seller);
            viewHolder.mAddShoppingCart = (ImageView) convertView.findViewById(R.id.im_add_shopping_cart);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final StoreDetailsBean.shopCommoditys.commoditys mList = commoditys.get(position);
        if (TextUtils.isEmpty(mList.getCommodityIcon())){
            viewHolder.mShoreShopPicture.setImageResource(R.drawable.image_car_defult);
        }else {
            Picasso.with(context).load(mList.commodityIcon).into(viewHolder.mShoreShopPicture);
        }
        viewHolder.mStoreShopName.setText(mList.getCommodityTitle());
        viewHolder.mStoreShopPrice.setText("￥" + mList.getCommodityNewPrice());
        viewHolder.mStoreShopDec.setText(mList.getCommodityDescription());
        viewHolder.mStoreShopSeller.setText("销量：" + mList.getCommoditysellerNum());
        viewHolder.mAddShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commodityHandle = "0";
                String commodityShooCarNum = "1";
                String commodityid = mList.getCommodityid();
                String commodityShopid = shopId;

                String uid = (String) SPUtils.get(context,"uid","");
                if (uid.equals("")){
                    ToastUtils.showMessageShort(context,"请先登录！");
                    context.startActivity(new Intent(context, LoginActivity.class));
                }else {
                    getdata(commodityHandle,commodityShooCarNum,commodityid,commodityShopid,uid);
                }

            }
        });
        return convertView;
    }

    private void getdata(String commodityHandle, String commodityShooCarNum, String commodityid, String commodityShopid, String uid) {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getCommoditysHandleInfo\",\"commodityHandle\":\"" + commodityHandle +"\",\"commodityShooCarNum\":\""
                + commodityShooCarNum + "\",\"commodityid\":\"" + commodityid + "\",\"commodityShopid\":\"" + commodityShopid + "\",\"uid\":\"" + uid +"\"}";
        params.put("json", json);
        Log.i("StoreDetailsActivity", "onResponse: " + json.toString());

        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageShort(context, e.getMessage());
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("StoreDetailsActivity", "onResponse: " + response.toString());
                Gson gson = new Gson();
                ShopDetailsBean shopDetailsBean = gson.fromJson(response, ShopDetailsBean.class);
                if (shopDetailsBean.result.equals("1")) {
                    ToastUtils.showMessageShort(context, shopDetailsBean.resultNote);
                    return;
                }
                ToastUtils.showMessageShort(context,"已加入购物车！");
                if (onNumClickListener != null) {
                    num++;
                    String temp = String.valueOf(num);
                    onNumClickListener.onNumClick(temp);
                }
            }
        });
    }
    class ViewHolder {
        ImageView mShoreShopPicture,mAddShoppingCart;
        TextView mStoreShopName,mStoreShopPrice,mStoreShopDec,mStoreShopSeller;
    }


    public interface OnNumClickListener {

        void onNumClick(String num);
    }

    public void setOnNumClickListener(OnNumClickListener listener) {
        this.onNumClickListener = listener;
    }
}
