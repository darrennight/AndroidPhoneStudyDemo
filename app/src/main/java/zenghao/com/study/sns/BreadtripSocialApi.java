package zenghao.com.study.sns;

import android.app.Activity;
import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import zenghao.com.study.sns.listener.AuthListener;
import zenghao.com.study.sns.listener.SNSShareListener;
import zenghao.com.study.sns.request.QQRequest;
import zenghao.com.study.sns.request.SSORequest;
import zenghao.com.study.sns.request.SinaRequest;
import zenghao.com.study.sns.request.WXRequest;
import zenghao.com.study.sns.share.ISNSShareConent;

/**
 *SNS使用API
 * 分享继承SNSShareActivity
 * @author zenghao
 * @since 16/9/26 下午7:03
 * 登陆时
 * activity中需要使用下面这句 微信平台除外
 * BreadtripSocialApi.get(this).getSSORequest(PlatformType.SINA_WB).onActivityResult(requestCode,resultCode,data);
 *
 *
 *
 * 微信平台sdk
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317340&token=&lang=zh_CN
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317851&token=&lang=zh_CN
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&token=&lang=zh_CN
 *
 * qq平台sdk
 * http://wiki.open.qq.com/wiki/%E9%A6%96%E9%A1%B5
 * http://wiki.open.qq.com/wiki/%E5%88%86%E4%BA%AB%E6%B6%88%E6%81%AF%E5%88%B0QQ%EF%BC%88%E5%AE%9A%E5%90%91%E5%88%86%E4%BA%AB%EF%BC%89
 * http://wiki.open.qq.com/wiki/mobile/Android_SDK%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E
 *
 * 新浪微博
 * http://open.weibo.com/wiki/%E6%8E%88%E6%9D%83%E6%9C%BA%E5%88%B6
 * https://github.com/sinaweibosdk/weibo_android_sdk
 *
 *
 *
 * 框架借鉴
 * https://github.com/xinzy/Social-lib
 * http://www.jianshu.com/p/4ec1d9c15763
 * http://www.jianshu.com/p/71b6be4d1e0b
 *
 * https://github.com/tsy12321/SocialSDKAndroid
 *
 * https://github.com/shaohui10086/ShareUtil
 * https://github.com/lingochamp/ShareLoginLib
 */
public class BreadtripSocialApi {


    private static BreadtripSocialApi mApi = null;
    private static Context mContext = null;

    private final Map<PlatformType, SSORequest> mMapSSORequest = new HashMap();

    private BreadtripSocialApi(Context context) {
        mContext = context;
    }

    /**
     * 获取单例
     * @param context 建议传入全局context
     * @return
     */
    public static BreadtripSocialApi get(Context context) {
        if(mApi == null) {
            mApi = new BreadtripSocialApi(context);
        }

        return mApi;
    }

    public SSORequest getSSORequest(PlatformType platformType) {
        if(mMapSSORequest.get(platformType) == null) {
            switch (platformType) {
                case WEIXIN://微信和微信朋友圈使用同一个request
                case WEIXIN_CIRCLE:
                    mMapSSORequest.put(platformType, new WXRequest());
                    break;

                case QQ:
                    mMapSSORequest.put(platformType, new QQRequest());
                    break;

                case SINA_WB:
                    mMapSSORequest.put(platformType, new SinaRequest());
                    break;
                default:
                    break;
            }
        }

        return mMapSSORequest.get(platformType);
    }

    /**
     * 第三方登录授权
     * @param activity
     * @param platformType 第三方平台
     * @param authListener 授权回调
     * 微信没有网页授权
     */
    public void doOauthVerify(Activity activity, PlatformType platformType, AuthListener authListener) {
        SSORequest ssoRequest = getSSORequest(platformType);
        ssoRequest.init(mContext, PlatformConfig.getPlatformConfig(platformType));
        ssoRequest.actionType(SSORequest.LOGIN_TYPE);
        ssoRequest.authorize(activity, authListener);
    }

    /***
     * 第三方分享
     * @param activity
     * @param platformType 第三方平台
     * @param shareMedia 分享类型
     * @param shareListener 分享回调监听
     */
    public void doShare(Activity activity, PlatformType platformType, ISNSShareConent shareMedia, SNSShareListener shareListener) {
        SSORequest ssoHandler = getSSORequest(platformType);
        ssoHandler.init(mContext, PlatformConfig.getPlatformConfig(platformType));
        ssoHandler.actionType(SSORequest.SHARE_TYPE);
        ssoHandler.share(activity, shareMedia, shareListener);
    }


}
