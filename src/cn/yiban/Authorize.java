package cn.yiban;

import java.net.URLEncoder;
import java.util.List;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


/**
 * 易班授权验证接口
 *
 * 通过易班开放平台申请得到的AppID与AppSecret及本地的回调URL申请授权
 * 只有授权成功获取到访问令牌后才能对授权进行管理操作（如取消授权）
 * 授权类依赖于Apache的HttpClient第三方库。
 */
public class Authorize
{

    /**
     * 访问令牌管理类
     *
     * 通过本类维护或查询相关访问令牌的信息
     * 使用 Authorize::getManInstance()方法取得实例
     */
    public class Man
    {
        private String token;

        public Man(String token)
        {
            this.token = token;
        }

        /**
         * 获取所属应用指定access_token的信息，expire_in值为0，则该授权凭证已过期，需要重新授权
         *@return
         *  {
         *  "status":"返回状态",
         *  "userid":"授权用户id",
         *  "access_token":"用户授权凭证",
         *  "create_at":"截止有效期",
         *  "expire_in":"剩余秒数"
         *  }
         *返回状态：200-授权有效，404-授权已过期或不存在
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
         * 开发者主动取消指定用户的授权
             {
                "status":"返回状态"
             }
             //返回状态说明：200-已注销、500-注销失败
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
     * 请求的终端类型（网站，手机，客户端）
     */
    public enum DISPLAY_TAG_T {WEB, MOBILE, CLIENT};

    /**
     * 授权访问令牌管理实例
     */
    private Man manInstance;

    /**
     * 接入应用 APPID
     */
    private String appKey;

    /**
     * 接入应用的 AppSecret
     */
    private String appSecret;

    private final String TEXT_ENCODING		= "UTF-8";

    private final String YIBAN_OPEN_URL		= "https://openapi.yiban.cn/";

    private final String API_OAUTH_CODE		= "oauth/authorize";
    private final String API_OAUTH_TOKEN	= "oauth/access_token";
    private final String API_TOKEN_QUERY	= "oauth/token_info";
    private final String API_TOKEN_REVOKE	= "oauth/revoke_token";

    /**
     * 构造函数
     *
     * 设置应用接入时的AppKey与AppSecret值
     *
     */
    public Authorize(String appkey, String appsecret)
    {
        this.appKey		= appkey;
        this.appSecret	= appsecret;
    }

    /**
     * 访问令牌管理实例对象
     *
     * 对过访问令牌初始化并返回实例
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
     * 访问令牌管理实例对象
     *
     * 若已经使用token对Man对象初始化
     * 可以不带参数直接取到该对象
     *
     * @return	Authorize.Man
     */
    public Man getManInstance()
    {
        return manInstance;
    }

    /**
     * 生成授权认证地址
     *
     * 客户端重定向到授权地址
     * 获取授权认证的CODE用于取得访问令牌
     *
     * @return	String 授权认证页面地址
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
     * 通过授权的CODE获取访问令牌
     *
     * 应用服务器只需要请用此接口
     * 自动处理重定向
     *
         {
             "access_token":"授权凭证",
             "userid":"授权用户id",
             "expires":"截止有效期"
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
