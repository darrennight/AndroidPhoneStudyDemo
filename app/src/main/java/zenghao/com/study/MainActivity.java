package zenghao.com.study;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import zenghao.com.study.DownLoad.DownLoadActivity;
import zenghao.com.study.DownLoadV2.DownLoadV2ActivityV2;
import zenghao.com.study.IME.TestHideIme;
import zenghao.com.study.IME.TestHideIme2;
import zenghao.com.study.IPC.Service.TestMutilProcessActivity;
import zenghao.com.study.Loader.TestLoaderActivity;
import zenghao.com.study.RX.RXTestActivity;
import zenghao.com.study.RecyclerViewDemo.TestDiffUtilActivity;
import zenghao.com.study.RxBus.RXBusMainActivity;
import zenghao.com.study.StatusBar.TestStatusBarActivity;
import zenghao.com.study.UI.RiseNumberActivity;
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
import zenghao.com.study.adapter.LVCommon.TestListCommonActivity;
import zenghao.com.study.adapter.MyAdapter;
import zenghao.com.study.banner.BannerActivity;
import zenghao.com.study.bottomManger.BottomMangerActivity;
import zenghao.com.study.commonActivity.CaptureVideoActivity;
import zenghao.com.study.commonActivity.ChangeBgColor;
import zenghao.com.study.commonActivity.CommonModifyActivity;
import zenghao.com.study.commonActivity.CountDownActivity;
import zenghao.com.study.commonActivity.CustomToastActivity;
import zenghao.com.study.commonActivity.EditTextContactsActivity;
import zenghao.com.study.commonActivity.FullScreenActivity;
import zenghao.com.study.commonActivity.GuidePopTisActivity;
import zenghao.com.study.commonActivity.LVTextClickActivity;
import zenghao.com.study.commonActivity.ListVideoActivity;
import zenghao.com.study.commonActivity.MoneyCovertActivity;
import zenghao.com.study.commonActivity.RoundProgress;
import zenghao.com.study.commonActivity.SnackBarActivity;
import zenghao.com.study.commonActivity.TestConstraintLayoutActivity;
import zenghao.com.study.commonActivity.TestFlowLayoutActivity;
import zenghao.com.study.commonActivity.TestPercentLayoutActivity;
import zenghao.com.study.commonActivity.ThemeActivity;
import zenghao.com.study.holdStyle.HoldMainActivity;
import zenghao.com.study.lazyFragment.LazyFragmentActivity;
import zenghao.com.study.listStatusSwitch.LoadingActivity;
import zenghao.com.study.listStatusSwitch.state.StateMainActivity;
import zenghao.com.study.localImageVideo.LocalImageActivity;
import zenghao.com.study.permissionBuilder.XPermissionTestActivity;
import zenghao.com.study.permissionFramework.TestPermissionActivity;
import zenghao.com.study.plugin.ReSkin.ReSkinActivity;
import zenghao.com.study.retrofit.TestRetrofitActivity;
import zenghao.com.study.suspension.MainActivityChild;
import zenghao.com.study.util.CommonUtil;
import zenghao.com.study.viewpager.NewBaseAdapter.SimpleActivity;
import zenghao.com.study.viewpager.VPUpdateFragment1;
import zenghao.com.study.viewpager.vpAnim.MyJazzPagerActivity;

public class MainActivity extends AppCompatActivity {
// 主界面 底部导航 fragment＋viewpager
    private ListView mListView;
    private MyAdapter adapter;
    private PackageManager mPm;
    private ComponentName mDefault;
    private ComponentName mDouble11;

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

        ViewGroup contentParent = ((ViewGroup) findViewById(android.R.id.content));
        View chile = contentParent.getChildAt(0);
        Log.e("====view",contentParent.getClass().getName().toString()+""+chile.getClass().getName().toString());

        mPm = getApplicationContext().getPackageManager();

        mDefault = getComponentName();
        mDouble11 = new ComponentName(getBaseContext(),"zenghao.com.study.TestAndroidAlias");

        /*修改启动icon
            bug 1.app会被清理kill 2.切换后马上退出app到home点击icon启动无法启动
            disableComponent(mDefault);
        enableComponent(mDouble11);*/

    }


    private void enableComponent(ComponentName componentName) {
        mPm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void disableComponent(ComponentName componentName) {
        mPm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
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
        list.add("24换肤");
        list.add("25本地图片扫描");
        list.add("26视频截图&标签文字跟随");
        list.add("27支付宝金额变化");
        list.add("28listview通用adapter&holder");
        list.add("29viewpager切换动画");
        list.add("30banner");
        list.add("31testLoader");
        list.add("32ListView中item文字点击");
        list.add("33页面加载状态切换");
        list.add("34下载框架测试");
        list.add("35下载框架V2多进程进度处理测试");
        list.add("36测试隐藏键盘方式一");
        list.add("37测试隐藏键盘方式二");
        list.add("38圆角进度");
        list.add("39listVideo");
        list.add("40百分比布局");
        list.add("41个人信息修改模版框架");
        list.add("42UI界面拆分");
        list.add("43pagerAdapter封装JakeWharton");
        list.add("44testFullScreen");
        list.add("45testThemeActivity");
        list.add("46EditText分块显示联系人");
        list.add("47短信验证码读取");
        list.add("48权限框架XPermission");
        list.add("49changeIcon");
        list.add("50snackbar封装");
        list.add("51新手引导");
        list.add("52金额转化");
        list.add("53倒计时");
        list.add("54flowlayout");
        list.add("55自定义toast");
        list.add("56页面加载状态切换");
        list.add("57retrofit+APK升级");

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
                    case 24:
                        Intent intent24 = new Intent(MainActivity.this, ReSkinActivity.class);
                        startActivity(intent24);
                        break;
                    case 25:
                        Intent intent25 = new Intent(MainActivity.this, LocalImageActivity.class);
                        startActivity(intent25);
                        break;

                    case 26:
                        Intent intent26 = new Intent(MainActivity.this, CaptureVideoActivity.class);
                        startActivity(intent26);
                        break;
                    case 27:
                        Intent intent27 = new Intent(MainActivity.this, RiseNumberActivity.class);
                        startActivity(intent27);
                        break;
                    case 28:
                        Intent intent28 = new Intent(MainActivity.this, TestListCommonActivity.class);
                        startActivity(intent28);
                        break;
                    case 29:
                        Intent intent29 = new Intent(MainActivity.this, MyJazzPagerActivity.class);
                        startActivity(intent29);
                        break;
                    case 30:
                        Intent intent30 = new Intent(MainActivity.this, BannerActivity.class);
                        startActivity(intent30);
                        break;
                    case 31:
                        Intent intent31 = new Intent(MainActivity.this, TestLoaderActivity.class);
                        startActivity(intent31);
                        break;
                    case 32:
                        Intent intent32 = new Intent(MainActivity.this, LVTextClickActivity.class);
                        startActivity(intent32);
                        break;
                    case 33:
                        Intent intent33 = new Intent(MainActivity.this, LoadingActivity.class);
                        startActivity(intent33);
                        break;
                    case 34:
                        Intent intent34 = new Intent(MainActivity.this, DownLoadActivity.class);
                        startActivity(intent34);
                        break;
                    case 35:
                        Intent intent35 = new Intent(MainActivity.this, DownLoadV2ActivityV2.class);
                        startActivity(intent35);
                        break;

                    case 36:
                        Intent intent36 = new Intent(MainActivity.this, TestHideIme.class);
                        startActivity(intent36);
                        break;

                    case 37:
                        Intent intent37 = new Intent(MainActivity.this, TestHideIme2.class);
                        startActivity(intent37);
                        break;

                    case 38:
                        Intent intent38 = new Intent(MainActivity.this, RoundProgress.class);
                        startActivity(intent38);
                        break;

                    case 39:
                        Intent intent39 = new Intent(MainActivity.this, ListVideoActivity.class);
                        startActivity(intent39);
                        break;

                    case 40:
                        Intent intent40 = new Intent(MainActivity.this, TestPercentLayoutActivity.class);
                        startActivity(intent40);
                        break;

                    case 41:
                        Intent intent41 = new Intent(MainActivity.this, CommonModifyActivity.class);
                        startActivity(intent41);
                        break;

                    case 42:
                        Intent intent42 = new Intent(MainActivity.this, HoldMainActivity.class);
                        startActivity(intent42);
                        break;

                    case 43:
                        Intent intent43 = new Intent(MainActivity.this, SimpleActivity.class);
                        startActivity(intent43);
                        break;

                    case 44:
                        Intent intent44 = new Intent(MainActivity.this, FullScreenActivity.class);
                        startActivity(intent44);
                        break;

                    case 45:
                        Intent intent45 = new Intent(MainActivity.this, ThemeActivity.class);
                        startActivity(intent45);
                        break;

                    case 46:
                        Intent intent46 = new Intent(MainActivity.this, EditTextContactsActivity.class);
                        startActivity(intent46);
                        break;

                    case 47:
                        Intent intent47 = new Intent(MainActivity.this, zenghao.com.study.commonActivity.sms.MainActivity.class);
                        startActivity(intent47);
                        break;

                    case 48:
                        Intent intent48 = new Intent(MainActivity.this, XPermissionTestActivity.class);
                        startActivity(intent48);
                        break;


                    case 49:
                        disableComponent(mDefault);
                        enableComponent(mDouble11);
                        /*//主动干掉桌面app，让它自动重启。
                        ActivityManager am = (ActivityManager)getSystemService(Activity.ACTIVITY_SERVICE);
                        Intent i = new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.addCategory(Intent.CATEGORY_DEFAULT);
                        List<ResolveInfo> resolves = mPm.queryIntentActivities(i, 0);
                        for (ResolveInfo res : resolves) {
                            if (res.activityInfo != null) {
                                am.killBackgroundProcesses(res.activityInfo.packageName);
                            }
                        }*/
                        break;

                    case 50:
                        Intent intent50 = new Intent(MainActivity.this, SnackBarActivity.class);
                        startActivity(intent50);
                        break;

                    case 51:
                        Intent intent51 = new Intent(MainActivity.this, GuidePopTisActivity.class);
                        startActivity(intent51);
                        break;

                    case 52:
                        Intent intent52 = new Intent(MainActivity.this, MoneyCovertActivity.class);
                        startActivity(intent52);
                        break;


                    case 53:
                        Intent intent53 = new Intent(MainActivity.this, CountDownActivity.class);
                        startActivity(intent53);
                        break;

                    case 54:
                        Intent intent54 = new Intent(MainActivity.this, TestFlowLayoutActivity.class);
                        startActivity(intent54);
                        break;


                    case 55:
                        Intent intent55 = new Intent(MainActivity.this, CustomToastActivity.class);
                        startActivity(intent55);
                        break;

                    case 56:
                        Intent intent56 = new Intent(MainActivity.this, StateMainActivity.class);
                        startActivity(intent56);
                        break;

                    case 57:
                        Intent intent57 = new Intent(MainActivity.this, TestRetrofitActivity.class);
                        startActivity(intent57);
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

    @Override
    protected void onResume() {
        super.onResume();
        boolean permis = CommonUtil.isNotifyEnable(this);
        Log.e("=====perm",permis+"");
        Toast.makeText(this,""+permis,Toast.LENGTH_SHORT).show();
    }
}
