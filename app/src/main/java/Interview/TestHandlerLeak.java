package Interview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * 解决handler内存泄露问题
 *
 * @author zenghao
 * @since 2019-10-12 14:10
 */
public class TestHandlerLeak extends Activity {

    private static class LeakHandler extends Handler{
        private WeakReference<TestHandlerLeak> mWeakReference ;
        public LeakHandler(TestHandlerLeak activity){
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Activity activity = mWeakReference.get();
            if(activity!=null){
                //实现相应逻辑
            }
        }
    }

    private LeakHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new LeakHandler(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mHandler.removeCallbacksAndMessages(null);
    }
}
