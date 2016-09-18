package zenghao.com.study.RxBus;

import android.app.Activity;
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
public class RXBusSecondActivity extends Activity {

    private TextView mTxt;
    private Button mBtn;
    private Button mBtn2;
    private CompositeSubscription _subscriptions;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbus_second);
        _subscriptions = new CompositeSubscription();
        mTxt = ((TextView) findViewById(R.id.tv_txt));
        mBtn = ((Button) findViewById(R.id.btn_send1));
        mBtn2 = ((Button) findViewById(R.id.btn_send2));
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RxBus.getDefault().hasObservers()){
                    Student student = new Student();
                    student.id = "one";
                    student.name = "stuZhang";
                    RxBus.getDefault().post(student);
                }

            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(RxBus.getDefault().hasObservers()){
                    Teacher teacher = new Teacher();
                    teacher.id = "one";
                    teacher.name = "teacherZhang";
                    RxBus.getDefault().post(teacher);
                }*/
                testSticky();

            }
        });
        _subscriptions.add(RxBus.getDefault().toObservableSticky(Student.class).subscribe(new Subscriber<Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                Log.e("=====","hahahahhahah");
                mTxt.setText(student.name+student.toString());
            }
        }));

    }


    private void testSticky(){
        if(RxBus.getDefault().hasObservers()){
            Student student = new Student();
            student.id = "three";
            student.name = "stuwwwwwwww";
            RxBus.getDefault().postSticky(student);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class Student{
        public Student(){}
        public String id;
        public String name;
    }
    public class Teacher{
        public String id;
        public String name;
    }
}
