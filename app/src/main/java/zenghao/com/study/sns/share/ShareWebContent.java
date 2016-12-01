package zenghao.com.study.sns.share;

import android.graphics.Bitmap;

/**
 *分享网页
 * @author zenghao
 * @since 16/9/28 下午5:33
 */
public class ShareWebContent implements ISNSShareConent {

    /**待分享的网页url*/
    private String mWebPageUrl;
    /**网页标题*/
    private String mTitle;
    /**网页描述*/
    private String mDescription;
    /**网页缩略图 微信限制32K*/
    private Bitmap mThumb;
    /**网页缩略图*/
    private String localPath;

    public String getWebPageUrl() {
        return mWebPageUrl;
    }

    public void setWebPageUrl(String webPageUrl) {
        mWebPageUrl = webPageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Bitmap getThumb() {
        return mThumb;
    }

    public void setThumb(Bitmap thumb) {
        mThumb = thumb;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
