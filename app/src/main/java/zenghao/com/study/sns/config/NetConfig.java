package zenghao.com.study.sns.config;

import zenghao.com.study.BuildConfig;

/**
 * 网络地址管理类
 *
 * @author jiwei
 * @since 16/9/1 下午12:05
 */
public class NetConfig {

    /**
     * domain
     */
    //public final static String DOMAIN = BuildConfig.DOMAIN;

    //public final static String KEY_SESSIONID = BuildConfig.SESSIONID;
    public final static String KEY_SESSIONID = "test";

    /**
     * api host
     */
    //public final static String APIHOST = getHost("api");
    /**
     * web host
     */
    //public final static String WEBHOST = getHost("web");

    //private static String getHost(String host) {
    //    return (BuildConfig.ISDEBUG ? "http://" : "https://") + host + "." + DOMAIN;
    //}

    /**
     * 登录
     */
    public static final String LOGIN = "accounts/login/";

    /**
     * 注册
     */
    public static final String SIGNUP = "accounts/signup/";
    /**
     * 验证验证码
     */
    public static final String CHECK_VERIFY_CODE = "/common/check_message_code/";

    /**
     * 发送短信验证码
     */
    public static final String SEND_MESSAGE_CODE = "common/send_message_code/";

    /**
     * 登出
     */
    public static final String LOGOUT = "accounts/logout/";

    /** 新浪微博回调 */
    public static final String SINA_LOGIN_CALLBACK =
            "http://api.breadtrip.com/accounts/oauth2_sina/login/callback/";

    /** 新浪微博网页授权登陆 */
    //public static final String SINA_WEB_LOGIN = APIHOST + "/accounts/oauth2_sina/login/v2/?id=%s";
    //public final static String SINA_LOGIN_SUCCEED =
    //        APIHOST + "/accounts/oauth2_sina/statuses/success/";

    public static final String SINA_WEB_LOGIN = "test";
    public final static String SINA_LOGIN_SUCCEED = "test";
}
