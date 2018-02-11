package zenghao.com.study;

import android.support.multidex.MultiDex;
import com.facebook.stetho.Stetho;
import zenghao.com.study.applicationHelper.ApplicationHelper;
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



    /**
     * 这里对三方库的进行初始化
     * 比如网络框架、图片加载、即时通讯、数据库框架等
     * 很简单的几行代码搞定，维护起来也很方便。
     */
    private void initThirdLibs() {
        ApplicationHelper.init(this)
                .initNetWork()
                .initImageLoader()
                .initIM()
                .initDataBase();
    }
}
