package zenghao.com.study.bottomManger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by zenghao on 16/7/14.
 */
public class FragmentNavigator {

    private FragmentManager mManager;
    private FragmentNavigatorAdapter mAdapter;
    /**替换fragment的资源ID*/
    private int mContainerViewId;
    /**当前选择的position*/
    private int mCurrentPosition = -1;
    private int mDefaultPosition;
    private static final String EXTRA_CURRENT_POSITION = "extra_current_position";
    public FragmentNavigator(FragmentManager manager , FragmentNavigatorAdapter adapter,int containerId){
        this.mManager = manager;
        this.mAdapter = adapter;
        this.mContainerViewId = containerId;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(EXTRA_CURRENT_POSITION, mDefaultPosition);
        }
    }

    public void onSaveInstanceState(Bundle outState){
        outState.putInt(EXTRA_CURRENT_POSITION, mCurrentPosition);
    }

    public void showFragment(int position){
        showFragment(position,false);
    }
    public void showFragment(int position, boolean reset) {
        this.mCurrentPosition = position;
        FragmentTransaction transaction = mManager.beginTransaction();
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            if (position == i) {
                if (reset) {
                    remove(position, transaction);
                    add(position, transaction);
                } else {
                    show(i, transaction);
                }
            } else {
                hide(i, transaction);
            }
        }
            transaction.commitAllowingStateLoss();
    }


    /**
     * reset all the fragments and show current fragment
     *
     * @see #resetFragments(int)
     */
    public void resetFragments() {
        resetFragments(mCurrentPosition);
    }

    /**
     * @see #resetFragments(int, boolean)
     */
    public void resetFragments(int position) {
        resetFragments(position, false);
    }

    /**
     * reset all the fragment and show given position fragment
     *
     * @param position fragment position
     * @param allowingStateLoss true if allowing state loss otherwise false
     */
    public void resetFragments(int position, boolean allowingStateLoss) {
        this.mCurrentPosition = position;
        FragmentTransaction transaction = mManager.beginTransaction();
        removeAll(transaction);
        add(position, transaction);
        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    /**
     * @see #removeAllFragment(boolean)
     */
    public void removeAllFragment() {
        removeAllFragment(false);
    }

    /**
     * remove all fragment in the {@link FragmentManager}
     *
     * @param allowingStateLoss true if allowing state loss otherwise false
     */
    public void removeAllFragment(boolean allowingStateLoss) {
        FragmentTransaction transaction = mManager.beginTransaction();
        removeAll(transaction);
        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    /**
     * @return current showing fragment's position
     */
    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    /**
     * Also @see #getFragment(int)
     *
     * @return current position fragment
     */
    public Fragment getCurrentFragment() {
        return getFragment(mCurrentPosition);
    }

    /**
     * Get the fragment has been added in the given position. Return null if the fragment
     * hasn't been added in {@link FragmentManager} or has been removed already.
     *
     * @param position position of fragment in {@link FragmentNavigatorAdapter#onCreateFragment(int)}}
     *                 and {@link FragmentNavigatorAdapter#getTag(int)}
     * @return The fragment if found or null otherwise.
     */
    public Fragment getFragment(int position) {
        String tag = mAdapter.getTag(position);
        return mManager.findFragmentByTag(tag);
    }

    private void show(int position, FragmentTransaction transaction) {
        String tag = mAdapter.getTag(position);
        Fragment fragment = mManager.findFragmentByTag(tag);
        if (fragment == null) {
            add(position, transaction);
        } else {
            transaction.show(fragment);
        }
    }

    private void hide(int position, FragmentTransaction transaction) {
        String tag = mAdapter.getTag(position);
        Fragment fragment = mManager.findFragmentByTag(tag);
        if (fragment != null) {
            transaction.hide(fragment);
        }
    }

    private void add(int position, FragmentTransaction transaction) {
        Fragment fragment = mAdapter.onCreateFragment(position);
        String tag = mAdapter.getTag(position);
        transaction.add(mContainerViewId, fragment, tag);
    }

    private void removeAll(FragmentTransaction transaction) {
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            remove(i, transaction);
        }
    }

    private void remove(int position, FragmentTransaction transaction) {
        String tag = mAdapter.getTag(position);
        Fragment fragment = mManager.findFragmentByTag(tag);
        if (fragment != null) {
            transaction.remove(fragment);
        }
    }

    public void setDefaultPosition(int defaultPosition) {
        this.mDefaultPosition = defaultPosition;
        if (mCurrentPosition == -1) {
            this.mCurrentPosition = defaultPosition;
        }
    }
}
