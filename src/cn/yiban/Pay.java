package cn.yiban;

/**
 * Created by ɵ�� on 2018/1/30.
 */
public class Pay {

    private static final String YIBAN_OPEN_URL = "https://openapi.yiban.cn/";
    /**
     * ����Ϊ��н�ӿ�
     */

    private static final String API_PAY_YBWX = "pay/yb_wx";
    private static final String API_PAY_TRADEWX= "pay/trade_wx";
    //��ǰ�û���н֧����
    //У��Ȩ�� Ӧ��Ȩ�����뱸���ĵ���ע����ʹ�øýӿڣ����ṩԤ��ѧУ����
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
    //��ǰ�û���н���ס�
    //�߼�Ȩ�ޡ�У��Ȩ�ޡ�����Ȩ��
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
