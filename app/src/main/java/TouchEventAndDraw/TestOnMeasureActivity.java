package TouchEventAndDraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import zenghao.com.study.R;

/**
 * TODO
 *
 * @author zenghao
 * @since 2019-11-18 15:47
 */
public class TestOnMeasureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_onmasure);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);//布局view/viewgroup的dispatchTouchEvent
//        return true; //消费不传递
//        return false;//消费不传递
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("---=====activity","onTouchEventonTouchEvent"+event.getAction());
        return super.onTouchEvent(event);
    }
}
