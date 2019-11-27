package zenghao.com.study.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * Created by lizhennian on 2014/5/30.
 */
@SuppressWarnings("deprecation")
public class ScaleGallery extends Gallery {
	public ScaleGallery(Context context) {
		super(context);
	}

	public ScaleGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScaleGallery(Context context, AttributeSet attrs, int defStyle) {
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

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		 return super.onFling(e1, e2, 0, velocityY);
		//return false;
	}
}
