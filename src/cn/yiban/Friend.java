package cn.yiban;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 好友分组接口
 */
public class Friend {

    private static final String YIBAN_OPEN_URL = "https://openapi.yiban.cn/";
    /**
     * 以下为 好友分组接口
     */
    private static final String API_FRIEND_APPLY = "friend/apply";
    private static final String API_FRIEND_LIST	= "friend/me_list";
    private static final String API_FRIEND_RECOMMEND = "friend/recommend";
    private static final String API_FRIEND_CHECK= "friend/check";
    private static final String API_FRIEND_REMOVE= "friend/remove";
    //查看好友列表
    public String list(int page, int count, String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += API_FRIEND_LIST;
        query += "?access_token=";
        query += access_token;
        query += "&page=";
        query += String.valueOf(page);
        query += "&count=";
        query += String.valueOf(count);
        return HTTPSimple.GET(query);
    }
    //查询与指定用户的好友关系
    public String check(int yb_friend_uid , String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += API_FRIEND_CHECK;
        query += "?access_token=";
        query += access_token;
        query += "&yb_friend_uid=";
        query += String.valueOf(yb_friend_uid);
        return HTTPSimple.GET(query);
    }
    //推荐好友接口
    public static String recommend(String access_token,int count)
    {
        String query = YIBAN_OPEN_URL+API_FRIEND_RECOMMEND;
        query += "?access_token=";
        query += access_token;
        query += "&count=";
        query += count;
        //System.out.println(query);
        return HTTPSimple.GET(query);
    }
    //发送好友申请
    public static String apply(String access_token,String to_yb_uid,String content)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("to_yb_uid", to_yb_uid));
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("content", content));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_FRIEND_APPLY, param);
    }
    //删除指定好友
    public static String remove(String access_token,String yb_friend_uid)
    {
        String query = YIBAN_OPEN_URL+API_FRIEND_REMOVE;
        query += "?access_token=";
        query += access_token;
        query += "&yb_friend_uid=";
        query += yb_friend_uid;
        return HTTPSimple.GET(query);
    }

}
