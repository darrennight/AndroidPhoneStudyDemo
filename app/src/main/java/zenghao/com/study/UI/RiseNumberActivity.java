package zenghao.com.study.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import zenghao.com.study.R;
import zenghao.com.study.UI.widget.RiseTextView;

/**
 *支付宝金额变化动画
 * @author zenghao
 * @since 16/11/24 上午11:47
 */
public class RiseNumberActivity extends AppCompatActivity {

    private RiseTextView mRise;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rise_number);
        mRise = ((RiseTextView) findViewById(R.id.rtv_test_rise));
        mRise.withNumber(1000).startInt();

    }
}
