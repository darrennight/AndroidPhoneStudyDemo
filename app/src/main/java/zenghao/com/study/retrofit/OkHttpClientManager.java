package zenghao.com.study.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zenghao on 16/8/5.
 * 通用的header token 日志拦截  cookie
 * 等通用属性在此 添加处理
 */
public class OkHttpClientManager {

    private OkHttpClient mOkHttpClient;

    private static OkHttpClientManager mInstance;
    private OkHttpClientManager(){
        mOkHttpClient = new OkHttpClient();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        HeaderInterceptor headerInterceptor = new HeaderInterceptor();
        RequestInterceptor requestInterceptor = new RequestInterceptor();

        mOkHttpClient.newBuilder()
                //持久化cookie
                .cookieJar(new JavaNetCookieJar())
                //添加日志打印//可以设置debug才打印
                .addInterceptor(loggingInterceptor)
                //请求超时相关
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                //错误重连
                .retryOnConnectionFailure(true)
                .addInterceptor(headerInterceptor)
                .addInterceptor(requestInterceptor)
                .build();
                //.cache().addInterceptor() 添加缓存拦截器没有网络显示数据




    }
    public static OkHttpClientManager getInstance(){
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class){
                if (mInstance == null)
                    mInstance = new OkHttpClientManager();
            }
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
