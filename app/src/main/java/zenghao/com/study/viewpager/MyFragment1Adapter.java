package zenghao.com.study.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zenghao on 16/7/1.
 * FragmentPagerAdapter 中的fragment没有被remove只是被detach了  内存中会一直存在
 * 即使adapter是重新new出来的  但是FragmentManager 一直保存原有的fragment
 * 界面销毁重建时 出现的还是原来的fragment
 *
 * 而FragmentStatePagerAdapter
 * 使用 @Override
 public int getItemPosition(Object object) {
 return POSITION_NONE;
 }即可刷新
 */
public class MyFragment1Adapter extends FragmentPagerAdapter {

    private FragmentManager manager;
    private List<MyFragment1> fragments = new ArrayList<>();
    public boolean[] mUpdateFlag;

    public MyFragment1Adapter(FragmentManager fm) {
        super(fm);
        manager = fm;
    }

    public void setData(List<MyFragment1> fragments){
        this.fragments = fragments;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //执行notifyDataSetChanged();时走这个方法
        //得到缓存的fragment
        Fragment fragment = (Fragment)super.instantiateItem(container,position);
        //得到tag
        String fragmentTag = fragment.getTag();

        if (mUpdateFlag[position % mUpdateFlag.length]) {
            //如果这个fragment需要更新

            FragmentTransaction ft =manager.beginTransaction();

            //移除旧的fragment

            ft.remove(fragment);
            ft.commit();


            //换成新的fragment

            fragment =fragments.get(position %fragments.size());

            //添加新fragment时必须用前面获得的tag ❶

            ft =manager.beginTransaction();

            ft.add(container.getId(), fragment, fragmentTag);

            ft.attach(fragment);
            ft.commit();




            //复位更新标志

            mUpdateFlag[position %mUpdateFlag.length] =false;
        }

        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    public void updateViewOne(List<MyFragment1> fragments) {
        if(this.fragments != null){
            FragmentTransaction ft = manager.beginTransaction();
            for(Fragment f:this.fragments){
                ft.remove(f);
            }
            ft.commit();
            manager.executePendingTransactions();
            this.fragments.clear();
            this.fragments.addAll(fragments);
            notifyDataSetChanged();
        }

    }

    public void updateViewTwo(){
        List<Fragment> fragments =   manager.getFragments();
        if(fragments!=null && !fragments.isEmpty()){
            for(Fragment fragment : fragments){
                if(fragment instanceof MyFragment1){
                    MyFragment1 child = ((MyFragment1) fragment);
                    child.updateTxt(""+System.currentTimeMillis());
                }
            }
        }
    }
}
