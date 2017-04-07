package com.lixin.carclassstore.http;

import android.content.Context;

import com.lixin.carclassstore.R;


public class HttpException extends Exception
{
    private static final long serialVersionUID = 0L;

    /** 构造HTTP请求体时发生异常，包括创建HttpUriRequest，以及请求数据参数设置过程中发生的字符码转换异常 */
    public static final int ON_INIT_EXCEPTION = 90000;
    /** 连接服务器时发生异常 */
    public static final int ON_REQUEST_EXCEPTION = 90001;
    /** 获取服务器返回体时发生异常 */
    public static final int ON_RESPONSE_EXCEPTION = 90002;
    /** 处理服务器返回数据时发生异常 */
    public static final int ON_HANDLE_EXCEPTION = 90003;

    private int mExceptionCode;
    private String mDetailMessage;

    public HttpException()
    {
        super();
    }

    public HttpException(Throwable throwable)
    {
        super(throwable);
    }

    public HttpException(String detailMessage, Throwable throwable)
    {
        super(detailMessage, throwable);
        this.mDetailMessage = detailMessage;
    }

    public HttpException(String detailMessage)
    {
        super(detailMessage);
        this.mDetailMessage = detailMessage;
    }

    public HttpException(int exceptionCode)
    {
        super();
        this.mExceptionCode = exceptionCode;
    }

    public HttpException(int exceptionCode, Throwable throwable)
    {
        super(throwable);
        this.mExceptionCode = exceptionCode;
    }

    public String getExceptionInfo(Context context)
    {
        if (context == null)
        {
            return mDetailMessage;
        }

        switch (mExceptionCode)
        {
            case ON_INIT_EXCEPTION:
                return context.getString(R.string.err_init_fail);
            case ON_REQUEST_EXCEPTION:
                return context.getString(R.string.err_connect_fail);
            case ON_RESPONSE_EXCEPTION:
                return context.getString(R.string.err_response_fail);
            case ON_HANDLE_EXCEPTION:
                return context.getString(R.string.err_handle_fail);
            default:
                return mDetailMessage;
        }
    }

}
