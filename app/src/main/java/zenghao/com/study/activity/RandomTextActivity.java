package zenghao.com.study.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import zenghao.com.study.R;
import zenghao.com.study.view.RandomTextView;

/**
 *
 * @author zenghao
 * @since 2018/1/17 下午5:34
 */
public class RandomTextActivity extends AppCompatActivity {

    private RandomTextView mRandomTextView;
    private int[] pianyiliang = new int[6];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_text);


        mRandomTextView = (RandomTextView) findViewById(R.id.rtv);
        pianyiliang[0] = 10;
        pianyiliang[1] = 9;
        pianyiliang[2] = 8;
        pianyiliang[3] = 7;
        pianyiliang[4] = 6;
        pianyiliang[5] = 5;
        mRandomTextView.setPianyilian(pianyiliang);
        mRandomTextView.start();
    }

    public void start(View v) {
        mRandomTextView.setText("876543");
        mRandomTextView.setPianyilian(RandomTextView.ALL);
        mRandomTextView.start();

    }

    public void start2(View v) {
        mRandomTextView.setText("912111");
        pianyiliang[0] = 7;
        pianyiliang[1] = 6;
        pianyiliang[2] = 12;
        pianyiliang[3] = 8;
        pianyiliang[4] = 18;
        pianyiliang[5] = 10;
        mRandomTextView.setMaxLine(20);
        mRandomTextView.setPianyilian(pianyiliang);
        mRandomTextView.start();

    }

    public void start3(View v) {
        mRandomTextView.setText("9078111123");
        mRandomTextView.setPianyilian(RandomTextView.FIRSTF_LAST);
        mRandomTextView.start();

    }

    public void start4(View v) {
        mRandomTextView.setText("12313288");
        mRandomTextView.setPianyilian(RandomTextView.FIRSTF_FIRST);
        mRandomTextView.start();

    }


}
