package zenghao.com.study.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import zenghao.com.study.R;
import zenghao.com.study.view.ResideLayout;

/**
 * 其它库
 * https://github.com/SpecialCyCi/AndroidResideMenu
 * https://github.com/jfeinstein10/SlidingMenu
 *
 * @author zenghao
 * @since 2018/1/18 下午4:07
 */
public class ResideLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reside_layout);
        final ResideLayout resideLayout = (ResideLayout) findViewById(R.id.reside_layout);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager()));
        ListView menu = (ListView) findViewById(R.id.menu);
        menu.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[] {
                "menu1", "menu2", "menu3"
        }));
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ResideLayoutActivity.this, "Menu " + ++position + " selected.",
                        Toast.LENGTH_SHORT).show();
                resideLayout.closePane();
            }
        });
    }

    private class SimplePagerAdapter extends FragmentPagerAdapter {

        public SimplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return SimpleFragment.newInstance(i);
        }

        @Override
        public int getCount() {
            return 5;
        }


    }

    public static class SimpleFragment extends Fragment{

        private int mIndex;
        private TextView mView;

        public static SimpleFragment newInstance(int index){
            SimpleFragment fragment = new SimpleFragment();
            Bundle args = new Bundle();
            args.putInt("index", index);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mIndex = getArguments().getInt("index");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
            mView = new TextView(container.getContext());
            mView.setBackgroundColor(Color.WHITE);
            return mView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mView.setText("Fragment: " + mIndex);
        }
    }
}