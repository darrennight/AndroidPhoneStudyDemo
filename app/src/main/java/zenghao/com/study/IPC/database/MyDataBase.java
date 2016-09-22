package zenghao.com.study.IPC.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/9/19 下午6:57
 */
public class MyDataBase extends SQLiteOpenHelper {

    private static final String name = "ipcdata_provide.db";
    private static final int version = 1;

    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";

    public MyDataBase(Context context){
        super(context,name,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "
            + BOOK_TABLE_NAME + "(_id INTEGER PRIMARY KEY, name TEXT)";

    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
            + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY, name TEXT, sex INT)";
}
