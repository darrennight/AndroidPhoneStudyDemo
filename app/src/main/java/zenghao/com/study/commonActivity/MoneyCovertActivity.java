package zenghao.com.study.commonActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import zenghao.com.study.R;
import zenghao.com.study.util.MoneyUtil;

/**
 *
 * @author zenghao
 * @since 17/3/28 下午3:41
 */
public class MoneyCovertActivity extends AppCompatActivity {

    private TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_convert);
        text = ((TextView) findViewById(R.id.tv_test_covert));
        test();
    }


    private void test(){
        text.setText(MoneyUtil.convert(MoneyUtil.moneyFormat("50200300.2883")));
    }
}
