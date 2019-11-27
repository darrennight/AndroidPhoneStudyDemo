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
public class MyRefreshLayout extends LinearLayout {

    public MyRefreshLayout(Context context) {
        this(context, null);
    }

    public MyRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        slop = ViewConfigurationCompat.getScaledHoverSlop(ViewConfiguration.get(context));
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        addHeadRefresh();
//        addFooterRefresh();
    }

    private void addHeadRefresh() {

        ProgressBar progressBar = new ProgressBar(getContext());
        addView(progressBar);
    }

    private void addFooterRefresh() {
        ProgressBar progressBar = new ProgressBar(getContext());
        addView(progressBar);
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
//        View footer = getChildAt(1);
        View content = getChildAt(1);

        header.layout(0,0-header.getMeasuredHeight(),header.getMeasuredWidth(),0);

        content.layout(0,0,content.getMeasuredWidth(),content.getMeasuredHeight());

//        footer.layout(0,content.getMeasuredHeight(),footer.getMeasuredWidth(),content.getMeasuredHeight()+footer.getMeasuredHeight());


    }

    int lastY = 0;
    int slop = 0;
    int curY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                curY = (int) ev.getRawY();
                Log.e("====", "ACTION_DOWNcurYcurY" + curY);
                break;


            case MotionEvent.ACTION_MOVE:
                //这里没有执行到---down没有拦截---子view(没有处理)---自己到onTouchEvent
                Log.e("====", "curY - lastY" + (curY - lastY));
                if (curY - lastY >= slop) {
                    lastY = curY;
                    return true;
                }
                break;
            default:
                break;
        }

        lastY = curY;

        return super.onInterceptTouchEvent(ev);
    }


    int distance = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //动画还没结束的时候，直接消耗掉事件,不处理。
        if (!scroller.isFinished()) {
            return true;
        }

        int moveY = 0;
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                curY = (int) event.getRawY();
//                Log.e("====",curY+"ACTION_MOVEACTION_MOVE"+(lastY));
                moveY = curY - lastY;
                if (moveY >= 0) {
                    scrollBy(0, -(moveY / 3));
                    distance += (moveY / 3);
                } else {
                    if ((distance + moveY / 3) < 0) {
                        return true;
                    }
                    scrollBy(0, -(moveY / 3));
                    distance += (moveY / 3);
                    //判断distanc 是否大于 header的高度了
                }
                lastY = curY;

                break;

            case MotionEvent.ACTION_UP:

                if (distance >= getChildAt(0).getHeight()) {

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
