package zenghao.com.study.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.text.TextUtils;
 
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
/** 
 * 图片处理类 
 * Created by zhoujun on 15/5/25. 
 */ 
public class ImageUtil { 
    /** 
     * 计算压缩尺寸比例 
     * @param options 
     * @param reqWidth 
     * @param reqHeight 
     * @return 
     */ 
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
 
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        } 
        return inSampleSize;
    } 
 
    /** 
     * 获取小图 
     * @param filePath 
     * @return 
     */ 
    public static Bitmap getSmallBitmap(String filePath,int width,int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
 
        // Calculate inSampleSize 
        options.inSampleSize = calculateInSampleSize(options, width, height);
 
        // Decode bitmap with inSampleSize set 
        options.inJustDecodeBounds = false;
 
        return BitmapFactory.decodeFile(filePath, options);
 
    } 
    /** 
     * 用当前时间给取得的图片命名 
     * 
     */ 
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
        return dateFormat.format(date) + ".jpg";
    } 
    /** 
     * 压缩图片，并对图片角度旋转 
     * @param filePath 
     * @param outFilePath 
     * @return 
     */ 
    public static String compressImage(String filePath,String outFilePath){
        return compressImage(filePath,outFilePath,640,640);
    } 
    /** 
     * 压缩图片 
     * @param filePath 
     * @param outFilePath 
     * @return 
     */ 
    public static String compressImage(String filePath,String outFilePath,int width,int height)  {
        if(TextUtils.isEmpty(filePath)){
            return ""; 
        } 
        Bitmap bm = getSmallBitmap(filePath,width,height);
 
        int degree = readPictureDegree(filePath);
 
        if(degree!=0){//旋转照片角度
            bm=rotateBitmap(bm,degree);
        } 
 
        FileOutputStream out = null;
        try { 
            out = new FileOutputStream(outFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        try { 
            //压缩到60K 
            bm.compress(Bitmap.CompressFormat.JPEG, compressBitmap(bm, 60), out);
        }catch (Exception e){
            try { 
                out.close();
            } catch (IOException e1) {
 
            } 
            return ""; 
        } 
        bm.recycle();
        try { 
            out.close();
        } catch (IOException e1) {
 
        } 
        return outFilePath;
    } 
 
    /** 
     *读取照片角度 
     * @param path 
     * @return 
     */ 
    public static int readPictureDegree(String path) {
        int degree = 0;
        try { 
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break; 
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break; 
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break; 
            } 
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return degree;
    } 
 
    /** 
     * 旋转照片 
     * @param bitmap 
     * @param degress 
     * @return 
     */ 
    public static Bitmap rotateBitmap(Bitmap bitmap,int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        } 
        return bitmap;
    } 
 
    /** 
     * 图片质量压缩 
     * @param bitmap 
     * @param size 
     * @return 
     */ 
    public static int  compressBitmap(Bitmap bitmap,float size){
        int quality=100;
        if(bitmap==null||getSizeOfBitmap(bitmap)<=size){
            return quality;//如果图片本身的大小已经小于这个大小了，就没必要进行压缩
        } 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
        while (baos.toByteArray().length / 1024f>size) {
            quality=quality-4;// 每次都减少4
            baos.reset();// 重置baos即清空baos
            if(quality<=0){
                break; 
            } 
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        } 
        try { 
            baos.close();
        } catch (IOException e) {
 
        } 
        return quality;
    } 
 
    /** 
     * 获取bitmap大小，单位kb 
     * @param bitmap 
     */ 
    public static long getSizeOfBitmap(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//这里100的话表示不压缩质量
        int length = baos.toByteArray().length/1024;//读出图片的kb大小
        try { 
            baos.close();
        } catch (IOException e) {
 
        } 
        return length;
    } 
 
    /** 
     * 生成圆角图片 
     * @param bitmap 
     * @param roundPx 
     * @return 
     */ 
    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap,float roundPx) {
        try { 
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
 
            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
 
            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        } 
    } 
} 
