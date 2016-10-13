package zenghao.com.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zenghao.com.study.IPC.Service.TestMutilProcessActivity;
import zenghao.com.study.RX.RXTestActivity;
import zenghao.com.study.RecyclerViewDemo.TestDiffUtilActivity;
import zenghao.com.study.RxBus.RXBusMainActivity;
import zenghao.com.study.StatusBar.TestStatusBarActivity;
import zenghao.com.study.VHRecyleView.VPRecyleMainActivity;
import zenghao.com.study.VHRecyleView2.VHListUIActivity;
import zenghao.com.study.activity.CaptureActivity;
import zenghao.com.study.activity.ItemListActivity;
import zenghao.com.study.activity.LogoFristActivity;
import zenghao.com.study.activity.MainSwipeActivity;
import zenghao.com.study.activity.PermissionActivity;
import zenghao.com.study.activity.PermissionFrameworkActivity;
import zenghao.com.study.activity.SplashVideoActivity;
import zenghao.com.study.activity.TestConstraintLayout;
import zenghao.com.study.activity.TestNotificationCompat;
import zenghao.com.study.activity.TestSlidingPaneLayout;
import zenghao.com.study.adapter.MyAdapter;
import zenghao.com.study.bottomManger.BottomMangerActivity;
import zenghao.com.study.commonActivity.ChangeBgColor;
import zenghao.com.study.commonActivity.TestConstraintLayoutActivity;
import zenghao.com.study.lazyFragment.LazyFragmentActivity;
import zenghao.com.study.permissionFramework.TestPermissionActivity;
import zenghao.com.study.suspension.MainActivityChild;
import zenghao.com.study.viewpager.VPUpdateFragment1;

public class MainActivity extends AppCompatActivity {
// 主界面 底部导航 fragment＋viewpager
    private ListView mListView;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();

    }

    private void initView(){
        mListView = ((ListView) this.findViewById(R.id.lv_list));
        List<String> list = new ArrayList<>();
        list.add("0权限申请");
        list.add("1分类表格");
        list.add("2testTestNotificationCompat");
        list.add("3TestSlidingPaneLayout");
        list.add("4swipeitem滑动");
        list.add("5capture");
        list.add("6权限动态申请测试");
        list.add("7splashvideo");
        list.add("8悬浮监测");
        list.add("9RXDemo");
        list.add("10viewpager更新处理");
        list.add("11bottom+fragment管理");
        list.add("12横竖滑动");
        list.add("13lazyFragment");
        list.add("14statusBarCompat");
        list.add("15框架式动态权限申请");
        list.add("16RXBus测试");
        list.add("17testRcDiff");
        list.add("18TestConstraintLayout");
        list.add("19颜色过渡切换");
        list.add("20IPC测试");
        list.add("21测试ConstraintLayout");
        list.add("22水平垂直滑动列表");
        list.add("23引导页视频");

        adapter = new MyAdapter(this,list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, PermissionActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, ItemListActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this,TestNotificationCompat.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, TestSlidingPaneLayout.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MainActivity.this, MainSwipeActivity.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(MainActivity.this, CaptureActivity.class);
                        startActivity(intent5);
                    break;
                    case 6:
                        Intent intent6 = new Intent(MainActivity.this,PermissionFrameworkActivity.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(MainActivity.this, LogoFristActivity.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(MainActivity.this, MainActivityChild.class);
                        startActivity(intent8);
                    break;
                    case 9:
                        Intent intent9 = new Intent(MainActivity.this, RXTestActivity.class);
                        startActivity(intent9);
                        break;
                    case 10:
                        Intent intent10 = new Intent(MainActivity.this, VPUpdateFragment1.class);
                        startActivity(intent10);
                        break;
                    case 11:
                        Intent intent11 = new Intent(MainActivity.this, BottomMangerActivity.class);
                        startActivity(intent11);
                        break;

                    case 12:
                        Intent intent12 = new Intent(MainActivity.this, VPRecyleMainActivity.class);
                        startActivity(intent12);
                        break;

                    case 13:
                        Intent intent13 = new Intent(MainActivity.this, LazyFragmentActivity.class);
                        startActivity(intent13);
                        break;

                    case 14:
                        Intent intent14 = new Intent(MainActivity.this, TestStatusBarActivity.class);
                        startActivity(intent14);
                        break;

                    case 15:
                        Intent intent15 = new Intent(MainActivity.this, TestPermissionActivity.class);
                        startActivity(intent15);
                        break;
                    case 16:
                        Intent intent16 = new Intent(MainActivity.this, RXBusMainActivity.class);
                        startActivity(intent16);
                        break;

                    case 17:
                        Intent intent17 = new Intent(MainActivity.this, TestDiffUtilActivity.class);
                        startActivity(intent17);
                        break;

                    case 18:
                        Intent intent18 = new Intent(MainActivity.this, TestConstraintLayout.class);
                        startActivity(intent18);
                        break;
                    case 19:
                        Intent intent19 = new Intent(MainActivity.this, ChangeBgColor.class);
                        startActivity(intent19);
                        break;
                    case 20:
                        Intent intent20 = new Intent(MainActivity.this, TestMutilProcessActivity.class);
                        startActivity(intent20);
                        break;
                    case 21:
                        Intent intent21 = new Intent(MainActivity.this, TestConstraintLayoutActivity.class);
                        startActivity(intent21);
                        break;

                    case 22:
                        Intent intent22 = new Intent(MainActivity.this, VHListUIActivity.class);
                        startActivity(intent22);
                        break;
                    case 23:
                        Intent intent23 = new Intent(MainActivity.this, SplashVideoActivity.class);
                        startActivity(intent23);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(adapter.flag){
                adapter.flag = false;
                adapter.notifyDataSetChanged();
            }else{
                adapter.flag = true;
                adapter.notifyDataSetChanged();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
