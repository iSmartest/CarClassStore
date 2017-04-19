package com.lixin.carclassstore.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.LogInfo;
import com.lixin.carclassstore.bean.ReplyBean;
import com.lixin.carclassstore.dialog.TipsDialog;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SharedPreferencesUtil;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.view.ChangeAddressPopwindow;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 * 金融保险
 */

public class MoneySafeActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout linear_driving_city,linear_yes_no_licence;
    private TextView text_city_name,text_yes_or_no,text_choose_time,text_sumit;
    private EditText edi_input_car_licence_num,edi_car_price;
    int mYear, mMonth, mDay;
    private String uid = "123";
    private String insuranceIsNum;
    final int DATE_DIALOG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_safe);
        setTitleText("金融保险");
        hideBack(false);
        initView();
    }
    private void initView() {
        linear_driving_city = (LinearLayout) findViewById(R.id.linear_driving_city);
        linear_yes_no_licence = (LinearLayout) findViewById(R.id.linear_yes_no_licence);
        text_city_name = (TextView) findViewById(R.id.text_city_name);
        text_yes_or_no = (TextView) findViewById(R.id.text_yes_or_no);
        text_choose_time = (TextView) findViewById(R.id.text_choose_time);
        edi_car_price = (EditText) findViewById(R.id.edi_car_price);
        text_sumit = (TextView) findViewById(R.id.text_sumit);
        edi_input_car_licence_num = (EditText) findViewById(R.id.edi_input_car_licence_num);
        linear_driving_city.setOnClickListener(this);
        linear_yes_no_licence.setOnClickListener(this);
        text_choose_time.setOnClickListener(this);
        text_sumit.setOnClickListener(this);
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_sumit:
                sumit();
                break;
            case R.id.text_choose_time:
                showDialog(DATE_DIALOG);
                break;
            case R.id.linear_yes_no_licence:
                new AlertDialog.Builder(MoneySafeActivity.this).setTitle("提示").setMessage("是否有车牌")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                text_yes_or_no.setText("是");
                                insuranceIsNum = "0";
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        text_yes_or_no.setText("否");
                        insuranceIsNum = "1";
                    }
                }).show();
                break;
            case R.id.linear_driving_city:
                ChangeAddressPopwindow mChangeAddressPopwindow = new ChangeAddressPopwindow(MoneySafeActivity.this);
                mChangeAddressPopwindow.setAddress("广东", "深圳", "福田区");
                mChangeAddressPopwindow.showAtLocation(text_city_name, Gravity.BOTTOM, 0, 0);
                mChangeAddressPopwindow
                        .setAddresskListener(new ChangeAddressPopwindow.OnAddressCListener() {

                            @Override
                            public void onClick(String province, String city, String area) {
                                // TODO Auto-generated method stub
                                Toast.makeText(MoneySafeActivity.this,
                                        province + "-" + city + "-" + area,
                                        Toast.LENGTH_LONG).show();
                                text_city_name.setText(province + city + area);
                            }
                        });
                break;
        }
    }

    private void sumit() {
        String carPrice = edi_car_price.getText().toString().trim();
        String carLicence = edi_input_car_licence_num.getText().toString().trim();
        String yesNo = text_yes_or_no.getText().toString().trim();
        String chooseTime = text_choose_time.getText().toString().trim();
        String carCity = text_city_name.getText().toString().trim();
        if (TextUtils.isEmpty(carPrice)) {
            ToastUtils.showMessageShort(context, "请输入爱车价格！");
            return;
        } else if (TextUtils.isEmpty(carLicence)){
            ToastUtils.showMessageShort(context, "请输入车牌号码！");
        } else if (TextUtils.isEmpty(yesNo)) {
            ToastUtils.showMessageShort(context, "是否有车牌");
        } else if (TextUtils.isEmpty(chooseTime)) {
            ToastUtils.showMessageShort(context, "请选择提车时间");
        } else if (TextUtils.isEmpty(carCity)) {
            ToastUtils.showMessageShort(context, "请选择行驶城市");
        }
        getdata(carPrice,carLicence,chooseTime,carCity);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display() {
        text_choose_time.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
    private void getdata(final String insurancePrice, final String insuranceCity, final String insuranceCarNum, final String insuranceTime ) {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"userInsurance\",\"insuranceCity\":\"" + insuranceCity + "\"" + ",\"uid\":\"" + uid + "\"" + ",\"insuranceIsNum\":\"" + insuranceIsNum + "\"" +
                ",\"insuranceCarNum\":\"" + insuranceCarNum +"\"" + ",\"insuranceTime\":\"" + insuranceTime +"\"" + ",\"insurancePrice\":\"" + insurancePrice + "\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showMessageShort(context, e.getMessage());
                        dialog1.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("111", "onResponse: " + response.toString());
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        ReplyBean rplyBean = gson.fromJson(response, ReplyBean.class);
                        if (rplyBean.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, rplyBean.getResultNote());
                            return;
                        }else {
                            ToastUtils.showMessageShort(context, "提交成功！");
                            SharedPreferencesUtil.putSharePre(context,"insuranceCity",insuranceCity);
                            SharedPreferencesUtil.putSharePre(context,"insuranceCarNum",insuranceCarNum);
                            SharedPreferencesUtil.putSharePre(context,"insuranceTime",insuranceTime);
                            SharedPreferencesUtil.putSharePre(context,"insurancePrice",insurancePrice);
                        }
                    }
                });
    }
}
