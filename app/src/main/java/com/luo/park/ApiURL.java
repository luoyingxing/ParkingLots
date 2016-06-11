package com.luo.park;

/**
 * 接口访问
 * Created by luoyingxing on 16/1/19.
 */
public class ApiURL {

    public static String SERVER_HOST = "http://113.106.49.191:8080/renwoxue";
    public static final String API_FILE_IMAGE = "/upload/image";

    /**
     * 将相对路径补全成完整的URL
     *
     * @param url url相对路径
     * @return 自动补全URL
     */
    //TODO 后续应该结合友盟的在线参数功能, 实现动态切换服务端IP地址. 以后的接口全部使用相对路径.
    public static String getUrl(String url) {
        if (url.startsWith("/")) {
            return ApiURL.SERVER_HOST + url;
        }
        return url;
    }
}