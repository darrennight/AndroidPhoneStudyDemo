package zenghao.com.study.view;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;
import zenghao.com.study.MyApplication;
import zenghao.com.study.util.ScreenUtil;

import static zenghao.com.study.R.id.textView;

public class DeepScrollView extends ScrollView {
 
 
	public DeepScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	} 
 
	public DeepScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	} 
 
	public DeepScrollView(Context context) {
		super(context);
	} 
 
 
	public void scrollToDeepChild(View child) {
		Point childOffset = new Point();
 
		getDeepChildOffset(child.getParent(), child, childOffset);
 
 
		Rect childRect = new Rect(childOffset.x, childOffset.y, childOffset.x + child.getWidth(), childOffset.y + child.getHeight());
		int deltay = computeScrollDeltaToGetChildRectOnScreen(childRect);
		smoothScrollBy(0, deltay);
	} 
 
	private void getDeepChildOffset(ViewParent nextParent, View nextChild, Point accumulatedOffset) {
		ViewGroup parent = (ViewGroup) nextParent;
		accumulatedOffset.x += nextChild.getLeft();
		accumulatedOffset.y += nextChild.getTop();
		if(parent == this) {
			return; 
		} 
		getDeepChildOffset(parent.getParent(), parent, accumulatedOffset);
	}

	public int newY=0;
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		this.newY=t;

	}
	/*public void translateView(View view){
		int[] xy =new int[2];
		//view = ImageView
		view.getLocationOnScreen(xy);
		int scrollY = xy[1]+view.getBottom();
		//screenHeight =ScreenHeight of the phone
		if(scrollY> ScreenUtil.getSHight(getContext())){
			//heading textView
			textView.getLocationOnScreen(xy);
			int diff = screenHeight-xy[1];
			diff =Math.abs(diff);
			int moveHeight = (int) (this.newY+diff);
			this.smoothScrollTo(0, moveHeight);

		}
	}*/
}