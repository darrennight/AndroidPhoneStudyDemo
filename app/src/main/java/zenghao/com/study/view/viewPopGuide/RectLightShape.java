package zenghao.com.study.view.viewPopGuide;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class RectLightShape extends BaseLightShape {
    @Override 
    protected void drawShape(Bitmap bitmap, HighLight.ViewPosInfo viewPosInfo) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.SOLID));
        canvas.drawRoundRect(viewPosInfo.rectF,6,6,paint);
    } 
 
    @Override 
    protected void resetRectF4Shape(RectF viewPosInfoRectF, float dx, float dy) {
        viewPosInfoRectF.inset(dx,dy);
    } 
} 