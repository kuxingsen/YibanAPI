package cn.yiban;

/**
 * Created by 傻逼 on 2018/1/30.
 */
public class Pay {

    private static final String YIBAN_OPEN_URL = "https://openapi.yiban.cn/";
    /**
     * 以下为网薪接口
     */

    private static final String API_PAY_YBWX = "pay/yb_wx";
    private static final String API_PAY_TRADEWX= "pay/trade_wx";
    //当前用户网薪支付。
    //校级权限 应用权限申请备案文档中注明需使用该接口，并提供预设学校名称
    public String yb_wx(String access_token,String pay)
    {
        String query = YIBAN_OPEN_URL;
        query += API_PAY_YBWX;
        query += "?access_token=";
        query += access_token;
        query += "&pay=";
        query += pay;
        return HTTPSimple.GET(query);
    }
    //当前用户网薪交易。
    //高级权限、校级权限、合作权限
    public String trade_wx(String access_token,String pay,String sign_back,String yb_userid)
    {
        String query = YIBAN_OPEN_URL;
        query += API_PAY_TRADEWX;
        query += "?access_token=";
        query += access_token;
        query += "&pay=";
        query += pay;
        query += "&sign_back=";
        query += sign_back;
        if(!yb_userid.equals(""))
        {
            query += "&yb_userid=";
            query += yb_userid;
        }
        return HTTPSimple.GET(query);
    }
}
