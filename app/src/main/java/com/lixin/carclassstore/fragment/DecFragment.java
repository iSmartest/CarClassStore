package com.lixin.carclassstore.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.DecAdapter;
import com.lixin.carclassstore.bean.DecBean;
import com.lixin.carclassstore.bean.OpinionBean;
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
 * Create time on  2017/4/19
 * My mailbox is 1403241630@qq.com
 * 详情
 */

public class DecFragment extends BaseFragment{
    private View view;
    private ListView list_dec;
    private View foot;
    private ProgressBar progressBar;
    private WebView mWebView;
    private DecAdapter mDecAdapter;
    private String commodityid;
    private LinearLayout shopStyle;
    private List<DecBean.parameterTypes> mList = new ArrayList<>();
    private String url;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dec,null);
        Bundle bundle = getArguments();
        if(bundle != null){
            commodityid = bundle.getString("commodityid");
        }
        Log.i("DecFragment", "onCreateView: " + commodityid);
        Log.i("DecFragment", "onCreateView: " + url);
        initView();
        getdata();
        initWeb();
        return view;
    }

    private void initWeb() {
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setLoadsImagesAutomatically(true);
        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);
        saveData(mWebSettings);
        newWin(mWebSettings);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);
        if (!TextUtils.isEmpty(url)){
            mWebView.loadUrl(url);
        }

    }

    private void initView() {
        list_dec = (ListView) view.findViewById(R.id.list_dec);
        foot = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dec_foot,null);
        shopStyle = (LinearLayout) foot.findViewById(R.id.linear_view_shop_style);
        progressBar = (ProgressBar) foot.findViewById(R.id.progressBar);
        mWebView = (WebView) foot.findViewById(R.id.webView);
        list_dec.addFooterView(foot);
        mDecAdapter = new DecAdapter(getActivity());
        list_dec.setAdapter(mDecAdapter);
    }

    //请求参数
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getCommoditysDetilInfo\",\"commodityid\":\""
                + commodityid + "\"}";
        params.put("json", json);
        Log.i("DecFragment", "getdata: " + json.toString());
        dialog.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageShort(context, e.getMessage());
                dialog.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("DecFragment", "onResponse: " + response.toString());
                Gson gson = new Gson();
                dialog.dismiss();
                DecBean decBean = gson.fromJson(response, DecBean.class);
                if (decBean.result.equals("1")) {
                    ToastUtils.showMessageShort(context, decBean.resultNote);
                    return;
                }
                List<DecBean.parameterTypes> parameterTypeslist = decBean.parameterTypes;
                Log.i("DecFragment", "commodityslist: " + parameterTypeslist.get(0).getParameters());
                mList.addAll(parameterTypeslist);
                mDecAdapter.setDecList(getActivity(),mList);
                list_dec.setAdapter(mDecAdapter);
                url = decBean.commodityWebLink;
            }
        });
    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        String appCachePath = getActivity().getApplicationContext().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(false);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    WebViewClient webViewClient = new WebViewClient() {

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    };

    WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            setTitleText(title);
//            setTitleText("查违章");
        }

        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        //=========HTML5定位==========================================================

        //=========多窗口的问题==========================================================
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }
        //=========多窗口的问题==========================================================
    };
}
