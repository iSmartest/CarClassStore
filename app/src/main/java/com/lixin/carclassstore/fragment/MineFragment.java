package com.lixin.carclassstore.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.CustomerComplaintActivity;
import com.lixin.carclassstore.activity.CustomerServiceActivity;
import com.lixin.carclassstore.activity.FeedbackActivity;
import com.lixin.carclassstore.activity.LoginActivity;
import com.lixin.carclassstore.activity.MoneySafeActivity;
import com.lixin.carclassstore.activity.MyAllOrderActivity;
import com.lixin.carclassstore.activity.MyCollectionFootActivity;
import com.lixin.carclassstore.activity.MyReleaseActivity;
import com.lixin.carclassstore.activity.RoadRescueActivity;
import com.lixin.carclassstore.activity.ServiceReminderActivity;
import com.lixin.carclassstore.activity.SetUpActivity;
import com.lixin.carclassstore.activity.ShoppingCartActivity;
import com.lixin.carclassstore.bean.MineMenuBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;

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
public class MineFragment extends BaseFragment implements View.OnClickListener{
    private ImageView iv_set_up;
    private ImageView head_image;
    private TextView head_text;
    private LinearLayout linear_all_order;
    private View[] funcViews = new View[21];
    private String[] funcTxts;
    private View view;
    private List<MineMenuBean.messageMenu> messageMenuList = new ArrayList<>();
    private List<MineMenuBean.hpleMenu> hpleMenuList = new ArrayList<>();
    private List<MineMenuBean.aboutMenu> aboutMenuList = new ArrayList<>();
    private String remmondCode;
    private int[] bigBGs = new int[]{
            R.drawable.m_shopping_cat,
            R.drawable.m_collection,
            R.drawable.m_footprint,
            R.drawable.m_my_release,
            R.drawable.m_customer_service,
            R.drawable.m_wait_payment,
            R.drawable.m_wait_goods,
            R.drawable.m_wait_evaluate,
            R.drawable.m_is_completed,
            R.drawable.m_money_safe,
            R.drawable.m_road_rescue,
            R.drawable.m_customer_complaint,
            R.drawable.m_service_reminder,
            R.drawable.m_car_files,
            R.drawable.m_integral_center,
            R.drawable.m_exchang_zone,
            R.drawable.m_recomend_courtesy,
            R.drawable.m_feedback,
            R.drawable.m_mail,
            R.drawable.m_help_center,
            R.drawable.m_about_us
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine,container,false);
        initView();
        getdata();
        return view;
    }

    private void initView() {
        linear_all_order = (LinearLayout) view.findViewById(R.id.linear_all_order);
        linear_all_order.setOnClickListener(this);
        iv_set_up = (ImageView) view.findViewById(R.id.iv_set_up);
        iv_set_up.setOnClickListener(this);
        head_image = (ImageView) view.findViewById(R.id.head_image);
        head_image.setOnClickListener(this);
        head_text = (TextView) view.findViewById(R.id.head_text);
        head_text.setOnClickListener(this);
        funcTxts = getActivity().getResources().getStringArray(R.array.mine_functions);
        funcViews[0] = view.findViewById(R.id.text_shopping_cart);
        funcViews[1] = view.findViewById(R.id.text_collection);
        funcViews[2] = view.findViewById(R.id.text_footprint);
        funcViews[3] = view.findViewById(R.id.text_my_release);
        funcViews[4] = view.findViewById(R.id.text_customer_service01);
        funcViews[5] = view.findViewById(R.id.text_wait_pay_money);
        funcViews[6] = view.findViewById(R.id.text_wait_goods_receipt);
        funcViews[7] = view.findViewById(R.id.text_wait_evaluate);
        funcViews[8] = view.findViewById(R.id.text_wait_complete);
        funcViews[9] = view.findViewById(R.id.text_money_safe);
        funcViews[10] = view.findViewById(R.id.text_road_rescue);
        funcViews[11] = view.findViewById(R.id.text_customer_complaint);
        funcViews[12] = view.findViewById(R.id.text_service_reminder);
        funcViews[13] = view.findViewById(R.id.text_car_files);
        funcViews[14] = view.findViewById(R.id.text_integral_center);
        funcViews[15] = view.findViewById(R.id.text_exchange_zone);
        funcViews[16] = view.findViewById(R.id.text_recommend_courtesy);
        funcViews[17] = view.findViewById(R.id.text_feedback);
        funcViews[18] = view.findViewById(R.id.text_mail);
        funcViews[19] = view.findViewById(R.id.text_help_center);
        funcViews[20] = view.findViewById(R.id.text_about_us);

        for (int i = 0; i < funcViews.length; i++) {
            ImageView imageView = (ImageView) funcViews[i]
                    .findViewById(R.id.include_imagetext_view_image);
            TextView textView = (TextView) funcViews[i]
                    .findViewById(R.id.include_imagetext_textview_text);
            textView.setText(funcTxts[i]);
            imageView.setImageResource(bigBGs[i]);
            funcViews[i].setOnClickListener(this);
            funcViews[i].setId(i);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.head_image:
                startActivity(new Intent(getActivity(), LoginActivity.class));
//                ImageManager.imageLoader.displayImage(userLoginBean.nickName,head_image);
                break;
            //跳转到设置
            case R.id.iv_set_up:
                startActivity(new Intent(getActivity(),SetUpActivity.class));
                break;
            //跳转到我的订单
            case R.id.linear_all_order:
                Intent intent3 = new Intent(getActivity(),MyAllOrderActivity.class);
                intent3.putExtra("temp","0");
                startActivity(intent3);
                break;
//            跳转到购物车
            case 0:
                startActivity(new Intent(getActivity(),ShoppingCartActivity.class));
                break;
            case 1:
                //跳转到收藏
                Intent intent1 = new Intent(getActivity(),MyCollectionFootActivity.class);
                intent1.putExtra("handleType","1");
                startActivity(intent1);
                break;
            case 2:
                //跳转到足迹
                Intent intent2 = new Intent(getActivity(),MyCollectionFootActivity.class);
                intent2.putExtra("handleType","2");
                startActivity(intent2);
                break;
            case 3:
                //跳转到我的发布
                startActivity(new Intent(getActivity(),MyReleaseActivity.class));
                break;
            case 4:
                //跳转到客服平台
                startActivity(new Intent(getActivity(),CustomerServiceActivity.class));
                break;
            //跳转到待收款
            case 5:
                Intent intent4 = new Intent(getActivity(),MyAllOrderActivity.class);
                intent4.putExtra("temp","1");
                startActivity(intent4);
                break;
            //跳转到待收货
            case 6:
                Intent intent5 = new Intent(getActivity(),MyAllOrderActivity.class);
                intent5.putExtra("temp","2");
                startActivity(intent5);
                break;
            //跳转到待评价
            case 7:
                Intent intent6= new Intent(getActivity(),MyAllOrderActivity.class);
                intent6.putExtra("temp","3");
                startActivity(intent6);
                break;
            //跳转到已完成
            case 8:
                Intent intent7 = new Intent(getActivity(),MyAllOrderActivity.class);
                intent7.putExtra("temp","4");
                startActivity(intent7);
                break;
            case 9:
                //跳转到金融保险
                startActivity(new Intent(getActivity(),MoneySafeActivity.class));
                break;
            case 10:
                //跳转到道路救援
                startActivity(new Intent(getActivity(),RoadRescueActivity.class));
                break;
            case 11:
                //跳转到客户投诉
                startActivity(new Intent(getActivity(),CustomerComplaintActivity.class));
                break;
            case 12:
                //跳转到服务提醒
                startActivity(new Intent(getActivity(),ServiceReminderActivity.class));
                break;
            case 13:
                //爱车档案
                Log.i("TAG", "messageMenuList: " + messageMenuList.get(0).getMeunType());
                Log.i("TAG", "hpleMenuList: " + hpleMenuList.get(0).getMeunType());
                Log.i("TAG", "aboutMenuList: " + aboutMenuList.get(0).getMeunType());
                break;
            case 14:
                //积分中心
                break;
            case 15:
                //兑换专区
                break;
            case 16:
                //推荐有礼
                break;
            case 17:
                //意见反馈
                startActivity(new Intent(getActivity(),FeedbackActivity.class));
                break;
            case 18:
                //站内信

                break;
            case 19:
                //帮助中心
                break;
            case 20:
                //关于我们

                break;
        }
    }
    //请求参数
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getMineMenuInfo\"}";
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
                MineMenuBean mineMenuBean = gson.fromJson(response, MineMenuBean.class);
                if (mineMenuBean.getResult().equals("1")){
                    ToastUtils.showMessageLong(getActivity(),mineMenuBean.getResultNote());
                }

                List<MineMenuBean.messageMenu> messageMenu = mineMenuBean.messageMenu;//轮播图集合
                messageMenuList.addAll(messageMenu);

                List<MineMenuBean.hpleMenu> hpleMenu = mineMenuBean.hpleMenu;//轮播图集合
                hpleMenuList.addAll(hpleMenu);

                List<MineMenuBean.aboutMenu> aboutMenu = mineMenuBean.aboutMenu;//轮播图集合
                aboutMenuList.addAll(aboutMenu);
                remmondCode = mineMenuBean.getRemmondCode();
            }
        });
    }
}
