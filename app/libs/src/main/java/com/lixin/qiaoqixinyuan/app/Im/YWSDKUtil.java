package com.lixin.qiaoqixinyuan.app.Im;

import android.content.Context;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;


/**
 * 运旺IM登陆，返回YWIMKit
 * Created by Slingge on 2016/12/21 0021.
 */

public class YWSDKUtil {

        public final static String APP_KEY ="23697682";
   /* public final static String APP_KEY = "23015524";//云旺测试key,账号visitor1-visitor100,密码 taobao1234*/

    public static YWIMKit mIMKit = null;
    public static YWIMCore imCore=null;

    public static void loginYM(final String userid, final Context context) {
        //此实现不一定要放在Application onCreate中
//        final String userid = "testpro1";
        //此对象获取到后，保存为全局对象，供APP使用
        //此对象跟用户相关，如果切换了用户，需要重新获取
        mIMKit = YWAPI.getIMKitInstance(userid, APP_KEY);
        imCore = YWAPI.createIMCore(userid, APP_KEY);
        //开始登录
        IYWLoginService loginService = mIMKit.getLoginService();
        final YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, "111111");
        loginService.login(loginParam, new IWxCallback() {
            @Override
            public void onSuccess(Object... arg0) {
                ToastUtil.showToast(context,"IM登陆成功");

            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onError(int errCode, String description) {
                ToastUtil.showToast(context,"IM登陆失败," + description);
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
            }
        });
    }

    public static void Signout(){
        IYWLoginService loginService = mIMKit.getLoginService();
        loginService.logout(new IWxCallback() {
            @Override
            public void onSuccess(Object... objects) {
            }
            @Override
            public void onError(int i, String s) {
            }
            @Override
            public void onProgress(int i) {
            }
        });
    }

    public static YWIMKit getywIMKit() {
        return mIMKit;
    }

    public static YWIMCore getImCore() {
        return imCore;
    }

}
