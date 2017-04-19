package com.lixin.qiaoqixinyuan.app.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.widgets.DatePick;
import com.aigestudio.wheelpicker.widgets.ProvincePick;
import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.CityVo;
import com.lixin.qiaoqixinyuan.app.view.ProvinceVo;
import com.lixin.qiaoqixinyuan.app.view.TxtReader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Pin_fabu_Activity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private Button tv_pin_fabu_renche;
    private Button tv_pin_fabu_cheren;
    private LinearLayout pin_layout_fabu;
    private View cheren;
    private View renche;
    private TextView iv_pin_cheren_qujian;
    private Spinner iv_pin_cheren_fache_data;
    private Spinner iv_pin_cheren_fache_time;
    private EditText iv_pin_cheren_qidian;
    private EditText iv_pin_cheren_zhongdian;
    private EditText iv_pin_cheren_dianhua;
    private EditText iv_pin_cheren_beizhu;
    private TextView iv_pin_cheren_fabu;
    private TextView iv_pin_renche_qujian;
    private TextView iv_pin_renche_chengche_data;
    private TextView iv_pin_renche_chengche_time;
    private EditText iv_pin_renche_qidian;
    private EditText iv_pin_renche_zhongdian;
    private TextView iv_pin_renche_dianhua;
    private TextView iv_pin_renche_fabu;
    private List<String> list = new ArrayList<>();
    private EditText iv_pin_renche_beizhu;
    private String token;
    private String uid;
    private TextView iv_pin_cheren_fa_cheng_data;
    private TextView iv_pin_cheren_fa_cheng_time;
    private String cartype = "0";
    private AlertDialog alertDialog;
    private ArrayList<ProvinceVo> provinceList;
    private ArrayList<String> provinceStrList;
    private HashMap<String, ArrayList<String>> cityStrList;
    private ArrayList<CityVo> cityList;
    private String addressId;
    private String carpoolingtype;
    private LinearLayout layout_search;
    private String realeasepeoplestarting;
    private String realeasepeopleending;
    private String realeasepeopledate;
    private Object realeasepeopleinterval;
    String[] mItem1;
    String[] mItem2;
    String[] mItem3;
    String[] mItem4;
    String[] mItem5;
    private TextView tv_city_qinchang;
    private TextView tv_city_changqin;
    private TextView tv_city_qintai;
    private TextView tv_city_taiqin;
    private TextView tv_city_else;
    private TextView iv_pin_cheren_fache_shijian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_fabu_);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
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
        tv_title.setText("拼车需求");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        tv_pin_fabu_renche = (Button) findViewById(R.id.tv_pin_fabu_renche);
        tv_pin_fabu_renche.setOnClickListener(this);
        tv_pin_fabu_cheren = (Button) findViewById(R.id.tv_pin_fabu_cheren);
        tv_pin_fabu_cheren.setOnClickListener(this);
        pin_layout_fabu = (LinearLayout) findViewById(R.id.pin_layout_fabu);
        cheren = View.inflate(this, R.layout.pin_chezhaoren_item, null);
                        /*    车找人*/
        iv_pin_cheren_qujian = (TextView) cheren.findViewById(R.id.iv_pin_cheren_qujian);
        iv_pin_cheren_qujian.setOnClickListener(this);
        iv_pin_cheren_fache_data = (Spinner) cheren.findViewById(R.id.iv_pin_cheren_fache_data);
        iv_pin_cheren_fache_time = (Spinner) cheren.findViewById(R.id.iv_pin_cheren_fache_time);
        iv_pin_cheren_qidian = (EditText) cheren.findViewById(R.id.iv_pin_cheren_qidian);
        iv_pin_cheren_qidian.setOnClickListener(this);
        iv_pin_cheren_zhongdian = (EditText) cheren.findViewById(R.id.iv_pin_cheren_zhongdian);
        iv_pin_cheren_zhongdian.setOnClickListener(this);
        iv_pin_cheren_dianhua = (EditText) cheren.findViewById(R.id.iv_pin_cheren_dianhua);
        iv_pin_cheren_dianhua.setOnClickListener(this);
        iv_pin_cheren_beizhu = (EditText) cheren.findViewById(R.id.iv_pin_cheren_beizhu);
        iv_pin_cheren_beizhu.setOnClickListener(this);
        iv_pin_cheren_fabu = (TextView) cheren.findViewById(R.id.iv_pin_cheren_fabu);
        iv_pin_cheren_fabu.setOnClickListener(this);
        iv_pin_cheren_fa_cheng_data = (TextView) cheren.findViewById(R.id.iv_pin_cheren_fa_cheng_data);
        iv_pin_cheren_fa_cheng_time = (TextView) cheren.findViewById(R.id.iv_pin_cheren_fa_cheng_time);
        layout_search = (LinearLayout) cheren.findViewById(R.id.layout_search);
        iv_pin_cheren_fache_shijian = (TextView) cheren.findViewById(R.id.iv_pin_cheren_fache_shijian);
        iv_pin_cheren_fache_shijian.setOnClickListener(this);
        pin_layout_fabu.addView(cheren);
        iv_pin_cheren_fache_data.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                realeasepeopledate = adapterView.getItemAtPosition(i).toString();
                switch (i) {
                    case 0:
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(context, R.layout.item_textview, mItem1);
                        iv_pin_cheren_fache_time.setAdapter(adapter1);
                        break;
                    case 1:
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context, R.layout.item_textview, mItem2);
                        iv_pin_cheren_fache_time.setAdapter(adapter2);
                        break;
                    case 2:
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(context, R.layout.item_textview, mItem3);
                        iv_pin_cheren_fache_time.setAdapter(adapter3);
                        break;
                    case 3:
                        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(context, R.layout.item_textview, mItem4);
                        iv_pin_cheren_fache_time.setAdapter(adapter4);
                        break;
                    case 4:
                        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(context, R.layout.item_textview, mItem5);
                        iv_pin_cheren_fache_time.setAdapter(adapter5);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        iv_pin_cheren_fache_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                realeasepeopleinterval = adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.iv_pin_cheren_qujian:
                showdialog();
                break;
            case R.id.tv_pin_fabu_renche:
                cartype = "0";
                tv_pin_fabu_renche.setTextColor(getResources().getColor(R.color.theme));
                tv_pin_fabu_cheren.setTextColor(Color.BLACK);
                iv_pin_cheren_fa_cheng_data.setText("乘车日期");
                iv_pin_cheren_fa_cheng_time.setText("乘车时间");
                break;
            case R.id.tv_pin_fabu_cheren:
                cartype = "1";
                tv_pin_fabu_renche.setTextColor(Color.BLACK);
                tv_pin_fabu_cheren.setTextColor(getResources().getColor(R.color.theme));
                iv_pin_cheren_fa_cheng_data.setText("发车日期");
                iv_pin_cheren_fa_cheng_time.setText("发车时间");
                break;
            case R.id.tv_city_qinchang:
                carpoolingtype = "0";
                layout_search.setVisibility(View.GONE);
                iv_pin_cheren_qujian.setText("沁源—长治");
                realeasepeoplestarting = "0";
                realeasepeopleending = "0";
                alertDialog.dismiss();
                break;
            case R.id.tv_city_changqin:
                carpoolingtype = "1";
                layout_search.setVisibility(View.GONE);
                iv_pin_cheren_qujian.setText("长治-沁源");
                realeasepeoplestarting = "0";
                realeasepeopleending = "0";
                alertDialog.dismiss();
                break;
            case R.id.tv_city_qintai:
                carpoolingtype = "2";
                layout_search.setVisibility(View.GONE);
                iv_pin_cheren_qujian.setText("沁源—太原");
                realeasepeoplestarting = "0";
                realeasepeopleending = "0";
                alertDialog.dismiss();
                break;
            case R.id.tv_city_taiqin:
                carpoolingtype = "3";
                layout_search.setVisibility(View.GONE);
                iv_pin_cheren_qujian.setText("太原-沁源");
                realeasepeoplestarting = "0";
                realeasepeopleending = "0";
                alertDialog.dismiss();
                break;
            case R.id.tv_city_else:
                carpoolingtype = "4";
                layout_search.setVisibility(View.VISIBLE);
                iv_pin_cheren_qujian.setText("其他城市");
                realeasepeoplestarting = iv_pin_cheren_qidian.getText().toString();
                realeasepeopleending = iv_pin_cheren_zhongdian.getText().toString();
                alertDialog.dismiss();
                showcity();
                break;
            case R.id.iv_pin_cheren_fabu:
                getren_che();
                break;
            case R.id.iv_pin_cheren_fache_shijian:
                showtime();
                break;
        }
    }
    private void showtime() {
        DatePick pickerPopWin = new DatePick.Builder(context, new DatePick.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                Toast.makeText(context, dateDesc, Toast.LENGTH_SHORT).show();
                iv_pin_cheren_fache_shijian.setText(dateDesc);
            }
        }).textConfirm("确定") //text of confirm button
                .textCancel("取消") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                //.colorConfirm(getResources().getColor(R.color.colorAccent)//color of confirm button
                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                .minYear(1930) //min year in loop
                .maxYear(2020) // max year in loop
                .dateChose("2008-06-15") // date chose when init popwindow
                .build();
        pickerPopWin.showPopWin(Pin_fabu_Activity.this);
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

    private void getren_che() {
   /*     cmd:”realeasepeople”
        uid:"12"    //用户id
        realeasepeoplephone:"12365236589"   //电话   （车找人必填，人找车选填，不填传0）
        cartype:"0"  // 0 车找人 1 人找车
        carpoolingtype:"0" //0 沁源-长治 1 长治-沁源 2 沁源-太原 3 太原-沁源 4 其他城市
        realeasepeoplestarting:"沁源"    //起点城市   其他城市传，非其他城市传0
        realeasepeopleending:"太原"    //终点城市    其他城市传，非其他城市传0
        realeasepeopledate:"今天"    //乘车日期
        realeasepeopleinterval:"中午12点-13点"    //乘车区间
        realeasepeoplenote:"备注"   //备注信息
        token:    [JPUSHService registrationID]   //推送token*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "realeasepeople");
        params.put("uid", "12");
        params.put("realeasepeoplephone", iv_pin_renche_dianhua.getText().toString());
        params.put("cartype", "1");
        params.put("carpoolingtype", "4");
        params.put("realeasepeoplestarting", iv_pin_renche_qidian.getText().toString());
        params.put("realeasepeopleending", iv_pin_renche_zhongdian.getText().toString());
        params.put("realeasepeopledate", "0");
        params.put("realeasepeopleinterval", "0");
        params.put("realeasepeoplenote",iv_pin_renche_beizhu.getText().toString())*/
        ;
        String json = "{\"cmd\":\"realeasepeople\",\"uid\":\"" + uid + "\"" +
                ",\"realeasepeoplephone\":\"" + iv_pin_cheren_dianhua.getText().toString() + "\"" +
                ",\"cartype\":\"" + cartype + "\"" +
                ",\"carpoolingtype\":\"" + carpoolingtype + "\"" +
                ",\"realeasepeoplestarting\":\"" + realeasepeoplestarting + "\"" +
                ",\"realeasepeopleending\":\"" + realeasepeopleending + "\"" +
                ",\"realeasepeopledate\":\"" + iv_pin_cheren_fache_shijian.getText().toString() + "\"" +
                ",\"realeasepeopleinterval\":\"" + realeasepeopledate+realeasepeopleinterval + "\"" +
                ",\"realeasepeoplenote\":\"" + iv_pin_cheren_beizhu.getText().toString() + "\"" +
                ",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        ToastUtil.showToast(context, resout_bean.resultNote);
                    }
                });
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
                iv_pin_cheren_qujian.setText(dateDesc);
            }
        }).textConfirm("确定") //text of confirm button
                .textCancel("取消") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                .setProvinceList(provinceStrList) //min year in loop
                .setCityList(cityStrList) // max year in loop
                .dateChose("浙江省-宁波市") // date chose when init popwindow
                .build();
        provincePopWin.showPopWin(Pin_fabu_Activity.this);
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
