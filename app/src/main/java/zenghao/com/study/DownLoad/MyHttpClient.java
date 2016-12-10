package zenghao.com.study.DownLoad;

import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 *http下载
 *
 *
 * @author zenghao
 * @since 16/12/4 下午6:00
 */
public class MyHttpClient implements IHttpClient {
    private static final String TAG = MyHttpClient.class.getSimpleName();
    private IDownLoadListener mListener;
    private static final int DEFAULT_TIMEOUT = 20000;
    public MyHttpClient(){

    }

    @Override
    public void downLoad(IDownLoadInfo info) {

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        InputStream is = null;
        try {
            conn = (HttpURLConnection) new URL(info.getDownUrl()).openConnection();
            conn.setConnectTimeout(DEFAULT_TIMEOUT);
            conn.setReadTimeout(DEFAULT_TIMEOUT);

            addRequestHeaders(conn,info);

            raf = new RandomAccessFile(info.getFile(), "rwd");
            raf.seek(info.getStart());

            is = conn.getInputStream();

            byte[] b = new byte[4096];
            int len;
            while (info.getStatus() == DownStatus.STARTING && (len = is.read(b)) != -1) {
                info.setStart(info.getStart()+len);
                raf.write(b, 0, len);
                info.setProgress((info.getStart()*100)/info.getTotalBytes());
                mListener.onProgress(info,info.getStart(),info.getTotalBytes());
            }
            if (DownStatus.PAUSE == info.getStatus()) {
                mListener.onPause(info);
            } else {
                //回调监听下载完成
                info.setStatus(DownStatus.FINISH);
                mListener.onFinish(info);
            }
        } catch (IOException e) {
            mListener.onError(e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (null != is) is.close();
                if (null != raf) raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (null != conn) conn.disconnect();
        }
    }
    @Override
    public void setListener(IDownLoadListener listener) {
        mListener = listener;
    }

    /***
     * 添加头信息
     * @param conn
     */
    private void addRequestHeaders(HttpURLConnection conn , IDownLoadInfo info) {
        for (DLHeader header : HttpUtil.initRequestHeaders(new ArrayList<DLHeader>(),null)) {
            conn.addRequestProperty(header.key, header.value);
        }
        conn.setRequestProperty("Range", "bytes=" + info.getStart() + "-" + info.getEnd());
    }



}
