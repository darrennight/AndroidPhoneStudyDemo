package zenghao.com.study.sns.listener;

import zenghao.com.study.sns.PlatformType;

/**
 *
 *SNS授权回调
 * @author zenghao
 * @since 16/9/26 下午5:15
 */
public interface AuthListener {

    void onComplete(PlatformType platform_type, Object object);

    void onError(PlatformType platform_type, String err_msg);

    void onCancel(PlatformType platform_type);
}
