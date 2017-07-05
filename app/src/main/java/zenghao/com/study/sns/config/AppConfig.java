package zenghao.com.study.sns.config;

/**
 * App配置参数
 *
 * @author jiwei
 * @since 16/9/5 上午11:06
 */
public class AppConfig {

    /**
     * 网络参数加密秘钥
     */
    public static final String HTTP_ENCRYPT_SECRET =
            "MIIEowIBAAKCAQEAwbEIlVOexfkIQkYT9Us7iukU02PQsLZ9OHxxvWJaXCjKhdht";

    /**微信 appID*/
    public static final String APP_ID = "wxe334a1e34a01d971";//微信ID 新的
    /**微信 appSecert*/
    public static final String APP_SECERT = "f6703ea81e92f6de2d7e4634437b7271";
    /**新浪微博 appKey*/
    public static final String SINA_APP_KEY = "151032027";
    /**qq APPID*/
    public static final String QQ_APP_ID = "100411421";

    public final static String SALT = "2fwf*#&*2j32_sd";

    public final static String ALIPAY_NOTIFY = "http://%s/payment/alipay/wap/notify/%s/";
}
