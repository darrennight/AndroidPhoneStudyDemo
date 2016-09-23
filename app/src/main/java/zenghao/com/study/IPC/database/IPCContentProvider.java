package zenghao.com.study.IPC.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/9/19 下午6:11
 * 跨进程底层数据库存储数据
 *https://github.com/SpikeKing/ContentProviderDemo
 * https://github.com/grandcentrix/tray
 * https://github.com/seven456/MultiprocessSharedPreferences
 * http://zanelove.github.io/2016/01/06/IPC%E6%9C%BA%E5%88%B6-Android-IPC%E7%AE%80%E4%BB%8B/
 *
 * 跨进程 ContentProvider Broadcast handlerMessage socket
 *
 * ***** ContentProvider 需要在清单文件注册
 */
public class IPCContentProvider extends ContentProvider {

    private static final String TAG = "DEBUG-WCL: " + IPCContentProvider.class.getSimpleName();

    public static final String AUTHORITY = "org.wangchenlong.book.provider"; // 与AndroidManifest保持一致
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
    public static final Uri CALL_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/call");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // 关联Uri和Uri_Code
    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;
    @Override
    public boolean onCreate() {

        mContext = getContext();
        initProviderData(); // 初始化Provider数据

        return false;
    }


    private void initProviderData() {
        mDb = new MyDataBase(mContext).getWritableDatabase();
        /*mDb.execSQL("delete from " + MyDataBase.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + MyDataBase.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'Android');");
        mDb.execSQL("insert into book values(4, 'iOS');");
        mDb.execSQL("insert into book values(5, 'HTML5');");
        mDb.execSQL("insert into user values(1, 'Spike', 1);");
        mDb.execSQL("insert into user values(2, 'Wang', 0);");*/
    }

    @Nullable @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        showLogs("query 当前线程: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable @Override public String getType(Uri uri) {
        showLogs("getType");
        return null;
    }

    @Nullable @Override public Uri insert(Uri uri, ContentValues values) {
        showLogs("insert");
        String table = getTableName(uri);
        if (TextUtils.isEmpty(table)) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(table, null, values);

        // 插入数据后通知改变
        mContext.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
        showLogs("delete");

        String table = getTableName(uri);
        if (TextUtils.isEmpty(table)) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(table, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }

        return count; // 返回删除的函数
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        showLogs("update");

        String table = getTableName(uri);
        if (TextUtils.isEmpty(table)) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int row = mDb.update(table, values, selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }

        return row; // 返回更新的行数
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = MyDataBase.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = MyDataBase.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    //call 方法可以拓展操作  处理其他业务逻辑 试试通信 多进程service可以同步调用用
    //耗时需要再子线程处理
    @Nullable
    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        Log.e("======","callcalllcalll");
        Looper.prepare();
        Toast.makeText(getContext(),"callcallcall",Toast.LENGTH_SHORT).show();
        Looper.loop();
        return super.call(method, arg, extras);
    }

    private void showLogs(String msg) {
        Log.e(TAG, msg);
    }
}
