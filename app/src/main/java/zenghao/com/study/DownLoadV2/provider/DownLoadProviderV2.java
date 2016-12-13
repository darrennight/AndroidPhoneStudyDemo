package zenghao.com.study.DownLoadV2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import zenghao.com.study.DownLoadV2.db.CallArgs;
import zenghao.com.study.DownLoadV2.db.DownLoadDBMangerV2;

/**
 *
 * @author zenghao
 * @since 16/12/11 下午4:15
 */
public class DownLoadProviderV2 extends ContentProvider {

    public static final String AUTHORITY = "com.zeng.down.v2";
    public static final Uri TASK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/task");
    public static final Uri TASK_UPDATE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/update");
    public static final int TASK_URI_CODE = 0;
    public static final int TASK_UPDATE_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // 关联Uri和Uri_Code
    static {
        sUriMatcher.addURI(AUTHORITY, "task", TASK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "update", TASK_UPDATE_URI_CODE);
    }
    private Context mContext;
    private DownLoadDBMangerV2 mangerV2;
    @Override
    public boolean onCreate() {
        mContext = getContext();
        mangerV2 = DownLoadDBMangerV2.getInstance(mContext);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        //后期需要根据uri 和其他参数来查询
        return mangerV2.getDownCursor();
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        mangerV2.addDownInfo(values);
        mContext.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        mangerV2.updateDownInfo(values,selection,selectionArgs);
        mContext.getContentResolver().notifyChange(uri, null);

        return 0;
    }

    @Nullable
    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (extras == null) {
            return null;
        }
        return extras;
    }
}
