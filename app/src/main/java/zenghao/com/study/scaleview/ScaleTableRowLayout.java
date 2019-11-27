package zenghao.com.study.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;

/**
 * Created by lizhennian on 2014/5/29.
 */
public class ScaleTableRowLayout extends TableRow {
    public ScaleTableRowLayout(Context context) {
        super(context);
    }

    public ScaleTableRowLayout(Context context, AttributeSet attrs) {
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
