package zenghao.com.study;

import android.app.Activity;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import zenghao.com.study.applicationHelper.ApplicationHelper;
import zenghao.com.study.measureUI.sample.CustomAttribution;
import zenghao.com.study.measureUI.sample.FilterOutView;
import zenghao.com.study.measureUI.uetool.UETool;
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




        Fresco.initialize(this);

        UETool.putFilterClass(FilterOutView.class);
        UETool.putAttrsProviderClass(CustomAttribution.class);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            private int visibleActivityCount;
            private int uetoolDismissY = -1;

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                visibleActivityCount++;
                if (visibleActivityCount == 1 && uetoolDismissY >= 0) {
                    UETool.showUETMenu(uetoolDismissY);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                visibleActivityCount--;
                if (visibleActivityCount == 0) {
                    uetoolDismissY = UETool.dismissUETMenu();
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
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
