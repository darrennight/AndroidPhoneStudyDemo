package zenghao.com.study.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by weixiancui on 2014/12/9.
 */
public class ScaleTextView extends TextView {
    public ScaleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTextSize(getTextSize());
    }

    public ScaleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextSize(getTextSize());
    }

    public ScaleTextView(Context context) {
        super(context);
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
