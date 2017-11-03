package zenghao.com.study.listActivityFragment.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import zenghao.com.study.R;
import zenghao.com.study.listActivityFragment.widget.BaseListAdapter;
import zenghao.com.study.listActivityFragment.widget.BaseViewHolder;
import zenghao.com.study.listActivityFragment.widget.DividerItemDecoration;
import zenghao.com.study.listActivityFragment.widget.ILayoutManager;
import zenghao.com.study.listActivityFragment.widget.MyLinearLayoutManager;
import zenghao.com.study.listActivityFragment.widget.PullRecycler;

public abstract class BaseListFragment<T> extends BaseFragment implements PullRecycler.OnRecyclerRefreshListener {
    protected BaseListAdapter adapter;
    protected ArrayList<T> mDataList;
    protected PullRecycler recycler;
 
    @Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_list, container, false);
    } 
 
    @Override 
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = (PullRecycler) view.findViewById(R.id.pullRecycler);
        setUpAdapter(); 
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
    } 
 
    protected void setUpAdapter() { 
        adapter = new ListAdapter();
    } 
 
    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getContext());
    } 
 
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getContext(), R.drawable.list_divider);
    } 
 
    public class ListAdapter extends BaseListAdapter { 
 
        @Override 
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        } 
 
        @Override 
        protected int getDataCount() { 
            return mDataList != null ? mDataList.size() : 0;
        } 
 
        @Override 
        protected int getDataViewType(int position) {
            return getItemType(position);
        } 
 
        @Override 
        public boolean isSectionHeader(int position) {
            return BaseListFragment.this.isSectionHeader(position);
        } 
    } 
 
    protected boolean isSectionHeader(int position) {
        return false; 
    } 
 
    protected int getItemType(int position) {
        return 0; 
    } 
 
    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);
 
} 