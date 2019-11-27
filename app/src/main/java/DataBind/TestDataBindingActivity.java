package DataBind;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zenghao.com.study.R;
import zenghao.com.study.databinding.LayoutTestDatabindBinding;

/**
 * https://www.jianshu.com/p/e4c4a9aece40
 *
 * @author zenghao
 * @since 2019-10-16 10:35
 */
public class TestDataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LayoutTestDatabindBinding 根据布局文件名自动生成 文件名+Binding
        LayoutTestDatabindBinding binding = DataBindingUtil.setContentView(this, R.layout.layout_test_databind);
        final ObservableObjectsUser ObUser = new ObservableObjectsUser("容华", "谢后");
        final User user = new User("张三","李四");
        final ObservableFieldsUser ofUser = new ObservableFieldsUser("123","456");

        binding.setUser(user);
        binding.setObservableUser(ObUser);
        binding.setObservableFieldUser(ofUser);

        binding.setClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ObUser.setFirstName("update更新");
                user.setFirstName("11111");
                user.setLastName("11111");
                ofUser.firstName.set("更新的");
                ofUser.lastName.set("更新的");
            }
        });

    }
}
