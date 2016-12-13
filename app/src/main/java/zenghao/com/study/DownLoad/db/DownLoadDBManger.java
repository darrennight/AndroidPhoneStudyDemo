package zenghao.com.study.DownLoad.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import zenghao.com.study.DownLoad.DownLoadInfo;
import zenghao.com.study.DownLoad.DownStatus;
import zenghao.com.study.DownLoad.IDownLoadInfo;

/**
 * 数据manager
 *
 * @author zenghao
 * @since 16/12/8 下午7:10
 */
public class DownLoadDBManger {
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static DownLoadDBManger mInstance = null;
    private Context mContext;
    private DownLoadDBHelp mDownDB;
    private SQLiteDatabase mDatabase;

    private DownLoadDBManger(Context context) {
        this.mContext = context;
        mDownDB = new DownLoadDBHelp(context);
    }

    public static synchronized DownLoadDBManger getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DownLoadDBManger(context);
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
    public void addDownInfo(IDownLoadInfo info) {
        //SQLiteDatabase sql = mDownDB.getWritableDatabase();
        SQLiteDatabase sql = openDatabase();

        ContentValues values = new ContentValues();
        values.put(DownLoadDBHelp.DL_FILE_NAME,info.getFileName());
        values.put(DownLoadDBHelp.DL_FILE_PATH,info.getFilePath());
        values.put(DownLoadDBHelp.DL_FILE_START,info.getStart());
        values.put(DownLoadDBHelp.DL_FILE_END,info.getEnd());
        values.put(DownLoadDBHelp.DL_FILE_PROGRESS,info.getProgress());
        values.put(DownLoadDBHelp.DL_FILE_DWONURL,info.getDownUrl());
        values.put(DownLoadDBHelp.DL_FILE_DISPOSITION,info.getDisposition());
        values.put(DownLoadDBHelp.DL_FILE_LOCATION,info.getLocation());
        values.put(DownLoadDBHelp.DL_FILE_MIMETYPE,info.getMimeType());
        values.put(DownLoadDBHelp.DL_FILE_TOTALBYTES,info.getTotalBytes());

        values.put(DownLoadDBHelp.DL_FILE_STATUS,changeStatus(info.getStatus()));

        long i = sql.insert(DownLoadDBHelp.TB_DOWN_INFO, null,values);
        Log.e("====",info.getFileName()+"=="+i);
    }

    /**
     * 更新不存在添加
     * @param info
     */
    public void updateDownInfo(IDownLoadInfo info){
        //SQLiteDatabase sql = mDownDB.getWritableDatabase();
        SQLiteDatabase sql = openDatabase();

        ContentValues values = new ContentValues();
        values.put(DownLoadDBHelp.DL_FILE_NAME,info.getFileName());
        values.put(DownLoadDBHelp.DL_FILE_PATH,info.getFilePath());
        values.put(DownLoadDBHelp.DL_FILE_START,info.getStart());
        values.put(DownLoadDBHelp.DL_FILE_END,info.getEnd());
        values.put(DownLoadDBHelp.DL_FILE_PROGRESS,info.getProgress());
        values.put(DownLoadDBHelp.DL_FILE_DWONURL,info.getDownUrl());
        values.put(DownLoadDBHelp.DL_FILE_DISPOSITION,info.getDisposition());
        values.put(DownLoadDBHelp.DL_FILE_LOCATION,info.getLocation());
        values.put(DownLoadDBHelp.DL_FILE_MIMETYPE,info.getMimeType());
        values.put(DownLoadDBHelp.DL_FILE_TOTALBYTES,info.getTotalBytes());

        values.put(DownLoadDBHelp.DL_FILE_STATUS,changeStatus(info.getStatus()));
        //sql.replace(DownLoadDBHelp.TB_DOWN_INFO,null,values);//有问题一直insert添加多个数据
        sql.update(DownLoadDBHelp.TB_DOWN_INFO,values,DownLoadDBHelp.DL_FILE_NAME + " = ? ",new String[]{info.getFileName()});
    }

    /***
     * 获取info
     * @param status
     * @return
     */
    public List<IDownLoadInfo> getDownInfo(String status){
        List<IDownLoadInfo> list = new ArrayList<>();
        //SQLiteDatabase sql = mDownDB.getWritableDatabase();
        SQLiteDatabase sql = openDatabase();
        Cursor cursor = sql.query(DownLoadDBHelp.TB_DOWN_INFO,null
                        ,DownLoadDBHelp.DL_FILE_STATUS+" = ? "
                        ,new String[]{status},null,null,null);
        if(cursor != null){
            while (cursor.moveToNext()){
                IDownLoadInfo info = new DownLoadInfo();
                info.set_id(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
                info.setFileName(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_NAME)));
                info.setFilePath(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_PATH)));
                info.setStart(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_START)));
                info.setEnd(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_END)));
                info.setProgress(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_PROGRESS)));
                info.setDownUrl(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_DWONURL)));
                info.setDisposition(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_DISPOSITION)));
                info.setLocation(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_LOCATION)));
                info.setMimeType(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_MIMETYPE)));
                info.setTotalBytes(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_TOTALBYTES)));
                info.setStatus(changeStatus(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_STATUS))));
                list.add(info);
            }
        }

        return list;
    }

    public List<IDownLoadInfo> getDownInfo(){
        List<IDownLoadInfo> list = new ArrayList<>();
        //SQLiteDatabase sql = mDownDB.getWritableDatabase();
        SQLiteDatabase sql = openDatabase();
        Cursor cursor = sql.query(DownLoadDBHelp.TB_DOWN_INFO,null
                ,null
                ,null,null,null,null);
        if(cursor != null){
            while (cursor.moveToNext()){
                IDownLoadInfo info = new DownLoadInfo();
                info.set_id(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
                info.setFileName(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_NAME)));
                info.setFilePath(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_PATH)));
                info.setStart(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_START)));
                info.setEnd(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_END)));
                info.setProgress(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_PROGRESS)));
                info.setDownUrl(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_DWONURL)));
                info.setDisposition(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_DISPOSITION)));
                info.setLocation(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_LOCATION)));
                info.setMimeType(cursor.getString(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_MIMETYPE)));
                info.setTotalBytes(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_TOTALBYTES)));
                info.setStatus(changeStatus(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelp.DL_FILE_STATUS))));
                list.add(info);
            }
        }

        return list;
    }

    private int changeStatus(DownStatus status){
        if(status == DownStatus.STARTING){
            return 0;
        }else if(status == DownStatus.PAUSE){
            return 1;
        }else if(status == DownStatus.WAIT){
            return 2;
        }else if(status == DownStatus.ERROR){
            return 3;
        }else if(status == DownStatus.FINISH){
            return 4;
        }
        return 4;
    }

    private DownStatus changeStatus(int status){
        if(status == 0){
            return DownStatus.STARTING;
        }else if(status == 1){
            return DownStatus.PAUSE;
        }else if(status == 2){
            return DownStatus.WAIT;
        }else if(status == 3){
            return DownStatus.ERROR;
        }else if(status == 4){
            return DownStatus.FINISH;
        }
        return DownStatus.WAIT;
    }
}
