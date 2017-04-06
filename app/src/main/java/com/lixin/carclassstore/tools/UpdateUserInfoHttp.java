package com.lixin.carclassstore.tools;

import android.content.Context;
import android.text.TextUtils;

import com.lixin.carclassstore.bean.DataAccess;
import com.lixin.carclassstore.bean.Struct;
import com.lixin.carclassstore.bean.UserInfo;
import com.lixin.carclassstore.http.RequestParameters;
import com.lixin.carclassstore.http.SimpleHttp;
import com.lixin.carclassstore.utils.ToastUtils;

import java.io.InputStream;


/**
 * 修改用户信息
 */
public  class UpdateUserInfoHttp extends SimpleHttp {
    /**
     * mType  值为1.用户头像 2.昵称 3.密保问题 4.密码
     * （修改头像时头像地址用obj2字段做返回）
     */
    private int mType;
    private RequestParameters parameters;
    private Struct result;
    private operateResult mOperateResult;

    public UpdateUserInfoHttp(Context context, int type, operateResult operateResult) {
        super(context);
        mType = type;
        result = new Struct();
        mOperateResult = operateResult;
    }

    @Override
    public Object onRequestSuccess(InputStream in, RequestParameters parameters) {
        return DataAccess.parseOperateResult(in);
    }

    @Override
    public void onRequestSuccessInUI(Object obj) {
        result = (Struct) obj;
        if (obj == null) {
            ToastUtils.showMessageShort(mContext, "修改失败");
        } else {
            if (result.what == 0) {
                ToastUtils.showMessageShort(mContext, "修改成功");
            } else {
                ToastUtils.showMessageShort(mContext, "修改失败");
            }
            if (mOperateResult != null)
                mOperateResult.result(result);
        }
    }

    public void update(String content, String childId) {
        if (parameters == null) {
            parameters = new RequestParameters();
        }
        parameters.add("userId", TextUtils.isEmpty(UserInfo.UserId) ? "0" : UserInfo.UserId);
        parameters.add("childId", TextUtils.isEmpty(childId) ? "0" : childId);
        parameters.add("type", mType);
        parameters.add("paraString", content);

        parameters.printf();

    }

    /**
     * 操作结果
     */
    public interface operateResult {
        void result(Struct result);
    }
}
