package zenghao.com.study.listActivityFragment.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

public class MyStaggeredGridLayoutManager extends StaggeredGridLayoutManager implements ILayoutManager {
 
    public MyStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    } 
 
    public MyStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }


 
    @Override 
    public RecyclerView.LayoutManager getLayoutManager() {
        return this;
    } 
 
    @Override 
    public int findLastVisiblePosition() { 
        int[] positions = null;
        positions = findLastVisibleItemPositions(positions);
        return positions[0];
    } 
 
    @Override 
    public void setUpAdapter(BaseListAdapter adapter) {
 
    } 
} 