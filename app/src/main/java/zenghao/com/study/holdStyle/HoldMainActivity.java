package zenghao.com.study.holdStyle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import zenghao.com.study.R;

/**
 * activity界面内容层次类型太多
 * 所以东西在一个地方显得冗余
 * 可以将UI拆分
 * http://www.jianshu.com/p/d388808b8987
 * @author zenghao
 * @since 17/1/20 下午2:41
 */
public class HoldMainActivity extends AppCompatActivity {

    private FrameLayout mRecommendLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hold_main);
        init();
    }

    private void init(){
        mRecommendLayout = ((FrameLayout) findViewById(R.id.fr_hotRecommend));
        RecommendHold hold = new RecommendHold(this);
        mRecommendLayout.addView(hold.getContentView());
    }
}
