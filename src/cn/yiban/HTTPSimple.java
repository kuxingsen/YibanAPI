package cn.yiban;

import java.io.File;

import java.util.List;
import java.security.KeyStore;

import java.net.URL;
import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.Header;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.apache.http.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

/**
 * Created by ɵ�� on 2018/1/29.
 */
public class HTTPSimple {


    /**
     * HTTP��GET����
     *
     * @return	String ��������
     */
    public static String GET(String url)
    {
        String responseContext = "";
        try
        {
            CloseableHttpClient httpclient = getClientInstance(url);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if( status > 300 && status < 310)
            {
                Header[] h = response.getHeaders("Location");
                if(h.length > 0)
                {
                    httpclient.close();
                    return HTTPSimple.GET(h[0].toString().substring(10));
                }
            }
            HttpEntity entity = response.getEntity();
            responseContext = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            httpclient.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return responseContext;
    }


    /**
     * HTTP��POST����
     *
     * @return	String ��������
     */
    public static String POST(String url, List<NameValuePair> param)
    {
        String responseContext = "";
        int found = url.indexOf('?');
        if (found > 0)
        {
            url = url.substring(0, found);
        }
        try
        {
            CloseableHttpClient httpclient = getClientInstance(url);
            HttpPost httpPost = new HttpPost(url);
            //httpPost.setEntity(new UrlEncodedFormEntity(param));//ԭ����Ĭ��ֻ�ܷ���ISO_8859_1���룬��ָ��utf-8���ܷ�������
            httpPost.setEntity(new UrlEncodedFormEntity(param,"utf-8"));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if( status > 300 && status < 310)
            {
                Header[] h = response.getHeaders("Location");
                if(h.length > 0)
                {
                    httpclient.close();
                    return HTTPSimple.POST(h[0].toString().substring(10), param);
                }
            }
            HttpEntity entity = response.getEntity();
            responseContext = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            httpclient.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return responseContext;
    }

    /**
     * ͨ��URL��ַ��֤�Ƿ�HTTPS��ȫ����
     *
     * @return	boolean
     */
    private static boolean isSecurity(String url) throws Exception
    {
        URL u = new URL(url);
        return u.getProtocol().contentEquals("https");
    }

    /**
     * ��ʼ��HttpClient����
     *
     * ��urlΪhttps���ӣ���ʹ��SSLConnection����ʼ��
     * ��Ϊ��ͨ��HTTP���ӣ�ֻʹ��Ĭ�ϵ�HttpClient������ʼ��
     *
     * @return	CloseableHttpClient
     */
    private static CloseableHttpClient getClientInstance(String url) throws Exception
    {
        CloseableHttpClient httpclient = null;
        if (isSecurity(url))
        {
            KeyStore myTrustKeyStore = KeyStore.getInstance(
                    KeyStore.getDefaultType()
            );
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(myTrustKeyStore, new TrustStrategy() {
                @Override
                public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier()
            );
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
        else
        {
            httpclient = HttpClients.createDefault();
        }
        return httpclient;
    }
}
