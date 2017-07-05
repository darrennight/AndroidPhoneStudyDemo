package zenghao.com.study.bottomManger.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import zenghao.com.study.R;
import zenghao.com.study.bottomManger.other.lib.AdaptableBottomNavigationView;
import zenghao.com.study.bottomManger.other.lib.ViewSwapper;

/**
 * https://github.com/bufferapp/AdaptableBottomNavigation
 *
 * @author zenghao
 * @since 2017/5/9 下午6:05
 */
public class BottomMangerOtherActivity extends AppCompatActivity {

    private AdaptableBottomNavigationView bottomNavigationView;
    private BottomAdatper viewSwapperAdapter;
    private ViewSwapper viewSwapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_other);

        bottomNavigationView = (AdaptableBottomNavigationView)
                findViewById(R.id.view_bottom_navigation);
        viewSwapper = (ViewSwapper) findViewById(R.id.view_swapper);
        viewSwapperAdapter = new BottomAdatper(getSupportFragmentManager());

        viewSwapper.setAdapter(viewSwapperAdapter);
        bottomNavigationView.setupWithViewSwapper(viewSwapper);
    }
}
