package zenghao.com.study.listActivityFragment.sample;

import android.os.Bundle;
import zenghao.com.study.R;
import zenghao.com.study.listActivityFragment.base.BaseActivity;

public class SampleSectionListFragmentActivity extends BaseActivity {
 
    private SampleSectionListFragment mSampleListFragment;
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    } 
 
    @Override 
    protected void setUpContentView() { 
        setContentView(R.layout.activity_sample_list_1, R.string.title_recycler_section_fragment);
    } 
 
    @Override 
    protected void setUpView() { 
        mSampleListFragment = new SampleSectionListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mSampleListFragmentLayout, mSampleListFragment).commit();
    } 
 
    @Override 
    protected void setUpData() { 
    } 
} 