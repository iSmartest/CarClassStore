package com.lixin.carclassstore.http;



import com.lixin.carclassstore.tools.HttpException;

import java.io.InputStream;

public interface HttpListener
{
    /**
     * 当访问服务器成功时调用，在方法在非UI线程中调用。
     *
     * @param in
     *         输入流
     * @param parameters
     *         请求参数
     *
     * @return 返回的对象将会在onRequestSuccessInUI方法中当做参数
     */
    public Object onRequestSuccess(InputStream in, RequestParameters parameters);

    /**
     * 当访问服务器发生错误时调用，即连接服务器成功后，服务器返回代码非200，在方法在非UI线程中调用。
     *
     * @param in
     *         输入流
     * @param statusCode
     *         服务器返回代码
     * @param parameters
     *         请求参数
     *
     * @return 返回的对象将会在onRequestFailInUI方法中当做参数
     */
    public Object onRequestFail(InputStream in, int statusCode, RequestParameters parameters);

    /**
     * 当访问服务器发生异常时调用，在方法在非UI线程中调用。
     *
     * @param e
     *         异常信息
     * @param parameters
     *         请求参数
     *
     * @return 返回的对象将会在onRequestExceptionInUI方法中当做参数
     */
    public Object onException(HttpException e, RequestParameters parameters);

    /**
     * 当访问服务器成功时调用，在方法在UI线程中调用。
     *
     * @param obj
     *         onRequestSuccess方法的返回对象引用
     */
    public void onRequestSuccessInUI(Object obj);

    /**
     * 当访问服务器发生错误时调用，即连接服务器成功后，服务器返回代码非200，在方法在UI线程中调用。
     *
     * @param obj
     *         onRequestFail方法的返回对象引用
     */
    public void onRequestFailInUI(Object obj);

    /**
     * 当访问服务器发生异常时调用，在方法在UI线程中调用。
     *
     * @param obj
     *         onException方法的返回对象引用
     */
    public void onRequestExceptionInUI(Object obj);

    /**
     * 当访问服务器结束后调用，在方法在UI线程中调用且最后调用。
     */
    public void onComplete();
}
