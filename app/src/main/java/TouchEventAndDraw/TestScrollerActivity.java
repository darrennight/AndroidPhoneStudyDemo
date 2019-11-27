package TouchEventAndDraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import zenghao.com.study.R;

/**
 * Scroller view的内容滑动
 *不管是scrollTo()还是scrollBy()方法，滚动的都是该View内部的内容
 * scrollBy()方法是让View相对于当前的位置滚动某段距离，
 *
 * 而scrollTo()方法则是让View相对于初始的位置滚动某段距离
 * @author zenghao
 * @since 2019-10-18 10:50
 */
public class TestScrollerActivity extends AppCompatActivity {

    private LinearLayout layout;

    private Button scrollToBtn;

    private Button scrollByBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scroller);
        layout = (LinearLayout) findViewById(R.id.layout);
        scrollToBtn = (Button) findViewById(R.id.scroll_to_btn);
        scrollByBtn = (Button) findViewById(R.id.scroll_by_btn);
        scrollToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.scrollTo(0, -100);
            }
        });
        scrollByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.scrollBy(0, -100);
            }
        });
    }
}
