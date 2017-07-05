package zenghao.com.study.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import zenghao.com.study.sns.BreadtripSocialApi;
import zenghao.com.study.sns.PlatformConfig;
import zenghao.com.study.sns.PlatformType;
import zenghao.com.study.sns.request.WXRequest;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	public static boolean success;

	private WXRequest mWXRequest;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		BreadtripSocialApi api = BreadtripSocialApi.get(this.getApplicationContext());
		this.mWXRequest = ((WXRequest) api.getSSORequest(PlatformType.WEIXIN));
		this.mWXRequest.init(this.getApplicationContext(), PlatformConfig.getPlatformConfig(PlatformType.WEIXIN));
		this.mWXRequest.getWXApi().handleIntent(this.getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		this.mWXRequest.getWXApi().handleIntent(this.getIntent(), this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			/*if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
				success = true;
				Intent actionIntent = new Intent();
				actionIntent.setAction(WebViewActivity.ACTION_WXPAY_CALLBACK_MSG);
				sendBroadcast(actionIntent);
			} else if(resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL || resp.errCode == BaseResp.ErrCode.ERR_COMM){
				success = false;
			}*/
			if(this.mWXRequest !=null && resp != null){
				this.mWXRequest.getWXEventHandler().onResp(resp);
			}
		}
		finish();
	}
}