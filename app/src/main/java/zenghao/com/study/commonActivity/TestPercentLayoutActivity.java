package zenghao.com.study.commonActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import zenghao.com.study.R;

/**
 * 百分比布局
 * 百分比提供了以下两种布局
 *  android.support.percent.PercentRelativeLayout
 * android.support.percent.PercentFrameLayout
 *
 *
 *支持以下属性:
 *   layout_widthPercent
 *   layout_heightPercent
     layout_marginPercent
     layout_marginLeftPercent
     layout_marginTopPercent
     layout_marginRightPercent
     layout_marginBottomPercent
     layout_marginStartPercent
     layout_marginEndPercent

 借鉴拓展
 http://blog.csdn.net/lmj623565791/article/details/46695347
 https://github.com/hongyangAndroid/android-percent-support-extend
 */
public class TestPercentLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);
    }
}
