package zenghao.com.study.PullFresh.method1.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity; 
import android.support.v7.widget.Toolbar;
import zenghao.com.study.PullFresh.method1.AcFunFooter;
import zenghao.com.study.PullFresh.method1.AcFunHeader;
import zenghao.com.study.PullFresh.method1.SpringView;
import zenghao.com.study.R;

public class Demo5Activity extends AppCompatActivity { 
    private SpringView springView;
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff8277")));
 
        springView = (SpringView) findViewById(R.id.springview);
        springView.setGive(SpringView.Give.NONE);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override 
            public void onRefresh() { 
            } 
 
            @Override 
            public void onLoadmore() { 
            } 
        }); 
        springView.setHeader(new AcFunHeader(this,R.drawable.acfun_header));
        springView.setFooter(new AcFunFooter(this,R.drawable.acfun_footer));
    } 
} 