package cn.yiban;

import java.net.URLEncoder;
import java.util.List;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


/**
 * �װ���Ȩ��֤�ӿ�
 *
 * ͨ���װ࿪��ƽ̨����õ���AppID��AppSecret�����صĻص�URL������Ȩ
 * ֻ����Ȩ�ɹ���ȡ���������ƺ���ܶ���Ȩ���й����������ȡ����Ȩ��
 * ��Ȩ��������Apache��HttpClient�������⡣
 */
public class Authorize
{

    /**
     * �������ƹ�����
     *
     * ͨ������ά�����ѯ��ط������Ƶ���Ϣ
     * ʹ�� Authorize::getManInstance()����ȡ��ʵ��
     */
    public class Man
    {
        private String token;

        public Man(String token)
        {
            this.token = token;
        }

        /**
         * ��ȡ����Ӧ��ָ��access_token����Ϣ��expire_inֵΪ0�������Ȩƾ֤�ѹ��ڣ���Ҫ������Ȩ
         *@return
         *  {
         *  "status":"����״̬",
         *  "userid":"��Ȩ�û�id",
         *  "access_token":"�û���Ȩƾ֤",
         *  "create_at":"��ֹ��Ч��",
         *  "expire_in":"ʣ������"
         *  }
         *����״̬��200-��Ȩ��Ч��404-��Ȩ�ѹ��ڻ򲻴���
         *
         */
        public String query()
        {
            List <NameValuePair> param = new ArrayList <NameValuePair>();
            param.add(new BasicNameValuePair("client_id", appKey));
            param.add(new BasicNameValuePair("access_token", token));
            System.out.println(YIBAN_OPEN_URL + API_TOKEN_QUERY);
            System.out.println(appKey);
            System.out.println(token);
            return HTTPSimple.POST(YIBAN_OPEN_URL + API_TOKEN_QUERY, param);
        }

        /**
         * ����������ȡ��ָ���û�����Ȩ
             {
                "status":"����״̬"
             }
             //����״̬˵����200-��ע����500-ע��ʧ��
         */
        public String revoke()
        {
            List <NameValuePair> param = new ArrayList <NameValuePair>();
            param.add(new BasicNameValuePair("client_id", appKey));
            param.add(new BasicNameValuePair("access_token", token));
            return HTTPSimple.POST(YIBAN_OPEN_URL + API_TOKEN_REVOKE, param);
        }
    }

    /**
     * ������ն����ͣ���վ���ֻ����ͻ��ˣ�
     */
    public enum DISPLAY_TAG_T {WEB, MOBILE, CLIENT};

    /**
     * ��Ȩ�������ƹ���ʵ��
     */
    private Man manInstance;

    /**
     * ����Ӧ�� APPID
     */
    private String appKey;

    /**
     * ����Ӧ�õ� AppSecret
     */
    private String appSecret;

    private final String TEXT_ENCODING		= "UTF-8";

    private final String YIBAN_OPEN_URL		= "https://openapi.yiban.cn/";

    private final String API_OAUTH_CODE		= "oauth/authorize";
    private final String API_OAUTH_TOKEN	= "oauth/access_token";
    private final String API_TOKEN_QUERY	= "oauth/token_info";
    private final String API_TOKEN_REVOKE	= "oauth/revoke_token";

    /**
     * ���캯��
     *
     * ����Ӧ�ý���ʱ��AppKey��AppSecretֵ
     *
     */
    public Authorize(String appkey, String appsecret)
    {
        this.appKey		= appkey;
        this.appSecret	= appsecret;
    }

    /**
     * �������ƹ���ʵ������
     *
     * �Թ��������Ƴ�ʼ��������ʵ��
     *
     * @return	Authorize.Man
     */
    public Man getManInstance(String token)
    {
        if (manInstance == null)
        {
            manInstance = new Man(token);
        }
        return manInstance;
    }

    /**
     * �������ƹ���ʵ������
     *
     * ���Ѿ�ʹ��token��Man�����ʼ��
     * ���Բ�������ֱ��ȡ���ö���
     *
     * @return	Authorize.Man
     */
    public Man getManInstance()
    {
        return manInstance;
    }

    /**
     * ������Ȩ��֤��ַ
     *
     * �ͻ����ض�����Ȩ��ַ
     * ��ȡ��Ȩ��֤��CODE����ȡ�÷�������
     *
     * @return	String ��Ȩ��֤ҳ���ַ
     */
    public String forwardurl(String redirect_uri, String state, DISPLAY_TAG_T display)
    {
        String query = "?client_id=";
        try
        {
            query += URLEncoder.encode(appKey, TEXT_ENCODING);
            query += "&redirect_uri=";
            query += URLEncoder.encode(redirect_uri, TEXT_ENCODING);
            query += "&state=";
            query += URLEncoder.encode(state, TEXT_ENCODING);
            query += "&display=";
            switch (display)
            {
                case CLIENT:
                {
                    URLEncoder.encode("client", TEXT_ENCODING);
                    break;
                }
                case MOBILE:
                {
                    URLEncoder.encode("mobile", TEXT_ENCODING);
                    break;
                }
                case WEB:
                {
                    URLEncoder.encode("web", TEXT_ENCODING);
                    break;
                }
                default:
                {
                    throw new IllegalArgumentException();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return YIBAN_OPEN_URL + API_OAUTH_CODE + query;
    }

    /**
     * ͨ����Ȩ��CODE��ȡ��������
     *
     * Ӧ�÷�����ֻ��Ҫ���ô˽ӿ�
     * �Զ������ض���
     *
         {
             "access_token":"��Ȩƾ֤",
             "userid":"��Ȩ�û�id",
             "expires":"��ֹ��Ч��"
         }
     */
    public String querytoken(String code, String redirect_uri)
    {
        List <NameValuePair> param = new ArrayList <NameValuePair>();
        param.add(new BasicNameValuePair("client_id", appKey));
        param.add(new BasicNameValuePair("client_secret", appSecret));
        param.add(new BasicNameValuePair("code", code));
        param.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_OAUTH_TOKEN, param);
    }
}
