package com.lixin.carclassstore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.TagAdapter;
import com.lixin.carclassstore.bean.OpinionBean;
import com.lixin.carclassstore.bean.StoreDetailBean;
import com.lixin.carclassstore.fragment.DecFragment;
import com.lixin.carclassstore.fragment.OpinionFragment;
import com.lixin.carclassstore.fragment.ShopFragment;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.view.Attribute;
import com.lixin.carclassstore.view.FlowLayout;
import com.lixin.carclassstore.view.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by 小火
 * Create time on  2017/3/29
 * My mailbox is 1403241630@qq.com
 * 商品详情
 */

public class ShopDetailsActivity extends BaseActivity implements View.OnClickListener,ShopFragment.CallBackValue{
    private TextView[] mTextView;
    private Fragment[] mFragments;
    private FragmentTransaction transaction;
    private int current = 0;
    private List<StoreDetailBean> mList = new ArrayList<>();
    private ImageView imBack,imTelephone;
    private String commodityid;
    private String meunid;
    private String commodityShopid;
    private String commodityBrandid;
    private String commodityType;
    private String number;
    private TextView addShoppingCart,fastBuy;
    private String uid ;
    private Attribute SizeAtt = new Attribute();
    private Attribute ColorAtt = new Attribute();
    private int Num;
    LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_shop_details);
        Intent intent = getIntent();
        commodityid = intent.getStringExtra("commodityid");
        meunid = intent.getStringExtra("meunid");
        commodityShopid = intent.getStringExtra("commodityShopid");
        commodityBrandid = intent.getStringExtra("commodityBrandid");
        commodityType = intent.getStringExtra("commodityType");
        uid = (String) SPUtils.get(context,"uid","");
        initView();
        initFragment();
        refreshView();
        getdata();
    }
    protected void initView() {
        mTextView = new TextView[3];
        mTextView[0] = (TextView) findViewById(R.id.text_shop);
        mTextView[1] = (TextView) findViewById(R.id.text_dec);
        mTextView[2] = (TextView) findViewById(R.id.text_opinion);
        imBack = (ImageView) findViewById(R.id.img_back);
        imTelephone = (ImageView) findViewById(R.id.im_telephone);
        addShoppingCart = (TextView) findViewById(R.id.text_immediately_pay);
        fastBuy = (TextView) findViewById(R.id.text_fast_buy);
        imBack.setOnClickListener(this);
        fastBuy.setOnClickListener(this);
        imTelephone.setOnClickListener(this);
        addShoppingCart.setOnClickListener(this);
    }
    private void initFragment() {
        mFragments = new Fragment[3];
        mFragments[0] = new ShopFragment();
        mFragments[1] = new DecFragment();
        mFragments[2] = new OpinionFragment();
        setCurrent(0);
    }
    private void setCurrent(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("commodityid", commodityid);
        bundle.putString("meunid", commodityType);
        bundle.putString("commodityShopid", commodityShopid);
        bundle.putString("commodityBrandid", commodityBrandid);
        mFragments[position].setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_shop_layout_content, mFragments[position]);
        transaction.commitAllowingStateLoss();
//        transaction.commit();
        mTextView[position].setSelected(true);
        for (int i = 0; i < mTextView.length; i++) {
            if (i != position) {
                mTextView[i].setSelected(false);
            }
        }
        current = position;
    }
    private void refreshView() {
        for (int i = 0; i < mTextView.length; i++) {
            mTextView[i].setId(i);
            mTextView[i].setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case 0:
                setCurrent(0);
                break;
            case 1:
                setCurrent(1);
                break;
            case 2:
                setCurrent(2);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.im_telephone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
                break;
            case R.id.text_immediately_pay:
                CommodityAttribute mCommodityAttribute = new CommodityAttribute(ShopDetailsActivity.this);
                mCommodityAttribute.showAtLocation(addShoppingCart, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.text_fast_buy:
                ToastUtils.showMessageShort(context,"跳转到支付页面");
                break;
            default:
                break;
        }
    }
    @Override
    public void SendMessageValue(String strValue) {
        // TODO Auto-generated method stub
        number = strValue;
    }
    //请求参数
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getCommoditysInfo\",\"uid\":\"" + uid +"\",\"meunid\":\""
                + meunid + "\",\"commodityid\":\"" + commodityid + "\",\"commodityBrandid\":\"" + commodityBrandid +"\",\"commodityShopid\":\"" +
                commodityShopid +"\"}";
        params.put("json", json);
        Log.i("ShopDetailsActivity", "onResponse: " + json.toString());
        dialog1.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageShort(context, e.getMessage());
                dialog1.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("ShopDetailsActivity", "onResponse: " + response.toString());
                Gson gson = new Gson();
                dialog1.dismiss();
                OpinionBean opinionBean = gson.fromJson(response, OpinionBean.class);
                if (opinionBean.result.equals("1")) {
                    ToastUtils.showMessageShort(context, opinionBean.resultNote);
                    return;
                }
            }
        });
    }

    /**
     * 商品属性PopupWind
     */

    public class CommodityAttribute extends PopupWindow {
        private View CommodityAttributeView;
        private TagFlowLayout mTfSize;
        private TagFlowLayout mTfColor;
        private TextView mTvOk,mNum;
        private mTagAdapter mSizeAdapter;
        private mTagAdapter mColorAdapter;
        private ImageView shopReduce,shopAdd;
        public CommodityAttribute(Activity context) {
            super(context);
//            Select(Sku);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            CommodityAttributeView = inflater.inflate(R.layout.sku_popupwindow, null);
            mTfSize = (TagFlowLayout) CommodityAttributeView.findViewById(R.id.tf_size);
            mTfColor = (TagFlowLayout) CommodityAttributeView.findViewById(R.id.tf_color);
            mTvOk = (TextView) CommodityAttributeView.findViewById(R.id.popupwind_ok);
            mNum = (TextView) CommodityAttributeView.findViewById(R.id.tv_shop_num);
            shopReduce = (ImageView) CommodityAttributeView.findViewById(R.id.iv_shop_reduce);
            shopAdd = (ImageView) CommodityAttributeView.findViewById(R.id.iv_shop_add);


//            mColorAdapter = new mTagAdapter(ColorAtt);
//            mTfColor.setAdapter(mColorAdapter);
//            mColorAdapter.setSelectedList(Color);
//            ColorStr = mColorDate.get(Color);
//            mSizeAdapter = new mTagAdapter(SizeAtt);
//            mTfSize.setAdapter(mSizeAdapter);
//            mSizeAdapter.setSelectedList(Size);
//            SizeStr = mSizeDate.get(Size);

//            //颜色属性标签点击事件
//            mTfColor.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//                boolean is;
//                @Override
//                public boolean onTagClick(View view, int position, FlowLayout parent) {
//
//                    is = false;
//                    //从Base类中拿到不可点击的属性名称进行比较
//                    List<String> st = ColorAtt.getFailureAliasName();
//                    for (int i = 0; i < st.size(); i++) {
//                        if (st.get(i).equals(mColorDate.get(position))) {
//                            is = true;
//                        }
//                    }
//                    //如是不可点击就进接return 这样就形成了不可点击的假像，达到了我们的目的
//                    if (is) {
//                        return true;
//                    }
//                    if (position == DefaultColor) {
//                        DefaultColor = -1;
//                        ColorStr = "";
//                        SizeAtt.FailureAliasName.clear();
//                        TagAdapNotify(mSizeAdapter, DefaultSize);
//                        return true;
//                    } else {
//                        DefaultColor = position;
//                        ColorStr = mColorDate.get(position);
//                    }
//                    SizeAtt.FailureAliasName.clear();
//                    for (int i = 0; i < mSizeDate.size(); i++) {
//                        String sku = ColorStr + ":" + mSizeDate.get(i);
//                        for (int j = 0; j < mFailureSkuDate.size(); j++) {
//                            if (sku.equals(mFailureSkuDate.get(j))) {
//                                SizeAtt.FailureAliasName.add(mSizeDate.get(i));
//                            }
//                        }
//                    }
//                    TagAdapNotify(mSizeAdapter, DefaultSize);
//                    return true;
//                }
//            });

//            //大小属性标签点击事件
//            mTfSize.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//                boolean is;
//
//                @Override
//                public boolean onTagClick(View view, int position, FlowLayout parent) {
//
//                    is = false;
//                    List<String> st = SizeAtt.getFailureAliasName();
//                    for (int i = 0; i < st.size(); i++) {
//                        if (st.get(i).equals(mSizeDate.get(position))) {
//                            is = true;
//                        }
//                    }
//                    if (is) {
//                        return true;
//                    }
//
//                    if (position == DefaultSize) {
//                        DefaultSize = -1;
//                        SizeStr = "";
//                        ColorAtt.FailureAliasName.clear();
//                        TagAdapNotify(mColorAdapter, DefaultColor);
//                        return true;
//                    } else {
//                        DefaultSize = position;
//                        SizeStr = mSizeDate.get(position);
//                    }
//
//                    ColorAtt.FailureAliasName.clear();
//                    for (int i = 0; i < mColorDate.size(); i++) {
//                        String sku = mColorDate.get(i) + ":" + SizeStr;
//                        for (int j = 0; j < mFailureSkuDate.size(); j++) {
//                            if (sku.equals(mFailureSkuDate.get(j))) {
//                                ColorAtt.FailureAliasName.add(mColorDate.get(i));
//                            }
//                        }
//                    }
//                    TagAdapNotify(mColorAdapter, DefaultColor);
//
//                    return true;
//                }
//            });
//
//
            mTvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (DefaultColor == -1 || DefaultSize == -1) {
//                        Toast.makeText(getApplicationContext(), "请选择属性", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    mTv.setText("所选属性:" + ColorStr + "," + SizeStr);
//                    Sku = ColorStr + ":" + SizeStr;
//                    Size = DefaultSize;
//                    Color = DefaultColor;
                    addShoppingCart.setVisibility(View.GONE);
                    fastBuy.setVisibility(View.VISIBLE);
                    dismiss();
                }
            });
            //增加按钮
            shopAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int temp = Integer.parseInt( mNum.getText().toString().trim());
                    temp ++ ;
                    mNum.setText(""+ temp);
                }
            });
            //减少按钮
            shopReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int temp = Integer.parseInt( mNum.getText().toString().trim());
                    temp -- ;
                    if (temp <= 1){
                        mNum.setText("1");
                    }else {
                        mNum.setText("" + temp);
                    }
                }
            });
            // 设置SelectPicPopupWindow的View
            this.setContentView(CommodityAttributeView);
            // 设置SelectPicPopupWindow弹出窗体的宽
            this.setWidth(ViewPager.LayoutParams.MATCH_PARENT);
            // 设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(ViewPager.LayoutParams.WRAP_CONTENT);
            // 在PopupWindow里面就加上下面两句代码，让键盘弹出时，不会挡住pop窗口。
            this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            // 设置popupWindow以外可以触摸
            this.setOutsideTouchable(true);
            // 以下两个设置点击空白处时，隐藏掉pop窗口
            this.setFocusable(true);
            this.setBackgroundDrawable(new BitmapDrawable());
            // 设置popupWindow以外为半透明0-1 0为全黑,1为全白
            backgroundAlpha(0.3f);
            // 添加pop窗口关闭事件
            this.setOnDismissListener(new poponDismissListener());
            // 设置动画--这里按需求设置成系统输入法动画
            this.setAnimationStyle(R.style.AnimBottom);
            // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
            CommodityAttributeView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    int height = CommodityAttributeView.findViewById(R.id.pop_layout)
                            .getTop();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {

                            dismiss();
                        }
                    }
                    return true;
                }
            });
        }
    }

    /**
     * PopouWindow设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        this.getWindow().setAttributes(lp);
    }

    /**
     * PopouWindow添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    /**
     * 尺寸的流式布局Adatper
     *
     * @author itzwh
     */
    class mTagAdapter extends TagAdapter<String> {

        private TextView tv;

        public mTagAdapter(Attribute ab) {
            super(ab);
        }

        @Override
        public View getView(FlowLayout parent, int position, Attribute t) {
            boolean is = false;
            //两个布局,一个是可点击的布局，一个是不可点击的布局
            List<String> st = t.FailureAliasName;
            if (st != null) {
                for (int i = 0; i < st.size(); i++) {
                    if (st.get(i).equals(t.aliasName.get(position))) {
                        is = true;
                    }
                }
            }
            if (!is) {
                tv = (TextView) mInflater.inflate(R.layout.popupwindow_tv, parent, false);
                tv.setText(t.aliasName.get(position));
            } else {
                tv = (TextView) mInflater.inflate(R.layout.popupwindow_tv1, parent, false);
                tv.setText(t.aliasName.get(position));
            }

            return tv;
        }
    }
}
