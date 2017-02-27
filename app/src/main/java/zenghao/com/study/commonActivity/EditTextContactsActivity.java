package zenghao.com.study.commonActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import zenghao.com.study.R;
import zenghao.com.study.view.CloudEditText;

/**
 * EditText中显示的内容 按块显示
 *https://github.com/hootsuite/nachos
 * https://github.com/g707175425/CloudEditText
 * @author zenghao
 * @since 17/2/18 下午4:56
 */
public class EditTextContactsActivity extends AppCompatActivity {

    private CloudEditText mEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext_contact);
        mEditText = ((CloudEditText) findViewById(R.id.et_user_contact));
    }
}
