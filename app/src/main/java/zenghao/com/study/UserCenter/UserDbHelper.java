package zenghao.com.study.UserCenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zenghao on 16/5/20.
 */
public class UserDbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDatabase;

    private UserDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static UserDbHelper mInstance;
    public static synchronized UserDbHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new UserDbHelper(context);
        }
        return mInstance;
    }


    public synchronized SQLiteDatabase openDatabase() {
        if(mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mInstance.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if(mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();

        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserUnit.createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
