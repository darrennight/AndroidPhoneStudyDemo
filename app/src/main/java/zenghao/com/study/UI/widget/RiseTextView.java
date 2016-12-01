package zenghao.com.study.UI.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 *
 * @author zenghao
 * @since 16/11/24 上午11:52
 */
public class RiseTextView extends TextView {

    private float number;

    private float fromNumber;

    private long duration=1500;

    final static int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE };

    /**
     * 1.int 2.float
     */
    private int numberType=2;



    public RiseTextView(Context context) {
        super(context);
    }

    public RiseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RiseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void startInt(){
        ValueAnimator animator = ValueAnimator.ofInt((int)fromNumber,(int)number);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setText(animation.getAnimatedValue().toString());
            }
        });

        animator.start();
    }

    static int sizeOfInt(int x) {
        for (int i = 0;; i++)
            if (x <= sizeTable[i])
                return i + 1;
    }


    public RiseTextView withNumber(int number) {
        this.number=number;
        numberType=1;
        if (number>1000){
            fromNumber=number-(float)Math.pow(10,sizeOfInt((int)number)-2);
        }else {
            fromNumber=number/2;
        }

        return this;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
