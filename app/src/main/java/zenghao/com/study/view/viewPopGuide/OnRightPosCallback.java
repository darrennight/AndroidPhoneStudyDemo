package zenghao.com.study.view.viewPopGuide;

import android.graphics.RectF;

public class OnRightPosCallback extends OnBaseCallback {
    public OnRightPosCallback() { 
    } 
 
    public OnRightPosCallback(float offset) {
        super(offset);
    } 
 
    @Override 
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.leftMargin = rectF.right + offset;
        marginInfo.topMargin = rectF.top;
    } 
} 