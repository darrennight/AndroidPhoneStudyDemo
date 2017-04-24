package zenghao.com.study.view.viewPopGuide;

import android.graphics.RectF;

public class OnTopPosCallback extends OnBaseCallback {
    public OnTopPosCallback() { 
    } 
 
    public OnTopPosCallback(float offset) {
        super(offset);
    } 
 
    @Override 
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.leftMargin = rectF.right - rectF.width() / 2;
        marginInfo.bottomMargin = bottomMargin+rectF.height()+offset;
    } 
} 