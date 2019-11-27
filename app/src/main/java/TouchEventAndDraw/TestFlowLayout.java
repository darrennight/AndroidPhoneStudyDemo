package TouchEventAndDraw;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义
 *
 * @author zenghao
 * @since 2019-11-21 19:39
 */
public class TestFlowLayout extends ViewGroup {

    public TestFlowLayout(Context context) {
        super(context);
    }

    public TestFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //遍历去调用所有子元素的measure方法（child.getMeasuredHeight()才能获取到值，否则为0）

        //测量子view方式1
//        measureChildren(widthMeasureSpec, heightMeasureSpec);//这行就不用每个去getChildMeasureSpec

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int groupHeight = 0;
        int meaWidth = 0;

        //测量子 view

        for (int i=0;i<getChildCount();i++){
            View childView = getChildAt(i);

            //测量子view方式2
            LayoutParams params = childView.getLayoutParams();
            //子 view 宽高的 MeasureSpec makeMeasureSpec
            int childeWidthSpec = getChildMeasureSpec(widthMeasureSpec,getPaddingLeft()+getPaddingRight(),params.width);
            int childeHeigthSpec = getChildMeasureSpec(heightMeasureSpec,getPaddingTop()+getPaddingBottom(),params.height);
            //测量子 view的宽高
            childView.measure(childeWidthSpec,childeHeigthSpec);

            // 测量每一个child的宽和高
//            measureChild(childView, widthMeasureSpec, heightMeasureSpec);


            if(meaWidth + childView.getMeasuredWidth() > widthSize){
                //换行
                groupHeight = groupHeight + childView.getMeasuredHeight();
                meaWidth = childView.getMeasuredWidth();

            }else{
                meaWidth = meaWidth + childView.getMeasuredWidth();
            }

        }

        groupHeight = groupHeight + getChildAt(0).getMeasuredHeight();


        //测量viewgroup
//        if (widthMode == MeasureSpec.EXACTLY?widthSize:)
        if (heightMode == MeasureSpec.EXACTLY){
            setMeasuredDimension(widthSize,heightSize);
        }else{
            setMeasuredDimension(widthSize,groupHeight);
        }




    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //布局子view
        int useLeft = getLeft();
        int useTop = 0;

        for (int i=0;i<getChildCount();i++){
            View child = getChildAt(i);

            if(useLeft+child.getMeasuredWidth() > getMeasuredWidth()){
                //换行
                useLeft = getLeft();
                useTop = useTop+child.getMeasuredHeight();
                child.layout(useLeft,useTop,useLeft+child.getMeasuredWidth(),useTop+child.getMeasuredHeight());
                useLeft = useLeft + child.getMeasuredWidth();

            }else{

                child.layout(useLeft,useTop,useLeft+child.getMeasuredWidth(),useTop+child.getMeasuredHeight());
                useLeft = useLeft + child.getMeasuredWidth();



            }


        }

    }
}
