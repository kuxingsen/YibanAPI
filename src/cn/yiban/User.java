package cn.yiban;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 傻逼 on 2018/1/29.
 */
public class User {

    private final String YIBAN_OPEN_URL		= "https://openapi.yiban.cn/";

    /**
     * 以下为 用户接口
     */
    private static final String YIBAN_USER_ME_INFO	= "user/me";
    private static final String YIBAN_USER_OTHER	= "user/other";
    private static final String YIBAN_USER_ISREAL	= "user/is_real";
    private static final String YIBAN_USER_REALME	= "user/real_me";
    private static final String YIBAN_USER_VERIFYME	= "user/verify_me";
    private static final String YIBAN_USER_ISVERIFY	= "user/is_verify";
    private static final String YIBAN_USER_CHECKVERIFY	= "user/check_verify";
    //查看本人基本消息，不包含实名信息
    public String me(String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_ME_INFO;
        query += "?access_token=";
        query += access_token;
        return HTTPSimple.GET(query);
    }
    //查看指定用户基本信息，不包含实名信息
    public String other(int userid , String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_OTHER;
        query += "?access_token=";
        query += access_token;
        query += "&yb_userid=";
        query += String.valueOf(userid);
        return HTTPSimple.GET(query);
    }
    //查看指定用户是否实名认证
    public String is_real(int yb_userid , String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_ISREAL;
        query += "?access_token=";
        query += access_token;
        query += "&yb_userid=";
        query += String.valueOf(yb_userid);
        return HTTPSimple.GET(query);
    }
    //查看自己的真实信息
    //校级权限、合作权限
    public String realme(String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_REALME;
        query += "?access_token=";
        query += access_token;
        return HTTPSimple.GET(query);
    }
    //当前用户是否校方认证
    //高级权限、校级权限、合作权限
    public String is_verify(String access_token,String School_name,String verify_key,String verify_value)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_ISVERIFY;
        query += "?access_token=";
        query += access_token;
        query += "&School_name=";
        query += School_name;
        query += "&verify_key=";
        query += verify_key;
        query += "&verify_value=";
        query += verify_value;
        return HTTPSimple.GET(query);
    }
    //获取当前用户校方认证信息
    //校级权限
    public String verify_me(String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_VERIFYME;
        query += "?access_token=";
        query += access_token;
        return HTTPSimple.GET(query);
    }
    //当前用户完成校方认证
    //校级权限
    public String checkVerify(String access_token,String School_name,String real_name,String verify_key,String verify_value)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("to_yb_uid", School_name));
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("content", real_name));
        param.add(new BasicNameValuePair("access_token", verify_key));
        param.add(new BasicNameValuePair("content", verify_value));

        return HTTPSimple.POST(YIBAN_OPEN_URL + YIBAN_USER_CHECKVERIFY, param);
    }

}
