package zenghao.com.study.sns.request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import zenghao.com.study.sns.PlatformConfig;
import zenghao.com.study.sns.SNSWebLoginActivity;
import zenghao.com.study.sns.config.AppConfig;
import zenghao.com.study.sns.listener.AuthListener;
import zenghao.com.study.sns.listener.SNSShareListener;
import zenghao.com.study.sns.share.ISNSShareConent;
import zenghao.com.study.sns.share.ShareWebContent;

/**
 *qq登陆分享request
 * @author zenghao
 * @since 16/9/28 上午10:42
 */
public class QQRequest extends SSORequest {

    private Context mContext;
    private Activity mActivity;

    private Tencent mTencent;

    private PlatformConfig.QQ mConfig;
    private AuthListener mAuthListener;
    private SNSShareListener mShareListener;

    private int mActionType;

    @Override
    public void init(Context context, PlatformConfig.Platform config) {
        super.init(context, config);

        mContext = context;
        mTencent = Tencent.createInstance(AppConfig.QQ_APP_ID, mContext);

        mConfig = ((PlatformConfig.QQ) config);
    }

    @Override
    public void authorize(Activity activity, AuthListener authListener) {
        super.authorize(activity, authListener);

        mActivity = activity;
        mAuthListener = authListener;

        if (isQqSSOExisted(mContext)) {
            //sso授权
            this.mTencent.login(this.mActivity, "all", new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    if (null == o) {
                        mAuthListener.onError(mConfig.getPlatformType(), "");
                        return;
                    }

                    JSONObject response = (JSONObject) o;

                    initOpenidAndToken(response);
                    //TODO 请求接口获取user
                    mAuthListener.onComplete(mConfig.getPlatformType(), jsonToMap(response));

                    //成功后需要注销
                    mTencent.logout(mActivity);
                }

                @Override
                public void onError(UiError uiError) {
                    mAuthListener.onError(mConfig.getPlatformType(), uiError.errorMessage);
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
            intent.putExtra(SNSWebLoginActivity.PLATFORM_KEY, SNSWebLoginActivity.QQ_FLAG);
            mActivity.startActivityForResult(intent, SNSWebLoginActivity.QQ_REQUEST_CODE);
        }
    }

    @Override
    public void share(Activity activity, ISNSShareConent shareMedia,
            SNSShareListener shareListener) {
        super.share(activity, shareMedia, shareListener);
        mActivity = activity;
        mShareListener = shareListener;

        Bundle params = new Bundle();

        //分享到qq
        if (shareMedia instanceof ShareWebContent) {
            //网页分享
            ShareWebContent shareWebMedia = (ShareWebContent) shareMedia;

            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, shareWebMedia.getTitle());
            //最长50个字
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareWebMedia.getDescription());
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareWebMedia.getWebPageUrl());
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareWebMedia.getLocalPath());
        } else {
            if (this.mShareListener != null) {
                this.mShareListener.onError(this.mConfig.getPlatformType(), "");
            }
            return;
        }

        //qq分享
        mTencent.shareToQQ(mActivity, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                mShareListener.onComplete(mConfig.getPlatformType());
            }

            @Override
            public void onError(UiError uiError) {
                mShareListener.onError(mConfig.getPlatformType(), uiError.errorMessage);
            }

            @Override
            public void onCancel() {
                mShareListener.onCancel(mConfig.getPlatformType());
            }
        });
    }

    public Map<String, String> jsonToMap(JSONObject val) {
        HashMap map = new HashMap();

        Iterator iterator = val.keys();

        while (iterator.hasNext()) {
            String var4 = (String) iterator.next();
            map.put(var4, val.opt(var4) + "");
        }

        return map;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (mActionType == SHARE_TYPE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, null);
        } else if (mActionType == LOGIN_TYPE) {
            if (isQqSSOExisted(mContext)) {//TODO 判断是否是网页授权
                //sso授权
                Tencent.onActivityResultData(requestCode, resultCode, data, null);
            } else {
                //网页授权
                //mAuthListener 回调
            }
        }
    }

    @Override
    public void actionType(int type) {
        super.actionType(type);
        mActionType = type;
    }

    //要初始化open_id和token
    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token =
                    jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
            String expires =
                    jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
            String openId =
                    jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);

            mTencent.setAccessToken(token, expires);
            mTencent.setOpenId(openId);
        } catch (Exception e) {
        }
    }

    /**
     * 检查Qq SSO是否可用
     * TODO 目前由于qqAPPid问题 暂时只能 网页登陆 故此方法直接返回 false
     */
    private boolean isQqSSOExisted(Context context) {
        /*boolean value = false;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.tencent.mobileqq", PackageManager.GET_SERVICES);
            if (info != null) {
                value = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
        return false;
    }
}
