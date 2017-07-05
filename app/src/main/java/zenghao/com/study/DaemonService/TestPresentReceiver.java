package zenghao.com.study.DaemonService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * TODO
 *
 * @author zenghao
 * @since 2017/5/8 下午3:55
 */
public class TestPresentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("=====","==TestPresentReceiver");
    }
}
