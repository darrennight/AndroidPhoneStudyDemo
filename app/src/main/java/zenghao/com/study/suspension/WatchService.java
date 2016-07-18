package zenghao.com.study.suspension;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * Created by zenghao on 16/5/3.
 */
public class WatchService extends Service {


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            MyWindowManager manager = MyWindowManager.getInstance();
            manager.upContent(bundle.getString("name"));
        }
    };
    private Thread mTashThread;
    private boolean flag;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyWindowManager manager = MyWindowManager.getInstance();
        manager.createWindow(getApplicationContext());
        flag = true;

        mTashThread = new Thread() {
            @Override
            public void run() {
                super.run();
                String name = "";
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

                while (flag) {
//                    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                        UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService(Context.USAGE_STATS_SERVICE);
//                    }
// else{
//                       name  = am.getRunningTasks(1).get(0).topActivity.getClassName();
//                    }
                    String s = am.getRunningTasks(1).get(0).topActivity.getClassName();
//                    String s = getTopPackage(mUsageStatsManager,new RecentUseComparator());

                    if (!name.equalsIgnoreCase(s)) {
                        name = s;
                        Message msg = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", s);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                    }
                }
            }
        };
        mTashThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyWindowManager manager = MyWindowManager.getInstance();
        manager.removeWindow(getApplicationContext());
        flag = false;
        if (mTashThread != null && mTashThread.isAlive()) {
            mTashThread.interrupt();
        }
    }
//  private   String getTopPackage(UsageStatsManager mUsageStatsManager,RecentUseComparator mRecentComp){
//        long ts = System.currentTimeMillis();
//
//        List<UsageStats> usageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts-1000*1000, ts);
//        if (usageStats == null || usageStats.size() == 0) {
//            return "";
//        }
//      Collections.sort(usageStats, mRecentComp);
//        return usageStats.get(0).getPackageName();
//    }
//
//    static class RecentUseComparator implements Comparator<UsageStats> {
//
//        @Override
//        public int compare(UsageStats lhs, UsageStats rhs) {
//            return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed()) ? -1 : (lhs.getLastTimeUsed() == rhs.getLastTimeUsed()) ? 0 : 1;
//        }
//    }
}
