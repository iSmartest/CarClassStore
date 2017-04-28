package com.lixin.carclassstore.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.view.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by 小火
 * Create time on  2017/4/27
 * My mailbox is 1403241630@qq.com
 * 爱车档案
 */

public class CarFilesActivity extends Activity implements View.OnClickListener {
    private RoundedImageView car_image;
    private ImageView Iv_files_back;
    private TextView car_text,car_style,car_model_text,text_choose_time01,text_choose_time02
            ,text_choose_time03, text_choose_time04,text_base_add,tv_files_titleText;
    private String mCarIcon;
    private String mCarName;
    private String mCarModel;
    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_foles);
        if (TextUtils.isEmpty(mCarIcon)){
            new AlertDialog.Builder(CarFilesActivity.this).setTitle("提示").setMessage("是否添加爱车")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(CarFilesActivity.this,CarStyleChooseActivity.class);
                            intent.putExtra("flag","4");
                            startActivity(intent);
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }else {
            Intent intent = getIntent();
            mCarIcon = intent.getStringExtra("carIcon");
            mCarName = intent.getStringExtra("carName");
            mCarModel = intent.getStringExtra("carStyle");
        }
        initView();
    }

    private void initView() {
        car_image = (RoundedImageView) findViewById(R.id.car_image);
        Picasso.with(CarFilesActivity.this).load(mCarIcon).into(car_image);
        car_text = (TextView) findViewById(R.id.car_text);
        car_text.setText(mCarName);
        car_style = (TextView) findViewById(R.id.car_style);
        car_style.setText(mCarName);
        car_model_text = (TextView) findViewById(R.id.car_model_text);
        car_model_text.setText(mCarModel);
        text_choose_time01 = (TextView) findViewById(R.id.text_choose_time01);
        text_choose_time01.setOnClickListener(this);
        text_choose_time02 = (TextView) findViewById(R.id.text_choose_time02);
        text_choose_time02.setOnClickListener(this);
        text_choose_time03 = (TextView) findViewById(R.id.text_choose_time03);
        text_choose_time03.setOnClickListener(this);
        text_choose_time04 = (TextView) findViewById(R.id.text_choose_time04);
        text_choose_time04.setOnClickListener(this);
        Iv_files_back = (ImageView) findViewById(R.id.Iv_files_back);
        Iv_files_back.setOnClickListener(this);
        text_base_add = (TextView) findViewById(R.id.text_base_add);
        text_base_add.setOnClickListener(this);
        tv_files_titleText = (TextView) findViewById(R.id.tv_files_titleText);
        tv_files_titleText.setText("爱车档案");
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_choose_time01:
                showDialog(DATE_DIALOG);
                flag = 1;
                break;
            case R.id.text_choose_time02:
                showDialog(DATE_DIALOG);
                flag = 2;
                break;
            case R.id.text_choose_time03:
                showDialog(DATE_DIALOG);
                flag = 3;
                break;
            case R.id.text_choose_time04:
                showDialog(DATE_DIALOG);
                flag = 4;
                break;
            case R.id.text_base_add:
                suimt();
                break;
            case R.id.Iv_files_back:
                finish();
                break;
        }
    }

    private void suimt() {
        String chooseTime01 = text_choose_time01.getText().toString().trim();
        String chooseTime02 = text_choose_time02.getText().toString().trim();
        String chooseTime03 = text_choose_time03.getText().toString().trim();
        String chooseTime04 = text_choose_time04.getText().toString().trim();
        if (TextUtils.isEmpty(chooseTime01)) {
            ToastUtils.showMessageShort(context, "请选择生产时间！");
            return;
        } else if (TextUtils.isEmpty(chooseTime02)){
            ToastUtils.showMessageShort(context, "请选择保险时间！");
        } else if (TextUtils.isEmpty(chooseTime03)) {
            ToastUtils.showMessageShort(context, "请选择上路时间!");
        } else if (TextUtils.isEmpty(chooseTime04)) {
            ToastUtils.showMessageShort(context, "请选择保养时间!");
        }

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
        if (flag == 1)
            text_choose_time01.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
        else if (flag == 2)
            text_choose_time02.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
        else if (flag == 3)
            text_choose_time03.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
        else if (flag == 4)
            text_choose_time04.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
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
}
