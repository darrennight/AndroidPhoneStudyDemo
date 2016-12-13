package zenghao.com.study.DownLoadV2.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import zenghao.com.study.DownLoad.DownLoadInfo;
import zenghao.com.study.DownLoad.DownStatus;
import zenghao.com.study.DownLoadV2.DownLoadInfoV2;
import zenghao.com.study.DownLoadV2.DownLoadMangerV2;
import zenghao.com.study.DownLoadV2.DownStatusV2;
import zenghao.com.study.DownLoadV2.IDownLoadInfoV2;
import zenghao.com.study.DownLoadV2.db.DownLoadDBHelpV2;
import zenghao.com.study.DownLoadV2.provider.DownLoadProviderV2;

/**
 *
 * @author zenghao
 * @since 16/12/11 下午4:34
 */
public class DownLoadServiceV2 extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("=====","onStartCommandonStartCommandV2V2V2V2V2V2");
        startDown();
        return super.onStartCommand(intent, flags, startId);
    }

    private String saveDir = Environment.getExternalStorageDirectory() + "/AigeStudio/";
    //此service中存储到数据库中activity是可以查询到
    private void startDown(){
        for (int i=0;i<=1;i++){
            DownLoadInfoV2 info = new DownLoadInfoV2();
            info.setFilePath(saveDir);
            info.setFileName("test"+i+".apk");
            if(i % 2 == 0){
                info.setDownUrl("http://download.chinaunix.net/down.php?id=10608&ResourceID=5267&site=1");
            }else{
                info.setDownUrl("http://down.tech.sina.com.cn/download/d_load.php?d_id=49535&down_id=1&ip=42.81.45.159");

            }

            info.setStatus(DownStatusV2.WAIT);
            DownLoadMangerV2.getInstance(this).addInfo(info);
            //addTask(info);
        }
    }



    private void addTask(IDownLoadInfoV2 info){
        Uri uri = DownLoadProviderV2.TASK_CONTENT_URI;
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

        getContentResolver().insert(uri, values);
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
