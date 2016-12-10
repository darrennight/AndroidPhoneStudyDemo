package zenghao.com.study.DownLoad;

import android.text.TextUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import zenghao.com.study.util.CommonUtil;

/**
 *下载使用任务
 * @author zenghao
 * @since 16/12/2 下午8:58
 */
public class DownLoadTask extends IDownLoadTask{

    private IDownLoadListener mListener;
    private static final int DEFAULT_TIMEOUT = 20000;
    public DownLoadTask(IDownLoadInfo info,IDownLoadListener listener){
        super(info);
        this.mListener = listener;
    }

    @Override
    public void run() {
        int MAX_REDIRECTS = 5;
        int redirect = 0;
        while (redirect < MAX_REDIRECTS){
            //第一步http请求需要先请求下载的接口获取下载文件的相关信息
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(mInfo.getDownUrl()).openConnection();
                /**重定向 是否连接遵循重定向*/
                conn.setInstanceFollowRedirects(false);
                conn.setConnectTimeout(DEFAULT_TIMEOUT);
                conn.setReadTimeout(DEFAULT_TIMEOUT);
                addRequestHeaders(conn);
                int code = conn.getResponseCode();
                //第一次是302需要处理重定向
                if(code == 200 || code == 206){
                    //第二步通过封装的信息进行下载
                    DownLoadInit(conn);
                    if(createFile(mInfo.getFilePath(),mInfo.getFileName())){
                        mInfo.setFile(new File(mInfo.getFilePath(), mInfo.getFileName()));
                        if (mInfo.getFile().exists() && mInfo.getFile().length() == mInfo.getTotalBytes()) {
                            //一个已经下载完成的
                            return;
                        }
                        if(code == 200){
                            //不支持断点
                            mInfo.setStatus(DownStatus.STARTING);
                            directDown(conn);
                        } else{
                            //支持断点
                            breakPointDown();
                        }
                    }
                    return;
                }else if(code == 301 || code == 302 ||code == 303 ||code == 304 || code == 307){
                    String location = conn.getHeaderField("location");
                    mInfo.setDownUrl(location);
                    ++redirect;
                    continue;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                    if(conn!=null){
                        conn.disconnect();
                    }
            }
        }

    }

    /**
     * 初始化下载信息
     * @param conn
     */
    private void DownLoadInit(HttpURLConnection conn){
        mInfo.setDisposition(conn.getHeaderField("Content-Disposition"));
        mInfo.setLocation(conn.getHeaderField("Content-Location"));
        mInfo.setMimeType(CommonUtil.normalizeMimeType(conn.getContentType()));
        final String transferEncoding = conn.getHeaderField("Transfer-Encoding");
        if (TextUtils.isEmpty(transferEncoding)) {
            try {
                mInfo.setTotalBytes(Integer.parseInt(conn.getHeaderField("Content-Length")));
            } catch (NumberFormatException e) {
                mInfo.setTotalBytes(-1);
            }
        } else {
            mInfo.setTotalBytes(-1);
        }
        if (mInfo.getTotalBytes() == -1 && (TextUtils.isEmpty(transferEncoding) ||
                !transferEncoding.equalsIgnoreCase("chunked")))
            throw new RuntimeException("Can not obtain size of download file.");
        if (TextUtils.isEmpty(mInfo.getFileName()))
            mInfo.setFileName(HttpUtil.obtainFileName(mInfo.getDownUrl(), mInfo.getDisposition(), mInfo.getLocation()));

    }


    /***
     * 不支持断点的直接下载
     * @param conn
     * @throws IOException
     */
    private void directDown(HttpURLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        FileOutputStream fos = new FileOutputStream(mInfo.getFile());
        byte[] b = new byte[4096];
        int len;
        mInfo.setProgress(0);
        while (mInfo.getStatus() == DownStatus.STARTING && (len = is.read(b)) != -1) {
            fos.write(b, 0, len);
            mInfo.setProgress(mInfo.getProgress()+len);
            mListener.onProgress(mInfo,mInfo.getProgress(),mInfo.getTotalBytes());
        }
        if (DownStatus.PAUSE == mInfo.getStatus()) {
            mListener.onPause(mInfo);
        } else {
            //回调监听下载完成
            mListener.onFinish(mInfo);
        }
        fos.close();
        is.close();
    }

    /***
     * 断点下载
     */
    private void breakPointDown(){
        //如果是一个线程下载一个文件 在此计算 start end

        /**一个线程下载一个任务*/
        mInfo.setStart(mInfo.getStart());
        mInfo.setEnd(mInfo.getTotalBytes());
        IHttpClient client = new MyHttpClient();
        client.setListener(mListener);
        mInfo.setStatus(DownStatus.STARTING);
        client.downLoad(mInfo);
    }

    /***
     * 添加头信息
     * @param conn
     */
    private void addRequestHeaders(HttpURLConnection conn) {
        for (DLHeader header : HttpUtil.initRequestHeaders(new ArrayList<DLHeader>(),mInfo)) {
            conn.addRequestProperty(header.key, header.value);
        }
    }

    /**
     * 创建下载文件
     * @param path
     * @param fileName
     * @return
     */
    private boolean createFile(String path, String fileName) {
        boolean hasFile = false;
        try {
            File dir = new File(path);
            boolean hasDir = dir.exists() || dir.mkdirs();
            if (hasDir) {
                File file = new File(dir, fileName);
                hasFile = file.exists() || file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hasFile;
    }
}
