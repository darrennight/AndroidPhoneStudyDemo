package zenghao.com.study.sns.request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import java.util.HashMap;
import java.util.Map;
import zenghao.com.study.sns.PlatformConfig;
import zenghao.com.study.sns.SNSWebLoginActivity;
import zenghao.com.study.sns.config.AppConfig;
import zenghao.com.study.sns.config.NetConfig;
import zenghao.com.study.sns.listener.AuthListener;
import zenghao.com.study.sns.listener.SNSShareListener;
import zenghao.com.study.sns.share.ISNSShareConent;
import zenghao.com.study.sns.share.ShareTextContent;
import zenghao.com.study.sns.share.ShareWebContent;

/**
 * 新浪登陆分享request
 *
 * @author zenghao
 * @since 16/9/27 下午3:53
 */
public class SinaRequest extends SSORequest {

    private Context mContext;
    private Activity mActivity;

    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private PlatformConfig.SinaWeiBo mConfig;
    private AuthListener mAuthListener;
    private IWeiboShareAPI mWeiboShareAPI;
    private SNSShareListener mShareListener;

    private final String REDIRECT_URL = NetConfig.SINA_LOGIN_CALLBACK;
    private final String SCOPE = "";

    @Override
    public void init(Context context, PlatformConfig.Platform config) {
        super.init(context, config);
        this.mContext = context;
        this.mConfig = ((PlatformConfig.SinaWeiBo) config);

        String appKey = TextUtils.isEmpty(mConfig.appKey) ? AppConfig.SINA_APP_KEY : mConfig.appKey;
        this.mAuthInfo = new AuthInfo(mContext, appKey, REDIRECT_URL, SCOPE);

        this.mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, appKey);
        this.mWeiboShareAPI.registerApp();
    }

    @Override
    public void authorize(Activity activity, AuthListener authListener) {
        this.mAuthListener = authListener;
        this.mActivity = activity;

        if (isSinaSSOExisted(mContext)) {
            //sso授权
            this.mSsoHandler = new SsoHandler(mActivity, mAuthInfo);
            mSsoHandler.authorize(new WeiboAuthListener() {
                @Override
                public void onComplete(Bundle bundle) {
                    // 从 Bundle 中解析 Token
                    Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(bundle);
                    if (accessToken.isSessionValid()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("uid", accessToken.getUid());
                        map.put("access_token", accessToken.getToken());
                        map.put("refresh_token", accessToken.getRefreshToken());
                        map.put("expire_time", "" + accessToken.getExpiresTime());

                        //TODO 请求接口获取user回调user
                        mAuthListener.onComplete(mConfig.getPlatformType(), map);
                    } else {
                        mAuthListener.onError(mConfig.getPlatformType(), "");
                    }
                }

                @Override
                public void onWeiboException(WeiboException e) {
                    mAuthListener.onError(mConfig.getPlatformType(), e.getMessage());
                }

                @Override
                public void onCancel() {
                    mAuthListener.onCancel(mConfig.getPlatformType());
                }
            });
        } else {
            //网页授权
            Intent intent = new Intent();
            intent.setClass(mActivity, SNSWebLoginActivity.class);
            intent.putExtra(SNSWebLoginActivity.PLATFORM_KEY, SNSWebLoginActivity.SINA_WB_FLAG);
            mActivity.startActivityForResult(intent, SNSWebLoginActivity.SINA_REQUEST_CODE);
        }
    }

    @Override
    public void share(Activity activity, ISNSShareConent shareMedia,
            SNSShareListener shareListener) {
        super.share(activity, shareMedia, shareListener);
        this.mActivity = activity;
        this.mShareListener = shareListener;

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

        if (shareMedia instanceof ShareTextContent) {   //文字分享
            ShareTextContent shareTextMedia = (ShareTextContent) shareMedia;

            TextObject textObject = new TextObject();
            textObject.text = shareTextMedia.getText();

            weiboMessage.textObject = textObject;
        } else if (shareMedia instanceof ShareWebContent) {       //网页分享
            ShareWebContent shareWebMedia = (ShareWebContent) shareMedia;

            WebpageObject mediaObject = new WebpageObject();
            mediaObject.identify = Utility.generateGUID();
            mediaObject.title = shareWebMedia.getTitle();
            mediaObject.description = shareWebMedia.getDescription();
            mediaObject.setThumbImage(shareWebMedia.getThumb());
            mediaObject.actionUrl = shareWebMedia.getWebPageUrl();

            weiboMessage.mediaObject = mediaObject;
        } else {
            if (this.mShareListener != null) {
                this.mShareListener.onError(this.mConfig.getPlatformType(), "");
            }
            return;
        }

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        mWeiboShareAPI.sendRequest(mActivity, request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isSinaSSOExisted(mContext)) {
            //sso授权
            if (mSsoHandler != null) {
                mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
            }
        } else {
            //网页授权
            //mAuthListener 回调
        }
    }

    public void onNewIntent(Intent intent, IWeiboHandler.Response response) {
        if (intent != null && mWeiboShareAPI != null) {
            mWeiboShareAPI.handleWeiboResponse(intent, response);
        }
    }

    public void onResponse(BaseResponse baseResponse) {
        if (baseResponse != null) {
            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    if (this.mShareListener != null) {
                        this.mShareListener.onComplete(this.mConfig.getPlatformType());
                    }
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    if (this.mShareListener != null) {
                        this.mShareListener.onCancel(this.mConfig.getPlatformType());
                    }
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    if (this.mShareListener != null) {
                        this.mShareListener.onError(this.mConfig.getPlatformType(),
                                baseResponse.errMsg);
                    }
                    break;
            }
        }
    }

    /**
     * 是否能sso授权
     */
    public static boolean isSinaSSOExisted(Context context) {
        boolean value = false;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.sina.weibo", PackageManager.GET_SERVICES);
            if ((int) info.versionName.charAt(info.versionName.length() - 1) > 3) {
                value = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }
}
