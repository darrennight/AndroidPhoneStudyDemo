package zenghao.com.study.IPC.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/9/19 下午6:11
 * 跨进程底层数据库存储数据
 *https://github.com/SpikeKing/ContentProviderDemo
 * https://github.com/grandcentrix/tray
 * https://github.com/seven456/MultiprocessSharedPreferences
 *
 * 跨进程 ContentProvider Broadcast handler socket
 */
public class IPCContentProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
