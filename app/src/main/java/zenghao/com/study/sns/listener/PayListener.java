package zenghao.com.study.sns.listener;

import zenghao.com.study.sns.PlatformType;

/**
 *支付回调
 * @author zenghao
 * @since 2017/5/7 下午2:52
 */
public interface PayListener {

    void onComplete(PlatformType platform_type);

    void onError(PlatformType platform_type, String err_msg);

    void onCancel(PlatformType platform_type);
}
