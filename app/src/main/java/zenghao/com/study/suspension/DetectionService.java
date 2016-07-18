package zenghao.com.study.suspension;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by shawn
 * Data: 2/3/2016
 * Blog: effmx.com
 */
public class DetectionService extends AccessibilityService {

    final static String TAG = "DetectionService";

    static String foregroundPackageName;


    private static DetectionService mInstance = null;
    private SharedPreferences preferences;

    public DetectionService() {

    }


    public static DetectionService getInstance() {
        if (mInstance == null) {
            synchronized (DetectionService.class) {
                if (mInstance == null) {
                    mInstance = new DetectionService();
                }
            }
        }
        return mInstance;
    }
   private MyWindowManager manager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0; // 根据需要返回不同的语义值
    }


    /**
     * 重载辅助功能事件回调函数，对窗口状态变化事件进行处理
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        preferences = getSharedPreferences("watch",MODE_PRIVATE);
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
          boolean flag =   preferences.getBoolean("watchflag",false);
            if(flag){
                if(manager==null){
                    manager = MyWindowManager.getInstance();
                    manager.createWindow(getApplicationContext());
                }

            /*
             * 如果 与 DetectionService 相同进程，直接比较 foregroundPackageName 的值即可
             * 如果在不同进程，可以利用 Intent 或 bind service 进行通信
             */
                foregroundPackageName = event.getPackageName().toString();

            /*
             * 基于以下还可以做很多事情，比如判断当前界面是否是 Activity，是否系统应用等，
             * 与主题无关就不再展开。
             */
                //ComponentName cName = new ComponentName(event.getPackageName().toString(),event.getClassName().toString());
//            Log.e("====","foregroundPackageName"+foregroundPackageName);
                //Log.e("====","foregroundPackageName"+event.getClassName());

                manager.upContent(event.getClassName().toString());
//            添加sharepre 判断是否显示
            }

        }




    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected  void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyWindowManager manager = MyWindowManager.getInstance();
        manager.removeWindow(getApplicationContext());
    }
}


      /*  /获取事件具体信息
        Parcelable parcelable = event.getParcelableData();
        //如果是下拉通知栏消息
        if(parcelable instanceof Notification){
        } else {
        //其它通知信息，包括Toast
        String toastMsg = (String) event.getText().get(0);
        String log = "Latest Toast Message: "+toastMsg+" [Source: "+sourcePackageName+"]";
        System.out.println(log);
        }
        //        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
//        if(nodeInfo!=null){
//            Log.e("====","foregroundPackageName"+nodeInfo.getViewIdResourceName());
//        }
        */