package zenghao.com.study.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by lizhennian on 2014/5/30.
 */
public class ScaleButton extends Button {
    public ScaleButton(Context context) {
        super(context);
        setTextSize(getTextSize());
    }

    public ScaleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextSize(getTextSize());
    }

    public ScaleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTextSize(getTextSize());
    }

    @Override
    public void setTextSize(float textSize) {
        setTextSize(0, textSize);
    }

    @Override
    public void setTextSize(int unit, float textSize) {
        try {
            textSize = ScaleCalculator.getInstance().scaleTextSize(textSize);
        } catch (Exception e) {

        }
        super.setTextSize(unit, textSize);
    }
}
