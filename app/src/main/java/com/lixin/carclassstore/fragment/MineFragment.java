package com.lixin.carclassstore.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixin.carclassstore.R;

/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */
public class MineFragment extends Fragment implements View.OnClickListener{
    private ImageView iv_set_up;
    private LinearLayout linear_all_order;
    private View[] funcViews = new View[21];
    private String[] funcTxts;
    private View view;
    private int[] bigBGs = new int[]{
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine,container,false);
        initView();
        return view;
    }

    private void initView() {
        linear_all_order = (LinearLayout) view.findViewById(R.id.linear_all_order);
        linear_all_order.setOnClickListener(this);
        iv_set_up = (ImageView) view.findViewById(R.id.iv_set_up);
        iv_set_up.setOnClickListener(this);
        funcTxts = getActivity().getResources().getStringArray(R.array.mine_functions);
        funcViews[0] = view.findViewById(R.id.text_shopping_cart);
        funcViews[1] = view.findViewById(R.id.text_collection);
        funcViews[2] = view.findViewById(R.id.text_footprint);
        funcViews[3] = view.findViewById(R.id.text_my_release);
        funcViews[4] = view.findViewById(R.id.text_customer_service);
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
//        switch (v.getId()){
//            //跳转到购物车
//            case R.id.iv_set_up:
//                startActivity(new Intent(MineActivity.this,SetUpActivity.class));
//                break;
//            case 0:
//                startActivity(new Intent(MineActivity.this,ShoppingCartActivity.class));
//                break;
//            case 1:
//                //跳转到收藏
//                startActivity(new Intent(MineActivity.this,MyCollectionActivity.class));
//                break;
//            case 2:
//                //跳转到足迹
//                startActivity(new Intent(MineActivity.this,MyCollectionActivity.class));
//                break;
//            case 3:
//                //跳转到我的发布
//                startActivity(new Intent(MineActivity.this,MyReleaseActivity.class));
//                break;
//            case 4:
//                startActivity(new Intent(MineActivity.this,CustomerServiceActivity.class));
//                break;
//            case 9:
//                startActivity(new Intent(MineActivity.this,MoneySafeActivity.class));
//                break;
//            case 10:
//                startActivity(new Intent(MineActivity.this,RoadRescueActivity.class));
//                break;
//            case 11:
//                startActivity(new Intent(MineActivity.this,CustomerComplaintActivity.class));
//
//        }
    }
}
