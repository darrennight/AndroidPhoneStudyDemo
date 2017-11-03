package zenghao.com.study.listActivityFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import zenghao.com.study.R;
import zenghao.com.study.listActivityFragment.sample.HomeActivity;
import zenghao.com.study.listActivityFragment.sample.SampleListActivity;

/**
 *列表activity & fragment 封装
 * UI列表 数据加载 UI状态 失败 空 网络错误 正确
 * @author zenghao
 * @since 2017/5/19 上午11:51
 * https://github.com/Stay/PullRecycler
 */
public class ListMainActvity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);
        findViewById(R.id.btn_list_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListMainActvity.this, HomeActivity.class));
            }
        });
    }
}
