package zenghao.com.study.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;

/**
 * Created by lizhennian on 2014/5/29.
 */
public class ScaleTableLayout extends TableLayout {
    public ScaleTableLayout(Context context) {
        super(context);
    }

    public ScaleTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
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
