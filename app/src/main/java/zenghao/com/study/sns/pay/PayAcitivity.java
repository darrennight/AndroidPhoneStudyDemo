package zenghao.com.study.sns.pay;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 *支付activty 支付界面金额输入
 * @author zenghao
 * @since 2017/5/7 下午1:45
 */
public class PayAcitivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //请求获取支付相关info 设置info
        /*PlatformConfig.setWeixin(WX_APPID);
        PlatformConfig.setQQ(QQ_APPID);
        PlatformConfig.setSinaWB(SINA_WB_APPKEY);*/

        //调用breadtripSocialApi 中的doPay支付
    }
}
