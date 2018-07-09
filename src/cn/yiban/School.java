package cn.yiban;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 校级接口
 */
public class School {
    private static final String YIBAN_OPEN_URL = "https://openapi.yiban.cn/";

    private static final String API_SCHOOL_USERACTIVE = "school/user_active";
    private static final String API_SCHOOL_EDGA = "school/egpa";
    private static final String API_SCHOOL_RELATEAPP = "school/relate_app";
    private static final String API_SCHOOL_USERCHANGE = "school/user_change";
    private static final String API_SCHOOL_AWARDWX = "school/award_wx";
    private static final String API_SCHOOL_NOTICE = "school/notice";
    //获取当日用户活跃统计
    //校级权限
    public String user_active(String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += API_SCHOOL_USERACTIVE;
        query += "?access_token=";
        query += access_token;
        return HTTPSimple.GET(query);
    }
    //获取行政公共群EGPA统计
    //校级权限
    public String egpa(String access_token,String group_id)
    {
        String query = YIBAN_OPEN_URL;
        query += API_SCHOOL_EDGA;
        query += "?access_token=";
        query += access_token;
        query += "?group_id=";
        query += group_id;
        return HTTPSimple.GET(query);
    }
    //获取当前应用所属开发者/可见学校其它的关联应用。
    public static String relateApp(String access_token,String sc_name,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        if(!Objects.equals(sc_name, ""))
        {
            url += "&sc_name=";
            url += sc_name;
        }
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_SCHOOL_RELATEAPP+url);
    }
    //获取指定学校批量用户的昨日网薪/经验变化量
    //校级权限
    public static String user_change(String access_token,String sc_name,String yb_userid,String date_time)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("sc_name", sc_name));
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("yb_userid", yb_userid));
        param.add(new BasicNameValuePair("date_time", date_time));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_SCHOOL_USERCHANGE, param);
    }
    //学校活动账户向指定用户发放活动网薪
    //校级权限 应用权限申请备案文档中注明需使用该接口，并提供预设学校名称、作为学校活动账户的易班账号
    public static String award_wx(String access_token,String yb_userid,String award)
    {
        String url = "?access_token=";
        url += access_token;
        url += "?yb_userid=";
        url += yb_userid;
        url += "?award=";
        url += award;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_SCHOOL_AWARDWX+url);
    }
    //向多个用户发送学校通知
    //校级权限
    public static String notice(String access_token,String to_uid,String content,String type)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("to_yb_uid", to_uid));
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("content", content));
        param.add(new BasicNameValuePair("content", type));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_SCHOOL_NOTICE, param);
    }
    //发表所属学校机构号首页通知 不会
    //校级权限

}
