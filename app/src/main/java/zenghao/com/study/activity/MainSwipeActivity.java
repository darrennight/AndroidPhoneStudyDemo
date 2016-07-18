package zenghao.com.study.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zenghao.com.study.R;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/28 10:23
 * 描述:https://github.com/bingoogolapple/BGASwipeItemLayout-Android
 */
public class MainSwipeActivity extends AppCompatActivity {
    private static final String TAG = MainSwipeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainswipe);
    }

    public void changeToSwipeItemDemo(View v) {
        startActivity(new Intent(this, SwipeItemActivity.class));
    }

    public void changeToListViewDemo(View v) {
        startActivity(new Intent(this, ListViewDemoActivity.class));
    }

    public void changeToRecyclerViewDemo(View v) {
        startActivity(new Intent(this, RecyclerViewDemoActivity.class));
    }

}