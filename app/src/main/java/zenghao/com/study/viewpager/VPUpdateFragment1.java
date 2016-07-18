package zenghao.com.study.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/7/1.
 * viewpager 刷新 fragment
 */
public class VPUpdateFragment1 extends FragmentActivity {

    private ViewPager mViewPager;
    private MyFragment1Adapter mAdapter;
    private Button mUpdate1;
    private Button mUpdate2;
    private Button mUpdate3;
    List<MyFragment1> list = new ArrayList<>();
    boolean[] fragmentsUpdateFlag = { false, false};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp_fragment1);
        mUpdate1 = ((Button) findViewById(R.id.btn_update1));
        mUpdate2 = ((Button) findViewById(R.id.btn_update2));
        mUpdate3 = ((Button) findViewById(R.id.btn_update3));
        mViewPager = ((ViewPager) findViewById(R.id.vp_viewpager));
        mAdapter = new MyFragment1Adapter(getSupportFragmentManager());
        MyFragment1 fragment1 = MyFragment1.newInstance(""+ System.currentTimeMillis());
        MyFragment1 fragment2 = MyFragment1.newInstance(""+ System.currentTimeMillis());

        list.add(fragment1);
        list.add(fragment2);
        mAdapter.mUpdateFlag = fragmentsUpdateFlag;
//        mAdapter.updateViewOne(list);
        mAdapter.setData(list);
        mViewPager.setAdapter(mAdapter);
        setListener();
    }


    private void setListener(){
        mUpdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFragment1 fragment1 = MyFragment1.newInstance(""+ System.currentTimeMillis());
                List<MyFragment1> list = new ArrayList<>();
                list.add(fragment1);
                mAdapter.updateViewOne(list);
            }
        });

        mUpdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.updateViewTwo();
            }
        });

        mUpdate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int i =  getSupportFragmentManager().getFragments().size();
                Log.e("====","===size"+i);
                //需要重新new一个fragment 因为 之前的fragment被remove了null；
                list.remove(list.get(1));
                list.add(MyFragment1.newInstance(""+System.currentTimeMillis()));
//                list.get(1).updateTxt(""+System.currentTimeMillis());
                fragmentsUpdateFlag[1] = true;
                mAdapter.mUpdateFlag = fragmentsUpdateFlag;
                mAdapter.notifyDataSetChanged();
            }
        });
    }

}
