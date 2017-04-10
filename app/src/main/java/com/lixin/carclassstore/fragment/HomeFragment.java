package com.lixin.carclassstore.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.BuyInsuranceActivity;
import com.lixin.carclassstore.activity.CarStoreActivity;
import com.lixin.carclassstore.activity.CheckViolationWebActivity;
import com.lixin.carclassstore.activity.CustomerServiceActivity;
import com.lixin.carclassstore.activity.NewCarActivity;
import com.lixin.carclassstore.activity.StoreActivity;
import com.lixin.carclassstore.bean.JavaBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
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
    private String result;
    private String resultNote;
    private String serve;
    private List<JavaBean.Serve.rotateAdvertisement> rotateAdvertisement = new ArrayList<>();
    private List<JavaBean.Serve.serveTop> topList = new ArrayList<>();
    private List<JavaBean.Serve.serveBottom> bottomList = new ArrayList<>();
    private List<JavaBean.Serve.CheckAdvertisement.checkServes> checkServesList = new ArrayList<>();
    private JavaBean.Serve.CheckAdvertisement checkAdvertisements ;
    private ImageView iv_car;
    private ListView list_activity;
    private View head;
    private View foot;
    private ImageSlideshow imageSlideshow;
    private List<String> imageUrlList;
    private List<String> titleList ;
    TextView text_home_head01,text_home_head02,text_home_head03;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        imageUrlList = new ArrayList<>();
        titleList = new ArrayList<>();
        initView();
        getdata();
        return view;
    }
    //轮播
    private void initViewData(List<JavaBean.Serve.rotateAdvertisement> rotateAdvertisement) {
        for (int i = 0; i < rotateAdvertisement.size(); i++) {
            imageSlideshow.addImageTitle(rotateAdvertisement.get(i).getServeIcon());
        }
        imageSlideshow.setDotSpace(12);
        imageSlideshow.setDotSize(12);
        imageSlideshow.setDelay(3000);
        imageSlideshow.setOnItemClickListener(new ImageSlideshow.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

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
        list_activity = (ListView) view.findViewById(R.id.list_activity);
        head = LayoutInflater.from(getActivity()).inflate(R.layout.home_list_head,null);
        text_home_head01 = (TextView) head.findViewById(R.id.text_home_head01);
        text_home_head02 = (TextView) head.findViewById(R.id.text_home_head02);
        text_home_head03 = (TextView) head.findViewById(R.id.text_home_head03);
        foot = LayoutInflater.from(getActivity()).inflate(R.layout.home_list_head,null);
        text_home_head01 = (TextView) foot.findViewById(R.id.text_home_head01);
        text_home_head02 = (TextView) foot.findViewById(R.id.text_home_head02);
        text_home_head03 = (TextView) foot.findViewById(R.id.text_home_head03);
        if (head != null){
            list_activity.addHeaderView(head);
            list_activity.addFooterView(foot);
        }
    }
    //主页面数据加载
    private void initData() {
        String[] bigBGs = new String[]{
                //顶部服务展示4个
                topList.get(0).getServeIcon(),
                topList.get(1).getServeIcon(),
                topList.get(2).getServeIcon(),
                topList.get(3).getServeIcon(),
                //顶部服务展示6个
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
            case 0:
            case 1:
            case 2:
            case 3:
            case 19:
            case 18:
            case 14:
            case 13:
            case 12:
            case 11:
            case 10:
            case 6:
            case 7:
                startActivity(new Intent(getActivity(),StoreActivity.class));
                Log.i("qqqq", "bottomList: " + bottomList.get(0).getServeIcon());
                break;
            //跳转到客服
            case R.id.text_customer_service:
                startActivity(new Intent(getActivity(),CustomerServiceActivity.class));
                break;
            //跳转到新车
            case 4:
                startActivity(new Intent(getActivity(),NewCarActivity.class).putExtra(NewCarActivity.TAG, 1));
                break;
            //跳转到二手车
            case 5:
                startActivity(new Intent(getActivity(),NewCarActivity.class).putExtra(NewCarActivity.TAG, 0));
                break;
            //跳转到查违章
            case 8:
                Intent intent = new Intent(getActivity(), CheckViolationWebActivity.class);
                intent.putExtra(CheckViolationWebActivity.URL, "http://www.baidu.com");
                startActivity(intent);
                break;
            //跳转到买保险
            case 9:
                startActivity(new Intent(getActivity(),BuyInsuranceActivity.class));
                break;
            //跳转到车品商店
            case 16:
                startActivity(new Intent(getActivity(),CarStoreActivity.class));
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
                Gson gson = new Gson();
                dialog.dismiss();
                javaBean = gson.fromJson(response, JavaBean.class);
                if (javaBean.getResult().equals("1")){
                    ToastUtils.showMessageLong(getActivity(),javaBean.getResultNote());
                }
                JavaBean.Serve serve = gson.fromJson(response, JavaBean.Serve.class);
                List<JavaBean.Serve.rotateAdvertisement> rotateAdvertisementList = serve.rotateAdvertisement;//轮播图集合
                rotateAdvertisement.addAll(rotateAdvertisementList);
                topList = serve.serveTop;
                Log.i("qqqq", "topList: " + topList.get(0).getServeIcon());
                bottomList = serve.serveBottom;
                checkAdvertisements = gson.fromJson(response, JavaBean.Serve.CheckAdvertisement.class);
                checkServesList = checkAdvertisements.checkServes;
                initData();
                initViewData(rotateAdvertisement);
            }
        });
    }
}