package zenghao.com.study.listStatusSwitch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import zenghao.com.study.R;

/***
 * 可以试试ViewStub其他状态 加载失败和错误
 * 节省资源
 *
 * 其他借鉴
 * https://github.com/hongyangAndroid/LoadingAndRetryManager
 */
public class LoadingActivity extends AppCompatActivity implements View.OnClickListener  {
 
 
    LoadingLayout vLoading;
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
 
        vLoading = (LoadingLayout) findViewById(R.id.loading);
        vLoading.setRetryListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "retry" , Toast.LENGTH_LONG).show();
            } 
        }); 
        vLoading.showContent();
 
 
        findViewById(R.id.action_content).setOnClickListener(this);
        findViewById(R.id.action_empty).setOnClickListener(this);
        findViewById(R.id.action_loading).setOnClickListener(this);
        findViewById(R.id.action_error).setOnClickListener(this);
 
     } 
 
    @Override 
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.action_empty:
            vLoading.showEmpty();
            break; 
        case R.id.action_loading:
            vLoading.showLoading();
            break; 
        case R.id.action_content:
            vLoading.showContent();
            break; 
        case R.id.action_error:
            vLoading.showError();
            break; 
        } 
    } 
}