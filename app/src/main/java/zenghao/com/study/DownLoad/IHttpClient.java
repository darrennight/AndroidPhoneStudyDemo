package zenghao.com.study.DownLoad;

/**
 *http下载
 * @author zenghao
 * @since 16/12/4 下午5:59
 */
public interface IHttpClient {
    void downLoad(IDownLoadInfo info);
    void setListener(IDownLoadListener listener);
}
