package zenghao.com.study.sns.pay;

import zenghao.com.study.sns.config.AppConfig;

public class AlipayUtils {

	public static final String PARTNER = "2088511694902142";
	// 商户收款账号
	public static final String SELLER = "tonghesiji@breadtrip.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJH49UfXZPCTG7/pybHaMqweRO/gPuFlYqEP7kveweCCZL2Ww7FVN1qF25DqXyRt7vtaQrdmGkdVDBfL3mUI3T8cnBuYcfB43Doj3pNdN9Cd7OTqEdpvmiB5ga4k1lVbTkE9rKkfY8qnjMe/+aNPHyequB8JoNhFcxLl3PzL3O/5AgMBAAECgYApzN/4TZ7zIT0uDLWcP36JOTXGdzGqe8ztkFFN2qEsOEq+28B4NI3gVzqT2fmaxfxMcAFw4UUfLiSDX/b0ecBZFpKcSdsN2qVKK4Ch3kDDEMt3pH/uywMFcb7+2kDFF1jTOTUNpgxL+PY44V7xk+HyBQRESu5Wx1E6nqPUhC0iAQJBAMFT4AjpUTF2LWNpg6pP+YBqEVYs5mEiFORl/yoFkWovMK3pisI/wqMQByoPR+Vc/DBQdpmGNVcBSVsP7VkH67kCQQDBSxzgM6EY5QW+QAipY6etlGPb0LhE2Cc8YZzR+sZeTgXTYG2d0DUR8sMu1UTTeATVdFb/pyfvfqbM6aLTUcZBAkEAksyBvcKM6KX36Rjh5Z08s4nXCDyn4vHiM1xP2TTJKnwS1rwTpVSNEwzEo+dejIeC55zxsbn7HsnXTdJWQ9iD4QJAGlB5e+RLeDIlmALrqvPQLgdXmONPUsACh2vah5OWUdxZWq9z/NoNf4YHIGcUWZ2rOsv91AwTmb0qRBdCfXZSgQJAWt6wg8aE0tJyXkLB5dMhnAIyFjFJv6UhxiuSvr1I0Rm5mTRescZBuUDHcHsdmo0thY2m3vzQ1BVddxURg/vKgQ==";
	//支付宝公钥
	public static final String RSA_PUBLIC = "";
	
	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public final static String getOrderInfo(String subject, String body, String price, String oid, String domain, String versionCode) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";
		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + oid + "\"";
		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";
		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";
		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";
		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + String.format(AppConfig.ALIPAY_NOTIFY, domain, oid) + "\"";
		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";
		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";
		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";
		//标识客户端 breadtrip
		orderInfo += "&app_id=\"breadtrip\"";
		//客户端来源
		orderInfo += "&appenv=\"system=android^version=" + versionCode + "\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
       // orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	

	/**
	 * get the sign type we use. 获取签名方式
	 *
	 */
	public final static String getSignType() {
		return "sign_type=\"RSA\"";
	}

	public final static String getPayInfo(String subject, String body, String price, String oid, String domain, String versionCode, String sign){
		// 订单
		String orderInfo = getOrderInfo(subject, body, price, oid, domain, versionCode);
		// 完整的符合支付宝参数规范的订单信息
		return orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
	}
}
