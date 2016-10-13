package zenghao.com.study.IPC.sharePreNewImp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

/***
 * share 跨进程 新方式
 * <provider
 android:name=".provi.PreferencesProvider"
 android:authorities="com.melodyxxx.sharedpreferencesdemo.sp"/>
 */
public class PreferencesProvider extends BasePreferencesProvider {

    // putString()方法标识
    public static final String METHOD_PUT_STRING = "put_string";
    // getString()方法标识
    public static final String METHOD_GET_STRING = "get_string";
    // putBoolean()方法标识
    public static final String METHOD_PUT_BOOLEAN = "put_boolean";
    // getBoolean()方法标识
    public static final String METHOD_GET_BOOLEAN = "get_boolean";

    public static final String EXTRA_KEY = "key";
    public static final String EXTRA_VALUE = "value";
    public static final String EXTRA_DEFAULT_VALUE = "default_value";

    private SharedPreferences mPreferences;

    @Override
    public boolean onCreate() {
        // Provider创建时获取SharedPreferences
        mPreferences = getContext().getSharedPreferences("app_config", Context.MODE_PRIVATE);
        return false;
    }

    @Nullable
    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        // 用于将数据返回给调用方，例如getString()、getBoolean()
        Bundle replyData = null;
        switch (method) {
            case METHOD_PUT_STRING: {
                String key = extras.getString(EXTRA_KEY);
                String value = extras.getString(EXTRA_VALUE);
                // 将值存起来 - putString()
                mPreferences.edit().putString(key, value).commit();
                break;
            }
            case METHOD_GET_STRING: {
                String key = extras.getString(EXTRA_KEY);
                String defValue = extras.getString(EXTRA_DEFAULT_VALUE);
                // 获取到的值 - getString()
                String value = mPreferences.getString(key, defValue);
                replyData = new Bundle();
                // 将获取到的值放进Bundle
                replyData.putString(EXTRA_VALUE, value);
                break;
            }
            case METHOD_PUT_BOOLEAN: {
                String key = extras.getString(EXTRA_KEY);
                boolean value = extras.getBoolean(EXTRA_VALUE);
                // 将值存起来 - putBoolean()
                mPreferences.edit().putBoolean(key, value).commit();
                break;
            }
            case METHOD_GET_BOOLEAN: {
                String key = extras.getString(EXTRA_KEY);
                boolean defValue = extras.getBoolean(EXTRA_DEFAULT_VALUE);
                // 获取到的值 - getBoolean()
                boolean value = mPreferences.getBoolean(key, defValue);
                replyData = new Bundle();
                replyData.putBoolean(EXTRA_VALUE, value);
                break;
            }
        }
        // 将获取到的值返回给调用方，若为put操作，replyData则为null
        return replyData;
    }
}