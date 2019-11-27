package TouchEventAndDraw;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;

/**
 * 下拉刷新
 * 内容为不能滑动的
 *
 * @author zenghao
 * @since 2019-11-25 20:30
 */
public class MyRefreshLayoutAndScrollView extends LinearLayout {

    private boolean isShowRefresh = false;

    public MyRefreshLayoutAndScrollView(Context context) {
        this(context, null);
    }

    public MyRefreshLayoutAndScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRefreshLayoutAndScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        slop = ViewConfigurationCompat.getScaledHoverSlop(ViewConfiguration.get(context));
        init();
    }

    private void init() {
        addHeadRefresh();
    }

    private void addHeadRefresh() {
        setOrientation(LinearLayout.VERTICAL);
        ProgressBar progressBar = new ProgressBar(getContext());
        addView(progressBar, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //-往下移动 +网上移动  代表方向
//        scrollTo(0, getChildAt(0).getHeight());

        View header = getChildAt(0);
        View content = getChildAt(1);

        header.layout(0,0-header.getMeasuredHeight(),header.getMeasuredWidth(),0);

        content.layout(0,0,content.getMeasuredWidth(),content.getMeasuredHeight());
    }

    int lastY = 0;
    int slop = 0;
    int curY = 0;


    /*其实可以在dispatchTouchEvent 作出是否拦截的判断条件然后return super.dispatchTouchEvent(ev)  然后在onInterceptTouchEvent return这个条件  最后*/
    /*参考SpringView*/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.e("======","dispatchTouchEventdispatchTouchEvent");

        boolean result = false;             // 默认状态为没有消费过
        if (!onInterceptTouchEvent(ev)) {   // 如果没有拦截交给子View
            result = getChildAt(1).dispatchTouchEvent(ev);
        }
        if (!result) {                      // 如果事件没有被消费,询问自身onTouchEvent
            result = onTouchEvent(ev);
        }
        return result;

//        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

//        Log.e("======onintercept","onInterceptTouchEventonInterceptTouchEvent");

        View scrollView = getChildAt(1);
        View head = getChildAt(0);

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                curY = (int) ev.getRawY();
                break;


            case MotionEvent.ACTION_MOVE:
                curY = (int) ev.getRawY();


                if(curY - lastY > 0 && scrollView.getScrollY() == 0){
                    //下拉
                    return true;
                }else if (curY - lastY < 0 && scrollView.getScrollY() == 0 && (isShowRefresh || distance >0)){
                    //上拉
                    Log.e("=====",getScrollY()+ "111111111");
                    if(getScrollY()<=0 && distance <=0){
                        return false;
                    }
                    return true;
                }

            case MotionEvent.ACTION_UP:

                if (scrollView.getScrollY() == 0 && (isShowRefresh || distance >0)){
                    //上拉
                    return true;
                }
                break;

            default:
                break;
        }

        lastY = curY;

//        return super.onInterceptTouchEvent(ev);
        return false;
    }


    int distance = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //动画还没结束的时候，直接消耗掉事件,不处理。
        if (!scroller.isFinished()) {
            return true;
        }
//        Log.e("====", "headheadhead" + getScrollY());


        int moveY = 0;
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                curY = (int) event.getRawY();

                moveY = curY - lastY;

                if ((distance + moveY / 3) <= 0) {
                    scrollBy(0, distance);
                    distance = 0;
                    return false;
                }
                scrollBy(0, -(moveY / 3));
                distance += (moveY / 3);
                lastY = curY;

                break;

            case MotionEvent.ACTION_UP:

                if (distance >= getChildAt(0).getHeight()) {
                    isShowRefresh = true;
                    smoothToScroll(distance - getChildAt(0).getHeight());
                    distance = getChildAt(0).getHeight();

                } else {
                    endRefres();
                }

                break;

            default:
                break;
        }

        return true;
    }

    private void endRefres() {
        smoothToScroll(distance);
        distance = 0;
        isShowRefresh = false;
    }

    //下面是典型滑动固定代码
    private Scroller scroller = new Scroller(getContext());

    private void smoothToScroll(int destaY) {
        scroller.startScroll(0, getScrollY(), 0, destaY, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }
    }
}
