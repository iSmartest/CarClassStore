package com.lixin.carclassstore.fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.BuyInsuranceActivity;
import com.lixin.carclassstore.activity.CarStyleChooseActivity;
import com.lixin.carclassstore.activity.CheckViolationWebActivity;
import com.lixin.carclassstore.activity.CustomerServiceActivity;
import com.lixin.carclassstore.activity.ShopActivity;
import com.lixin.carclassstore.adapter.GridAdapter;
import com.lixin.carclassstore.bean.ContentBean;
import com.lixin.carclassstore.bean.JavaBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.view.viewPage.ImageSlideshow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{
    private View[] funcViews = new View[19];
    private String[] funcTxts;
    private JavaBean javaBean;
    private View view;
    private List<JavaBean.rotateAdvertisement> rotateAdvertisement = new ArrayList<>();
    private List<JavaBean.filtrate> filtrateList = new ArrayList<>();
    private List<JavaBean.serveTop> topList = new ArrayList<>();
    private List<JavaBean.serveBottom> bottomList = new ArrayList<>();
    private List<JavaBean.CheckAdvertisement.checkServes> checkServesList = new ArrayList<>();
    private List<ContentBean.commoditysList> mList = new ArrayList<>();
    private JavaBean.CheckAdvertisement checkAdvertisements ;
    private ImageView iv_car;
    private int nowPage = 1;
    private ImageSlideshow imageSlideshow;
    TextView recommended,recommendedPrice,recommendedContent,boutique,boutiquePrice,boutiqueContent,dayKill;
    ImageView ivRecommended,ivBoutique;
    GridView gridKill;
    GridAdapter gridAdapter;
    LinearLayout linear01,linear02,linear03;
    CallBackValue callBackValue;
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        callBackValue =(HomeFragment.CallBackValue) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        initView();
        getdata();
        getCommoditysData();
        return view;
    }

    private void getCommoditysData() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getHomeContentInfo\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageLong(context, "网络异常");
                dialog.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("commoditysList", "onResponse:111 " + response.toString());
                Gson gson = new Gson();
                dialog.dismiss();
                ContentBean contentBean = gson.fromJson(response, ContentBean.class);
                if (contentBean.result.equals("1")){
                    ToastUtils.showMessageLong(getActivity(),contentBean.resultNote);
                }
                List<ContentBean.commoditysList> commoditysList = contentBean.commoditysList;

                Log.i("commoditysList", "onResponse2222: " + commoditysList.toString());
                if (commoditysList.get(0).sectionName.equals("天天秒杀")){
                    linear03.setVisibility(View.VISIBLE);
                    dayKill.setText(commoditysList.get(0).sectionName);
                    gridKill.setAdapter(gridAdapter);
                    List<ContentBean.commoditysList.commoditys> commoditys = contentBean.commoditysList.get(0).commoditys;
                    Log.i("commoditysList", "onResponse5555: " + commoditys.get(0).commodityid);
                    gridAdapter.setGrid(commoditys);
                }else {
                    linear03.setVisibility(View.GONE);
                }
                if (commoditysList.get(1).sectionName.equals("活动推荐")){
                    linear02.setVisibility(View.VISIBLE);
                    recommended.setText(commoditysList.get(1).sectionName);
                    recommendedPrice.setText(commoditysList.get(1).commoditys.get(0).commodityOriginalPrice);
                    recommendedContent.setText(commoditysList.get(1).commoditys.get(0).commodityDescription);
                    Picasso.with(context).load(commoditysList.get(1).commoditys.get(0).commodityIcon).into(ivRecommended);
                }else {
                    linear02.setVisibility(View.GONE);
                }
                if (commoditysList.get(1).sectionName.equals("精品推荐")){
                    linear01.setVisibility(View.VISIBLE);
                    boutique.setText(commoditysList.get(1).sectionName);
                    boutiquePrice.setText(commoditysList.get(1).commoditys.get(0).commodityOriginalPrice);
                    boutiqueContent.setText(commoditysList.get(1).commoditys.get(0).commodityDescription);
                    Picasso.with(context).load(commoditysList.get(1).commoditys.get(0).commodityIcon).into(ivBoutique);
                }else {
                    linear01.setVisibility(View.GONE);
                }
            }
        });
    }

    //轮播
    private void initViewData(final List<JavaBean.rotateAdvertisement> rotateAdvertisement) {
        for (int i = 0; i < rotateAdvertisement.size(); i++) {
            imageSlideshow.addImageTitle(rotateAdvertisement.get(i).getServeIcon());
        }
        imageSlideshow.setDotSpace(12);
        imageSlideshow.setDotSize(12);
        imageSlideshow.setDelay(3000);
        imageSlideshow.setOnItemClickListener(new ImageSlideshow.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),ShopActivity.class);
                intent.putExtra("serveTypeId",rotateAdvertisement.get(position).getServeTypeId() );
                startActivity(intent);
            }
        });
        imageSlideshow.commit();
        Log.i("jjjjjj", "initViewData: " + rotateAdvertisement);
    }

    private void initView() {
        funcTxts = this.getResources().getStringArray(R.array.home_functions);
        iv_car = (ImageView) view.findViewById(R.id.iv_car);
        imageSlideshow = (ImageSlideshow) view.findViewById(R.id.is_gallery);
        funcViews[0] = view.findViewById(R.id.text_change_tire);
        funcViews[1] = view.findViewById(R.id.text_do_maintenance);
        funcViews[2] = view.findViewById(R.id.text_wash_car);
        funcViews[3] = view.findViewById(R.id.text_customer_service);
        funcViews[4] = view.findViewById(R.id.text_new_car);
        funcViews[5] = view.findViewById(R.id.text_used_car);
        funcViews[6] = view.findViewById(R.id.text_maintenance);
        funcViews[7] = view.findViewById(R.id.text_car_decoration);
        funcViews[8] = view.findViewById(R.id.text_check_violation);
        funcViews[9] = view.findViewById(R.id.text_buy_insurance);
        funcViews[10] = view.findViewById(R.id.text_small_maintenance);
        funcViews[11] = view.findViewById(R.id.text_give_oil);
        funcViews[12] = view.findViewById(R.id.text_reduction);
        funcViews[13] = view.findViewById(R.id.text_free_work);
        funcViews[14] = view.findViewById(R.id.text_plated_crystal_maintenance);
        funcViews[15] = view.findViewById(R.id.text_car_store);
        funcViews[16] = view.findViewById(R.id.text_special_offer);
        funcViews[17] = view.findViewById(R.id.text_automative_lighting);
        funcViews[18] = view.findViewById(R.id.text_refrigerator);
        //活动推荐
        recommended = (TextView) view.findViewById(R.id.text_activity_recommended);
        recommendedPrice = (TextView) view.findViewById(R.id.text_activity_recommended_price);
        recommendedContent = (TextView) view.findViewById(R.id.text_activity_recommended_content);
        ivRecommended = (ImageView) view.findViewById(R.id.imv_activity_recommended);
        linear02 = (LinearLayout) view.findViewById(R.id.linear02);
        //精品推荐
        boutique  = (TextView) view.findViewById(R.id.text_boutique_recommend);
        boutiquePrice = (TextView) view.findViewById(R.id.text_boutique_price);
        boutiqueContent = (TextView) view.findViewById(R.id.text_boutique_dec);
        ivBoutique = (ImageView) view.findViewById(R.id.iv_boutique_picture);
        linear01 = (LinearLayout) view.findViewById(R.id.linear01);
        //天天秒杀
        dayKill = (TextView) view.findViewById(R.id.text_everyday_kill);
        gridKill = (GridView) view.findViewById(R.id.grid_everyday_kill);
        linear03 = (LinearLayout) view.findViewById(R.id.linear03);
        gridAdapter = new GridAdapter(getActivity());
        gridKill.setAdapter(gridAdapter);
        iv_car.setOnClickListener(this);
    }
    //主页面数据加载
    private void initData() {
        String[] bigBGs = new String[]{
                //顶部服务展示4个
                topList.get(0).getServeIcon(),
                topList.get(1).getServeIcon(),
                topList.get(2).getServeIcon(),
                topList.get(3).getServeIcon(),
                //中部服务展示6个
                bottomList.get(0).getServeIcon(),
                bottomList.get(1).getServeIcon(),
                bottomList.get(2).getServeIcon(),
                bottomList.get(3).getServeIcon(),
                bottomList.get(4).getServeIcon(),
                bottomList.get(5).getServeIcon(),
                //宫格广告展示10个
                checkServesList.get(0).getServeIcon(),
                checkServesList.get(1).getServeIcon(),
                checkServesList.get(2).getServeIcon(),
                checkServesList.get(3).getServeIcon(),
                checkServesList.get(4).getServeIcon(),
                checkServesList.get(6).getServeIcon(),
                checkServesList.get(7).getServeIcon(),
                checkServesList.get(8).getServeIcon(),
                checkServesList.get(9).getServeIcon(),

        };
        for (int i = 0; i < funcViews.length; i++) {
            ImageView imageView = (ImageView) funcViews[i]
                    .findViewById(R.id.include_imagetext_view_image);
            TextView textView = (TextView) funcViews[i]
                    .findViewById(R.id.include_imagetext_textview_text);
            textView.setText(funcTxts[i]);
            Picasso.with(context).load(bigBGs[i]).into(imageView);
            funcViews[i].setOnClickListener(this);
            funcViews[i].setId(i);
        }
        Picasso.with(context).load(checkServesList.get(5).getServeIcon()).into(iv_car);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //跳转到车品商店
            case 0:
                Intent intent00 = new Intent(getActivity(),ShopActivity.class);
                intent00.putExtra("serveTypeId",topList.get(0).getServeTypeId() );
                ToastUtils.showMessageShort(getActivity(),topList.get(0).getServeType());
                startActivity(intent00);
                break;
            case 1:
                Intent intent01 = new Intent(getActivity(),ShopActivity.class);
                intent01.putExtra("serveTypeId",topList.get(1).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),topList.get(1).getServeType());
                startActivity(intent01);
                break;
            case 2:
                Intent intent02 = new Intent(getActivity(),ShopActivity.class);
                intent02.putExtra("serveTypeId",topList.get(2).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),topList.get(2).getServeType());
                startActivity(intent02);
                break;
            case 3:
                Intent intent03 = new Intent(getActivity(),CustomerServiceActivity.class);
                startActivity(intent03);
                break;
            case 10:
                Intent intent10 = new Intent(getActivity(),ShopActivity.class);
                intent10.putExtra("serveTypeId",checkServesList.get(0).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(0).getServeType());
                startActivity(intent10);
                break;
            case 11:
                Intent intent11 = new Intent(getActivity(),ShopActivity.class);
                intent11.putExtra("serveTypeId",checkServesList.get(1).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(1).getServeType());
                startActivity(intent11);
                break;
            case 12:
                Intent intent12 = new Intent(getActivity(),ShopActivity.class);
                intent12.putExtra("serveTypeId",checkServesList.get(2).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(2).getServeType());
                startActivity(intent12);
                break;
            case 13:
                Intent intent13 = new Intent(getActivity(),ShopActivity.class);
                intent13.putExtra("serveTypeId",checkServesList.get(3).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(3).getServeType());
                startActivity(intent13);
                break;
            case 14:
                Intent intent14 = new Intent(getActivity(),ShopActivity.class);
                intent14.putExtra("serveTypeId",checkServesList.get(4).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(4).getServeType());
                startActivity(intent14);
                break;
            case R.id.iv_car:
                Intent intent04 = new Intent(getActivity(),ShopActivity.class);
                intent04.putExtra("serveTypeId",checkServesList.get(5).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(5).getServeType());
                startActivity(intent04);
                break;
            case 15:
                Intent intent15 = new Intent(getActivity(),ShopActivity.class);
                intent15.putExtra("serveTypeId",checkServesList.get(6).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(6).getServeType());
                startActivity(intent15);
                break;
            case 16:
                Intent intent16 = new Intent(getActivity(),ShopActivity.class);
                intent16.putExtra("serveTypeId",checkServesList.get(7).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(7).getServeType());
                startActivity(intent16);
                break;
            case 17:
                Intent intent17 = new Intent(getActivity(),ShopActivity.class);
                intent17.putExtra("serveTypeId",checkServesList.get(8).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(8).getServeType());
                startActivity(intent17);
                break;
            case 18:
                Intent intent18 = new Intent(getActivity(),ShopActivity.class);
                intent18.putExtra("serveTypeId",checkServesList.get(9).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(9).getServeType());
                startActivity(intent18);
                break;
            case 19:
                Intent intent19 = new Intent(getActivity(),ShopActivity.class);
                intent19.putExtra("serveTypeId",checkServesList.get(10).getServeTypeId());
                ToastUtils.showMessageShort(getActivity(),checkServesList.get(10).getServeType());
                startActivity(intent19);
                break;
            case 6:
                StoreFragment storeFragment1 = StoreFragment.newInstance("serveType");
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                transaction1.add(R.id.activity_new_main_layout_content, storeFragment1);
                transaction1.addToBackStack(null);
                transaction1.commit();
                callBackValue.SendMessageValue("6");
                break;
            case 7:
                StoreFragment storeFragment2 = StoreFragment.newInstance("serveType");
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                transaction2.add(R.id.activity_new_main_layout_content, storeFragment2);
                transaction2.addToBackStack(null);
                transaction2.commit();
                callBackValue.SendMessageValue("6");
                break;
            //跳转到客服
            case R.id.text_customer_service:
                startActivity(new Intent(getActivity(),CustomerServiceActivity.class));
                break;
            //跳转到新车
            case 4:
                startActivity(new Intent(getActivity(),CarStyleChooseActivity.class).putExtra("flag", "2"));
                break;
            //跳转到二手车
            case 5:
                startActivity(new Intent(getActivity(),CarStyleChooseActivity.class).putExtra("flag", "3"));
                break;
            //跳转到查违章
            case 8:
                Intent intent = new Intent(getActivity(), CheckViolationWebActivity.class);
                intent.putExtra("isStoreDetailsOrCheckviolation","2");
                intent.putExtra(CheckViolationWebActivity.URL, "http://www.baidu.com");
                startActivity(intent);
                break;
            //跳转到买保险
            case 9:
                startActivity(new Intent(getActivity(),BuyInsuranceActivity.class));
                break;
        }
    }
    //请求参数
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getAppLaunchInfo\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageLong(context, "网络异常");
                dialog.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("qqqq", "topList: " + response.toString());
                Gson gson = new Gson();
                dialog.dismiss();
                javaBean = gson.fromJson(response, JavaBean.class);
                if (javaBean.getResult().equals("1")){
                    ToastUtils.showMessageLong(getActivity(),javaBean.getResultNote());
                }
                List<JavaBean.rotateAdvertisement> rotateAdvertisementList = javaBean.rotateAdvertisement;//轮播图集合
                List<JavaBean.filtrate> filtrate = javaBean.filtrate;
                filtrateList.addAll(filtrate);
//                SPUtils.put(getActivity(),"filtrateList",filtrateList);
                rotateAdvertisement.addAll(rotateAdvertisementList);
                List<JavaBean.serveTop> serveTopList = javaBean.serveTop;
                topList.addAll(serveTopList);
                Log.i("qqqq", "topList: " + topList.get(0).getServeIcon());
                bottomList = javaBean.serveBottom;
                checkAdvertisements = gson.fromJson(response, JavaBean.CheckAdvertisement.class);
                checkServesList = checkAdvertisements.checkServes;
                initData();
                initViewData(rotateAdvertisement);
            }
        });
    }
    //写一个回调接口
    public interface CallBackValue{
        public void SendMessageValue(String strValue);
    }
}