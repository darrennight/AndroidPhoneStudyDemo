package zenghao.com.study.bottomManger;

import android.support.v4.app.Fragment;

/**
 * Created by aspsine on 16/4/3.
 */
public class ChildFragmentAdapter implements FragmentNavigatorAdapter {

    public static final String[] TABS = {"Friends", "Groups", "Official"};

    @Override
    public Fragment onCreateFragment(int position) {
        return MainFragment.newInstance(TABS[position]);
    }

    @Override
    public String getTag(int position) {
        return MainFragment.TAG + TABS[position];
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}
