package com.lixin.carclassstore.http;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;


import com.lixin.carclassstore.utils.CommonLog;
import com.lixin.carclassstore.utils.ConstantUtil;
import com.lixin.carclassstore.utils.IOUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HttpManager
{
    /** 最大重连次数 */
    private static final int MAX_RETRY_NUM = 1;
    /** 连接最大等待时间 */
    private static final int SET_CONNECTION_TIMEOUT = 20 * 1000;
    /** Socket最大等待时间 */
    private static final int SET_SOCKET_TIMEOUT = 20 * 1000;

    public enum HttpMethod
    {
        POST, GET, DELETE
    }

    /**
     * 进行HTTP或者HTTPS访问操作
     *
     * @param context
     *         用以获取APN，可以传null
     * @param url
     *         服务器地址
     * @param method
     *         "GET" 、"POST"、"DELETE"
     * @param params
     *         存放请求参数的容器
     * @param listener
     *         请求服务器的事件监听器
     */
    public static void openUrl(Context context, String url, HttpMethod method, RequestParameters params,
                               ResponseListener listener)
    {
        InputStream inputStream = null;
        HttpUriRequest request = null;
        DefaultHttpClient client = null;

        try
        {
            client = getNewHttpClient(context);
            // 构造请求体
            try
            {
                switch (method)
                {
                    case GET:
                        if (params != null)
                        {
                            url = url + encodeUrl(params);
                        }
                        request = new HttpGet(url);
                        break;
                    case POST:
                        HttpPost post = new HttpPost(url);
                        request = post;
                        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
                        String postParam = encodeParameters(params);
                        byte[] data = postParam.getBytes(ConstantUtil.ENCODE);
                        ByteArrayEntity formEntity = new ByteArrayEntity(data);
                        post.setEntity(formEntity);
                        break;
                    case DELETE:
                        request = new HttpDelete(url);
                        break;
                }
            }
            catch (Exception e)
            {
                if (listener != null)
                {
                    listener.onException(new HttpException(HttpException.ON_INIT_EXCEPTION, e), params);
                }
                return;
            }

            HttpResponse response = null;

            HttpRequestRetryHandler retryHandler = client.getHttpRequestRetryHandler();
            // 请求服务器，根据retry判断是否进行多次尝试连接
            boolean retry = true;
            HttpContext httpContext = new BasicHttpContext();
            for (int num = 1; retry; num++)
            {
                try
                {
                    response = client.execute(request, httpContext);
                    break;
                }
                catch (IOException e)
                {
                    retry = retryHandler.retryRequest(e, num, httpContext);
                    if (!retry)
                    {
                        if (listener != null)
                        {
                            listener.onException(new HttpException(HttpException.ON_REQUEST_EXCEPTION, e), params);
                        }
                        return;
                    }
                }
                catch (Exception e)
                {
                    if (listener != null)
                    {
                        listener.onException(new HttpException(HttpException.ON_REQUEST_EXCEPTION, e), params);
                    }
                    return;
                }
            }

            int statusCode = response.getStatusLine().getStatusCode();

            // 获取服务器返回数据
            try
            {
                inputStream = response.getEntity().getContent();

                Header header = response.getFirstHeader("Content-Encoding");
                if (header != null && header.getValue()
                                            .toLowerCase(Locale.getDefault())
                                            .contains("gzip"))
                {
                    inputStream = new GZIPInputStream(inputStream);
                }
            }
            catch (Exception e)
            {
                if (listener != null)
                {
                    listener.onException(new HttpException(HttpException.ON_RESPONSE_EXCEPTION, e), params);
                }
                return;
            }

            // 处理返回数据
            try
            {
                if (listener != null)
                {
                    if (statusCode == HttpStatus.SC_OK)
                    {
                        listener.onRequestSuccess(inputStream, params);
                    }
                    else
                    {
                        listener.onRequestFail(inputStream, statusCode, params);
                    }
                }
            }
            catch (Exception e)
            {
                listener.onException(new HttpException(HttpException.ON_RESPONSE_EXCEPTION, e), params);
            }
        }
        finally
        {
            if (client != null)
            {
                client.getConnectionManager().shutdown();
            }
            IOUtils.close(inputStream);
            abort(request);
        }
    }

    private static void abort(HttpUriRequest request)
    {
        if (request != null)
        {
            try
            {
                request.abort();
            }
            catch (Exception e)
            {
                CommonLog.e(e);
            }
        }
    }

    /**
     * 创建HttpClient，可以进行HTTP和HTTPS访问操作
     *
     * @return 如果创建过程中发生异常，则返回一个默认的HttpClient
     */
    private static DefaultHttpClient getNewHttpClient(Context context)
    {
        DefaultHttpClient client;
        try
        {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(params, SET_CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, SET_SOCKET_TIMEOUT);

            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            client = new DefaultHttpClient(ccm, params);
        }
        catch (Exception e)
        {
            client = new DefaultHttpClient();
        }

        // 尝试获取APN
        HttpHost apn = getAPN(context);
        if (apn != null)
        {
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, apn);
        }
        // 添加重连机制
        addRetry(client);

        return client;
    }

    /**
     * 根据表单数据载体构造HTTP GET访问时的URL后续内容
     *
     * @param parameters
     *         请求服务器的参数
     *
     * @return URL后续内容，包含"?"
     */
    public static String encodeUrl(RequestParameters parameters)
    {
        return "?" + encodeParameters(parameters);
    }

    /**
     * 根据表单数据载体构造HTTP/HTTPS POST访问时的FORM信息
     *
     * @param parameters
     *         请求服务器的参数
     *
     * @return FORM信息，不包含"?"
     */
    public static String encodeParameters(RequestParameters parameters)
    {
        if (parameters == null || parameters.size() == 0)
        {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parameters.size(); i++)
        {
            String _name = parameters.getKey(i);
            String _value = parameters.getValue(_name).toString();
            try
            {
                sb.append(URLEncoder.encode(_name, ConstantUtil.ENCODE)).append("=")
                  .append(URLEncoder.encode(_value, ConstantUtil.ENCODE)).append("&");
            }
            catch (Exception e)
            {
                CommonLog.e(e);
            }
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * 获取当前APN并返回HttpHost对象
     */
    private static HttpHost getAPN(Context context)
    {
        HttpHost proxy = null;
        Cursor mCursor = null;
        try
        {
            Uri uri = Uri.parse("content://telephony/carriers/preferapn");
            if (context == null)
            {
                return null;
            }
            ConnectivityManager connectMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                mCursor = context.getContentResolver().query(uri, null, null, null, null);
                if (mCursor != null && mCursor.moveToFirst())
                {
                    // 游标移至第一条记录，当然也只有一条
                    String proxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
                    if (proxyStr != null && proxyStr.trim().length() > 0)
                    {
                        proxy = new HttpHost(proxyStr, 80);
                    }
                }
            }
        }
        catch (Exception e)
        {
            CommonLog.e(e);
        }
        finally
        {
            if (mCursor != null)
            {
                mCursor.close();
            }
        }
        return proxy;
    }

    /**
     * 添加重连接机制
     */
    private static synchronized void addRetry(DefaultHttpClient httpClient)
    {
        HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), false);

        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler()
        {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context)
            {
                CommonLog.i("第" + executionCount + "次连接失败，失败原因:" + exception.getMessage());

                if (executionCount >= MAX_RETRY_NUM)
                {
                    return false;
                }
                if (exception instanceof ConnectTimeoutException)
                {
                    // 连接超时
                    return true;
                }
                if (exception instanceof SocketTimeoutException)
                {
                    return true;
                }

                if (exception instanceof InterruptedIOException)
                {
                    return false;
                }
                if (exception instanceof UnknownHostException)
                {
                    return false;
                }
                if (exception instanceof NoHttpResponseException)
                {
                    return true;
                }
                if (exception instanceof SSLHandshakeException)
                {
                    return false;
                }
                HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
                return !(request instanceof HttpEntityEnclosingRequest);
            }
        };
        httpClient.setHttpRequestRetryHandler(myRetryHandler);
    }

    /**
     * 用以支持HTTPS
     *
     * @author Administrator
     */
    private static class MySSLSocketFactory extends SSLSocketFactory
    {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException
        {
            super(truststore);

            TrustManager tm = new X509TrustManager()
            {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
                {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
                {
                }

                public X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[]{tm}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException
        {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException
        {
            return sslContext.getSocketFactory().createSocket();
        }
    }
}
