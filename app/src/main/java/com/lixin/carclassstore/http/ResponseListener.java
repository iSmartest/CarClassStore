package com.lixin.carclassstore.http;

import java.io.InputStream;

public interface ResponseListener
{
    /**
     * 当访问服务器成功时调用
     *
     * @param in
     *         输入流
     * @param parameters
     *         请求参数
     */
    public void onRequestSuccess(InputStream in, RequestParameters parameters);

    /**
     * 当访问服务器发生错误时调用，即连接服务器成功后，服务器返回代码非200
     *
     * @param in
     *         输入流
     * @param statusCode
     *         服务器返回代码
     * @param parameters
     *         请求参数
     */
    public void onRequestFail(InputStream in, int statusCode, RequestParameters parameters);

    /**
     * 当访问服务器发生异常时调用
     *
     * @param e
     *         异常信息
     * @param parameters
     *         请求参数
     */
    public void onException(HttpException e, RequestParameters parameters);
}
