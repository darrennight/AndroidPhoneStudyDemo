package zenghao.com.study.bottomManger;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/7/14.
 */
public class BottomNavigationView extends LinearLayout {

    private int mCurrentPos;

    private OnBottomNavigationItemClick mOnItemClick;
    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setOrientation(HORIZONTAL);
        inflate(context, R.layout.layout_bottom_navigator, this);

        for (int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClick!=null){
                        mOnItemClick.onNavigationItemClick(finalI,v);
                    }
                }
            });
        }
    }


    public void select(int position) {
        mCurrentPos = position;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == position) {
                selectChild(child, true);
            } else {
                selectChild(child, false);
            }
        }
    }

    private void selectChild(View child, boolean select) {
        if (child instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) child;
            group.setSelected(select);
            for (int i = 0; i < group.getChildCount(); i++) {
                selectChild(group.getChildAt(i), select);
            }
        } else {
            child.setSelected(select);
            if (child instanceof ImageView) {
                ImageView iv = (ImageView) child;
                Drawable drawable = iv.getDrawable().mutate();
                if (select) {
                    drawable.setColorFilter(getResources().getColor(R.color.colorTabSelected), PorterDuff.Mode.SRC_ATOP);
                } else {
                    drawable.setColorFilter(getResources().getColor(R.color.colorTabNormal), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
    }

    public int getmCurrentPos() {
        return mCurrentPos;
    }

    public void setOnBottomNavigationClick(OnBottomNavigationItemClick click){
        this.mOnItemClick = click;
    }

    public interface OnBottomNavigationItemClick{
        void onNavigationItemClick(int position, View view);
    }
}
