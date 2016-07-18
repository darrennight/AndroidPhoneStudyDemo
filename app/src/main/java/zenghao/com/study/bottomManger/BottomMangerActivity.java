package zenghao.com.study.bottomManger;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/7/14.
 * 底部导航＋fragment整体框架使用
 */
public class BottomMangerActivity extends FragmentActivity implements BottomNavigationView.OnBottomNavigationItemClick{

    private BottomNavigationView mBottomNavigator;
    private FragmentNavigator mNavigator;
    private static final int DEFAULT_POSITION = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_manger);

        mNavigator = new FragmentNavigator(getSupportFragmentManager(), new FragmentAdapter(), R.id.container);
        mNavigator.setDefaultPosition(DEFAULT_POSITION);
        mNavigator.onCreate(savedInstanceState);


        mBottomNavigator = ((BottomNavigationView) findViewById(R.id.bottomNavigatorView));
        mBottomNavigator.setOnBottomNavigationClick(this);
        mBottomNavigator.select(0);


    }

    @Override
    public void onNavigationItemClick(int position, View view) {
        if(mBottomNavigator.getmCurrentPos() != position){
            mBottomNavigator.select(position);
            mNavigator.showFragment(position);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mNavigator.onSaveInstanceState(outState);
    }
}
