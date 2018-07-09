package cn.yiban;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 以下为群话题接口
 *
 */
public class Group {
    private static final String YIBAN_OPEN_URL = "https://openapi.yiban.cn/";

    private static final String API_GROUP_PUBLICGROUP = "group/public_group";
    private static final String API_GROUP_ORGANGROUP = "group/organ_group";
    private static final String API_GROUP_MYGROUP = "group/my_group";
    private static final String API_GROUP_GROUPINFO = "group/group_info";
    private static final String API_GROUP_GROUPMEMBER = "group/group_member";
    private static final String API_GROUP_GROUPTOPIC = "group/group_topic";
    private static final String API_GROUP_ORGANTOPIC = "group/organ_topic";
    private static final String API_GROUP_MYTOPIC = "group/my_topic";
    private static final String API_GROUP_HOTTOPIC = "group/hot_topic";
    private static final String API_GROUP_TOPICINFO = "group/topic_info";
    private static final String API_GROUP_TOPICCOMMENT = "group/topic_comment";
    private static final String API_GROUP_SENDTOPIC = "group/send_topic";
    private static final String API_GROUP_SENDCOMMENT = "group/send_comment";
    private static final String API_GROUP_DELETETOPIC = "group/delete_topic";
    private static final String API_GROUP_DELETECOMMENT = "group/delete_comment";
    //获取当前用户已加入的公共群。
    public static String publicGroup(String access_token,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_PUBLICGROUP+url);
    }
    //获取当前用户已加入的机构群
    public static String organGroup(String access_token,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_ORGANGROUP+url);
    }
    //获取当前用户创建的机构群/公共群
    public static String myGroup(String access_token,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_MYGROUP+url);
    }
    //获取指定机构群/公共群信息
    public static String groupInfo(String access_token,String group_id)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_GROUPINFO+url);
    }
    //获取指定机构群/公共群成员列表
    public static String groupMember(String access_token,String group_id,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_GROUPMEMBER+url);
    }
    //获取指定机构群/公共群话题列表
    public static String groupTopic(String access_token,String group_id,int page,int count,int order)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        url += "&order=";
        url += order;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_GROUPTOPIC+url);
    }
    //获取指定机构号板块话题列表
    public static String organTopic(String access_token,String organ_uid,int page,int count,String item,int order)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&organ_uid=";
        url += organ_uid;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        if(!item.equals(""))
        {
            url += "&item=";
            url += item;
        }
        url += "&order=";
        url += order;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_ORGANTOPIC+url);
    }
    //获取当前用户发表的话题列表
    public static String myTopic(String access_token,String group_id,int page,int count,int order)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        url += "&order=";
        url += order;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_MYTOPIC+url);
    }
    //获取全站/机构号热门话题列表
    public static String hotTopic(String access_token,int page,int count,int order)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        url += "&order=";
        url += order;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_HOTTOPIC+url);
    }
    //获取指定话题内容
    public static String topicInfo(String access_token,String group_id,String organ_id,String topic_id)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&organ_id=";
        url += organ_id;
        url += "&topic_id=";
        url += topic_id;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_TOPICINFO+url);
    }
    //获取指定话题的评论列表
    public static String topicComment(String access_token,String group_id,String organ_id,String topic_id,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&organ_id=";
        url += organ_id;
        url += "&topic_id=";
        url += topic_id;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_TOPICCOMMENT+url);
    }
    //在指定机构群/公共群范围发表话题
    public static String SendTopic(String access_token,String group_id,String topic_title,String topic_content)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("group_id", group_id));
        param.add(new BasicNameValuePair("topic_title", topic_title));
        param.add(new BasicNameValuePair("topic_content", topic_content));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_GROUP_SENDTOPIC, param);
    }
    //对指定话题发表/回复评论
    public static String sendComment(String access_token,String group_id,String organ_id,String topic_id,String comment_content,String comment_id)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("topic_id", topic_id));
        param.add(new BasicNameValuePair("comment_content", comment_content));
        if(group_id.equals(""))
            param.add(new BasicNameValuePair("group_id", group_id));
        if(organ_id.equals(""))
            param.add(new BasicNameValuePair("organ_id", organ_id));
        if(comment_id.equals(""))
            param.add(new BasicNameValuePair("comment_id", comment_id));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_GROUP_SENDCOMMENT, param);
    }
    //删除当前用户指定机构群/公共群范围发表的话题
    public static String deleteTopic(String access_token,String group_id,String topic_id)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&topic_id=";
        url += topic_id;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_DELETETOPIC+url);
    }
    //删除当前用户发表的话题评论。
    public static String deleteComment(String access_token,String group_id,String organ_id,String topic_id,String comment_id)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&organ_id=";
        url += organ_id;
        url += "&topic_id=";
        url += topic_id;
        url += "&comment_id=";
        url += comment_id;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_DELETECOMMENT+url);
    }
}
