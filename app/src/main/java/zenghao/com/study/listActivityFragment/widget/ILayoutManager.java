package zenghao.com.study.listActivityFragment.widget;

import android.support.v7.widget.RecyclerView;

public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();
    int findLastVisiblePosition();
    void setUpAdapter(BaseListAdapter adapter);
}