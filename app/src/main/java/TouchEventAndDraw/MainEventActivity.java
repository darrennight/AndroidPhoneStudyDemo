package TouchEventAndDraw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zenghao.com.study.R;
import zenghao.com.study.adapter.MyAdapter;

/**
 * view的事件分发机制以及绘制
 *
 * @author zenghao
 * @since 2019-10-18 10:40
 */
public class MainEventActivity extends AppCompatActivity {

    private ListView mListView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);
        mListView = ((ListView) this.findViewById(R.id.lv_list));

        List<String> list = new ArrayList<>();
        list.add("0ScrollBy/TO");
        list.add("1自定义layout 测试scroller");
        list.add("2OnMeasure参数测试");
        list.add("3下拉刷新第一步");
        list.add("4下拉刷新第2步+scrollView");
        adapter = new MyAdapter(this,list);
        mListView.setAdapter(adapter);
        mListView.setSelection(list.size()-1);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent0 = new Intent(MainEventActivity.this, TestScrollerActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainEventActivity.this, TestScrollerLayoutActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainEventActivity.this, TestOnMeasureActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainEventActivity.this, PullToActivity01.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MainEventActivity.this, PullToActivityScroll02.class);
                        startActivity(intent4);
                        break;
                }
            }
        });
    }
}
