package zenghao.com.study.bottomManger.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import zenghao.com.study.R;
import zenghao.com.study.bottomManger.other.lib.FragmentStateAdapter;

/**
 * TODO
 *
 * @author zenghao
 * @since 2017/5/9 下午6:04
 */
public class BottomAdatper extends FragmentStateAdapter {

    private static final int INDEX_BUFFER = 0;
    private static final int INDEX_RETREAT = 1;
    private static final int INDEX_VALUES = 2;

    public BottomAdatper(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case INDEX_BUFFER:
                return ImageFragment.newInstance(R.drawable.buffer);
            case INDEX_RETREAT:
                return ImageFragment.newInstance(R.drawable.retreat);
            case INDEX_VALUES:
                return ImageFragment.newInstance(R.drawable.values);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
