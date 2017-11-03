package zenghao.com.study.listActivityFragment.sample;
 
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
import zenghao.com.study.listActivityFragment.base.BaseSectionListActivity;
import zenghao.com.study.listActivityFragment.model.ConstantValues;
import zenghao.com.study.listActivityFragment.widget.BaseViewHolder;
import zenghao.com.study.listActivityFragment.widget.ILayoutManager;
import zenghao.com.study.listActivityFragment.widget.MyGridLayoutManager;
import zenghao.com.study.listActivityFragment.widget.MyLinearLayoutManager;
import zenghao.com.study.listActivityFragment.widget.MyStaggeredGridLayoutManager;
import zenghao.com.study.listActivityFragment.widget.PullRecycler;
import zenghao.com.study.listActivityFragment.widget.SectionData;

/** 
 * Created by Stay on 25/2/16. 
 * Powered by www.stay4it.com 
 */ 
public class SampleSectionListActivity extends BaseSectionListActivity<String> {
 
    private int random;
 
    @Override 
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.title_recycler_section_activity);
    } 
 
    @Override 
    protected void setUpData() { 
        super.setUpData(); 
        recycler.setRefreshing(); 
    } 
 
    @Override 
    protected BaseViewHolder onCreateSectionViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_list_item, parent, false);
        return new SampleViewHolder(view);
    } 
 
    @Override 
    protected ILayoutManager getLayoutManager() {
        random = new Random().nextInt(3);
        switch (random){
            case 0: 
                return new MyLinearLayoutManager(getApplicationContext());
            case 1: 
                return new MyGridLayoutManager(getApplicationContext(), 3);
            case 2: 
                return new MyStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        } 
        return super.getLayoutManager(); 
    } 
 
    @Override 
    protected RecyclerView.ItemDecoration getItemDecoration() { 
        if (random == 0){
            return super.getItemDecoration(); 
        }else { 
            return null; 
        } 
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