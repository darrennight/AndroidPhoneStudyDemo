package zenghao.com.study.suspension;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sevenheaven.iosswitch.ShSwitchView;

import zenghao.com.study.R;

public class MainActivityChild extends AppCompatActivity {

    private Button mStart;
    private Button mSotp;
    private DetectionService detectionService;
    private MyWindowManager manager ;
    private SharedPreferences preferences;
    private ShSwitchView mSwitch;
    private FloatingActionButton mFab;
    private LinearLayout mRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_child);
        preferences = getSharedPreferences("watch",MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        detectionService = DetectionService.getInstance();
        mStart = ((Button) this.findViewById(R.id.btn_start));
        mSotp = ((Button) this.findViewById(R.id.btn_stop));
        mFab = ((FloatingActionButton) findViewById(R.id.fab));
        mRootLayout = ((LinearLayout) findViewById(R.id.layout_root));
        mSwitch = ((ShSwitchView) findViewById(R.id.switch_view));
        //mSwitch.setTintColor(Color.YELLOW);设置OK颜色


        if (!isAccessibilitySettingsOn(this)){
            SharedPreferences.Editor editor =  preferences.edit();
            editor.putBoolean("watchflag",false);
            editor.commit();
        }
        boolean flag = preferences.getBoolean("watchflag",false);
        if(flag){
            mSwitch.setOn(true);
            mRootLayout.setBackgroundColor(Color.parseColor("#cc9CE949"));
        }
        mSwitch.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                if(isOn){
                    mRootLayout.setBackgroundColor(Color.parseColor("#cc9CE949"));
                    SharedPreferences.Editor editor =  preferences.edit();
                    editor.putBoolean("watchflag",true);
                    editor.commit();
                    anyMethod();
                }else{
                    mRootLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    SharedPreferences.Editor editor =  preferences.edit();
                    editor.putBoolean("watchflag",false);
                    editor.commit();
                    if(manager!=null){
                        manager.removeWindow(getApplicationContext());
                    }else{
                        manager =  MyWindowManager.getInstance();
                        manager.removeWindow(getApplicationContext());
                    }
                }
            }
        });

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SharedPreferences.Editor editor =  preferences.edit();
                editor.putBoolean("watchflag",true);
                editor.commit();
                anyMethod();
            }
        });
        mSotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor =  preferences.edit();
                editor.putBoolean("watchflag",false);
                editor.commit();
                if(manager!=null){
                    manager.removeWindow(getApplicationContext());
                }else{
                  manager =  MyWindowManager.getInstance();
                    manager.removeWindow(getApplicationContext());
                }

            }
        });


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            startActivity(new Intent(MainActivityChild.this,GuideActivity.class));

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
        manager =  MyWindowManager.getInstance();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }else if(id == R.id.action_top){
            manager.upLocation(getApplicationContext(), Gravity.TOP);
        }else if(id == R.id.action_mid){
            manager.upLocation(getApplicationContext(), Gravity.CENTER_VERTICAL);
        }else if(id == R.id.action_bottom){
            manager.upLocation(getApplicationContext(), Gravity.BOTTOM);
        }

        return super.onOptionsItemSelected(item);
    }
    final static String TAG = "AccessibilityUtil";


    // 此方法用来判断当前应用的辅助功能服务是否开启
    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.i(TAG, e.getMessage());
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!isAccessibilitySettingsOn(this)){
            mRootLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            mSwitch.setOn(false);
        }
    }

    private void anyMethod() {
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn(this)) {
            // 引导至辅助功能设置页面
            startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS),100);

        } else {
            // 执行辅助功能服务相关操作
            manager = MyWindowManager.getInstance();
            manager.createWindow(getApplicationContext());
        }
    }


}


//    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//    startActivity(intent);
//                Intent intent = new Intent(MainActivity.this,WatchService.class);
//                stopService(intent);
//            Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
//            startActivityForResult(intent, 0);
//if(detectionService != null){
//        detectionService.stopSelf();
//        }