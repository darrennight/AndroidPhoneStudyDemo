package zenghao.com.study.listActivityFragment.sample;
 
import android.os.Bundle;
import zenghao.com.study.R;
import zenghao.com.study.listActivityFragment.base.BaseActivity;

/** 
 * Created by Stay on 2/2/16. 
 * Powered by www.stay4it.com 
 */ 
public class SampleListFragmentActivity extends BaseActivity {
 
    private SampleListFragment mSampleListFragment;
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    } 
 
    @Override 
    protected void setUpContentView() { 
        setContentView(R.layout.activity_sample_list_1, R.string.title_recycler_fragment);
    } 
 
    @Override 
    protected void setUpView() { 
        mSampleListFragment = new SampleListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mSampleListFragmentLayout, mSampleListFragment).commit();
    } 
 
    @Override 
    protected void setUpData() { 
    } 
} 