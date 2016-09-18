package zenghao.com.study.commonActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/9/12.
 */
public class ChangeBgColor extends AppCompatActivity {

    private ImageView imageView = null;
    private ImageView imageView1 = null;
    private SeekBar mSeekbar;
    private ColorShades mColor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color);

        imageView = ((ImageView) findViewById(R.id.iv_test_bg));
        mSeekbar = ((SeekBar) findViewById(R.id.sb_seek));
        imageView1 = ((ImageView) findViewById(R.id.iv_test_bg2));
        mColor = new ColorShades();
        mSeekbar.setMax(10);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("=====","pro"+progress);
                mColor.setFromColor(Color.parseColor("#54CADC"))
                        .setToColor(Color.parseColor("#7AD199"))
                        .setShade(progress*0.1f);
                imageView.setBackgroundColor(mColor.generate());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        testAni(imageView1);

    }


    private void testAni(View view){
        ObjectAnimator translationUp = ObjectAnimator.ofInt(view,"backgroundColor", Color.parseColor("#54CADC"),Color.parseColor("#7AD199"));

        translationUp.setInterpolator(new LinearInterpolator());
        translationUp.setDuration(5000);
        translationUp.setRepeatCount(-1);
        translationUp.setRepeatMode(Animation.REVERSE);
        /*
         * ArgbEvaluator：这种评估者可以用来执行类型之间的插值整数值代表ARGB颜色。
         * FloatEvaluator：这种评估者可以用来执行浮点值之间的插值。
         * IntEvaluator：这种评估者可以用来执行类型int值之间的插值。
         * RectEvaluator：这种评估者可以用来执行类型之间的插值矩形值。
         *
         * 由于本例是改变View的backgroundColor属性的背景颜色所以此处使用ArgbEvaluator
         */

        translationUp.setEvaluator(new ArgbEvaluator());
        translationUp.start();
    }
}
