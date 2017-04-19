package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.SortAdapter;
import com.lixin.carclassstore.bean.CarStyleBean;
import com.lixin.carclassstore.bean.JavaBean;
import com.lixin.carclassstore.bean.SortModel;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.CharacterParser;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.PinyinComparator;
import com.lixin.carclassstore.utils.SideBar;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.view.ClearEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by 小火
 * Create time on  2017/4/8
 * My mailbox is 1403241630@qq.com
 */

public class CarStyleChooseActivity extends BaseActivity {
    private ClearEditText filter_edit;
    private ListView sortListView;
    private TextView text_dialog;
    private SideBar sideBar;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private View head;
    private List<CarStyleBean.hotCarsList> hotCars = new ArrayList<>();
    private List<CarStyleBean.carsSelectList> carsSelect = new ArrayList<>();
    //汉字转换成拼音的类
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;
    private CarStyleBean carStyleBean;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private View[] funcViews = new View[8];
    private PinyinComparator pinyinComparator;
    private TextView tvHotCar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        setTitleText("车型选择");
        hideBack(false);
        initViews();
        getdata();
    }
    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        filter_edit = (ClearEditText) findViewById(R.id.filter_edit);
        filter_edit.setVisibility(View.VISIBLE);
        pinyinComparator = new PinyinComparator();
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        text_dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(text_dialog);
        head = LayoutInflater.from(CarStyleChooseActivity.this).inflate(R.layout.include_hot_car,null);
        funcViews[0] = head.findViewById(R.id.car_style_0);
        funcViews[1] = head.findViewById(R.id.car_style_1);
        funcViews[2] = head.findViewById(R.id.car_style_2);
        funcViews[3] = head.findViewById(R.id.car_style_3);
        funcViews[4] = head.findViewById(R.id.car_style_4);
        funcViews[5] = head.findViewById(R.id.car_style_5);
        funcViews[6] = head.findViewById(R.id.car_style_6);
        funcViews[7] = head.findViewById(R.id.car_style_7);
        tvHotCar = (TextView) head.findViewById(R.id.text_hot_car);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        if (head != null)
            sortListView.addHeaderView(head);
        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, (Comparator<? super SortModel>) pinyinComparator);
        adapter.updateListView(filterDateList);
    }
    //请求参数
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getCarSelectInfo\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageLong(context, "网络异常");
                dialog1.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                dialog1.dismiss();
                carStyleBean = gson.fromJson(response, CarStyleBean.class);
                carsSelect = carStyleBean.carsSelectList;
                hotCars = carStyleBean.hotCarsList;
                Log.i("pppp", "carsSelect: " + carsSelect.get(0).getCarleader());
                Log.i("pppp", "hotCars: " + hotCars.get(0).getCarleader());
                Log.i("pppp", "hotCars: " + hotCars.get(0).getCarBrandId());

                String[] data = new String[carsSelect.size()];
                for (int i = 0; i < carsSelect.size(); i++) {
                    data[i] = carsSelect.get(i).getCarName();
                }
                Log.i("pppp", "data: " + data[0]);
                initData(data);
                initHeadData(hotCars);
            }
        });
    }

    private void initHeadData(List<CarStyleBean.hotCarsList> hotCars) {
        String[] bigBGs = new String[hotCars.size()];
        String[] funcTxts = new String[hotCars.size()];
        for (int i = 0; i < hotCars.size(); i++) {
            bigBGs[i] = hotCars.get(i).getCarleader();
            funcTxts[i] = hotCars.get(i).getCarName();
        }
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
    }
    private void initData(String[] data) {
        SourceDateList = filledData(data);
        // 根据a-z进行排序源数据
        SortModel sortModel;

        Collections.sort(SourceDateList, (Comparator<? super SortModel>) pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getApplication(), ((SortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,CarSeriesActivity.class);
                intent.putExtra("carbrandId",carsSelect.get(position-1).getCarBrandId());
                intent.putExtra("carname",carsSelect.get(position-1).getCarName());
                intent.putExtra("carleader",carsSelect.get(position - 1).getCarleader());
                Log.i("carbrandId", "onItemClick: " + carsSelect.get(position-1).getCarBrandId());
                startActivity(intent);
            }
        });
    }
}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        lv_test = (ListView)findViewById(R.id.lv_test);
////      头布局
//        vHead= View.inflate(this, R.layout.item_main, null);
//        ImageView iv_img= (ImageView) vHead.findViewById(R.id.iv_img);
////      头布局放入listView中
//        lv_test.addHeaderView(vHead);
////      绑定适配器
//        String [] arrarStr={"刘备","关羽","张飞","马超","黄忠"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrarStr);
//        lv_test.setAdapter(adapter);
//        myOnClick();
//
//    }
//    private void myOnClick() {
//        lv_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "position:"+position, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//}