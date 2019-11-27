package zenghao.com.study.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by lizhennian on 2014/6/3.
 */
public class ScaleEditText extends EditText {
    public ScaleEditText(Context context) {
        super(context);
        setTextSize(getTextSize());
    }

    public ScaleEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTextSize(getTextSize());
    }

    public ScaleEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
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
