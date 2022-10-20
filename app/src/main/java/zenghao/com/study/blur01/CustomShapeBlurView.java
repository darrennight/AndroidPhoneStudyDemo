package zenghao.com.study.blur01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;


/**
 * Created by mmin18 on 9/27/16.
 * 聊天气泡形式
 */
public class CustomShapeBlurView extends RealtimeBlurView {
	Paint mPaint;
	RectF mRectF;
	RectF mRectFTest;
	private int round =0;
	private int arrowHeight =0;

	public CustomShapeBlurView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mRectF = new RectF();
		mRectFTest = new RectF();
//		round = ViewUtils.INSTANCE.dp2px(6);
//		arrowHeight = ViewUtils.INSTANCE.dp2px(10);
	}

	/**
	 * Custom oval shape
	 */
	@Override
	protected void drawBlurredBitmap(Canvas canvas, Bitmap blurredBitmap, int overlayColor) {
		if (blurredBitmap != null) {
			mRectF.right = getMeasuredWidth();
			mRectF.bottom = getMeasuredHeight()-arrowHeight;


			BitmapShader shader = new BitmapShader(blurredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			Matrix matrix = new Matrix();
			matrix.postScale(mRectF.width() / blurredBitmap.getWidth(), (mRectF.height()+arrowHeight) / blurredBitmap.getHeight());
			shader.setLocalMatrix(matrix);
			mPaint.setShader(shader);

			//画矩形
			canvas.drawRoundRect(mRectF,round,round,mPaint);
			//画三角形（这里是基于path路径的绘制）
			Path path = new Path();
			path.moveTo((mRectF.right/2)-(arrowHeight/2f), mRectF.bottom);
			path.lineTo((mRectF.right / 2), mRectF.bottom+(arrowHeight));
			path.lineTo((mRectF.right/2)+(arrowHeight/2f), mRectF.bottom);
			path.close();
			canvas.drawPath(path, mPaint);


			mPaint.reset();
			mPaint.setAntiAlias(true);
			mPaint.setColor(overlayColor);
			canvas.drawRoundRect(mRectF, 10, 10, mPaint);

			Path path1 = new Path();
			path1.moveTo((mRectF.right/2)-(arrowHeight/2f), mRectF.bottom);
			path1.lineTo((mRectF.right / 2), mRectF.bottom+(arrowHeight));
			path1.lineTo((mRectF.right/2)+(arrowHeight/2f), mRectF.bottom);
			path1.close();
			canvas.drawPath(path1, mPaint);

		}
	}
}