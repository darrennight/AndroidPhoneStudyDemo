package zenghao.com.study.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by lizhennian on 2014/5/30.
 */
public class ScaleFrameLayout extends FrameLayout {
    public ScaleFrameLayout(Context context) {
        super(context);
    }

    public ScaleFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            ScaleCalculator.getInstance().scaleViewGroup(this);
        } catch (Exception e) {

        }
    }
}
