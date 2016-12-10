package zenghao.com.study.DownLoad.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 *
 * @author zenghao
 * @since 16/12/7 下午6:07
 */
public class DownLoadDBHelp extends SQLiteOpenHelper {
    private static final String DB_NAME = "myDownLoad.db";
    private static final int DB_VERSION = 1;

    public static final String TB_DOWN_INFO = "downInfo";

    public static final String DL_FILE_NAME = "fileName";
    public static final String DL_FILE_PATH = "filePath";
    public static final String DL_FILE_START = "fileStart";
    public static final String DL_FILE_END = "fileEnd";
    public static final String DL_FILE_PROGRESS = "fileProgress";
    public static final String DL_FILE_DWONURL = "fileDownUrl";
    public static final String DL_FILE_DISPOSITION = "fileDisposition";
    public static final String DL_FILE_LOCATION = "fileLocation";
    public static final String DL_FILE_MIMETYPE = "fileMimeType";
    public static final String DL_FILE_TOTALBYTES = "fileTotalBytes";
    public static final String DL_FILE_STATUS = "fileStatus";



    DownLoadDBHelp(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    private String getTableSql(){
        String sql = "CREATE TABLE " + TB_DOWN_INFO + "(" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DL_FILE_NAME + " CHAR, " +
                DL_FILE_PATH + " CHAR, " +
                DL_FILE_START + " INTEGER, " +
                DL_FILE_END + " INTEGER, " +
                DL_FILE_PROGRESS + " INTEGER, " +
                DL_FILE_DWONURL + " CHAR, " +
                DL_FILE_DISPOSITION + " CHAR, " +
                DL_FILE_LOCATION + " CHAR, " +
                DL_FILE_MIMETYPE + " CHAR, " +
                DL_FILE_TOTALBYTES + " INTEGER, " +
                DL_FILE_STATUS + " INTEGER)";

        return sql;
    }

}
