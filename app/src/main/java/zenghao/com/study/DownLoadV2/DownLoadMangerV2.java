package zenghao.com.study.DownLoadV2;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import zenghao.com.study.DownLoadV2.db.DownLoadDBHelpV2;
import zenghao.com.study.DownLoadV2.db.DownLoadDBMangerV2;
import zenghao.com.study.DownLoadV2.provider.DownLoadProviderV2;

/**
 * 下载manager
 *
 * @author zenghao
 * @since 16/12/2 下午8:21
 */
public class DownLoadMangerV2 {

    private Context mContext;
    /**最大下载任务数*/
    private int MAX_TASK = 1;
    private static DownLoadMangerV2 mInstance;

    /** 下载中队列 */
    private static final ConcurrentHashMap<String, IDownLoadInfoV2> TASK_DLING =
            new ConcurrentHashMap<>();
    /** 准备中队列 */
    private static final List<IDownLoadInfoV2> TASK_WAIT =
            Collections.synchronizedList(new ArrayList<IDownLoadInfoV2>());
    /** 暂停中队列 */
    private static final ConcurrentHashMap<String, IDownLoadInfoV2> TASK_PAUSE =
            new ConcurrentHashMap<>();
    /** 失败队列 */
    private static final ConcurrentHashMap<String, IDownLoadInfoV2> TASK_FAILE =
            new ConcurrentHashMap<>();

    private static final int CORES = Runtime.getRuntime().availableProcessors();
    private static final int POOL_SIZE = CORES + 1;
    private static final int POOL_SIZE_MAX = CORES * 2 + 1;
    private static final BlockingQueue<Runnable> POOL_QUEUE_TASK = new LinkedBlockingQueue<>(56);

    private static final ThreadFactory TASK_FACTORY = new ThreadFactory() {
        private final AtomicInteger COUNT = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "DLTask==" + COUNT.getAndIncrement());
        }
    };

    private static final ExecutorService POOL_TASK = new ThreadPoolExecutor(POOL_SIZE,
            POOL_SIZE_MAX, 3, TimeUnit.SECONDS, POOL_QUEUE_TASK, TASK_FACTORY);


    private DownLoadMangerV2(Context context) {
        this.mContext = context;
    }

    public static synchronized DownLoadMangerV2 getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DownLoadMangerV2(context);
        }
        return mInstance;
    }

    public int getMaxTask() {
        return MAX_TASK;
    }

    /**
     * 设置同时最大上传任务
     * @param max
     */
    public void setMaxTask(int max) {
        this.MAX_TASK = max;
    }

    /***
     * 添加上传数据
     * @param info
     */
    public void addInfo(IDownLoadInfoV2 info) {
        //判断是否有网 是否有wifi info是否正确!=null
        //是否给info设置httpHeader

        if(TASK_DLING.size() >= MAX_TASK){
            //下载中的>=最大任务
            info.setStatus(DownStatusV2.WAIT);
            TASK_WAIT.add(info);
            //DownLoadDBMangerV2.getInstance(mContext).addDownInfo(info);
            addTask(info);
        }else{
            if(!TASK_DLING.containsKey(info.getFilePath())){
                info.setStatus(DownStatusV2.STARTING);
                TASK_DLING.put(info.getFilePath(), info);
                //DownLoadDBMangerV2.getInstance(mContext).addDownInfo(info);
                addTask(info);
                startDownLoad(info);
            }

        }


    }

    /***
     * 开始上传
     * @param info
     */
    private void startDownLoad(IDownLoadInfoV2 info){

        //TODO 其实 下面几种状态回调都应该存储到数据库
        //TODO 下面几种状态存储应该写在DownLoadTask里面的回调

        //DownLoadDBManger.getInstance(mContext).addDownInfo(info);

        //执行下载
        POOL_TASK.execute(new DownLoadTaskV2(info,new IDownLoadListenerV2() {

            @Override
            public void onStart(IDownLoadInfoV2 info) {
                Log.e("=====onStart","onStart"+info.getFileName());
            }

            @Override
            public void onProgress(IDownLoadInfoV2 info,int progress,int Total) {
                Log.e("=====pro",""+progress);
                if(mCallBack!=null){
                    mCallBack.onCall(info);
                }

                //TODO 实时存储进度到数据库太慢 可以不存 具体进度和start断点 依据下载本地文件
                //DownLoadDBMangerV2.getInstance(mContext).updateDownInfo(info);
                updateTask(info);
            }

            @Override
            public void onPause(IDownLoadInfoV2 info) {
                Log.e("=====onStop",info.getStart()+"onStop"+info.getFileName());
                info.setStatus(DownStatusV2.PAUSE);
                //DownLoadDBMangerV2.getInstance(mContext).updateDownInfo(info);
                updateTask(info);
                startNext();
            }

            @Override
            public void onFinish(IDownLoadInfoV2 info) {
                Log.e("=====onFinish",info.getTotalBytes()+"onFinishonFinish"+info.getStart());
                if(mCallBack!=null){
                    mCallBack.onCall(info);
                }
                TASK_DLING.remove(info.getFilePath());
                startNext();
            }

            @Override
            public void onError(String error) {
                Log.e("=====onError","onError"+error);
                startNext();
            }
        }));
    }

    public void pauseDownLoad(IDownLoadInfoV2 info){
                 TASK_DLING.remove(info.getFilePath()).setStatus(DownStatusV2.PAUSE);
    }

    /***
     *重新上传
     */
    public void reStart(IDownLoadInfoV2 info){

        if(TASK_DLING.size() < MAX_TASK){
            if(!TASK_DLING.contains(info.getFilePath())){
                TASK_DLING.put(info.getFilePath(),info);
            }
            startDownLoad(info);
        }


    }

    /**
     * 取消下载
     * @param info
     */
    private void cancelDownLoad(IDownLoadInfoV2 info){

    }

    /**
     * 删除下载
     * @param info
     */
    private void deleteDownLoad(IDownLoadInfoV2 info){

    }

    /**
     * 下载下一个
     */
    private void startNext(){
        //TODO 判断一个任务情况防止 一直循环

        IDownLoadInfoV2 info = null;
        if(!TASK_WAIT.isEmpty()){
            info = TASK_WAIT.remove(0);

        }else if(!TASK_PAUSE.isEmpty()){
            info = TASK_PAUSE.remove(0);

        }else if(!TASK_FAILE.isEmpty()){
            info = TASK_FAILE.remove(0);
        }
        if(info!=null){
            startDownLoad(info);
        }

    }

    private TestCallback mCallBack;
    public void setCallBack(TestCallback call){
        this.mCallBack = call;
    }
    /***
     * 临时使用进度回调
     * 多进程这种方式不能回调
     */
    interface TestCallback{
        void onCall(IDownLoadInfoV2 info);
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

        mContext.getContentResolver().insert(uri, values);
    }

    /**
     * TODO 更新只更新相关字段就像 不需要全部更新
     * @param info
     */
    private void updateTask(IDownLoadInfoV2 info){
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

        mContext.getContentResolver().update(uri, values,DownLoadDBHelpV2.DL_FILE_NAME + " = ? ",new String[]{info.getFileName()});
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
}
