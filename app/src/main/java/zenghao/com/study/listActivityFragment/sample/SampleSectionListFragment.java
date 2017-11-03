package zenghao.com.study.listActivityFragment.sample;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Random;
import zenghao.com.study.R;
import zenghao.com.study.listActivityFragment.base.BaseSectionListFragment;
import zenghao.com.study.listActivityFragment.model.ConstantValues;
import zenghao.com.study.listActivityFragment.widget.BaseViewHolder;
import zenghao.com.study.listActivityFragment.widget.ILayoutManager;
import zenghao.com.study.listActivityFragment.widget.MyGridLayoutManager;
import zenghao.com.study.listActivityFragment.widget.MyLinearLayoutManager;
import zenghao.com.study.listActivityFragment.widget.MyStaggeredGridLayoutManager;
import zenghao.com.study.listActivityFragment.widget.PullRecycler;
import zenghao.com.study.listActivityFragment.widget.SectionData;

public class SampleSectionListFragment extends BaseSectionListFragment<String> {
 
    private int random;
 
    @Override 
    protected BaseViewHolder onCreateSectionViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_list_item, parent, false);
        return new SampleViewHolder(view);
    } 
 
    @Override 
    protected ILayoutManager getLayoutManager() {
        random = new Random().nextInt(3);
        switch (random) {
            case 0: 
                return new MyLinearLayoutManager(getContext());
            case 1: 
                return new MyGridLayoutManager(getContext(), 3);
            case 2: 
                return new MyStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        } 
        return super.getLayoutManager(); 
    } 
 
    @Override 
    protected RecyclerView.ItemDecoration getItemDecoration() {
        if (random == 0) {
            return super.getItemDecoration(); 
        } else { 
            return null; 
        } 
    } 
 
    @Override 
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setRefreshing(); 
    } 
 
    @Override 
    public void onRefresh(final int action) {
        if (mDataList == null) { 
            mDataList = new ArrayList<>();
        } 
 
        recycler.postDelayed(new Runnable() {
            @Override 
            public void run() { 
                if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                    mDataList.clear(); 
                } 
                int size = mDataList.size();
                mDataList.add(new SectionData(true, size, "header " + size));
                for (int i = size; i < size + 20; i++) {
                    mDataList.add(new SectionData(ConstantValues.images[i]));
                } 
                adapter.notifyDataSetChanged(); 
                recycler.onRefreshCompleted(); 
                if (mDataList.size() < 100) { 
                    recycler.enableLoadMore(true); 
                } else { 
                    recycler.enableLoadMore(false); 
                } 
            } 
        }, 3000); 
    } 
 
    class SampleViewHolder extends BaseViewHolder { 
 
        ImageView mSampleListItemImg;
        TextView mSampleListItemLabel;
 
        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.mSampleListItemLabel);
            mSampleListItemImg = (ImageView) itemView.findViewById(R.id.mSampleListItemImg);
        } 
 
        @Override 
        public void onBindViewHolder(int position) {
            mSampleListItemLabel.setVisibility(View.GONE);
            Glide.with(mSampleListItemImg.getContext())
                    .load(mDataList.get(position).t)
                    .centerCrop() 
                    .placeholder(R.color.app_primary_color)
                    .crossFade() 
                    .into(mSampleListItemImg);
        } 
 
        @Override 
        public void onItemClick(View view, int position) {
 
        } 
 
    } 
} 