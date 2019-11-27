package TouchEventAndDraw;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO
 *
 * @author zenghao
 * @since 2019-11-18 16:10
 */
public class TestOnMeasureView extends View {

    public TestOnMeasureView(Context context) {
        super(context);
    }

    public TestOnMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestOnMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  //取消这个需要设置 setMeasuredDimension

        int tempWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int tempheight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.e("======","ViewtempWidth  "+tempWidth);
        Log.e("======","ViewwidthModeAT_MOST  "+ (widthMode == MeasureSpec.AT_MOST));
        Log.e("======","ViewwidthModeEXACTLY  "+ (widthMode == MeasureSpec.EXACTLY));
        Log.e("======","ViewwidthModeUNSPECIFIED  "+ (widthMode == MeasureSpec.UNSPECIFIED));

        Log.e("======","Viewtempheight  "+tempheight);
        Log.e("======","ViewheightMode  "+(heightMode == MeasureSpec.AT_MOST));
        Log.e("======","ViewheightMode  "+(heightMode == MeasureSpec.EXACTLY));
        Log.e("======","ViewheightMode  "+(heightMode == MeasureSpec.UNSPECIFIED));

//        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
//        setMeasuredDimension(200,100);//可以设置View更大
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("-----===View","dispatchTouchEventdispatchTouchEvent");
        return super.dispatchTouchEvent(event);//调用下面自己的onTouchEvent
//        return false; //回传给父onTouchEvent
//        return true; //消费不传递不回传到此结束
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("-----===View","onTouchEventonTouchEvent");
        return super.onTouchEvent(event);//回传给父onTouchEvent
        //        return false; //回传给父onTouchEvent
        //        return true; //消费不传递不回传到此结束
    }
}
