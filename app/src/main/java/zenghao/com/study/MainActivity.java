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

import zenghao.com.study.RX.RXTestActivity;
import zenghao.com.study.VHRecyleView.VPRecyleMainActivity;
import zenghao.com.study.activity.CaptureActivity;
import zenghao.com.study.activity.ItemListActivity;
import zenghao.com.study.activity.LogoFristActivity;
import zenghao.com.study.activity.MainSwipeActivity;
import zenghao.com.study.activity.PermissionActivity;
import zenghao.com.study.activity.PermissionFrameworkActivity;
import zenghao.com.study.activity.TestNotificationCompat;
import zenghao.com.study.activity.TestSlidingPaneLayout;
import zenghao.com.study.adapter.MyAdapter;
import zenghao.com.study.bottomManger.BottomMangerActivity;
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
        list.add("权限申请");
        list.add("分类表格");
        list.add("testTestNotificationCompat");
        list.add("TestSlidingPaneLayout");
        list.add("swipeitem滑动");
        list.add("capture");
        list.add("权限动态申请测试");
        list.add("splashvideo");
        list.add("悬浮监测");
        list.add("RXDemo");
        list.add("viewpager更新处理");
        list.add("bottom+fragment管理");
        list.add("横竖滑动");

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
