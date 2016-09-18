package zenghao.com.study.RxBus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import zenghao.com.study.R;

/**
 * Created by zenghao on 16/8/19.
 */
public class RXBusFirstActivity extends Activity {

    private TextView mTxt;
    private TextView mTxt2;

    private Button mBtn;
    private CompositeSubscription _subscriptions;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbus_first);
        _subscriptions = new CompositeSubscription();
        mTxt = ((TextView) findViewById(R.id.tv_txt));
        mTxt2 = ((TextView) findViewById(R.id.tv_txt2));
        mBtn = ((Button) findViewById(R.id.btn_next));
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RXBusFirstActivity.this,RXBusSecondActivity.class));
            }
        });

        _subscriptions.add(RxBus.getDefault().toObserverable().subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(Object o) {
                mTxt.setText(o.toString());
                Log.e("===","object"+o.toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }));

        _subscriptions.add(RxBus.getDefault().toObservable(RXBusSecondActivity.Teacher.class).subscribe(new Subscriber<RXBusSecondActivity.Teacher>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RXBusSecondActivity.Teacher teacher){
                mTxt2.setText(teacher.toString());
                Log.e("=====","RXBus"+teacher.toString());
            }
        }));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        _subscriptions.unsubscribe();
    }
}
