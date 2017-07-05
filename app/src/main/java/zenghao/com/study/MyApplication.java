package zenghao.com.study;

import android.support.multidex.MultiDex;
import com.facebook.stetho.Stetho;
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
        //http://blog.csdn.net/sbsujjbcy/article/details/45420475
        //http://www.jianshu.com/p/a7fdcb2641e8
        //chrome://inspect/
        Stetho.initializeWithDefaults(this);
        MultiDex.install(this);
    }

    public static MyApplication getApplication() {
        return mInstance;
    }
}
