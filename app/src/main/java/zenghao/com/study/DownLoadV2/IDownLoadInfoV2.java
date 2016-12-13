package zenghao.com.study.DownLoadV2;

import java.io.File;

/**
 * 下载相关信息基类
 *
 * @author zenghao
 * @since 16/12/2 下午8:24
 */
public abstract class IDownLoadInfoV2 {
    /**数据库id*/
    private int _id;

    /**文件名*/
    private String fileName;
    /**本地存储路径*/
    private String filePath;
    /**下载文件--本地*/
    private File file;
    /**断点其实*/
    private int start;
    /**断点结尾*/
    private int end;
    /**下载进度*/
    private int progress;
    /**下载请求url*/
    private String downUrl;
    /**解析服务器header获取文件名字使用*/
    private String disposition;
    /**解析服务器header获取文件名字使用*/
    private String location;
    private String mimeType;
    /**文件总大小*/
    private int totalBytes;
    /**下载状态*/
    private DownStatusV2 status;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public DownStatusV2 getStatus() {
        return status;
    }

    public void setStatus(DownStatusV2 status) {
        this.status = status;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(int totalBytes) {
        this.totalBytes = totalBytes;
    }
}
