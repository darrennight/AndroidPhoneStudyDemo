package zenghao.com.study.DBUtils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多现场并发数据库处理
 */
public class DatabaseManager {

    private AtomicInteger mOpenCounter = new AtomicInteger();

    private static DatabaseManager instance;
    private SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    private DatabaseManager(SQLiteOpenHelper helper) {
        mDatabaseHelper = helper;
    }

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager(helper);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    private synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        Log.d("","Database open counter: " + mOpenCounter.get());
        return mDatabase;
    }

    private synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();

        }
        Log.d("","Database open counter: " + mOpenCounter.get());
    }

}