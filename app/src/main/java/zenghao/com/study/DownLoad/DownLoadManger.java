package zenghao.com.study.DownLoad;

import android.content.Context;
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
import zenghao.com.study.DownLoad.db.DownLoadDBManger;

/**
 * 下载manager
 *
 * @author zenghao
 * @since 16/12/2 下午8:21
 */
public class DownLoadManger {

    private Context mContext;
    /**最大下载任务数*/
    private int MAX_TASK = 1;
    private static DownLoadManger mInstance;

    /** 下载中队列 */
    private static final ConcurrentHashMap<String, IDownLoadInfo> TASK_DLING =
            new ConcurrentHashMap<>();
    /** 准备中队列 */
    private static final List<IDownLoadInfo> TASK_WAIT =
            Collections.synchronizedList(new ArrayList<IDownLoadInfo>());
    /** 暂停中队列 */
    private static final ConcurrentHashMap<String, IDownLoadInfo> TASK_PAUSE =
            new ConcurrentHashMap<>();
    /** 失败队列 */
    private static final ConcurrentHashMap<String, IDownLoadInfo> TASK_FAILE =
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


    private DownLoadManger(Context context) {
        this.mContext = context;
    }

    public static synchronized DownLoadManger getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DownLoadManger(context);
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
    public void addInfo(IDownLoadInfo info) {
        //判断是否有网 是否有wifi info是否正确!=null
        //是否给info设置httpHeader

        if(TASK_DLING.size() >= MAX_TASK){
            //下载中的>=最大任务
            info.setStatus(DownStatus.WAIT);
            TASK_WAIT.add(info);
            DownLoadDBManger.getInstance(mContext).addDownInfo(info);
        }else{
            if(!TASK_DLING.containsKey(info.getFilePath())){
                info.setStatus(DownStatus.STARTING);
                TASK_DLING.put(info.getFilePath(), info);
                DownLoadDBManger.getInstance(mContext).addDownInfo(info);
                startDownLoad(info);
            }

        }


    }

    /***
     * 开始上传
     * @param info
     */
    private void startDownLoad(IDownLoadInfo info){

        //TODO 其实 下面几种状态回调都应该存储到数据库
        //TODO 下面几种状态存储应该写在DownLoadTask里面的回调

        //DownLoadDBManger.getInstance(mContext).addDownInfo(info);

        //执行下载
        POOL_TASK.execute(new DownLoadTask(info,new IDownLoadListener() {

            @Override
            public void onStart(IDownLoadInfo info) {
                Log.e("=====onStart","onStart"+info.getFileName());
            }

            @Override
            public void onProgress(IDownLoadInfo info,int progress,int Total) {
                Log.e("=====pro",""+progress);
                mCallBack.onCall(info);
                //TODO 实时存储进度到数据库太慢 可以不存 具体进度和start断点 依据下载本地文件
                DownLoadDBManger.getInstance(mContext).updateDownInfo(info);

            }

            @Override
            public void onPause(IDownLoadInfo info) {
                Log.e("=====onStop",info.getStart()+"onStop"+info.getFileName());
                info.setStatus(DownStatus.PAUSE);
                DownLoadDBManger.getInstance(mContext).updateDownInfo(info);
                startNext();
            }

            @Override
            public void onFinish(IDownLoadInfo info) {
                Log.e("=====onFinish",info.getTotalBytes()+"onFinishonFinish"+info.getStart());
                mCallBack.onCall(info);
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

    public void pauseDownLoad(IDownLoadInfo info){
                 TASK_DLING.remove(info.getFilePath()).setStatus(DownStatus.PAUSE);
    }

    /***
     *重新上传
     */
    public void reStart(IDownLoadInfo info){

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
    private void cancelDownLoad(IDownLoadInfo info){

    }

    /**
     * 删除下载
     * @param info
     */
    private void deleteDownLoad(IDownLoadInfo info){

    }

    /**
     * 下载下一个
     */
    private void startNext(){
        //TODO 判断一个任务情况防止 一直循环

        IDownLoadInfo info = null;
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
     */
    interface TestCallback{
        void onCall(IDownLoadInfo info);
    }


}
