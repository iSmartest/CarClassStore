package com.lixin.carclassstore.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.BuyInsuranceActivity;
import com.lixin.carclassstore.activity.CarStoreActivity;
import com.lixin.carclassstore.activity.CheckViolationWebActivity;
import com.lixin.carclassstore.activity.CustomerServiceActivity;
import com.lixin.carclassstore.activity.NewCarActivity;
import com.lixin.carclassstore.activity.StoreActivity;
/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
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
    String Url = "http://116.255.239.201:8080/carmallService/service.action";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        initView();
        return view;
    }

    private void initView() {
        funcTxts = this.getResources().getStringArray(R.array.home_functions);
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
        funcViews[14] = view.findViewById(R.id.iv_car);
        funcViews[15] = view.findViewById(R.id.text_plated_crystal_maintenance);
        funcViews[16] = view.findViewById(R.id.text_car_store);
        funcViews[17] = view.findViewById(R.id.text_special_offer);
        funcViews[18] = view.findViewById(R.id.text_automative_lighting);
        funcViews[19] = view.findViewById(R.id.text_refrigerator);
        funcViews[20] = view.findViewById(R.id.text_more);

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
}