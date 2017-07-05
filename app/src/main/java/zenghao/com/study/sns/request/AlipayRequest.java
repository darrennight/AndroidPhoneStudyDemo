package zenghao.com.study.sns.request;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import zenghao.com.study.sns.PlatformConfig;
import zenghao.com.study.sns.listener.PayListener;
import zenghao.com.study.sns.pay.AliPayInfo;
import zenghao.com.study.sns.pay.AlipayUtils;
import zenghao.com.study.sns.pay.IPayInfo;
import zenghao.com.study.sns.pay.PayResult;
import zenghao.com.study.util.CommonUtil;

/**
 *支付宝支付请求
 * @author zenghao
 * @since 2017/5/7 下午1:25
 */
public class AlipayRequest extends SSORequest {
    //上下文请使用activity
    private Context mContext;
    private PlatformConfig.AliPay mConfig;
    private PayListener mPayListener;
    private static final int ALI_PAY = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == ALI_PAY) {
                PayResult payResult = new PayResult((String) msg.obj);
                String resultStatus = payResult.getResultStatus();

                if(mPayListener !=null) {

                    if (TextUtils.equals(resultStatus, "9000")) {
                        //订单支付成功
                        mPayListener.onComplete(mConfig.getPlatformType());
                    }else if(TextUtils.equals(resultStatus, "8000")) {
                        //正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    }else if(TextUtils.equals(resultStatus, "6001")) {
                        //用户中途取消
                        mPayListener.onCancel(mConfig.getPlatformType());
                    }else if(TextUtils.equals(resultStatus, "6002")) {
                        //网络连接出错
                        mPayListener.onError(mConfig.getPlatformType(),"请检查网络状态");
                    }else if(TextUtils.equals(resultStatus, "5000")) {
                        //重复请求
                    }else if(TextUtils.equals(resultStatus, "4000")) {
                        //订单支付失败
                        mPayListener.onError(mConfig.getPlatformType(),"订单支付失败");
                    }else if(TextUtils.equals(resultStatus, "6004")) {
                        //支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    }else {
                        mPayListener.onError(mConfig.getPlatformType(),"支付失败");
                    }
                }
            }
        }
    };
    @Override
    public void init(Context context, PlatformConfig.Platform config) {
        super.init(context, config);
        this.mConfig = ((PlatformConfig.AliPay) config);
    }

    @Override
    public boolean isInstall() {
        return super.isInstall();
    }

    @Override
    public void pay(final Context context, IPayInfo info, PayListener payListener) {
        super.pay(context, info, payListener);
        this.mPayListener = payListener;
        final AliPayInfo payInfo = ((AliPayInfo) info);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(((Activity) context));
                //rsa2 ? "RSA2" : "RSA"
                String pays = AlipayUtils.getPayInfo(payInfo.getSubject()
                                        ,payInfo.getBody(),
                                        payInfo.getPrice(),payInfo.getOid(),payInfo.getDomain(),
                        CommonUtil.getAppVersionName(context),"RSA2");

                String result = alipay.pay(pays,true);

                Message msg = new Message();
                msg.arg1 = ALI_PAY;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
