package zenghao.com.study.DBUtils.use;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import java.util.List;
import zenghao.com.study.R;

/**
 * 此数据库框架使用方式
 * 添加新数据库需要继承BaseContentProvider在清单文件注册仿照sampleDB
 * 添加新的表创建需要分别实现 BaseTableFields CustomTableHelper BaseTableOperator子类
 * 仿照userInfo
 *
 * operator用于数据库的CRUD
 * fields数据库表字段 bean
 * help表的创建升级等
 *
 * @author zenghao
 * @since 2017/6/29 上午11:18
 */
public class TestDBFrameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_frame);
        //new SampleDB();
        //getContentResolver()
        //UserInfoOperator.getImpl().delete()

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoFields fields = new UserInfoFields();
                fields.setAge("20");
                fields.setName("testName");
                fields.setSex("1");

                UserInfoOperator.getImpl().insert(fields);
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*UserInfoFields fields =  UserInfoOperator.getImpl().query(UserInfoFields.NAME+"=?",new String[]{"testName"});
                if(fields!=null){
                    Log.e("===","hah"+fields.getName());
                }*/

                List<UserInfoFields> list = UserInfoOperator.getImpl().query(UserInfoFields.NAME+"=?",new String[]{"testName"},null);
                Log.e("====","size"+list);
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
