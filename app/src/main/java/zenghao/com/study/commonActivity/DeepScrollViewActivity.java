package zenghao.com.study.commonActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import zenghao.com.study.R;
import zenghao.com.study.view.DeepScrollView;

/**
 * TODO
 *
 * @author zenghao
 * @since 2017/9/5 下午6:02
 */
public class DeepScrollViewActivity extends AppCompatActivity {

    private DeepScrollView mDeep;
    private Button btn3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deep_scroll);
        mDeep = ((DeepScrollView) findViewById(R.id.root_deep));
        btn3 = ((Button) findViewById(R.id.btn_3));

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mDeep.scrollToDeepChild(btn3);
                mDeep.smoothScrollTo(0, btn3.getTop());
            }
        });
    }
}
