package zenghao.com.study.sns.request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import zenghao.com.study.sns.PlatformConfig;
import zenghao.com.study.sns.listener.AuthListener;
import zenghao.com.study.sns.listener.PayListener;
import zenghao.com.study.sns.listener.SNSShareListener;
import zenghao.com.study.sns.pay.IPayInfo;
import zenghao.com.study.sns.share.ISNSShareConent;

/**
 * TODO
 *社会化sdk请求抽象类
 * @author zenghao
 * @since 16/9/26 下午5:00
 */
public abstract class SSORequest {

    public static final int SHARE_TYPE = 0;
    public static final int LOGIN_TYPE = 1;
    public static final int PAY_TYPE = 2;

    /**
     * 初始化
     * @param config 配置信息
     */
    public void init(Context context, PlatformConfig.Platform config) {

    }

    /**
     * 登录授权
     * @param activity
     * @param authListener 授权回调
     */
    public void authorize(Activity activity, AuthListener authListener) {

    }

    /**
     * 分享
     * @param shareMedia 分享内容
     * @param shareListener 分享回调
     */
    public void share(Activity activity, ISNSShareConent shareMedia, SNSShareListener shareListener) {

    }

    public void pay(Context context ,IPayInfo info,PayListener payListener){

    }

    /**
     * 重写onActivityResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    /**
     * 是否安装
     * @return
     */
    public boolean isInstall() {
        return true;
    }

    /**操作类型 分享 授权登陆*/
    public void actionType(int type){

    }
}
