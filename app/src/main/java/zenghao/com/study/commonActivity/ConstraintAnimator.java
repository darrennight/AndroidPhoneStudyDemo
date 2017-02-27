package zenghao.com.study.commonActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;

import zenghao.com.study.R;

public class ConstraintAnimator extends AppCompatActivity {
 
    private ConstraintLayout constraintLayout;
    private ConstraintSet applyConstraintSet = new ConstraintSet();
    private ConstraintSet resetConstraintSet = new ConstraintSet();
 
    @Override 
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_animator_layout);
 
        constraintLayout = (ConstraintLayout) findViewById(R.id.main);
        applyConstraintSet.clone(constraintLayout);
        resetConstraintSet.clone(constraintLayout);
    } 
 
    public void onApplyClick(View view) {
//        marginConnect(); 
        chainPacked(); 
        applyConstraintSet.applyTo(constraintLayout);
    } 
 
    public void onResetClick(View view) {
        TransitionManager.beginDelayedTransition(constraintLayout);
        resetConstraintSet.applyTo(constraintLayout);
    } 
 
    private void marginConnect() { 
        TransitionManager.beginDelayedTransition(constraintLayout);
//        applyConstraintSet.setMargin(R.id.button1, ConstraintSet.START, 8); 
        applyConstraintSet.setMargin(R.id.button1, ConstraintSet.START, 0);
        applyConstraintSet.setMargin(R.id.button1, ConstraintSet.END, 0);
        applyConstraintSet.setMargin(R.id.button2, ConstraintSet.START, 0);
        applyConstraintSet.setMargin(R.id.button2, ConstraintSet.END, 0);
        applyConstraintSet.setMargin(R.id.button3, ConstraintSet.START, 0);
        applyConstraintSet.setMargin(R.id.button3, ConstraintSet.END, 0);
        applyConstraintSet.setMargin(R.id.button3, ConstraintSet.TOP, 0);
        applyConstraintSet.setMargin(R.id.button3, ConstraintSet.BOTTOM, 0);
        applyConstraintSet.constrainHeight(R.id.button3, 200);
        applyConstraintSet.constrainWidth(R.id.button3, 600);
        applyConstraintSet.setVisibility(R.id.button1, View.GONE);
        applyConstraintSet.clear(R.id.button2);
        applyConstraintSet.connect(R.id.button2, ConstraintSet.LEFT, R.id.main, ConstraintSet.LEFT, 0);
        applyConstraintSet.connect(R.id.button2, ConstraintSet.TOP, R.id.main, ConstraintSet.TOP, 0);
        applyConstraintSet.connect(R.id.button2, ConstraintSet.RIGHT, R.id.main, ConstraintSet.RIGHT, 0);
        applyConstraintSet.connect(R.id.button2, ConstraintSet.BOTTOM, R.id.main, ConstraintSet.BOTTOM, 0);
 
 
        applyConstraintSet.centerHorizontally(R.id.button1, R.id.main);
        applyConstraintSet.centerHorizontally(R.id.button2, R.id.main);
        applyConstraintSet.centerHorizontally(R.id.button3, R.id.main);
        applyConstraintSet.centerVertically(R.id.button3, R.id.main);
    } 
 
    private void chainPacked() { 
        TransitionManager.beginDelayedTransition(constraintLayout);
 
        applyConstraintSet.clear(R.id.button1);
        applyConstraintSet.clear(R.id.button2);
        applyConstraintSet.clear(R.id.button3);
 
        // button 1 left and top align to parent 
        applyConstraintSet.connect(R.id.button1, ConstraintSet.LEFT, R.id.main, ConstraintSet.LEFT, 0);
 
        // button 3 right and top align to parent 
        applyConstraintSet.connect(R.id.button3, ConstraintSet.RIGHT, R.id.main, ConstraintSet.RIGHT, 0);
 
        // bi-direction or Chaining between button 1 and button 2 
        applyConstraintSet.connect(R.id.button2, ConstraintSet.LEFT, R.id.button1, ConstraintSet.RIGHT, 0);
        applyConstraintSet.connect(R.id.button1, ConstraintSet.RIGHT, R.id.button2, ConstraintSet.LEFT, 0);
 
        // bi-direction or Chaining between button 2 and button 3 
        applyConstraintSet.connect(R.id.button2, ConstraintSet.RIGHT, R.id.button3, ConstraintSet.LEFT, 0);
        applyConstraintSet.connect(R.id.button3, ConstraintSet.LEFT, R.id.button2, ConstraintSet.RIGHT, 0);
 
        applyConstraintSet.createHorizontalChain(R.id.button1,
                R.id.button3,
                new int[]{R.id.button1, R.id.button3}, null, ConstraintWidget.CHAIN_PACKED);
 
        applyConstraintSet.constrainWidth(R.id.button1, ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainWidth(R.id.button2, ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainWidth(R.id.button3, ConstraintSet.WRAP_CONTENT);
 
        applyConstraintSet.constrainHeight(R.id.button1, ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainHeight(R.id.button2, ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainHeight(R.id.button3, ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.setHorizontalBias(R.id.button1, .1f);
    } 
} 