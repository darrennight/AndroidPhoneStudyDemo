package zenghao.com.study.commonActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;

import zenghao.com.study.R;

import static android.support.constraint.ConstraintSet.BOTTOM;

/**
 *
 * @author zenghao
 * @since 16/9/20 上午11:25
 * https://github.com/googlecodelabs/constraint-layout
 * http://www.wangchenlong.org/2016/08/07/1608/077-constraint-layout-concept/
 * https://github.com/hitherejoe/Constraints
 * http://www.jianshu.com/p/d64d845b6b90
 *
 * https://developer.android.google.cn/reference/android/support/constraint/ConstraintLayout.html
 */
public class TestConstraintLayoutActivity extends AppCompatActivity {

    ConstraintSet mConstraintSet1 = new ConstraintSet(); // create a Constraint Set
    ConstraintSet mConstraintSet2 = new ConstraintSet(); // create a Constraint Set
    ConstraintLayout mConstraintLayout; // cache the ConstraintLayout

    private Button mBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);
        mConstraintLayout = ((ConstraintLayout) findViewById(R.id.lay_root));
        mBottom = ((Button) findViewById(R.id.btn_bottom));

//        setContentView(R.layout.activity_constraint_layout2);
//        mConstraintSet2.clone(context, R.layout.state2); // get constraints from layout


        /*mConstraintSet1.clone(mConstraintLayout);
        TransitionManager.beginDelayedTransition(mConstraintLayout);
        mConstraintSet1.applyTo(mConstraintLayout);*/

        mBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWidthHeight();
                setCenter();
            }
        });

    }

    /**
     * 设置某button宽高
     */
    public void setWidthHeight(){
       mConstraintSet1.constrainHeight(R.id.btn_testch, 500);
       mConstraintSet1.constrainWidth(R.id.btn_testch, 500);
        mConstraintSet1.applyTo(mConstraintLayout);
   }

    /***
     *设置居中
     */
   public void setCenter(){
       mConstraintSet1.centerHorizontally(R.id.btn_testch, R.id.lay_root);
       mConstraintSet1.centerVertically(R.id.btn_testch, R.id.lay_root);
       mConstraintSet1.applyTo(mConstraintLayout);
   }

    /**
     * 暂时不知道什么意思
     */
   public void testConnect(){
       mConstraintSet1.connect(R.id.btn_testch, BOTTOM, R.id.lay_root, BOTTOM, 8);
       mConstraintSet1.applyTo(mConstraintLayout);
   }

}



        /*layout_constraintTop_toTopOf — 期望视图的上边对齐另一个视图的上边。
        layout_constraintTop_toBottomOf — 期望视图的上边对齐另一个视图的底边。
        layout_constraintTop_toLeftOf — 期望视图的上边对齐另一个视图的左边。
        layout_constraintTop_toRightOf — 期望视图的上边对齐另一个视图的右边。
        layout_constraintBottom_toTopOf — 期望视图的下边对齐另一个视图的上边。
        layout_constraintBottom_toBottomOf — 期望视图的底边对齐另一个视图的底边。
        layout_constraintBottom_toLeftOf — 期望视图的底边对齐另一个视图的左边。
        layout_constraintBottom_toRightOf — 期望视图的底边对齐另一个视图的右边。
        layout_constraintLeft_toTopOf — 期望视图的左边对齐另一个视图的上边。
        layout_constraintLeft_toBottomOf — 期望视图的左边对齐另一个视图的底边。
        layout_constraintLeft_toLeftOf — 期望视图的左边对齐另一个视图的左边。
        layout_constraintLeft_toRightOf — 期望视图的左边对齐另一个视图的右边。
        layout_constraintRight_toTopOf — 期望视图的右边对齐另一个视图的上边。
        layout_constraintRight_toBottomOf — 期望视图的右边对齐另一个视图的底边。
        layout_constraintRight_toLeftOf — 期望视图的右边对齐另一个视图的左边。
        layout_constraintRight_toRightOf — 期望视图的右边对齐另一个视图的右边。
        app:layout_constraintHorizontal_bias="0.8" 水平百分比
        app:layout_constraintBaseline_toBaselineOf 按文字底部对齐

        提供View.GONE时的间距  根据依赖的空间关系使用不同的goneMargin
        layout_goneMarginStart
        layout_goneMarginEnd
        layout_goneMarginLeft
        layout_goneMarginTop
        layout_goneMarginRight
        layout_goneMarginBottom


        app:layout_constraintDimensionRatio
        纵横比，而使用则需要把宽（layout_width）或者高（layout_height）设置为0dp，根据另一个属性和比例, 计算当前属性
        值可以是一个float，页可以是“width:height”的形式
        也可以把宽和高都设为MATCH_CONSTRAINT (0dp)，设置在Ratio的时候添加“W”或“H”以适应宽度活高度

        app:layout_constraintDimensionRatio="H,2:1" 已知高度 计算宽度
        app:layout_constraintDimensionRatio="w,2:1" 已知宽度 计算高度度
        参赛H表示已知高度 W表示已知宽度 后面是宽高比  但是源码计算时是反着的  例如：H＝100 w＝100*（1/2）即 2W:1H == w=1H/2



        app:layout_constraintGuide_begin
        表示在布局中引导线距顶部或左边框的距离（如：20dp表示距顶部或者左边框20dp）
        app:layout_constraintGuide_end
        表示在布局中引导线距底部的距离（如：10dp表示距顶部10dp）
        app:layout_constraintGuide_percent
        表示在整个布局中引导线距离左边框的百分百（如：app:layout_constraintGuide_percent="0.5"表示距离左边框50%的位置）
        */