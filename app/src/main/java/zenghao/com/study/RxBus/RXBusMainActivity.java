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
public class RXBusMainActivity extends Activity {

    private TextView mTxt;
    private Button mBtn;
    private Button mSendSicky;
    private CompositeSubscription _subscriptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_main);
        _subscriptions = new CompositeSubscription();
        mTxt = ((TextView) findViewById(R.id.tv_txt));
        mBtn = ((Button) findViewById(R.id.btn_next));
        mSendSicky = ((Button) findViewById(R.id.btn_sendsticky));


        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RXBusMainActivity.this,RXBusFirstActivity.class));
            }
        });
        mSendSicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RxBus.getDefault().hasObservers()){
                    RXBusSecondActivity activity = new RXBusSecondActivity();
                    RXBusSecondActivity.Student student = activity.new Student();
                    student.id = "two";
                    student.name = "stulilili";
                    RxBus.getDefault().postSticky(student);
                }
            }
        });


        _subscriptions.add(RxBus.getDefault().toObservable(RXBusSecondActivity.Student.class).subscribe(new Subscriber<RXBusSecondActivity.Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RXBusSecondActivity.Student student){
                mTxt.setText(student.toString());
                Log.e("=====","RXBus"+student.toString());
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _subscriptions.unsubscribe();
    }
}
