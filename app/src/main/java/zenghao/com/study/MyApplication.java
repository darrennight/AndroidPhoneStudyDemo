package zenghao.com.study;

import android.app.Application;

/**
 * Created by zenghao on 16/5/20.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getApplication() {
        return mInstance;
    }
}
