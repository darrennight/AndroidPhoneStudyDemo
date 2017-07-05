package zenghao.com.study.PullFresh.method1.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity; 
import android.support.v7.widget.Toolbar;
import zenghao.com.study.PullFresh.method1.DefaultFooter;
import zenghao.com.study.PullFresh.method1.DefaultHeader;
import zenghao.com.study.PullFresh.method1.SpringView;
import zenghao.com.study.R;

public class Demo1Activity extends AppCompatActivity { 
 
 
    private SpringView springView;
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        springView = (SpringView) findViewById(R.id.springview);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override 
            public void onRefresh() { 
                new Handler().postDelayed(new Runnable() {
                    @Override 
                    public void run() { 
                        springView.onFinishFreshAndLoad();
                    } 
                }, 1000); 
            } 
 
            @Override 
            public void onLoadmore() { 
                new Handler().postDelayed(new Runnable() {
                    @Override 
                    public void run() { 
                        springView.onFinishFreshAndLoad();
                    } 
                }, 1000); 
            } 
        }); 
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    } 
} 