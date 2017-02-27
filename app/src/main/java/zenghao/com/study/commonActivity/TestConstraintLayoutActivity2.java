package zenghao.com.study.commonActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import zenghao.com.study.R;

import static android.support.constraint.ConstraintSet.BOTTOM;

/**
 *
 * @author zenghao
 * @since 16/9/20 上午11:25
 *
 */
public class TestConstraintLayoutActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout02);
        findViewById(R.id.whatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestConstraintLayoutActivity2.this, ConstraintAnimator.class));
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestConstraintLayoutActivity2.this, DoubleLayoutTransition.class));
            }
        });
    }

}