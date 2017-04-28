package com.lixin.carclassstore.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.OpinionAdapter;
import com.lixin.carclassstore.bean.ShopDetailsBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.view.viewPage.ImageSlideshow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/19
 * My mailbox is 1403241630@qq.com
 * 商品
 */

public class ShopFragment extends BaseFragment{
    private View view;
    private View head;
    private ImageSlideshow headSlideshow;
    private ImageView headCollection;
    private TextView headShopName,headShopDec,headShopKillTime,headShopPrice,headShopTotal,headUserOpinion,headScore,headTotalOpinion;
    private RatingBar headStar;
    private LinearLayout headAllOpinion;
    private ListView mListView;
    private OpinionAdapter mAdapter;
    private String uid;
    private String commodityid;
    private String meunid;
    private String commodityShopid;
    private String commodityBrandid;
    private List<ShopDetailsBean.commodityCommentLists> commentList = new ArrayList<>();
    private List<ShopDetailsBean.commodityRelateds> relatedsList = new ArrayList<>();
    private int commodityHandle;
    private boolean isCollention;
    private int commodityShooCarNum = 1;
    private List<String> rotateList = new ArrayList<>();
    CallBackValue callBackValue;
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        callBackValue =(CallBackValue) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop,null);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle != null){
            commodityid = bundle.getString("commodityid");
            meunid = bundle.getString("meunid");
            commodityShopid = bundle.getString("commodityShopid");
            commodityBrandid = bundle.getString("commodityBrandid");
        }
        uid = (String) SPUtils.get(getActivity(),"uid","");
        initView();
        getdata();
        return view;
    }
    //轮播
    private void initData(String[] rotateCommodityPics) {
        for (int i = 0; i < rotateCommodityPics.length; i++) {
            headSlideshow.addImageTitle(rotateCommodityPics[i]);
            Log.i("ShopFragment", "onResponse: " + rotateCommodityPics[i]);
        }
        headSlideshow.setDotSpace(12);
        headSlideshow.setDotSize(12);
        headSlideshow.setDelay(3000);
        headSlideshow.setOnItemClickListener(new ImageSlideshow.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        headSlideshow.commit();
        Log.i("jjjjjj", "initViewData: " + rotateCommodityPics);
    }
    private void initView() {
        mListView = (ListView) view.findViewById(R.id.list_shop);
        head = LayoutInflater.from(getActivity()).inflate(R.layout.shop_head_item,null);
        headSlideshow = (ImageSlideshow) head.findViewById(R.id.img_gallery);
        headCollection = (ImageView) head.findViewById(R.id.iv_head_collection);
        headShopName = (TextView) head.findViewById(R.id.text_head_shop_name);
        headShopDec = (TextView) head.findViewById(R.id.text_head_shop_dec);
        headShopKillTime = (TextView) head.findViewById(R.id.text_shop_kill_time);
        headShopPrice = (TextView) head.findViewById(R.id.text_head_shop_price);
        headShopTotal = (TextView) head.findViewById(R.id.text_head_shop_total);
        headUserOpinion = (TextView) head.findViewById(R.id.text_head_user_opinion);
        headScore = (TextView) head.findViewById(R.id.text_head_score);
        headTotalOpinion = (TextView) head.findViewById(R.id.text_head_total_opinion);
        headStar = (RatingBar) head.findViewById(R.id.rab_head_star);
        headAllOpinion = (LinearLayout) head.findViewById(R.id.linear_all_opinion);
        if (head != null)
            mListView.addHeaderView(head);
        mAdapter = new OpinionAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        headCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commodityHandle == 1){
                    commodityHandle = 2;
                    getCollection();
                    headCollection.setImageResource(R.drawable.shop_collection_normal);

                }else {
                    headCollection.setImageResource(R.drawable.shop_collection);
                    commodityHandle = 1;
                    getCollection();
                }
            }
        });
    }
    private void getCollection() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getCommoditysHandleInfo\",\"uid\":\"" + uid +"\",\"commodityHandle\":\""
                + commodityHandle + "\",\"commodityShooCarNum\":\"" + commodityShooCarNum + "\",\"commodityid\":\"" + commodityid +"\",\"commodityShopid\":\"" +
                commodityShopid +"\"}";
        params.put("json", json);
        Log.i("MyCollectionFootActivity", "onResponse: " + json.toString());
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageShort(context, e.getMessage());
                dialog.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("ShopFragment", "onResponse: " + response.toString());
                Gson gson = new Gson();
                dialog.dismiss();
                ShopDetailsBean shopDetailsBean = gson.fromJson(response, ShopDetailsBean.class);
                if (shopDetailsBean.result.equals("1")) {
                    ToastUtils.showMessageShort(context, shopDetailsBean.resultNote);
                    return;
                }
                if (commodityHandle == 1){
                    ToastUtils.showMessageShort(context,"收藏成功！");
                }else {
                    ToastUtils.showMessageShort(context,"已经取消收藏！");
                }
            }
        });
    }
    //请求参数
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getCommoditysInfo\",\"uid\":\"" + uid +"\",\"meunid\":\""
                + meunid + "\",\"commodityid\":\"" + commodityid + "\",\"commodityBrandid\":\"" + commodityBrandid +"\",\"commodityShopid\":\"" +
                commodityShopid +"\"}";
        params.put("json", json);
        Log.i("MyCollectionFootActivity", "onResponse: " + json.toString());
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageShort(context, e.getMessage());
                dialog.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("ShopFragment", "onResponse: " + response.toString());
                Gson gson = new Gson();
                dialog.dismiss();
                ShopDetailsBean shopDetailsBean = gson.fromJson(response, ShopDetailsBean.class);
                if (shopDetailsBean.result.equals("1")) {
                    ToastUtils.showMessageShort(context, shopDetailsBean.resultNote);
                    return;
                }
                //商品评论列表数据
                List<ShopDetailsBean.commodityCommentLists> commoditysList = shopDetailsBean.commodityCommentLists;
                commentList.addAll(commoditysList);
                //相关商品用于购物车添加
                List<ShopDetailsBean.commodityRelateds> commodityRelatedsList = shopDetailsBean.commodityRelateds;
                relatedsList.addAll(commodityRelatedsList);
                //商品展示轮播图
                List<String> rotateCommodityPicsList = shopDetailsBean.rotateCommodityPics;
                Log.i("ShopFragment", "onResponse: " + rotateCommodityPicsList.toString());
                String[] relate = new String[rotateCommodityPicsList.size()];
                for (int i = 0; i <rotateCommodityPicsList.size() ; i++) {
                    relate[i] = rotateCommodityPicsList.get(i);
                }
                initData(relate);
                Log.i("ShopFragment", "onResponse: " + relate.length);
//                mAdapter.setOpinionList(getActivity(),commentList);
                mListView.setAdapter(mAdapter);
                headShopName.setText(shopDetailsBean.getCommodityTitle());
                headShopDec.setText(shopDetailsBean.getCommodityDescription());
                headShopPrice.setText(shopDetailsBean.getCommodityNewPrice());
                headShopTotal.setText(shopDetailsBean.getCommoditysellerNum());
                headScore.setText(shopDetailsBean.getCommodityStarNum() + "分");
                headTotalOpinion.setText(shopDetailsBean.getCommodityCommendNum() + "人评价");
                float starNum = Float.parseFloat(shopDetailsBean.getCommodityCommendNum());
                headStar.setRating(starNum);
                callBackValue.SendMessageValue(shopDetailsBean.getShopTelephone());
            }
        });
    }
    //写一个回调接口
    public interface CallBackValue{
        public void SendMessageValue(String strValue);
    }
}
