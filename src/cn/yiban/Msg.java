package cn.yiban;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息接口
 */
public class Msg {

    private static final String YIBAN_OPEN_URL = "https://openapi.yiban.cn/";

    /**
     * 以下为 消息接口
     */
    private static final String API_MSG_LETTER = "msg/letter";
    //发送消息接口
    public static String letter(String access_token,String to_yb_uid,String content,String template)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("to_yb_uid", to_yb_uid));
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("content", content));
        param.add(new BasicNameValuePair("template", template));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_MSG_LETTER, param);
    }
}
