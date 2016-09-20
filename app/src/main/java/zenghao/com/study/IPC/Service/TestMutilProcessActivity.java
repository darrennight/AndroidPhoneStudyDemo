package zenghao.com.study.IPC.Service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import zenghao.com.study.R;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/9/18 下午4:53
 * 要注意处理 改activity被回收后 是否需要重新绑定 service
 */
public class TestMutilProcessActivity extends AppCompatActivity{


    private Button mStart;
    private Button mBind;
    private TextView mShowTxt;
    private Button mShow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mutil);
        mStart = ((Button) findViewById(R.id.btn_start));
        mBind = ((Button) findViewById(R.id.btn_bind));
        mShowTxt = ((TextView) findViewById(R.id.tv_show));
        mShow = ((Button) findViewById(R.id.btn_show));
        setListener();
    }

    private void setListener(){
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestMutilProcessActivity.this,MyMutilPService.class);
                intent.putExtra("flag1","hahah");
                Person person = new Person();
                person.setName("zhang");
                person.setAge(1);
                intent.putExtra("bean",person);
                startService(intent);
            }
        });

        mBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(TestMutilProcessActivity.this,MyMutilPService.class),connection, Context.BIND_AUTO_CREATE);
            }
        });

        mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = null;
                try {
                    s = iHello.getPerson().getName()+iHello.getString();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mShowTxt.setText(s);
            }
        });
    }


    private IHelloInterface iHello;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("=====","onServiceConnected");
            iHello = IHelloInterface.Stub.asInterface(service);

            try {
                iHello.registerCallback(callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            iHello.unregisterCallback(callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        unbindService(connection);
    }

    /**
     * service 执行逻辑后回调
     */
    private IHelloCallback callback = new IHelloCallback.Stub(){
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onString(String s) throws RemoteException {

        }

        @Override
        public void onPerson(String Name) throws RemoteException {

        }

        @Override
        public void onTest(Person p) throws RemoteException {

        }
    };
}
