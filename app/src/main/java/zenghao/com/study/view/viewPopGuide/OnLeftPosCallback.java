package zenghao.com.study.view.viewPopGuide;

import android.graphics.RectF;

public class OnLeftPosCallback extends OnBaseCallback {
    public OnLeftPosCallback() { 
    } 
 
    public OnLeftPosCallback(float offset) {
        super(offset);
    } 
 
    @Override 
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.rightMargin = rightMargin+rectF.width()+offset;
        marginInfo.topMargin = rectF.top;
    } 
} 