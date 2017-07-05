package zenghao.com.study.sns.pay;

/**
 *支付宝支付info
 * @author zenghao
 * @since 2017/5/7 下午4:46
 */
public class AliPayInfo implements IPayInfo {

    private String subject;
    private String body;
    private String price;
    private String oid;
    private String domain;
    private String versionCode;
    private String signl;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getSignl() {
        return signl;
    }

    public void setSignl(String signl) {
        this.signl = signl;
    }
}
