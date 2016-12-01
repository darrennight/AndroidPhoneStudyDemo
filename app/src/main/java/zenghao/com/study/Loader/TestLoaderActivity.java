package zenghao.com.study.Loader;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import zenghao.com.study.R;

/**
 *
 * @author zenghao
 * @since 16/11/28 下午6:51
 */
public class TestLoaderActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private LoaderManager manager = null;
    private Button mBtnAdd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_loader);
        mBtnAdd = ((Button) findViewById(R.id.btn_add));

        manager = getLoaderManager();
        //第一个参数 id 随意  第二个参数Bundle onCreateLoader中接收
        manager.initLoader(1000, null, this);
        //manager.restartLoader()
        //manager.destroyLoader();


        //使用CursorLoader 时 监听数据库的变化重新通过loader加载数据 自定义的就不用这段代码
        /*getContentResolver().registerContentObserver(MyContentProvider.uri, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                //manager.restartLoader(1000,null,TestLoaderActivity.this);
                //manager.destroyLoader(1000);//这句调用后会执行onLoaderReset
            }
        });*/
        setListener();
    }

    private void setListener(){
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = MyContentProvider.uri;
                ContentValues values = new ContentValues();
                values.put("name","zhang");
                values.put("age",1);
                getContentResolver().insert(uri,values);

               Cursor userCursor = getContentResolver().query(uri,new String[]{"_id", "name", "age"}, null, null, null);
                if (userCursor != null) {
                    while (userCursor.moveToNext()) {
                        String name = userCursor.getString(userCursor.getColumnIndex("name"));
                        int age = userCursor.getInt(userCursor.getColumnIndex("age"));
                        Log.e("====haha",name+age);
                    }
                    userCursor.close();
                }
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                Uri.parse("content://com.app.contentprovider"), null, null,
                null, null);
        SpecialLoader loader1 = new SpecialLoader(this);
        return loader1;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //解析cursor
        if(cursor!=null){
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                Log.e("=====",name+age);
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //重置相关旧数据
        Log.e("====","onLoaderResetonLoaderReset");
    }

}
