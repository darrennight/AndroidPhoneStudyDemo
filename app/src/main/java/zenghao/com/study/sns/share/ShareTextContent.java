package zenghao.com.study.sns.share;

/**
 *纯文本分享
 * @author zenghao
 * @since 16/9/28 下午5:29
 */
public class ShareTextContent implements ISNSShareConent {

    private String mText;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
