package zenghao.com.study.IME;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import zenghao.com.study.R;

/**
 *点击任意地方隐藏键盘
 * @author zenghao
 * @since 17/1/4 下午1:30
 */
public class TestHideIme2 extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_hide_img);
        HideIMEUtil.wrap(this);
        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestHideIme2.this,"hhaha",Toast.LENGTH_SHORT).show();
            }
        });
    }

}


