package zenghao.com.study.DownLoadV2;

/**
 *具体http下载接口
 * @author zenghao
 * @since 16/12/4 下午5:33
 */
public abstract class IDownLoadTaskV2 implements Runnable {

    protected IDownLoadInfoV2 mInfo;
    public IDownLoadTaskV2(IDownLoadInfoV2 info){
        this.mInfo = info;
    }
    @Override
    public void run() {

    }
}
