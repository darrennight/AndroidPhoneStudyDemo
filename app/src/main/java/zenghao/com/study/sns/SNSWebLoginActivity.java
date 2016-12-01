package zenghao.com.study.sns;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.UUID;
import zenghao.com.study.R;
import zenghao.com.study.bean.User;
import zenghao.com.study.sns.config.NetConfig;

/**
 *
 *SNS网页登陆
 * @author zenghao
 * @since 16/9/28 上午11:38
 */
public class SNSWebLoginActivity extends Activity {

    /**新浪微博网页登陆*/
    public static final int SINA_WB_FLAG = 0;
    public static final int SINA_REQUEST_CODE = 11;

    /**qq网页登陆*/
    public static final int QQ_FLAG = 1;
    public static final int QQ_REQUEST_CODE = 12;

    public static final String PLATFORM_KEY = "platform_key";

    /**平台类型*/
    private int mPlatformType;

    private String loadUrl;
    private String loginSucceedUrl;

    private static long tag = UUID.randomUUID().getMostSignificantBits();

    private WebView mWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_web_login);
        parseIntent();
        initView();
    }

    private void parseIntent(){
        Intent intent = getIntent();
        int mPlatformType = intent.getIntExtra(PLATFORM_KEY,-1);

        if(mPlatformType == SINA_WB_FLAG){
            //新浪
            loadUrl = NetConfig.SINA_WEB_LOGIN;
            loginSucceedUrl = NetConfig.SINA_LOGIN_SUCCEED;

        }else if(mPlatformType == QQ_FLAG){
            //qq
        }

    }


    private void initView(){
        mWebView = ((WebView) findViewById(R.id.wv_sns_login));

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if(url.contains(loginSucceedUrl)) {
                    view.stopLoading();
                    CookieManager cookieManager = CookieManager.getInstance();
                    String cookie = cookieManager.getCookie(url).replaceFirst(NetConfig.KEY_SESSIONID + "=", "");
                    //拿token

                    //netUserManager.getUserInfo(tag + "", EncodeUtils.getMD5String(tag + AppConfig.SALT), loginWay, listener, CODE_GET_USER_INFO);

                    //调用登陆接口
                    /*UserApi api = ApiService.createService(UserApi.class);
                    api.sinaWebLogin("","","").subscribe(new HObserver<User>() {
                        @Override
                        public void onSuccess(User user) {
                            //setResult(RESULT_OK, intent);
                            //finish();
                        }

                        @Override
                        public void onFail(HException e) {

                        }
                    });*/

                } else {
                    super.onPageStarted(view, url, favicon);
                }
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient());//growingio需要
        mWebView.loadUrl(String.format(loadUrl, tag));
    }



}
