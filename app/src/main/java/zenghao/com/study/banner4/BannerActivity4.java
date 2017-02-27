package zenghao.com.study.banner4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import zenghao.com.study.R;

/**
 * TODO
 *https://github.com/BoBoMEe/AutoScrollLoopViewPager
 * https://github.com/BoBoMEe/DrawableIndicator
 * @author zenghao
 * @since 17/2/8 下午4:03
 */
public class BannerActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner4);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_in_vp);
        if (null != tabLayout && null != viewPager) {
            //viewPager.setAdapter(new TabViewPagerAdapter(getSupportFragmentManager()));
            //tabLayout.setupWithViewPager(viewPager);
        }
    }


}
