package zenghao.com.study.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/***
 * view 截图
 */
public class ViewUnit {
public static Bitmap capture(View view, float width, float height, boolean scroll, Bitmap.Config config) {
	long first = System.currentTimeMillis();
    if (!view.isDrawingCacheEnabled()) {
        view.setDrawingCacheEnabled(true);
    } 


    Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, config);
    bitmap.eraseColor(Color.WHITE);


    Canvas canvas = new Canvas(bitmap);
    int left = view.getLeft();
    int top = view.getTop();
    if (scroll) {
        left = view.getScrollX();
        top = view.getScrollY();
    } 
    int status = canvas.save();
    canvas.translate(-left, -top);


    float scale = width / view.getWidth();
    canvas.scale(scale, scale, left, top);


    view.draw(canvas);
    canvas.restoreToCount(status);


    Paint alphaPaint = new Paint();
    alphaPaint.setColor(Color.TRANSPARENT);


    canvas.drawRect(0f, 0f, 1f, height, alphaPaint);
    canvas.drawRect(width - 1f, 0f, width, height, alphaPaint);
    canvas.drawRect(0f, 0f, width, 1f, alphaPaint);
    canvas.drawRect(0f, height - 1f, width, height, alphaPaint);
    canvas.setBitmap(null);

System.out.println("====timetime"+(System.currentTimeMillis()-first));
    return bitmap;
} 


public static float getDensity(Context context) {
    return context.getResources().getDisplayMetrics().density;
} 





public static int getWindowHeight(Context context) {
    return context.getResources().getDisplayMetrics().heightPixels;
} 


public static int getWindowWidth(Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
} 


} 
