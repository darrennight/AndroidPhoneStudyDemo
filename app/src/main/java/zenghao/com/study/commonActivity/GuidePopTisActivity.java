package zenghao.com.study.commonActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import zenghao.com.study.R;
import zenghao.com.study.view.viewPopGuide.CircleLightShape;
import zenghao.com.study.view.viewPopGuide.HighLight;
import zenghao.com.study.view.viewPopGuide.OnBottomPosCallback;

/**
 * activity界面新手指导
 *https://github.com/hongyangAndroid/Highlight
 * https://github.com/binIoter/GuideView
 * https://github.com/qiushi123/GuideView-master
 * https://github.com/worker8/TourGuide
 *
 * listviewitem高亮
 * http://www.jianshu.com/p/5aa96683d0dc
 * @author zenghao
 * @since 17/3/10 下午4:33
 */
public class GuidePopTisActivity extends AppCompatActivity {

    private HighLight mHightLight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guide);
        //btn_show_known_tip
        showTipView(findViewById(R.id.btn_show_tip));
    }


    public  void showTipView(View view){
        mHightLight = new HighLight(GuidePopTisActivity.this)//
                .anchor(findViewById(R.id.id_container))//如果是Activity上增加引导层，不需要设置anchor
                //.addHighLight(R.id.btn_rightLight,R.layout.info_gravity_left_down,new OnLeftPosCallback(45),new RectLightShape())
                //.addHighLight(R.id.btn_light,R.layout.info_gravity_left_down,new OnRightPosCallback(5),new CircleLightShape())
                //.addHighLight(R.id.btn_bottomLight,R.layout.info_gravity_left_down,new OnTopPosCallback(),new CircleLightShape())
                .addHighLight(view,R.layout.info_gravity_left_down,new OnBottomPosCallback(60),new CircleLightShape());
        mHightLight.show();
    }
}
