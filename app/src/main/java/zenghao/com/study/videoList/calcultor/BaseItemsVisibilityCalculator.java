package zenghao.com.study.videoList.calcultor;

import android.widget.AbsListView;
import zenghao.com.study.videoList.scroll.ItemsPositionGetter;
import zenghao.com.study.videoList.scroll.ScrollDirectionDetector;

public abstract class BaseItemsVisibilityCalculator implements ListItemsVisibilityCalculator{
 
    /** Initial scroll direction should be UP in order to set as active most top item if no active item yet*/ 
    protected ScrollDirectionDetector.ScrollDirection mScrollDirection = ScrollDirectionDetector.ScrollDirection.UP;
 
    protected final ItemsPositionGetter mPositionGetter;
 
    public BaseItemsVisibilityCalculator(ItemsPositionGetter positionGetter) {
        mPositionGetter = positionGetter;
    } 
 
    private final ScrollDirectionDetector mScrollDirectionDetector = new ScrollDirectionDetector(
            new ScrollDirectionDetector.OnDetectScrollListener() { 
        @Override 
        public void onScrollDirectionChanged(ScrollDirectionDetector.ScrollDirection scrollDirection) {
            //下面调用检查滑动方向 这里回调回来
            mScrollDirection = scrollDirection; 
        } 
    }); 
 
    @Override 
    public void onScrolled(int scrollState) {
        int firstVisiblePosition = mPositionGetter.getFirstVisiblePosition();
        //检查滑动方向
        mScrollDirectionDetector.onDetectedListScroll(mPositionGetter, firstVisiblePosition);
 
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                onStateTouchScroll(mPositionGetter);
                break; 
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                onStateTouchScroll(mPositionGetter);
                break; 
 
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                onScrollStateIdle(); 
                break; 
        } 
    } 
 
    public abstract void onStateLost(); 
 
    protected abstract void onStateTouchScroll(ItemsPositionGetter itemsPositionGetter);
 
} 