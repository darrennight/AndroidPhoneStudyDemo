package zenghao.com.study.DownLoad;

import java.io.File;

/**
 *下载监听回调
 * @author zenghao
 * @since 16/12/2 下午9:10
 */
public interface IDownLoadListener {


    void onStart(IDownLoadInfo info);

    void onProgress(IDownLoadInfo info,int progress,int Total);

    void onPause(IDownLoadInfo info);

    void onFinish(IDownLoadInfo info);

    void onError(String error);
}
