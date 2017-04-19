package com.lixin.qiaoqixinyuan.app.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.widgets.DatePick;
import com.aigestudio.wheelpicker.widgets.ProvincePick;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.view.CityVo;
import com.lixin.qiaoqixinyuan.app.view.ProvinceVo;
import com.lixin.qiaoqixinyuan.app.view.TxtReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static com.lixin.qiaoqixinyuan.R.id.iv_pin_cheren_fache_time;

public class Pin_search_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private TextView tv_pin_search_city;
    private TextView tv_pin_search_data;
    private LinearLayout layout_pin_search_fenlei;
    private TextView tv_pin_search_time;
    private LinearLayout layout_pin_search_time;
    private TextView tv_pin_search_search;
    private LinearLayout activity_pin_search_;
    private TextView tv_pin_search_renche;
    private TextView tv_pin_search_cheren;
    private TextView tv_pin_search_data_title;
    private String searchType;
    private ArrayList<ProvinceVo> provinceList;
    private ArrayList<String> provinceStrList;
    private HashMap<String, ArrayList<String>> cityStrList;
    private ArrayList<CityVo> cityList;
    private String addressId;
    private TextView tv_pin_search_qidian;
    private TextView tv_pin_search_zhongdian;
    private LinearLayout layout_else_city;
    private TextView tv_city_qinchang;
    private TextView tv_city_changqin;
    private TextView tv_city_qintai;
    private TextView tv_city_taiqin;
    private TextView tv_city_else;
    private AlertDialog alertDialog;
    private String carpoolingtype;
    private String realeasepeoplestarting;
    private String realeasepeopleending;
    private String realeasecardate;
    private TextView layout_time;
    private Spinner sp_pin_search_fenlei;
    private Spinner sp_pin_search_time;
    String[] mItem1;
    String[] mItem2;
    String[] mItem3;
    String[] mItem4;
    String[] mItem5;
    private String time1;
    private String time2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_search_);
        mItem1 = getResources().getStringArray(R.array.time1);
        mItem2 = getResources().getStringArray(R.array.time2);
        mItem3 = getResources().getStringArray(R.array.time3);
        mItem4 = getResources().getStringArray(R.array.time4);
        mItem5 = getResources().getStringArray(R.array.time5);
        initView();
        addcity();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("拼车查询");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        tv_pin_search_city = (TextView) findViewById(R.id.tv_pin_search_city);
        tv_pin_search_city.setOnClickListener(this);
        tv_pin_search_data = (TextView) findViewById(R.id.tv_pin_search_data);
        tv_pin_search_data.setOnClickListener(this);
        layout_pin_search_fenlei = (LinearLayout) findViewById(R.id.layout_pin_search_fenlei);
        tv_pin_search_time = (TextView) findViewById(R.id.tv_pin_search_time);
        tv_pin_search_time.setOnClickListener(this);
        layout_pin_search_time = (LinearLayout) findViewById(R.id.layout_pin_search_time);
        tv_pin_search_search = (TextView) findViewById(R.id.tv_pin_search_search);
        tv_pin_search_search.setOnClickListener(this);
        activity_pin_search_ = (LinearLayout) findViewById(R.id.activity_pin_search_);
        tv_pin_search_renche = (TextView) findViewById(R.id.tv_pin_search_renche);
        tv_pin_search_renche.setOnClickListener(this);
        tv_pin_search_cheren = (TextView) findViewById(R.id.tv_pin_search_cheren);
        tv_pin_search_cheren.setOnClickListener(this);
        tv_pin_search_data_title = (TextView) findViewById(R.id.tv_pin_search_data_title);
        tv_pin_search_data_title.setOnClickListener(this);
        tv_pin_search_qidian = (TextView) findViewById(R.id.tv_pin_search_qidian);
        tv_pin_search_qidian.setOnClickListener(this);
        tv_pin_search_zhongdian = (TextView) findViewById(R.id.tv_pin_search_zhongdian);
        tv_pin_search_zhongdian.setOnClickListener(this);
        layout_else_city = (LinearLayout) findViewById(R.id.layout_else_city);
        layout_else_city.setOnClickListener(this);
        layout_time = (TextView) findViewById(R.id.layout_time);
        sp_pin_search_fenlei = (Spinner) findViewById(R.id.sp_pin_search_fenlei);
        sp_pin_search_time = (Spinner) findViewById(R.id.sp_pin_search_time);
        sp_pin_search_fenlei.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time1 = adapterView.getItemAtPosition(i).toString();
                switch (i){
                    case 0:
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(context, R.layout.item_textview, mItem1);
                        sp_pin_search_time.setAdapter(adapter1);
                        break;
                    case 1:
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context, R.layout.item_textview, mItem2);
                        sp_pin_search_time.setAdapter(adapter2);
                        break;
                    case 2:
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(context, R.layout.item_textview, mItem3);
                        sp_pin_search_time.setAdapter(adapter3);
                        break;
                    case 3:
                        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(context, R.layout.item_textview, mItem4);
                        sp_pin_search_time.setAdapter(adapter4);
                        break;
                    case 4:
                        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(context, R.layout.item_textview, mItem5);
                        sp_pin_search_time.setAdapter(adapter5);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_pin_search_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time2 = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pin_search_renche:
                tv_pin_search_data_title.setText("发车日期");
                layout_time.setText("发车时间");
                layout_pin_search_time.setVisibility(View.VISIBLE);
                layout_pin_search_fenlei.setVisibility(View.GONE);
                searchType = "1";
                break;
            case R.id.tv_pin_search_cheren:
                tv_pin_search_data_title.setText("乘坐日期");
                layout_time.setText("乘车时间");
                layout_pin_search_time.setVisibility(View.GONE);
                layout_pin_search_fenlei.setVisibility(View.VISIBLE);
                searchType = "0";
                break;
            case R.id.tv_pin_search_search:
                Intent data = new Intent();
                data.putExtra("searchType", searchType);
                data.putExtra("carpoolingtype", carpoolingtype);
                data.putExtra("realeasepeoplestarting", realeasepeoplestarting);
                data.putExtra("realeasepeopleending", realeasepeopleending);
                data.putExtra("realeasecardate", realeasecardate);
                data.putExtra("realeasecarinterval", time1+time2);
                setResult(12, data);
                finish();
                break;
            case R.id.tv_pin_search_city:
                showdialog();
                break;
            case R.id.tv_pin_search_data:
                showtime();
                break;
            case R.id.tv_city_qinchang:
                carpoolingtype = "0";
                realeasepeoplestarting = "0";
                tv_pin_search_city.setText("沁源—长治");
                alertDialog.dismiss();
                break;
            case R.id.tv_city_changqin:
                carpoolingtype = "1";
                realeasepeoplestarting = "0";
                realeasepeopleending = "0";
                tv_pin_search_city.setText("长治-沁源");
                alertDialog.dismiss();
                break;
            case R.id.tv_city_qintai:
                carpoolingtype = "2";
                realeasepeoplestarting = "0";
                realeasepeopleending = "0";
                tv_pin_search_city.setText("沁源—太原");
                alertDialog.dismiss();
                break;
            case R.id.tv_city_taiqin:
                carpoolingtype = "3";
                realeasepeoplestarting = "0";
                realeasepeopleending = "0";
                tv_pin_search_city.setText("太原-沁源");
                alertDialog.dismiss();
                break;
            case R.id.tv_city_else:
                carpoolingtype = "4";
                realeasepeoplestarting = "0";
                realeasepeopleending = "0";
                tv_pin_search_city.setText("其他城市");
                alertDialog.dismiss();
                showcity();
                break;
            case R.id.iv_turnback:
                finish();
                break;
        }
    }

    private void showdialog() {
        alertDialog = new AlertDialog.Builder(this, R.style.Dialog).create();
        alertDialog.setView(new EditText(this));
        alertDialog.show();
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.city_setting, null);
        tv_city_qinchang = (TextView) view.findViewById(R.id.tv_city_qinchang);
        tv_city_changqin = (TextView) view.findViewById(R.id.tv_city_changqin);
        tv_city_qintai = (TextView) view.findViewById(R.id.tv_city_qintai);
        tv_city_taiqin = (TextView) view.findViewById(R.id.tv_city_taiqin);
        tv_city_else = (TextView) view.findViewById(R.id.tv_city_else);
        tv_city_qinchang.setOnClickListener(this);
        tv_city_changqin.setOnClickListener(this);
        tv_city_qintai.setOnClickListener(this);
        tv_city_taiqin.setOnClickListener(this);
        tv_city_else.setOnClickListener(this);
        alertDialog.getWindow().setContentView(view);
        Window dialogWindow = alertDialog.getWindow();
        dialogWindow.setWindowAnimations(R.style.Dialog);
        dialogWindow.setGravity(Gravity.BOTTOM);//显示在底部
        WindowManager m = getWindowManager();
        Display display = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        Point size = new Point();
        display.getSize(size);
        p.width = size.x;
        dialogWindow.setAttributes(p);
        //点击空白pop消失
    }

    private void showtime() {
        DatePick pickerPopWin = new DatePick.Builder(context, new DatePick.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                Toast.makeText(context, dateDesc, Toast.LENGTH_SHORT).show();
                realeasecardate = dateDesc;
                tv_pin_search_data.setText(dateDesc);
            }
        }).textConfirm("CONFIRM") //text of confirm button
                .textCancel("CANCEL") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                //.colorConfirm(getResources().getColor(R.color.colorAccent)//color of confirm button
                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                .minYear(1930) //min year in loop
                .maxYear(2020) // max year in loop
                .dateChose("2008-06-15") // date chose when init popwindow
                .build();
        pickerPopWin.showPopWin(Pin_search_Activity.this);
    }

    private void addcity() {
        provinceList = new ArrayList<>();
        cityList = new ArrayList<>();
        provinceStrList = new ArrayList<>();
        cityStrList = new HashMap<>();
        InputStream inputStream = getResources()
                .openRawResource(R.raw.cityinfo);
        TxtReader.getProvinceListAndCityList(inputStream, provinceList,
                cityList);
        ProvinceVo province;
        ArrayList<String> list;
        for (int i = 0; i < provinceList.size(); i++) {
            province = provinceList.get(i);
            provinceStrList.add(province.getProvinceName());
            list = getCityListByProviceCode(cityList, province.getProvincePostCode());
            cityStrList.put(province.getProvinceName(), list);
        }
    }

    private void showcity() {
        ProvincePick provincePopWin = new ProvincePick.Builder(context, new ProvincePick.OnProCityPickedListener() {
            @Override
            public void onProCityPickCompleted(String province, String city, String dateDesc) {
                Toast.makeText(context, dateDesc, Toast.LENGTH_SHORT).show();
                tv_pin_search_city.setText(dateDesc);
            }
        }).textConfirm("确定") //text of confirm button
                .textCancel("取消") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#00000000"))//color of confirm button
                .setProvinceList(provinceStrList) //min year in loop
                .setCityList(cityStrList) // max year in loop
                .dateChose("浙江省-宁波市") // date chose when init popwindow
                .build();
        provincePopWin.showPopWin(Pin_search_Activity.this);
    }

    private ArrayList<String> getCityListByProviceCode(ArrayList<CityVo> cityList, String provincePostCode) {
        ArrayList<String> cityName = new ArrayList<>();
        for (CityVo cv : cityList) {
            if (cv.getProvincePostCode().equals(provincePostCode)) {
                cityName.add(cv.getCityName());
            }
        }
        return cityName;
    }
}
