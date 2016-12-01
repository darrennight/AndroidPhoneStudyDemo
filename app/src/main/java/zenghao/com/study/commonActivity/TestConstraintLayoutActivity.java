package zenghao.com.study.commonActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import zenghao.com.study.R;

/**
 *
 * @author zenghao
 * @since 16/9/20 上午11:25
 * https://github.com/googlecodelabs/constraint-layout
 * http://www.wangchenlong.org/2016/08/07/1608/077-constraint-layout-concept/
 * https://github.com/hitherejoe/Constraints
 */
public class TestConstraintLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);
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
        layout_constraintRight_toRightOf — 期望视图的右边对齐另一个视图的右边。*/