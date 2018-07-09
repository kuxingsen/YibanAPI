package cn.yiban;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * ���Ͽ�ӿ�
 */
public class Data {
    private static final String YIBAN_OPEN_URL = "https://openapi.yiban.cn/";
    /**
     *
     */
    private static final String API_DATA_DOWNLOAD = "data/download";
    private static final String API_DATA_UPLOAD = "data/upload";

    // ��ȡ��ǰ�û����Ͽ��ļ������б�
    //�߼�Ȩ�ޡ�У��Ȩ�ޡ�����Ȩ��
    public static String download(String access_token,String dir_path,String file_type,String file_order,String page,String count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&dir_path=";
        url += dir_path;
        url += "&file_type=";
        url += file_type;
        url += "&file_order=";
        url += file_order;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_DATA_DOWNLOAD+url);
    }

    //�ϴ��ļ������Ͽ�
    //�߼�Ȩ�ޡ�У��Ȩ�ޡ�����Ȩ��
    public static String upload(String access_token,String file_name,String file_tmp,String share_type,String share_content)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("file_name", file_name));
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("file_tmp", file_tmp));
        param.add(new BasicNameValuePair("share_type", share_type));
        param.add(new BasicNameValuePair("share_content", share_content));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_DATA_UPLOAD, param);
    }
}
