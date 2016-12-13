package zenghao.com.study.DownLoadV2;

/**
 *http下载
 * @author zenghao
 * @since 16/12/4 下午5:59
 */
public interface IHttpClientV2 {
    void downLoad(IDownLoadInfoV2 info);
    void setListener(IDownLoadListenerV2 listener);
}
