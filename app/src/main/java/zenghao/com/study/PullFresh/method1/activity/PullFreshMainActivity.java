package zenghao.com.study.PullFresh.method1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import zenghao.com.study.R;

/**
 *https://github.com/liaoinstan/SpringView
 * @author zenghao
 * @since 2017/5/16 上午11:20
 */
public class PullFreshMainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_fresh);
        findViewById(R.id.demo1).setOnClickListener(this);
        findViewById(R.id.demo2).setOnClickListener(this);
        findViewById(R.id.demo3).setOnClickListener(this);
        findViewById(R.id.demo4).setOnClickListener(this);
        findViewById(R.id.demo5).setOnClickListener(this);
        findViewById(R.id.demo6).setOnClickListener(this);
        findViewById(R.id.demo7).setOnClickListener(this);
        findViewById(R.id.demo8).setOnClickListener(this);
        findViewById(R.id.warning).setOnClickListener(this);
        findViewById(R.id.test).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.demo1:
                intent.setClass(this,Demo1Activity.class);
                startActivity(intent);
                break;
            case R.id.demo2:
                intent.setClass(this,Demo2Activity.class);
                startActivity(intent);
                break;
            case R.id.demo3:
                intent.setClass(this,Demo3Activity.class);
                startActivity(intent);
                break;
            case R.id.demo4:
                intent.setClass(this,Demo4Activity.class);
                startActivity(intent);
                break;
            case R.id.demo5:
                intent.setClass(this,Demo5Activity.class);
                startActivity(intent);
                break;
            case R.id.demo6:
                intent.setClass(this,Demo6Activity.class);
                startActivity(intent);
                break;
            case R.id.demo7:
                intent.setClass(this,Demo7Activity.class);
                startActivity(intent);
                break;
            case R.id.demo8:
                intent.setClass(this,Demo8Activity.class);
                startActivity(intent);
                break;
            case R.id.warning:
                intent.setClass(this, WarningActivity.class);
                startActivity(intent);
                break;
            case R.id.test:
                intent.setClass(this, TestActivity.class);
                startActivity(intent);
                break;
        }
}
}
