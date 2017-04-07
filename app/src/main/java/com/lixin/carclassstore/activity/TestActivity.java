package com.lixin.carclassstore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.JavaBean;
import com.lixin.carclassstore.utils.GsonTools;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by 小火
 * Create time on  2017/4/7
 * My mailbox is 1403241630@qq.com
 */

public class TestActivity extends Activity{
    String Url = "http://116.255.239.201:8080/carmallService/service.action";
    private static final String TAG = "hhhhhh";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String jsonString = netLink(Url);//从网络获取数据
        Log.i(TAG, "onCreate: " + jsonString);
        JavaBean bean = GsonTools.getPerson(jsonString, JavaBean.class);//解析json数据

    }
    public static String netLink(String URL) {
        HttpClient httpClient = new DefaultHttpClient();
        //访问指定的服务器
        HttpGet httpGet = new HttpGet(URL);
        HttpResponse httpResponse = null;
        String response = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (httpResponse.getStatusLine().getStatusCode() == 2000) {
            //200说明请求和响应都是成功的
            HttpEntity entity = httpResponse.getEntity();
            try {
                response = EntityUtils.toString(entity,"utf-8");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return response;
    }
}
