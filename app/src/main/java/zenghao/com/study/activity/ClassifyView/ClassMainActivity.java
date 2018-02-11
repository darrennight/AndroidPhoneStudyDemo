package zenghao.com.study.activity.ClassifyView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import zenghao.com.study.R;

/**
 * https://github.com/AlphaBoom/ClassifyView
 *https://github.com/beasonshu/ClassifyView
 * @author zenghao
 * @since 2018/1/17 下午7:35
 */
public class ClassMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public static final String EXTRA_POSITION = "com.anarchy.classifyview.MainActivity.EXTRA_POSITION";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_main);
        ListView sampleList = (ListView) findViewById(R.id.sample_list);
        sampleList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.list_name)));
        sampleList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this,ContentActivity.class);
        i.putExtra(EXTRA_POSITION,position);
        startActivity(i);
    }
}
