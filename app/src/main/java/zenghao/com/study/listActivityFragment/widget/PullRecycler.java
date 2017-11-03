package zenghao.com.study.listActivityFragment.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import zenghao.com.study.R;

/***
 * 下拉刷新加载更多以及列表显示控制器
 */
public class PullRecycler extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    /**下拉刷新*/
    public static final int ACTION_PULL_TO_REFRESH = 1;
    /**加载更多*/
    public static final int ACTION_LOAD_MORE_REFRESH = 2;
    /**空闲*/
    public static final int ACTION_IDLE = 0;
    private OnRecyclerRefreshListener listener;
    private int mCurrentState = ACTION_IDLE;
    private boolean isLoadMoreEnabled = false;
    private boolean isPullToRefreshEnabled = true;
    private ILayoutManager mLayoutManager;
    private BaseListAdapter adapter;
 
    public PullRecycler(Context context) {
        super(context);
        setUpView(); 
    } 
 
    public PullRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpView(); 
    } 
 
    public PullRecycler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView(); 
    } 
 
    private void setUpView() {
        /**初始化布局 刷新+recyclerView*/
        LayoutInflater.from(getContext()).inflate(R.layout.widget_pull_to_refresh, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override 
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            } 
 
            @Override 
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /**加载更多*/
                if (mCurrentState == ACTION_IDLE && isLoadMoreEnabled && checkIfNeedLoadMore()) {
                    mCurrentState = ACTION_LOAD_MORE_REFRESH;
                    adapter.onLoadMoreStateChanged(true);
                    mSwipeRefreshLayout.setEnabled(false);
                    listener.onRefresh(ACTION_LOAD_MORE_REFRESH);
                } 
            } 
        }); 
    }

    /***
     * 检测是否可以加载更多了
     * 距离最后一个item位置 5以内可以开始加载更多了
     * @return
     */
    private boolean checkIfNeedLoadMore() { 
        int lastVisibleItemPosition = mLayoutManager.findLastVisiblePosition();
        int totalCount = mLayoutManager.getLayoutManager().getItemCount();
        return totalCount - lastVisibleItemPosition < 5;
    } 
 
    public void enableLoadMore(boolean enable) {
        isLoadMoreEnabled = enable;
    } 
 
    public void enablePullToRefresh(boolean enable) {
        isPullToRefreshEnabled = enable;
        mSwipeRefreshLayout.setEnabled(enable);
    } 
 
    public void setLayoutManager(ILayoutManager manager) {
        this.mLayoutManager = manager;
        mRecyclerView.setLayoutManager(manager.getLayoutManager());
    } 
 
    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        if (decoration != null) {
            mRecyclerView.addItemDecoration(decoration);
        } 
    } 
 
    public void setAdapter(BaseListAdapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
        mLayoutManager.setUpAdapter(adapter);
    } 
 
    public void setRefreshing() { 
        mSwipeRefreshLayout.post(new Runnable() {
            @Override 
            public void run() { 
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh(); 
            } 
        }); 
    } 
 
    public void setOnRefreshListener(OnRecyclerRefreshListener listener) {
        this.listener = listener;
    } 
 
    @Override 
    public void onRefresh() { 
        mCurrentState = ACTION_PULL_TO_REFRESH;
        listener.onRefresh(ACTION_PULL_TO_REFRESH);
    } 
 
    public void onRefreshCompleted() { 
        switch (mCurrentState) {
            case ACTION_PULL_TO_REFRESH:
                mSwipeRefreshLayout.setRefreshing(false);
                break; 
            case ACTION_LOAD_MORE_REFRESH:
                adapter.onLoadMoreStateChanged(false);
                if (isPullToRefreshEnabled) {
                    mSwipeRefreshLayout.setEnabled(true);
                } 
                break; 
        } 
        mCurrentState = ACTION_IDLE;
    } 
 
    public void setSelection(int position) {
        mRecyclerView.scrollToPosition(position);
    } 
 
 
    public interface OnRecyclerRefreshListener { 
        void onRefresh(int action);
    } 
} 