package zenghao.com.study.DownLoadV2;

/**
 * TODO 尝试拆分IDownLoadInfo
 *
 * @author zenghao
 * @since 16/12/5 下午8:07
 */
public class DownLoadInfoV2 extends IDownLoadInfoV2 {



    @Override
    public boolean equals(Object obj) {
        if(obj!=null){
            if(obj instanceof IDownLoadInfoV2){
                IDownLoadInfoV2 info = ((IDownLoadInfoV2) obj);
                if(this.getFileName().equalsIgnoreCase(info.getFileName())){
                    return true;
                }
            }
        }
        return false;
    }
}
