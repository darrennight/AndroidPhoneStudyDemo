package zenghao.com.study.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import zenghao.com.study.R;
import zenghao.com.study.view.switchText.Rotatable;
import zenghao.com.study.view.switchText.RotatingTextWrapper;

/**
 * TODO
 *
 * @author zenghao
 * @since 2018/1/18 下午5:21
 */
public class SwitchTextActivity extends AppCompatActivity {
    RotatingTextWrapper rotatingTextWrapper;
    Rotatable rotatable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_text);


        rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(30);

        rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "Word", "Word02", "Word03");
        rotatable.setSize(30);
        rotatable.setAnimationDuration(500);
        rotatable.setInterpolator(new DecelerateInterpolator());

        rotatingTextWrapper.setContent("This is ?", rotatable);
    }
}
