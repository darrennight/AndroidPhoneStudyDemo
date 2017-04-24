package zenghao.com.study.view.viewPopGuide;

import android.graphics.RectF;

public abstract class OnBaseCallback implements HighLight.OnPosCallback {
    protected float offset ;
 
    public OnBaseCallback() { 
    } 
 
    public OnBaseCallback(float offset) {
        this.offset = offset;
    } 
 
    /** 
     * 如果需要调整位置,重写该方法 
     * @param rightMargin 
     * @param bottomMargin 
     * @param rectF 
     * @param marginInfo 
     */ 
    public  void posOffset(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo){}
 
    @Override 
    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        getPosition(rightMargin,bottomMargin,rectF,marginInfo);
        posOffset(rightMargin,bottomMargin,rectF,marginInfo);
    } 
    public abstract void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo);
} 