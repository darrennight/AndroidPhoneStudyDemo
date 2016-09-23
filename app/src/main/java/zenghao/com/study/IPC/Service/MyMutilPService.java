package zenghao.com.study.IPC.Service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import zenghao.com.study.IPC.database.IPCContentProvider;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/9/18 下午4:50
 */
public class MyMutilPService extends Service {

    private String time;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("====", "onCreateonCreateonCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("====", "onStartCommandonStartCommand");
        String flag1 = intent.getStringExtra("flag1");
        Person person = intent.getParcelableExtra("bean");
        Log.e("====", "flag1" + flag1);
        Log.e("====", "person" + person);

        addBooks(person);

        //call方法使用
        calltest();

        return super.onStartCommand(intent, flags, startId);
    }

    public void calltest() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = IPCContentProvider.CALL_CONTENT_URI;
        Bundle bundle = contentResolver.call(uri, "method", null, null);
        String returnCall = bundle.getString("returnCall");
        Log.i("main", "-------------->" + returnCall);
    }

    public void addBooks(Person person) {
        Uri bookUri = IPCContentProvider.USER_CONTENT_URI;
        ContentValues values = new ContentValues();
        //values.put("_id", person.getAge());
        values.put("name", person.getName());
        values.put("sex", 1);
        getContentResolver().insert(bookUri, values);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends IHelloInterface.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getString() throws RemoteException {
            return time = System.currentTimeMillis() + "";
        }

        @Override
        public Person getPerson() throws RemoteException {
            Person p = new Person();
            p.setName("zhang");
            return p;
        }

        @Override
        public void registerCallback(IHelloCallback cb) throws RemoteException {
            mCallbacks.register(cb);
        }

        @Override
        public void unregisterCallback(IHelloCallback cb) throws RemoteException {
            mCallbacks.unregister(cb);
        }
    }

    /***
     * service 执行后调用这个方法将数据 回调给activity
     */
    public void onStart() {
        synchronized (mCallbacks) {
            final int N = mCallbacks.beginBroadcast();
            for (int i = 0; i < N; i++) {
                try {
                    mCallbacks.getBroadcastItem(i).onString("123456");
                } catch (RemoteException e) {
                    // The RemoteCallbackList will take care of removing
                    // the dead object for us.
                }
            }
            mCallbacks.finishBroadcast();
        }
    }

    final RemoteCallbackList<IHelloCallback> mCallbacks = new RemoteCallbackList<IHelloCallback>();
}
