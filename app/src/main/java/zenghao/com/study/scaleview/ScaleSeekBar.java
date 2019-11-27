package zenghao.com.study.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by lizhennian on 2014/5/29.
 */
public class ScaleSeekBar extends SeekBar {
    public ScaleSeekBar(Context context) {
        super(context);
    }

    public ScaleSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            ScaleCalculator.getInstance().scaleView(this);
        } catch (Exception e) {

        }
    }
}
