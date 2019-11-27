package TouchEventAndDraw;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * TODO
 *
 * @author zenghao
 * @since 2019-11-18 15:51
 */
public class TestMeasureLayout extends ViewGroup {

    public TestMeasureLayout(Context context) {
        super(context);
    }

    public TestMeasureLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestMeasureLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        View view  = getChildAt(0);
        LayoutParams params = view.getLayoutParams();
        Log.e("=====","paramsparamsparams===="+params.width);//wrap_content=-2 match_parent=-1 dp具体值


        int tempWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int tempheight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.e("======","layouttempWidth  "+tempWidth);
        Log.e("======","layoutwidthModeAT_MOST  "+ (widthMode == MeasureSpec.AT_MOST));
        Log.e("======","layoutwidthModeEXACTLY  "+ (widthMode == MeasureSpec.EXACTLY));
        Log.e("======","layoutwidthModeUNSPECIFIED  "+ (widthMode == MeasureSpec.UNSPECIFIED));

        Log.e("======","layouttempheight  "+tempheight);
        Log.e("======","layoutheightMode  "+(heightMode == MeasureSpec.AT_MOST));
        Log.e("======","layoutheightMode  "+(heightMode == MeasureSpec.EXACTLY));
        Log.e("======","layoutheightMode  "+(heightMode == MeasureSpec.UNSPECIFIED));


        measureChild(view,widthMeasureSpec,heightMeasureSpec);
        Log.e("=====","child===="+ view.getMeasuredWidth());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view  = getChildAt(0);
        view.layout(l,t,view.getMeasuredWidth(),view.getMeasuredHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.e("---=====layout","dispatchTouchEventdispatchTouchEvent");

        return super.dispatchTouchEvent(ev);//调用执行下面的调用自己的 onInterceptTouchEvent  super默认也是调用下面自己的onInterceptTouchEvent


//        return false;//回传给（activity/父view）的onTouchEvent 不会调用下面的方法也不会继续传递
//        return true;//消费了事件不会调用下面的方法也不会继续传递到此结束
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("---=====layout","onInterceptTouchEventonInterceptTouchEvent");
        //onInterceptTouchEvent 默认是不拦截所以super.onInterceptTouchEvent(ev); 和false是同一个意思

        return super.onInterceptTouchEvent(ev);//不拦截事件 调用子view的dispatchTouchEvent
        //        return false;                 //不拦截事件 调用子view的dispatchTouchEvent

//        return true; //拦截事件调用自己的onTouchEvent
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("-----=====layout","onTouchEventonTouchEvent");
        return super.onTouchEvent(event);//回传给activity的onTouchEvent
//        return false;                     //回传给（activity/或者父View/viewgroup）的onTouchEvent

//        return true;//消费了事件不会继续传递到此结束


    }
}
