package zenghao.com.study.DownLoadV2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import zenghao.com.study.DownLoadV2.DownLoadInfoV2;
import zenghao.com.study.DownLoadV2.DownStatusV2;
import zenghao.com.study.DownLoadV2.IDownLoadInfoV2;

/**
 * 数据manager
 *
 * @author zenghao
 * @since 16/12/8 下午7:10
 */
public class DownLoadDBMangerV2 {
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static DownLoadDBMangerV2 mInstance = null;
    private Context mContext;
    private DownLoadDBHelpV2 mDownDB;
    private SQLiteDatabase mDatabase;

    private DownLoadDBMangerV2(Context context) {
        this.mContext = context;
        mDownDB = new DownLoadDBHelpV2(context);
    }

    public static synchronized DownLoadDBMangerV2 getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DownLoadDBMangerV2(context);
        }
        return mInstance;
    }


    private synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mDownDB.getWritableDatabase();
        }
        return mDatabase;
    }

    private synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();

        }
    }

    /***
     * 添加断点现在info
     * @param info
     */
    public void addDownInfo(IDownLoadInfoV2 info) {
        //SQLiteDatabase sql = mDownDB.getWritableDatabase();
        SQLiteDatabase sql = openDatabase();

        ContentValues values = new ContentValues();
        values.put(DownLoadDBHelpV2.DL_FILE_NAME,info.getFileName());
        values.put(DownLoadDBHelpV2.DL_FILE_PATH,info.getFilePath());
        values.put(DownLoadDBHelpV2.DL_FILE_START,info.getStart());
        values.put(DownLoadDBHelpV2.DL_FILE_END,info.getEnd());
        values.put(DownLoadDBHelpV2.DL_FILE_PROGRESS,info.getProgress());
        values.put(DownLoadDBHelpV2.DL_FILE_DWONURL,info.getDownUrl());
        values.put(DownLoadDBHelpV2.DL_FILE_DISPOSITION,info.getDisposition());
        values.put(DownLoadDBHelpV2.DL_FILE_LOCATION,info.getLocation());
        values.put(DownLoadDBHelpV2.DL_FILE_MIMETYPE,info.getMimeType());
        values.put(DownLoadDBHelpV2.DL_FILE_TOTALBYTES,info.getTotalBytes());

        values.put(DownLoadDBHelpV2.DL_FILE_STATUS,changeStatus(info.getStatus()));

        long i = sql.insert(DownLoadDBHelpV2.TB_DOWN_INFO, null,values);
        Log.e("====",info.getFileName()+"=="+i);
    }

    public void addDownInfo(ContentValues values) {
        SQLiteDatabase sql = openDatabase();
        long i = sql.insert(DownLoadDBHelpV2.TB_DOWN_INFO, null,values);
        Log.e("====","=="+i);
    }

    /**
     * 更新不存在添加
     * @param info
     */
    public void updateDownInfo(IDownLoadInfoV2 info){
        //SQLiteDatabase sql = mDownDB.getWritableDatabase();
        SQLiteDatabase sql = openDatabase();

        ContentValues values = new ContentValues();
        values.put(DownLoadDBHelpV2.DL_FILE_NAME,info.getFileName());
        values.put(DownLoadDBHelpV2.DL_FILE_PATH,info.getFilePath());
        values.put(DownLoadDBHelpV2.DL_FILE_START,info.getStart());
        values.put(DownLoadDBHelpV2.DL_FILE_END,info.getEnd());
        values.put(DownLoadDBHelpV2.DL_FILE_PROGRESS,info.getProgress());
        values.put(DownLoadDBHelpV2.DL_FILE_DWONURL,info.getDownUrl());
        values.put(DownLoadDBHelpV2.DL_FILE_DISPOSITION,info.getDisposition());
        values.put(DownLoadDBHelpV2.DL_FILE_LOCATION,info.getLocation());
        values.put(DownLoadDBHelpV2.DL_FILE_MIMETYPE,info.getMimeType());
        values.put(DownLoadDBHelpV2.DL_FILE_TOTALBYTES,info.getTotalBytes());

        values.put(DownLoadDBHelpV2.DL_FILE_STATUS,changeStatus(info.getStatus()));
        //sql.replace(DownLoadDBHelp.TB_DOWN_INFO,null,values);//有问题一直insert添加多个数据
        sql.update(DownLoadDBHelpV2.TB_DOWN_INFO,values, DownLoadDBHelpV2.DL_FILE_NAME + " = ? ",new String[]{info.getFileName()});
    }

    public void updateDownInfo(ContentValues values,String where,String[] arg){
        //SQLiteDatabase sql = mDownDB.getWritableDatabase();
        SQLiteDatabase sql = openDatabase();
        sql.update(DownLoadDBHelpV2.TB_DOWN_INFO,values, where,arg);
    }

    /***
     * 获取info
     * @param status
     * @return
     */
    public List<IDownLoadInfoV2> getDownInfo(String status){
        List<IDownLoadInfoV2> list = new ArrayList<>();
        //SQLiteDatabase sql = mDownDB.getWritableDatabase();
        SQLiteDatabase sql = openDatabase();
        Cursor cursor = sql.query(DownLoadDBHelpV2.TB_DOWN_INFO,null
                        , DownLoadDBHelpV2.DL_FILE_STATUS+" = ? "
                        ,new String[]{status},null,null,null);
        if(cursor != null){
            while (cursor.moveToNext()){
                IDownLoadInfoV2 info = new DownLoadInfoV2();
                info.set_id(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
                info.setFileName(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_NAME)));
                info.setFilePath(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_PATH)));
                info.setStart(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_START)));
                info.setEnd(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_END)));
                info.setProgress(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_PROGRESS)));
                info.setDownUrl(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_DWONURL)));
                info.setDisposition(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_DISPOSITION)));
                info.setLocation(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_LOCATION)));
                info.setMimeType(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_MIMETYPE)));
                info.setTotalBytes(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_TOTALBYTES)));
                info.setStatus(changeStatus(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_STATUS))));
                list.add(info);
            }
        }

        return list;
    }

    public List<IDownLoadInfoV2> getDownInfo(){
        List<IDownLoadInfoV2> list = new ArrayList<>();
        //SQLiteDatabase sql = mDownDB.getWritableDatabase();
        SQLiteDatabase sql = openDatabase();
        Cursor cursor = sql.query(DownLoadDBHelpV2.TB_DOWN_INFO,null
                ,null
                ,null,null,null,null);
        if(cursor != null){
            while (cursor.moveToNext()){
                IDownLoadInfoV2 info = new DownLoadInfoV2();
                info.set_id(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
                info.setFileName(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_NAME)));
                info.setFilePath(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_PATH)));
                info.setStart(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_START)));
                info.setEnd(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_END)));
                info.setProgress(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_PROGRESS)));
                info.setDownUrl(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_DWONURL)));
                info.setDisposition(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_DISPOSITION)));
                info.setLocation(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_LOCATION)));
                info.setMimeType(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_MIMETYPE)));
                info.setTotalBytes(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_TOTALBYTES)));
                info.setStatus(changeStatus(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_STATUS))));
                list.add(info);
            }
        }

        return list;
    }

    public Cursor getDownCursor(){
        SQLiteDatabase sql = openDatabase();
        Cursor cursor = sql.query(DownLoadDBHelpV2.TB_DOWN_INFO,null
                ,null
                ,null,null,null,null);

        return cursor;
    }

    private int changeStatus(DownStatusV2 status){
        if(status == DownStatusV2.STARTING){
            return 0;
        }else if(status == DownStatusV2.PAUSE){
            return 1;
        }else if(status == DownStatusV2.WAIT){
            return 2;
        }else if(status == DownStatusV2.ERROR){
            return 3;
        }else if(status == DownStatusV2.FINISH){
            return 4;
        }
        return 4;
    }

    private DownStatusV2 changeStatus(int status){
        if(status == 0){
            return DownStatusV2.STARTING;
        }else if(status == 1){
            return DownStatusV2.PAUSE;
        }else if(status == 2){
            return DownStatusV2.WAIT;
        }else if(status == 3){
            return DownStatusV2.ERROR;
        }else if(status == 4){
            return DownStatusV2.FINISH;
        }
        return DownStatusV2.WAIT;
    }
}
