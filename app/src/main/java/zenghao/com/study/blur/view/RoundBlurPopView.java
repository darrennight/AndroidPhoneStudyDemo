package zenghao.com.study.blur.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;


public class RoundBlurPopView extends RealtimeBlurView {
    private RectF mRectF;
    private Paint mPaint;
    private Path mPath;

    public RoundBlurPopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRectF = new RectF();
        mPaint = new Paint();
    }

    @Override
    protected void drawBlurredBitmap(Canvas canvas, Bitmap blurBitmap, int overlayColor, float roundCornerRadius) {

        mRectF.right = getMeasuredWidth();
        mRectF.bottom = getMeasuredHeight()-50;

        mPaint.reset();
        mPaint.setAntiAlias(true);
        BitmapShader shader = new BitmapShader(blurBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.postScale(mRectF.width() / blurBitmap.getWidth(), mRectF.height() / blurBitmap.getHeight());
        shader.setLocalMatrix(matrix);
        mPaint.setShader(shader);
        canvas.drawRoundRect(mRectF, roundCornerRadius, roundCornerRadius, mPaint);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(overlayColor);
        canvas.drawRoundRect(mRectF, roundCornerRadius, roundCornerRadius, mPaint);

        //绘制矩形旋转90度
        mPaint.reset();
        mPaint.setAntiAlias(true);
//        mPaint.setColor(Color.parseColor("#ffffff"));
//        mPaint.setColor(overlayColor);
        BitmapShader shaders = new BitmapShader(blurBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrixs = new Matrix();
        matrix.postScale(mRectF.width() / blurBitmap.getWidth(), mRectF.height()+50 / blurBitmap.getHeight());
        shader.setLocalMatrix(matrixs);
        mPaint.setShader(shaders);


        canvas.drawRect(mRectF.right/2-20,mRectF.bottom,mRectF.right/2+20,getMeasuredHeight(),mPaint);

//        Path arrowPath = new Path();
//        arrowPath.moveTo(mRectF.right/2,mRectF.bottom);
//        arrowPath.lineTo(mRectF.right/2+40,mRectF.bottom);
//        arrowPath.lineTo(mRectF.right/2+20,mRectF.bottom+50);
//        arrowPath.close();
//        canvas.drawPath(arrowPath,mPaint);


        // 箭头区域（其实是旋转了 90 度后的正方形区域）
       /* PointF centerPointF = new PointF();
        Path arrowPath = new Path();
        arrowPath.moveTo(centerPointF.x - mArrowHeight, centerPointF.y);
        arrowPath.lineTo(centerPointF.x, centerPointF.y - mArrowHeight);
        arrowPath.lineTo(centerPointF.x + mArrowHeight, centerPointF.y);
        arrowPath.lineTo(centerPointF.x, centerPointF.y + mArrowHeight);
        arrowPath.close();

        mPath.addPath(arrowPath);*/

        /*if (mPath == null) {
            mPath = new Path();
        } else {
            mPath.reset();
        }
        mPaint.setMaskFilter(null);
        mPaint.setColor(overlayColor);
        canvas.drawPath(mPath, mPaint);*/

    }
}