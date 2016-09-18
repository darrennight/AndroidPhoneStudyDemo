package zenghao.com.study.retrofit;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import zenghao.com.study.MyApplication;

/**
 * Created by zenghao on 16/8/5.
 * 持久化cookie
 */
public class JavaNetCookieJar implements CookieJar {

    private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.getApplication());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
