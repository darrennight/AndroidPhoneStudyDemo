package zenghao.com.study.commonActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zenghao.com.study.R;

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCancel;
    private TextView tvTitle;
    private TextView tvSave;
    private EditText etContent;

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "EXTRA_CONTENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        initView();
    }

    private void initView() {
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvSave = (TextView) findViewById(R.id.tv_save);
        etContent = (EditText) findViewById(R.id.et_content);
        tvCancel.setOnClickListener(this);
        tvSave.setOnClickListener(this);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String content = getIntent().getStringExtra(EXTRA_CONTENT);
        tvTitle.setText(title);
        etContent.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_save:
                if (modifyListener != null) {
                    final String content = etContent.getText().toString().trim();
                    modifyListener.onResult(content)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean isSuccess) {
                                    if (isSuccess) {
                                        Intent data = new Intent();
                                        data.putExtra(EXTRA_CONTENT, content);
                                        setResult(RESULT_OK, data);
                                        finish();
                                    } else {
                                        Toast.makeText(ModifyActivity.this, "网络修改错误", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                break;
        }
    }

    public interface ModifyListener {
        Observable<Boolean> onResult(String content);
    }

    private static ModifyListener modifyListener;

    public static void startModify(Activity activity, Intent intent, int requestCode, ModifyListener listener) {
        modifyListener = listener;
        activity.startActivityForResult(intent, requestCode);
    }
}