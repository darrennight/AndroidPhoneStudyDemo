package zenghao.com.study.lazyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/7/24.
 */
public class LazyFragmentActivity extends FragmentActivity {
    private ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_fragment);
        viewPager = (ViewPager) findViewById(R.id.vp_lazy);

        mList = new ArrayList<>();
        LazyFirstFragment lazyFirstFragment = new LazyFirstFragment();
        LazySecondFragment lazySecondFragment = new LazySecondFragment();
        mList.add(lazyFirstFragment);
        mList.add(lazySecondFragment);


        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        };

        viewPager.setAdapter(mAdapter);
    }

}
