package zenghao.com.study.IPC.sharePreNewImp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

public class PreferencesUtils {

    private static final Uri sUri = Uri.parse("content://com.melodyxxx.sharedpreferencesdemo.sp");

    public static void putString(Context context, String key, String value) {
        Bundle data = new Bundle();
        data.putString(PreferencesProvider.EXTRA_KEY, key);
        data.putString(PreferencesProvider.EXTRA_VALUE, value);
        context.getContentResolver().call(sUri, PreferencesProvider.METHOD_PUT_STRING, null, data);
    }

    public static String getString(Context context, String key, String defValue) {
        String value = null;
        Bundle data = new Bundle();
        data.putString(PreferencesProvider.EXTRA_KEY, key);
        data.putString(PreferencesProvider.EXTRA_DEFAULT_VALUE, defValue);
        Bundle replyData = context.getContentResolver().call(sUri, PreferencesProvider.METHOD_GET_STRING, null, data);
        return replyData.getString(PreferencesProvider.EXTRA_VALUE);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        Bundle data = new Bundle();
        data.putString(PreferencesProvider.EXTRA_KEY, key);
        data.putBoolean(PreferencesProvider.EXTRA_VALUE, value);
        context.getContentResolver().call(sUri, PreferencesProvider.METHOD_PUT_BOOLEAN, null, data);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        Bundle data = new Bundle();
        data.putString(PreferencesProvider.EXTRA_KEY, key);
        data.putBoolean(PreferencesProvider.EXTRA_DEFAULT_VALUE, defValue);
        Bundle replyData = context.getContentResolver().call(sUri, PreferencesProvider.METHOD_GET_BOOLEAN, null, data);
        return replyData.getBoolean(PreferencesProvider.EXTRA_VALUE);
    }

}