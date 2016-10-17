package zenghao.com.study;

import zenghao.com.study.plugin.ReSkin.base.SkinBaseApplication;

/**
 * Created by zenghao on 16/5/20.
 */
public class MyApplication extends SkinBaseApplication {

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
