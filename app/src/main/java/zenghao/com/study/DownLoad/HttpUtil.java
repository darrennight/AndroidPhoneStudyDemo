package zenghao.com.study.DownLoad;

import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/12/6 下午7:56
 */
public class HttpUtil {

    /**
     * 初始化header
     * @param headers
     * @param info
     * @return
     */
    public static List<DLHeader> initRequestHeaders(List<DLHeader> headers, IDownLoadInfo info) {
        if (null == headers || headers.isEmpty()) {
            headers = new ArrayList<>();
            headers.add(new DLHeader("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg," +
                    "application/x-shockwave-flash, application/xaml+xml," +
                    "application/vnd.ms-xpsdocument, application/x-ms-xbap," +
                    "application/x-ms-application, application/vnd.ms-excel," +
                    "application/vnd.ms-powerpoint, application/msword, */*"));
            headers.add(new DLHeader("Accept-Ranges", "bytes"));
            headers.add(new DLHeader("Charset", "UTF-8"));
            headers.add(new DLHeader("Connection", "Keep-Alive"));
            headers.add(new DLHeader("Accept-Encoding", "identity"));
            headers.add(new DLHeader("Range", "bytes=" + 0 + "-"));
        }
        if (!hasRequestHeader("User-Agent", headers)) {
            headers.add(new DLHeader("User-Agent", String.format("%s/%s (Linux; Android %s; %s Build/%s)", "MY_APP_NAME", "MY_APP_VERSION_NAME", Build.VERSION.RELEASE, Build.MANUFACTURER, Build.ID)));
        }

        //headers.add(new DLHeader("If-Match", info.eTag));
        //conn.setRequestProperty("Connection", "Keep-Alive");
        return headers;
    }

    private static boolean hasRequestHeader(String key, List<DLHeader> headers) {
        for (DLHeader header : headers) {
            if (header.key.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }




    public static String obtainFileName(String url, String contentDisposition, String contentLocation) {
        String fileName = null;
        if (null != contentDisposition) {
            fileName = parseContentDisposition(contentDisposition);
            if (null != fileName) {
                int index = fileName.lastIndexOf('/') + 1;
                if (index > 0) {
                    fileName = fileName.substring(index);
                }
            }
        }
        if (fileName == null && contentLocation != null) {
            String decodedContentLocation = Uri.decode(contentLocation);
            if (decodedContentLocation != null && !decodedContentLocation.endsWith("/")
                    && decodedContentLocation.indexOf('?') < 0) {
                int index = decodedContentLocation.lastIndexOf('/') + 1;
                if (index > 0) {
                    fileName = decodedContentLocation.substring(index);
                } else {
                    fileName = decodedContentLocation;
                }
            }
        }
        if (fileName == null) {
            String decodedUrl = Uri.decode(url);
            if (decodedUrl != null && !decodedUrl.endsWith("/") && decodedUrl.indexOf('?') < 0) {
                int index = decodedUrl.lastIndexOf('/') + 1;
                if (index > 0) {
                    fileName = decodedUrl.substring(index);
                }
            }
        }
        if (fileName == null) {
            fileName = UUID.randomUUID().toString();
        }
        fileName = replaceInvalidVFATCharacters(fileName);
        return fileName;
    }

    public static String parseContentDisposition(String contentDisposition) {
        int index = contentDisposition.indexOf("=");
        if (index > 0) {
            return contentDisposition.substring(index + 1);
        }
        return null;
    }

    public static String replaceInvalidVFATCharacters(String fileName) {
        final char END_CTRLCODE = 0x1f;
        final char QUOTEDBL = 0x22;
        final char ASTERISK = 0x2A;
        final char SLASH = 0x2F;
        final char COLON = 0x3A;
        final char LESS = 0x3C;
        final char GREATER = 0x3E;
        final char QUESTION = 0x3F;
        final char BACKSLASH = 0x5C;
        final char BAR = 0x7C;
        final char DEL = 0x7F;
        final char UNDERSCORE = 0x5F;

        StringBuilder sb = new StringBuilder();
        char ch;
        boolean isRepetition = false;
        for (int i = 0; i < fileName.length(); i++) {
            ch = fileName.charAt(i);
            if (ch <= END_CTRLCODE || ch == QUOTEDBL || ch == ASTERISK || ch == SLASH ||
                    ch == COLON || ch == LESS || ch == GREATER || ch == QUESTION ||
                    ch == BACKSLASH || ch == BAR || ch == DEL) {
                if (!isRepetition) {
                    sb.append(UNDERSCORE);
                    isRepetition = true;
                }
            } else {
                sb.append(ch);
                isRepetition = false;
            }
        }
        return sb.toString();
    }
}
