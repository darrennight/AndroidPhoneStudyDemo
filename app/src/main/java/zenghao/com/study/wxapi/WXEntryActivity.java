package zenghao.com.study.wxapi;

import android.app.Activity;
import android.os.Bundle;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import zenghao.com.study.sns.BreadtripSocialApi;
import zenghao.com.study.sns.PlatformConfig;
import zenghao.com.study.sns.PlatformType;
import zenghao.com.study.sns.request.WXRequest;

/**
 *
 *微信sdk使用
 * @author zenghao
 * @since 16/9/26 下午4:07
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private WXRequest mWXRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BreadtripSocialApi api = BreadtripSocialApi.get(this.getApplicationContext());
        this.mWXRequest = (WXRequest)api.getSSORequest(PlatformType.WEIXIN);
        this.mWXRequest.init(this.getApplicationContext(), PlatformConfig.getPlatformConfig(PlatformType.WEIXIN));
        this.mWXRequest.getWXApi().handleIntent(this.getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        if(this.mWXRequest != null) {
            this.mWXRequest.getWXEventHandler().onReq(baseReq);
        }

        this.finish();

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(this.mWXRequest != null && baseResp != null) {
            try {
                this.mWXRequest.getWXEventHandler().onResp(baseResp);
            } catch (Exception var3) {
                ;
            }
        }

        this.finish();
    }
}
