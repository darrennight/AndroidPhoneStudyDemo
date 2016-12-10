package zenghao.com.study.DownLoad;

/**
 *具体http下载接口
 * @author zenghao
 * @since 16/12/4 下午5:33
 */
public abstract class IDownLoadTask implements Runnable {

    protected IDownLoadInfo mInfo;
    public IDownLoadTask(IDownLoadInfo info){
        this.mInfo = info;
    }
    @Override
    public void run() {

    }
}
