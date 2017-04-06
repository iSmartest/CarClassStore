package com.lixin.carclassstore.http;
import android.content.Context;
import android.text.TextUtils;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.utils.CommonLog;
import com.lixin.carclassstore.utils.IOUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.tools.HttpException;
import java.io.InputStream;


public abstract class SimpleHttp implements HttpListener
{
    protected Context mContext;
    protected boolean mShowMsg;
    private String mExceptionInfo;

    private static boolean showSimpleMsg;

    /** 默认的构造方法。本构造方法默认显示异常信息 */
    public SimpleHttp(Context context)
    {
        this(context, true);
    }

    /**
     * @param context
     *         context，用于显示异常信息
     * @param showMsg
     *         是否显示异常信息
     */
    public SimpleHttp(Context context, boolean showMsg)
    {
        mContext = context;
        mShowMsg = showMsg;
    }

    /** 设置显示简略的异常信息 */
    public static void setShowSimpleMsg(boolean flag)
    {
        showSimpleMsg = flag;
    }


    @Override
    public Object onRequestFail(InputStream in, int statusCode, RequestParameters parameters)
    {
        CommonLog.i("与服务器交互失败，错误代码:" + statusCode);
        IOUtils.printf(in);
        return null;
    }

    @Override
    public Object onException(HttpException e, RequestParameters parameters)
    {
        mExceptionInfo = e.getExceptionInfo(mContext);
        CommonLog.e(mExceptionInfo);
        CommonLog.e(e);
        return null;
    }

    @Override
    public void onRequestFailInUI(Object obj)
    {
        if (mShowMsg)
        {
            ToastUtils.showMessageShort(mContext, R.string.err_service_interactive_fail);
        }
    }

    @Override
    public void onRequestExceptionInUI(Object obj)
    {
        if (mShowMsg)
        {
            if (showSimpleMsg)
            {
                ToastUtils.showMessageShort(mContext, R.string.err_connect_fail);
            }
            else
            {
                if (TextUtils.isEmpty(mExceptionInfo))
                {
                    ToastUtils.showMessageShort(mContext, R.string.err_connect_fail);
                }
                else
                {
                    ToastUtils.showMessageShort(mContext, mExceptionInfo);
                }
            }
        }
    }

    /** 在本类中，此方法用以显示没有数据的Toast提醒 */
    @Override
    public void onRequestSuccessInUI(Object obj)
    {
        if (mShowMsg)
        {
            ToastUtils.showMessageShort(mContext, R.string.no_data);
        }
    }

    @Override
    public void onComplete()
    {
    }
}
