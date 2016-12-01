package zenghao.com.study.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *bitmap处理相关工具类
 * <ul>
 * <li>{@link #bitmap2Bytes(Bitmap bitmap)}Bitmap 转 bytes </li>
 * <li>{@link #compressBitmap(byte[] datas)}Bitmap压缩 </li>
 *
 * </ul>
 * @author zenghao
 * @since 16/9/28 下午5:44
 */
public class BitmapUtils {

    /**
     * Bitmap 转 bytes
     * @param bitmap
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        if(bitmap != null && !bitmap.isRecycled()) {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                if(byteArrayOutputStream.toByteArray() == null) {
                    Log.e("","bitmap2Bytes byteArrayOutputStream toByteArray=null");
                }
                return byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                Log.e("",e.toString());
            } finally {
                if(byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException var14) {
                        ;
                    }
                }
            }

            return null;
        } else {
            Log.e("","bitmap2Bytes bitmap == null or bitmap.isRecycled()");
            return null;
        }
    }

    /**
     * 压缩图片
     * @param datas
     * @return
     */
    public static byte[] compressBitmap(byte[] datas) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Bitmap tmpBitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length);
            tmpBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            if(outputStream != null) {
                byte[] outputStreamByte = outputStream.toByteArray();
                if(!tmpBitmap.isRecycled()) {
                    tmpBitmap.recycle();
                }
                return outputStreamByte;
            }

        return datas;
    }
}
