package TouchEventAndDraw;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 *
 *
 * @author zenghao
 * @since 2019-10-18 11:08
 */
public class ScrollerLayout extends ViewGroup {


    private Scroller mScroller;

    private int mTouchSlop;
    //手指按下X坐标
    private float mXDown;
    //手指当时所处的屏幕坐标 移动时坐标
    private float mXMove;

    //上次触发action_move事件时坐标
    private float mXLastMove;

    //界面可滚动的左边界
    private int leftBorder;

    //界面可滚动的右边界
    private int rightBorder;




    public ScrollerLayout(Context context) {
        this(context,null);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //创建scroller实例
        mScroller = new Scroller(context);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledHoverSlop(configuration);//getScaledPagingTouchSlop


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed){
            int childCount = getChildCount();
            for (int i=0;i<childCount;i++){
                View childView = getChildAt(i);
                // 为ScrollerLayout中的每一个子控件在水平方向上进行布局
//                在自定义布局的时候，我们的任务就是：遍历所有的子元素，确定它们的大小和位置（大小主要是通过 getMeasuredWidth() 和 getMeasuredHeight() 两个方法，
//                取出在 onMeasure() 方法中测量得到的宽/高；位置需要自行设置），然后调用 view.layout() 方法或直接调用ViewGroup中的方法 setChildFrame() 方法（
//                setChildFrame()方法内部调用的就是view.layout()方法），将子元素布局到这个ViewGroup中。
                /**
                 * 单一View	只计算View本身的位置
                 * ViewGroup	确定View本身及子View在父容器中的位置
                 */
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }

            // 初始化左右边界值
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();

            Log.e("====border",leftBorder+"====="+rightBorder);

        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXDown;

                if(diff > mTouchSlop ){
                    //拦截事件
                    // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                    return true;
                }
                break;
        }


        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int)(mXLastMove - mXMove);
//                getScrollX() 这个layout内部子 view 左边距  左边缘和屏幕左边的距离
                if(getScrollX() + scrolledX < leftBorder){//左边界向右滑动不处理
                    scrollTo(leftBorder, 0);
                    return true;
                }else if (getScrollX() + getWidth() + scrolledX > rightBorder) {//右边界向左滑动不处理
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);//根据手指移动距离进行滑动 scrollBy相对于上一次位置（当前位置）
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                //getWidth 是当前Viewgroup的宽度  match屏幕宽度
                Log.e("====getScrollX",getScrollX()+"==="+getWidth()+"===="+getRight());

                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                Log.e("====",targetIndex+"");
                int dx = targetIndex * getWidth() - getScrollX();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;

        }


        return super.onTouchEvent(event);
    }

    //基本固定写法这个方法 scroller不能直接移动view 需要配合computeScroll方法
    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}
