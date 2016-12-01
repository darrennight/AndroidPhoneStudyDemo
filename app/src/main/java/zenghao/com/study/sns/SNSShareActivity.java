package zenghao.com.study.sns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.tencent.connect.common.Constants;
import zenghao.com.study.sns.request.SinaRequest;

/**
 *用于SNS分享的基类activity
 * 需要分享的activity继承此类
 * @author zenghao
 * @since 16/9/29 下午3:39
 * 需要分享功能的activity需要在清单文件添加
 * <intent-filter>
    <action android:name="com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY" />
    <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
 */
public class SNSShareActivity extends Activity implements IWeiboHandler.Response{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            ((SinaRequest)BreadtripSocialApi.get(this).getSSORequest(PlatformType.SINA_WB)).onNewIntent(getIntent(), this);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        ((SinaRequest)BreadtripSocialApi.get(this).getSSORequest(PlatformType.SINA_WB)).onNewIntent(intent, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_LOGIN) {
            BreadtripSocialApi.get(this).getSSORequest(PlatformType.QQ).onActivityResult(requestCode,resultCode,data);
        }

    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        ((SinaRequest)BreadtripSocialApi.get(this).getSSORequest(PlatformType.SINA_WB)).onResponse(baseResponse);
    }


}
