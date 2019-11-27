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
import zenghao.com.study.DownLoadV2.provider.DownLoadProviderV2;
import zenghao.com.study.IPC.Service.bean.Person;
import zenghao.com.study.IPC.database.IPCContentProvider;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/9/18 下午4:50
 */
public class MyMutilPService extends Service {

    private String time;

    private static final String TAG = " BindService " ;

    // 非多进程 bindService 后直接调用此方法
    //在bindService的地方onServiceConnected
//    @Override
//    public void onServiceConnected(ComponentName name, IBinder service) {
//        MyBinder binder = (MyBinder)service;
//        BindService bindService = binder.getService();
//        bindService.MyMethod();
//        flag = true ;
//    }
    public void MyMethod(){
        Log.i(TAG, " BindService-->MyMethod() " );
    }



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
        // TODO 目前调用这个方法会阻塞主线程 导致在此startservice 无法执行onStartCommand 后期查找原因
        // 正确使用 call 方式http://www.jianshu.com/p/d30e333d6e2e/comments/4093674
        //calltest();

        return super.onStartCommand(intent, flags, startId);
    }

    public void calltest() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = IPCContentProvider.CALL_CONTENT_URI;
        Bundle bundle = contentResolver.call(uri, "method", null, null);
        String returnCall = bundle.getString("returnCall");
        Log.i("main", "-------------->" + returnCall);
    }


    private void useCall(){
        //这种方式调用才行 自定义bean需要下面设置classloader 但是call方法需要返回一个bundle
        android.os.Bundle bundleArgs = new android.os.Bundle();
        bundleArgs.putString("key","test");

        android.os.Bundle bundleResult = getContentResolver().call(
                DownLoadProviderV2.TASK_CONTENT_URI,
                "", null, bundleArgs);

        if (bundleResult == null) {
            Log.e("====","bundleResultbundleResultnullnullnull");
        }
        //bundleResult.setClassLoader(CallArgs.class.getClassLoader());
        //CallArgs result = bundleResult.getParcelable(METHOD_CALL_RESULT);

        String s = bundleResult.getString("key");
        Log.e("====","===ss"+s);
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

        @Override
        public void addPersonIn(Person person) throws RemoteException {
        }

        /*@Override
        public void addPersonOut(Person person1) throws RemoteException {
        }

        @Override
        public void addPersonInOut(Person person2) throws RemoteException {
        }*/
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


//广播也可以处理service通信//
// class MyBinder extends Binder {
//
//    //向Activity返回MyService2实例
//    MyService2 getService() {
//        return MyService2.this;
//    }
//
//    //获取从Activity传来的数据
//    void TransferData(int mData) {
//        data = mData;
//    }
//
//    //将data数值传递给Activity
//    int getData() {
//        return data;
//    }
//
//}
//
//————————————————
//        版权声明：本文为CSDN博主「#Ekko」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/Gods_magic/article/details/84558169
