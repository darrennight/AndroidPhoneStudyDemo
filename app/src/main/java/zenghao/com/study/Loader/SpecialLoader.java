package zenghao.com.study.Loader;

import android.content.Context;
import android.database.Cursor;

/***
 * 自定义loader 对相应对uri监听了 能监听数据库对变化 自动调用onLoadFinished方法
 * 可以自定义loader 实现自己对需求
 */
public class SpecialLoader extends SimpleCursorLoader {

    ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    private Context context;

    public SpecialLoader(Context context) {
        super(context);
        this.context = context;

    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = getContext().getContentResolver().query(MyContentProvider.uri,new String[]{"_id", "name", "age"}, null, null, null);
            if (cursor != null) {
                //注册一下这个观察者
                cursor.registerContentObserver(mObserver);
                //这边也要注意 一定要监听这个uri的变化。但是如果你这个uri没有对应的provider的话
                //记得在你操作数据库的时候 通知一下这个uri
                cursor.setNotificationUri(context.getContentResolver(), MyContentProvider.uri);
            }

        return cursor;
    }
}