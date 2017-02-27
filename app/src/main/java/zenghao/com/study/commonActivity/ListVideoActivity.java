package zenghao.com.study.commonActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.R;
import zenghao.com.study.videoList.ItemUtils;
import zenghao.com.study.videoList.calcultor.SingleListViewItemActiveCalculator;
import zenghao.com.study.videoList.holder.BaseViewHolder;
import zenghao.com.study.videoList.holder.VideoViewHolder;
import zenghao.com.study.videoList.holder.ViewHolderFactory;
import zenghao.com.study.videoList.items.ListItem;
import zenghao.com.study.videoList.model.BaseItem;
import zenghao.com.study.videoList.scroll.ItemsProvider;
import zenghao.com.study.videoList.scroll.ListViewItemPositionGetter;

/**
 *列表视频滑动播放
 * https://github.com/waynell/VideoListPlayer
 * @author zenghao
 * @since 17/1/5 下午3:53
 */
public class ListVideoActivity extends AppCompatActivity {

    private ListView mListView;
    private static final int HEADER_COUNT = 2;

    private int mScrollState;

    private SingleListViewItemActiveCalculator mCalculator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);
        ButterKnife.bind(this);
        mListView = ((ListView) findViewById(R.id.lv_video));

        List<String> list = new ArrayList<>();
        list.add("/storage/emulated/0/DCIM/Video/V61027-114752.mp4");
        list.add("/storage/emulated/0/DCIM/Video/V61027-114803.mp4");
        list.add("/storage/emulated/0/DCIM/Video/V61027-171052.mp4");
        list.add("/storage/emulated/0/DCIM/Video/V61027-171101.mp4");
        list.add("/storage/emulated/0/DCIM/Video/V61027-171110.mp4");
        //MyAdapter adapter = new MyAdapter(this,list);
        //mListView.setAdapter(adapter);

        final  MyAdapter adapter = new MyAdapter();

        mCalculator = new SingleListViewItemActiveCalculator(adapter, new ListViewItemPositionGetter(mListView));

        // add hear here
        for (int i = 0; i < HEADER_COUNT; i++) {
            Button header = new Button(this);
            header.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            header.setText("Header");
            mListView.addHeaderView(header);
        }

        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mScrollState = scrollState;
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && adapter.getCount() > 0){
                    mCalculator.onScrollStateIdle();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mCalculator.onScrolled(mScrollState);
            }
        });
    }




    private class MyAdapter extends BaseAdapter implements ItemsProvider {

        private List<? extends BaseItem> mListItems;
        private ArrayMap<BaseViewHolder, Integer> mHolderHelper;

        public MyAdapter() {
            mListItems = ItemUtils.generateMockData();
            mHolderHelper = new ArrayMap<>();
        }

        @Override
        public int getCount() {
            return mListItems.size();
        }

        @Override
        public BaseItem getItem(int position) {
            return mListItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).getViewType();
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @SuppressWarnings("unchecked")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BaseViewHolder holder;

            if (convertView == null) {
                holder = ViewHolderFactory.buildViewHolder(parent, getItemViewType(position));
                convertView = holder.itemView;
                convertView.setTag(holder);
            }
            else {
                holder = (BaseViewHolder) convertView.getTag();
            }

            holder.onBind(position, getItem(position));

            // record holder's position, must add ListView header's count
            mHolderHelper.put(holder, position + mListView.getHeaderViewsCount());

            return convertView;
        }

        @Override
        public ListItem getListItem(int position) {
            // find the list item
            int childCount = mListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = mListView.getChildAt(i);
                if(view != null) {
                    if (view.getTag() instanceof VideoViewHolder) {
                        VideoViewHolder holder = (VideoViewHolder) view.getTag();
                        int holderPosition = mHolderHelper.get(holder);
                        if (holderPosition == position) {
                            return holder;
                        }
                    }
                }
            }
            return null;
        }

        @Override
        public int listItemSize() {
            return getCount();
        }
    }



}
