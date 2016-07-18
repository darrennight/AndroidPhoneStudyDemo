package zenghao.com.study.suspension;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ImageView;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/5/5.
 */
public class GuideActivity extends Activity{

    private ImageView mGuide2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_guide);
        mGuide2 = ((ImageView) findViewById(R.id.iv_guide2));
//        Drawable drawable = mGuide2.getDrawable().mutate();
//        drawable.setColorFilter(getResources().getColor(R.color.color_bleack), PorterDuff.Mode.SRC_OUT);


//        Bitmap bmpGrayscale = Bitmap.createBitmap(1024, 1024,
//                Bitmap.Config.RGB_565);
//        Canvas c = new Canvas(bmpGrayscale);
//        Paint paint = new Paint();
//        ColorMatrix cm = new ColorMatrix();
//        cm.setSaturation(0);
//        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
//        drawable.setColorFilter(f);
    }


    class MyImage extends ImageView{
        public MyImage(Context context) {
            super(context);
        }

        public MyImage(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyImage(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            setColorFilter();
        }
    }

}
