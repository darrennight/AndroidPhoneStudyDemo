package zenghao.com.study.IPC.sharePre;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import zenghao.com.study.MyApplication;

/**
 * 用ContentProvider跨进程读取数据
 * 底层数据存储是sharedPreference
 * 也可以可以用数据库存储
 *
 * 使用方式 直接调用次类的 静态方法
 * ConfigProvider.getLongValue
 */
public class ConfigProvider extends ContentProvider {

    public static final Uri CONFIG_CONTENT_URI		= Uri.parse("content://com.breadtrip.trip.provider.config");

    private static final int LENGTH_CONTENT_URI		= CONFIG_CONTENT_URI.toString().length() + 1;
    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        return null;
    }


    private static String EXTRA_TYPE		= "type";
    private static String EXTRA_KEY			= "key";
    private static String EXTRA_VALUE		= "value";

    private static final int TYPE_BOOLEAN	= 1;
    private static final int TYPE_INT		= 2;
    private static final int TYPE_LONG		= 3;
    private static final int TYPE_STRING	= 4;


    private static ContentResolver getCr(){
        return MyApplication.getApplication().getContentResolver();
    }


    private static boolean s_bFixedSysBug = false;
    private static Object  s_LockFixedBug = new Object();
    private static ContentProviderClient s_cpClientFixer = null;

    private static void FixProviderSystemBug(){
        synchronized (s_LockFixedBug) {
            if ( s_bFixedSysBug )
                return;
            s_bFixedSysBug = true;

            if ((Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT <= 18)
                    || (Build.VERSION.SDK_INT >= 9 && Build.VERSION.SDK_INT <= 10)) {
                s_cpClientFixer = getCr().acquireContentProviderClient(CONFIG_CONTENT_URI);
            }
        }
    }


    public static boolean contains(String key){
        FixProviderSystemBug();

        ContentValues contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_LONG);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, 0);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, contetvalues);
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalStateException e ) {
            //fix crash
            //----exception stack trace----
            return false;
        }

        if ( result == null ){
            return false;
        }

        return true;
    }

    public static void setBooleanValue(String key, boolean value) {
        ContentValues	contetvalues = new ContentValues();

        contetvalues.put(EXTRA_TYPE, TYPE_BOOLEAN);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, value);

        FixProviderSystemBug();
        try {
            getCr().update(CONFIG_CONTENT_URI, contetvalues, null, null);
        } catch (IllegalStateException e ) {
            //fix crash
            //----exception stack trace----
            e.printStackTrace();
        }
    }

    public static void setLongValue(String key, long value) {
        ContentValues	contetvalues = new ContentValues();

        contetvalues.put(EXTRA_TYPE, TYPE_LONG);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, value);

        FixProviderSystemBug();
        try {
            getCr().update(CONFIG_CONTENT_URI, contetvalues, null, null);
        } catch (IllegalStateException e ) {
            //fix crash
            e.printStackTrace();
        }
    }

    public static void setIntValue(String key, int value) {
        ContentValues	contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_INT);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, value);

        FixProviderSystemBug();
        try {
            getCr().update(CONFIG_CONTENT_URI, contetvalues, null, null);
        } catch (IllegalStateException e ) {
            //fix crash
            e.printStackTrace();
        }
    }

    public static void setStringValue(String key, String value) {
        ContentValues	contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_STRING);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, value);

        FixProviderSystemBug();
        try {
            getCr().update(CONFIG_CONTENT_URI, contetvalues, null, null);
        } catch (IllegalStateException e ) {
            //fix crash
            e.printStackTrace();
        }
    }


    public static long getLongValue(String key, long defValue){
        FixProviderSystemBug();

        ContentValues	contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_LONG);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, defValue);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, contetvalues);
        } catch (IllegalArgumentException e) {
            //fix crash
            return defValue;
        } catch (IllegalStateException e ) {
            //fix crash
            return defValue;
        }

        if ( result == null ){
            return defValue;
        }else{
            String str = result.toString();
            if(TextUtils.isEmpty(str)){
                return defValue;
            }else if(str.length() <= LENGTH_CONTENT_URI){
                return defValue;
            }
        }

        return Long.valueOf(result.toString().substring(LENGTH_CONTENT_URI));
    }

    public static boolean getBooleanValue(String key, boolean defValue){
        FixProviderSystemBug();

        ContentValues	contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_BOOLEAN);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, defValue);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, contetvalues);
        } catch (IllegalArgumentException e) {
            //fix crash
            return defValue;
        }  catch (IllegalStateException e ) {
            //fix crash
            return defValue;
        }

        if ( result == null )
            return defValue;

        return Boolean.valueOf(result.toString().substring(LENGTH_CONTENT_URI));
    }

    public static int getIntValue(String key, int defValue){
        FixProviderSystemBug();

        ContentValues	contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_INT);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, defValue);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, contetvalues);
        } catch (IllegalArgumentException e) {
            //fix crash
            return defValue;
        } catch (IllegalStateException e ) {
            //fix crash
            return defValue;
        }

        if ( result == null )
            return defValue;

        return Integer.valueOf(result.toString().substring(LENGTH_CONTENT_URI));
    }
    public static String getStringValue(String key, String defValue){
        FixProviderSystemBug();

        ContentValues	contetvalues = new ContentValues();
        contetvalues.put(EXTRA_TYPE, TYPE_STRING);
        contetvalues.put(EXTRA_KEY, key);
        contetvalues.put(EXTRA_VALUE, defValue);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, contetvalues);
        } catch (IllegalArgumentException e) {
            //fix crash
            return defValue;
        } catch (IllegalStateException e ) {
            return defValue;
        }

        if ( result == null )
            return defValue;

        return String.valueOf(result.toString().substring(LENGTH_CONTENT_URI));
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String res = "";
        int nType = values.getAsInteger(EXTRA_TYPE);
        if (nType == TYPE_BOOLEAN) {
            res += ConfigSharePreference.getInstance().getBooleanValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsBoolean(EXTRA_VALUE));
        } else if (nType == TYPE_STRING) {
            res += ConfigSharePreference.getInstance().getStringValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsString(EXTRA_VALUE));
        } else if (nType == TYPE_INT) {
            res += ConfigSharePreference.getInstance().getIntValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsInteger(EXTRA_VALUE));
        } else if (nType == TYPE_LONG) {
            res +=ConfigSharePreference.getInstance().getLongValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsLong(EXTRA_VALUE));
        }

        return Uri.parse(CONFIG_CONTENT_URI.toString() + "/" + res);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int nType = values.getAsInteger(EXTRA_TYPE);
        if (nType == TYPE_BOOLEAN) {
            ConfigSharePreference.getInstance().setBooleanValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsBoolean(EXTRA_VALUE));
        } else if (nType == TYPE_STRING) {
            ConfigSharePreference.getInstance().setStringValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsString(EXTRA_VALUE));
        } else if (nType == TYPE_INT) {
            ConfigSharePreference.getInstance().setIntValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsInteger(EXTRA_VALUE));
        } else if (nType == TYPE_LONG) {
            ConfigSharePreference.getInstance().setLongValue(values.getAsString(EXTRA_KEY),
                    values.getAsLong(EXTRA_VALUE));
        }
        return 1;
    }
}
