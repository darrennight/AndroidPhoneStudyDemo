package zenghao.com.study.sns.listener;

import zenghao.com.study.sns.PlatformType;

/**
 *SNS分享回调
 * @author zenghao
 * @since 16/9/28 下午4:34
 */
public interface SNSShareListener {
    void onComplete(PlatformType platform_type);

    void onError(PlatformType platform_type, String err_msg);

    void onCancel(PlatformType platform_type);
}
