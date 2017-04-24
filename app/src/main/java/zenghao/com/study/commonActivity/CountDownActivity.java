package zenghao.com.study.commonActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import zenghao.com.study.R;

/**
 *倒计时
 * @author zenghao
 * @since 17/3/31 下午2:00
 */
public class CountDownActivity extends AppCompatActivity {

    private TextView txtTime;
    private Button mStart;
    private Button mAdd;

    private long timeFirst = 10000;
    private long timeSecond = 1000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        txtTime = ((TextView) findViewById(R.id.txt_time));
        mStart = ((Button) findViewById(R.id.btn_start));
        mAdd = ((Button) findViewById(R.id.btn_add_time));


        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFirst = 30000;
                timer.cancel();
                restart();
            }
        });

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });
    }



    /**
     * 取消倒计时
     */
    public void oncancel() {
        timer.cancel();
    }

    /**
     * 开始倒计时
     */
    public void restart() {
        timer.start();
    }
    //重写这个源码实现 更改时间
    private CountDownTimer timer = new CountDownTimer(timeFirst, timeSecond) {

        @Override
        public void onTick(long millisUntilFinished) {

            txtTime.setText("tick"+(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            txtTime.setText("onFinish");
        }
    };
}
