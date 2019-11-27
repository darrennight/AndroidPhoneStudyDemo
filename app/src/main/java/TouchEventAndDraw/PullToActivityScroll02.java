package TouchEventAndDraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import zenghao.com.study.R;

/**
 * 下拉刷新第一步
 *
 * @author zenghao
 * @since 2019-11-26 09:50
 */
public class PullToActivityScroll02 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_scroll_01);
        findViewById(R.id.tv_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("=====","onClickonClickonClickonClick");
            }
        });
    }
}
