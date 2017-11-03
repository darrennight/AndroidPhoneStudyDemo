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
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zenghao.com.study.R;
import zenghao.com.study.listActivityFragment.base.BaseListFragment;
import zenghao.com.study.listActivityFragment.model.BaseModel;
import zenghao.com.study.listActivityFragment.model.Benefit;
import zenghao.com.study.listActivityFragment.request.Api;
import zenghao.com.study.listActivityFragment.widget.BaseViewHolder;
import zenghao.com.study.listActivityFragment.widget.ILayoutManager;
import zenghao.com.study.listActivityFragment.widget.MyGridLayoutManager;
import zenghao.com.study.listActivityFragment.widget.MyLinearLayoutManager;
import zenghao.com.study.listActivityFragment.widget.MyStaggeredGridLayoutManager;
import zenghao.com.study.listActivityFragment.widget.PullRecycler;

public class SampleListFragment extends BaseListFragment<Benefit> {
    private int random;
    private int page = 1;
 
    @Override 
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_list_item, parent, false);
        return new SampleViewHolder(view);
    } 
 
    @Override 
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setRefreshing(); 
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
    public void onRefresh(final int action) {
        if (mDataList == null) { 
            mDataList = new ArrayList<>();
        } 
 
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
        } 
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/") 
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build(); 
 
        Api api = retrofit.create(Api.class);
        api.rxBenefits(20, page++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseModel<ArrayList<Benefit>>>() {
                    @Override 
                    public void call(BaseModel<ArrayList<Benefit>> model) {
                        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                            mDataList.clear(); 
                        } 
                        if (model.results == null || model.results.size() == 0) {
                            recycler.enableLoadMore(false); 
                        } else { 
                            recycler.enableLoadMore(true); 
                            mDataList.addAll(model.results);
                            adapter.notifyDataSetChanged(); 
                        } 
                        recycler.onRefreshCompleted(); 
                    } 
                }, new Action1<Throwable>() {
                    @Override 
                    public void call(Throwable throwable) {
                        recycler.onRefreshCompleted(); 
                    } 
                }) 
        ; 
 
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
                    .load(mDataList.get(position).url)
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