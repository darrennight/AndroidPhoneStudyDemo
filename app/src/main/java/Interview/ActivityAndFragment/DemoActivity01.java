package Interview.ActivityAndFragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;

/**
 * activity和fragment通信Handler
 *Fragment对具体的Activity存在耦合，不利于Fragment复用
 *
 * 不利于维护，若想删除相应的Activity，Fragment也得改动
 *
 * 没法获取Activity的返回数据
 *
 * 所以一般不建议使用这种方法。
 * @author zenghao
 * @since 2019-10-12 14:37
 */
public class DemoActivity01 extends Activity {
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };



}


