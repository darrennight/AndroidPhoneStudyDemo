package zenghao.com.study.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zenghao.com.study.R;
import zenghao.com.study.commonActivity.ConstraintAnimator;
import zenghao.com.study.commonActivity.DoubleLayoutTransition;
import zenghao.com.study.commonActivity.TestConstraintLayoutActivity2;

/**
 * Created by zenghao on 16/9/1.
 */
public class TestConstraintLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout02);
        findViewById(R.id.whatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestConstraintLayout.this, ConstraintAnimator.class));
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestConstraintLayout.this, DoubleLayoutTransition.class));
            }
        });
    }

}