package zenghao.com.study.sns;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/9/26 下午5:04
 */
public class PlatformConfig {


    public static Map<PlatformType, Platform> configs = new HashMap();

    static {
        configs.put(PlatformType.WEIXIN, new PlatformConfig.Weixin(PlatformType.WEIXIN));
        configs.put(PlatformType.WEIXIN_CIRCLE, new PlatformConfig.Weixin(PlatformType.WEIXIN_CIRCLE));
        configs.put(PlatformType.QQ, new PlatformConfig.QQ(PlatformType.QQ));
        configs.put(PlatformType.SINA_WB, new PlatformConfig.SinaWeiBo(PlatformType.SINA_WB));
    }


    public interface Platform {
        /**获取平台类型*/
        PlatformType getPlatformType();
    }

    /**
     * 微信
     */
    public static class Weixin implements PlatformConfig.Platform {
        private final PlatformType mPlatformType;
        public String appId;

        public Weixin(PlatformType type) {
            this.mPlatformType = type;
        }

        public PlatformType getPlatformType() {
            return this.mPlatformType;
        }


    }

    /**
     * 设置微信配置信息
     * @param appId
     */
    public static void setWeixin(String appId) {
        PlatformConfig.Weixin weixin = (PlatformConfig.Weixin)configs.get(PlatformType.WEIXIN);
        weixin.appId = appId;

        PlatformConfig.Weixin weixin_circle = (PlatformConfig.Weixin)configs.get(PlatformType.WEIXIN_CIRCLE);
        weixin_circle.appId = appId;
    }

    /**
     * qq
     */
    public static class QQ implements PlatformConfig.Platform {
        private final PlatformType mPlatformType;
        public String appId;

        public QQ(PlatformType type) {
            this.mPlatformType = type;
        }

        public PlatformType getPlatformType() {
            return this.mPlatformType;
        }


    }

    /**
     * 设置qq配置信息
     * @param appId
     */
    public static void setQQ(String appId) {
        PlatformConfig.QQ qq = (PlatformConfig.QQ)configs.get(PlatformType.QQ);
        qq.appId = appId;

    }

    /**
     * 新浪微博
     */
    public static class SinaWeiBo implements PlatformConfig.Platform {
        private final PlatformType mPlatformType;
        public String appKey;

        public SinaWeiBo(PlatformType type) {
            this.mPlatformType = type;
        }

        public PlatformType getPlatformType() {
            return this.mPlatformType;
        }


    }

    /**
     * 设置新浪微博配置信息
     * @param appKey
     */
    public static void setSinaWB(String appKey) {
        PlatformConfig.SinaWeiBo sinaWB = (PlatformConfig.SinaWeiBo)configs.get(PlatformType.SINA_WB);
        sinaWB.appKey = appKey;
    }

    public static Platform getPlatformConfig(PlatformType platformType) {
        return configs.get(platformType);
    }
}
