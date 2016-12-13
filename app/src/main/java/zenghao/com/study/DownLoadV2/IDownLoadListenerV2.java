package zenghao.com.study.DownLoadV2;

/**
 *下载监听回调
 * @author zenghao
 * @since 16/12/2 下午9:10
 */
public interface IDownLoadListenerV2 {


    void onStart(IDownLoadInfoV2 info);

    void onProgress(IDownLoadInfoV2 info, int progress, int Total);

    void onPause(IDownLoadInfoV2 info);

    void onFinish(IDownLoadInfoV2 info);

    void onError(String error);
}
