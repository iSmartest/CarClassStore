
package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.ShoppingCartAdapter;
import com.lixin.carclassstore.bean.ShoppingCollectionFootBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by AYD on 2016/11/21.
 * 购物车页面
 */
public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener
        ,ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface {
    public TextView tv_title, tv_settlement, tv_show_price;
    private CheckBox ck_all;
    private ListView list_shopping_cart;
    private ShoppingCartAdapter shoppingCartAdapter;
    private List<ShoppingCollectionFootBean.commoditys> mList = new ArrayList<>();
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private String handleType = "0";
    private String uid = "123";
    private int nowPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        hideBack(false);
        setTitleText("购物车");
        initView();
        getdata();
    }

    private void initView() {
        list_shopping_cart = (ListView) findViewById(R.id.list_shopping_cart);
        ck_all = (CheckBox) findViewById(R.id.ck_all);
        ck_all.setOnClickListener(this);
        tv_show_price = (TextView) findViewById(R.id.tv_show_price);
        tv_settlement = (TextView) findViewById(R.id.tv_settlement);
        tv_settlement.setOnClickListener(this);
        shoppingCartAdapter = new ShoppingCartAdapter(this);
        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);
        list_shopping_cart.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.setShoppingCartBeanList(mList);
    }

    //请求参数
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getQueryHandleInfo\",\"handleType\":\"" + handleType + "\",\"uid\":\"" + uid +"\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageLong(context, "网络异常");
                dialog1.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                dialog1.dismiss();
                ShoppingCollectionFootBean shoppingCollectionFootBean = gson.fromJson(response, ShoppingCollectionFootBean.class);
                Log.i("commoditys", "commoditys: " + response.toString());
                if (shoppingCollectionFootBean.getResult().equals("1")){
                    ToastUtils.showMessageLong(context,shoppingCollectionFootBean.getResultNote());
                }
                List<ShoppingCollectionFootBean.commoditys> commoditys = shoppingCollectionFootBean.commoditys;
                Log.i("commoditys", "commoditys: " + commoditys.toString());
                mList.addAll(commoditys);


            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全选按钮
            case R.id.ck_all:
                if (mList.size() != 0) {
                    if (ck_all.isChecked()) {
                        for (int i = 0; i < mList.size(); i++) {
                            mList.get(i).setChoosed(true);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < mList.size(); i++) {
                            mList.get(i).setChoosed(false);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.tv_settlement:
                startActivity(new Intent(ShoppingCartActivity.this,OrderPaymentActivity.class));
                break;
        }
    }

    /**
     * 单选
     *
     * @param position  组元素位置
     * @param isChecked 组元素选中与否
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {

        mList.get(position).setChoosed(isChecked);

        if (isAllCheck())
            ck_all.setChecked(true);
        else
            ck_all.setChecked(false);

        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }


    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllCheck() {

        for (ShoppingCollectionFootBean.commoditys group : mList) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < mList.size(); i++) {
            ShoppingCollectionFootBean.commoditys commoditysBean = mList.get(i);
            if (commoditysBean.isChoosed()) {
                totalCount ++;
                totalPrice += Integer.parseInt(mList.get(i).commodityNewPrice) * mList.get(i).getCount();

            }
        }
        tv_show_price.setText("总计:￥" + totalPrice);
    }

    /**
     * 增加
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        ShoppingCollectionFootBean.commoditys shoppingCartBean = mList.get(position);
        int currentCount = shoppingCartBean.getCount();
        currentCount++;
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }

    /**
     * 删减
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        ShoppingCollectionFootBean.commoditys shoppingCartBean = mList.get(position);
        int currentCount = shoppingCartBean.getCount();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();

    }

    /**
     * 删除
     *
     * @param position
     */
    @Override
    public void childDelete(int position) {
        mList.remove(position);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();

    }


}
