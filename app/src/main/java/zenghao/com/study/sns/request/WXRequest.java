package zenghao.com.study.sns.request;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import java.util.HashMap;
import java.util.Map;
import zenghao.com.study.R;
import zenghao.com.study.sns.PlatformConfig;
import zenghao.com.study.sns.PlatformType;
import zenghao.com.study.sns.config.AppConfig;
import zenghao.com.study.sns.listener.AuthListener;
import zenghao.com.study.sns.listener.SNSShareListener;
import zenghao.com.study.sns.share.ISNSShareConent;
import zenghao.com.study.sns.share.ShareTextContent;
import zenghao.com.study.sns.share.ShareWebContent;
import zenghao.com.study.util.BitmapUtils;

/**
 * 微信登陆相关
 *
 * @author zenghao
 * @since 16/9/26 下午5:56
 */
public class WXRequest extends SSORequest {

    private Context mContext;
    private IWXAPIEventHandler mEventHandler;
    private AuthListener mAuthListener;
    private SNSShareListener mShareListener;
    private PlatformConfig.Weixin mConfig;
    private IWXAPI mWXApi;
    private String mScope = "snsapi_userinfo";
    private String mState = "bread_trip_sso_request";
    /** 微信分享缩略图最大值 */
    private static final int THUMBDATA_MAX_SIZE = 32 * 1024;

    public WXRequest() {
        this.mEventHandler = new IWXAPIEventHandler() {
            @Override
            public void onReq(BaseReq baseReq) {

            }

            @Override
            public void onResp(BaseResp baseResp) {
                int type = baseResp.getType();
                switch (type) {
                    case ConstantsAPI.COMMAND_SENDAUTH:     //授权返回
                        WXRequest.this.onAuthCallback((SendAuth.Resp) baseResp);
                        break;

                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:        //分享返回
                        onShareCallback((SendMessageToWX.Resp) baseResp);
                        break;

                    default:
                        break;
                }
            }
        };
    }

    public IWXAPI getWXApi() {
        return this.mWXApi;
    }

    public IWXAPIEventHandler getWXEventHandler() {
        return this.mEventHandler;
    }

    @Override
    public void init(Context context, PlatformConfig.Platform config) {
        super.init(context, config);

        this.mContext = context;
        this.mConfig = ((PlatformConfig.Weixin) config);

        String appId =
                TextUtils.isEmpty(this.mConfig.appId) ? AppConfig.APP_ID : this.mConfig.appId;
        this.mWXApi = WXAPIFactory.createWXAPI(mContext.getApplicationContext(), appId);
        this.mWXApi.registerApp(appId);
    }

    @Override
    public void authorize(Activity activity, AuthListener authListener) {
        this.mAuthListener = authListener;
        if (!isInstall()) {
            this.mAuthListener.onError(this.mConfig.getPlatformType(),"没有安装");
            return;
        }

        SendAuth.Req req1 = new SendAuth.Req();
        req1.scope = mScope;
        req1.state = mState;

        if (!this.mWXApi.sendReq(req1)) {
            this.mAuthListener.onError(this.mConfig.getPlatformType(), "");
        }
    }

    @Override
    public boolean isInstall() {
        return mWXApi.isWXAppInstalled();
    }

    @Override
    public void share(Activity activity, ISNSShareConent shareMedia,
            SNSShareListener shareListener) {
        super.share(activity, shareMedia, shareListener);

        this.mShareListener = shareListener;

        if (!isInstall()) {
            this.mShareListener.onError(this.mConfig.getPlatformType(),"没有按照");
            return;
        }

        String type;
        WXMediaMessage msg = new WXMediaMessage();
        if (shareMedia instanceof ShareTextContent) {
            //纯文本分享
            ShareTextContent shareTextMedia = (ShareTextContent) shareMedia;
            type = "text";

            //text object
            WXTextObject textObject = new WXTextObject();
            textObject.text = shareTextMedia.getText();

            msg.mediaObject = textObject;
            msg.description = shareTextMedia.getText();
        } else if (shareMedia instanceof ShareWebContent) {
            //网页分享

            ShareWebContent shareWebMedia = (ShareWebContent) shareMedia;
            type = "webpage";

            //web object
            WXWebpageObject webpageObject = new WXWebpageObject();
            webpageObject.webpageUrl = shareWebMedia.getWebPageUrl();

            msg.mediaObject = webpageObject;
            msg.title = shareWebMedia.getTitle();
            msg.description = shareWebMedia.getDescription();
            msg.thumbData = BitmapUtils.bitmap2Bytes(shareWebMedia.getThumb());
        } else {
            if (this.mShareListener != null) {
                this.mShareListener.onError(this.mConfig.getPlatformType(), "");
            }
            return;
        }

        if (msg.thumbData != null && msg.thumbData.length > THUMBDATA_MAX_SIZE) {
            //压缩缩略图
            msg.thumbData = BitmapUtils.compressBitmap(msg.thumbData);
        }

        //发起request
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = buildTransaction(type);

        if (this.mConfig.getPlatformType() == PlatformType.WEIXIN) {     //分享好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (this.mConfig.getPlatformType() == PlatformType.WEIXIN_CIRCLE) {      //分享朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }

        if (!this.mWXApi.sendReq(req)) {
            if (this.mShareListener != null) {
                this.mShareListener.onError(this.mConfig.getPlatformType(), "");
            }
        }
    }

    private String buildTransaction(String type) {
        return type == null ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    /** 授权验证回调 */
    protected void onAuthCallback(SendAuth.Resp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:       //授权成功
                Map<String, String> data = new HashMap<>();
                data.put("code", resp.code);
                // TODO 请求接口 获取token 和user 回调user
                this.mAuthListener.onComplete(PlatformType.WEIXIN, data);
                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:      //授权取消
                if (this.mAuthListener != null) {
                    this.mAuthListener.onCancel(PlatformType.WEIXIN);
                }
                break;

            default:    //授权失败
                //TODO 微信授权失败 提示信息 后期处理
                if (mAuthListener != null) {
                    mAuthListener.onError(PlatformType.WEIXIN, resp.errStr);
                }
                break;
        }
    }

    /** 分享回调 */
    protected void onShareCallback(SendMessageToWX.Resp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:       //分享成功
                if (this.mShareListener != null) {
                    this.mShareListener.onComplete(this.mConfig.getPlatformType());
                }
                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:      //分享取消
                if (this.mShareListener != null) {
                    this.mShareListener.onCancel(this.mConfig.getPlatformType());
                }
                break;

            default:    //分享失败
                if (mShareListener != null) {
                    mShareListener.onError(this.mConfig.getPlatformType(), resp.errStr);
                }
                break;
        }
    }
}
