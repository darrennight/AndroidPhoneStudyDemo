package zenghao.com.study.view.viewPopGuide;

import android.graphics.Bitmap;
import android.graphics.RectF;

public abstract class BaseLightShape implements HighLight.LightShape{
    protected float dx;
    protected float dy;
 
    public BaseLightShape() { 
    } 
 
    public BaseLightShape(float dx,float dy) {
        this.dx = dx;
        this.dy = dy;
    } 
 
    @Override 
    public void shape(Bitmap bitmap, HighLight.ViewPosInfo viewPosInfo) {
        resetRectF4Shape(viewPosInfo.rectF,dx,dy);
        drawShape(bitmap,viewPosInfo);
    } 
 
    /** 
     * reset RectF for Shape by dx and dy. 
     * @param viewPosInfoRectF 
     * @param dx 
     * @param dy 
     */ 
    protected abstract void resetRectF4Shape(RectF viewPosInfoRectF, float dx, float dy);
 
    /** 
     * draw shape into bitmap 
     * @param bitmap 
     * @param viewPosInfo 
     * @see HightLightView#buildMask()
     */ 
    protected abstract void drawShape(Bitmap bitmap, HighLight.ViewPosInfo viewPosInfo);
 
} 