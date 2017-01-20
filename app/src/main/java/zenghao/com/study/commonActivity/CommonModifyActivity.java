package zenghao.com.study.commonActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import rx.Observable;
import rx.functions.Func1;
import zenghao.com.study.R;

/**
 *例如个人信息界面相关信息修改抽取框架
 * @author zenghao
 * @since 17/1/17 下午3:08
 */
public class CommonModifyActivity extends AppCompatActivity implements View.OnClickListener {
    private final int REQUEST_MODIFY = 0x01;//请求修改字段
    private RelativeLayout rvName;
    private RelativeLayout rvAddress;
    private RelativeLayout rvDesc;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvDesc;

    private TextView currentMofify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_modify);
        initView();
    }

    private void initView() {
        rvName = (RelativeLayout) findViewById(R.id.rv_name);
        rvAddress = (RelativeLayout) findViewById(R.id.rv_address);
        rvDesc = (RelativeLayout) findViewById(R.id.rv_desc);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvDesc = (TextView) findViewById(R.id.tv_desc);

        rvName.setOnClickListener(this);
        rvAddress.setOnClickListener(this);
        rvDesc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rv_name:
                currentMofify = tvName;
                gotoModify("名字", tvName.getText().toString());
                break;
            case R.id.rv_address:
                currentMofify = tvAddress;
                gotoModify("地址", tvAddress.getText().toString());
                break;
            case R.id.rv_desc:
                currentMofify = tvDesc;
                gotoModify("签名", tvDesc.getText().toString());
                break;
        }
    }

    /**
     * 跳转到公用的修改界面
     */
    private void gotoModify(final String title, String content) {
        Intent intent = new Intent(this, ModifyActivity.class);
        intent.putExtra(ModifyActivity.EXTRA_TITLE, title);
        intent.putExtra(ModifyActivity.EXTRA_CONTENT, content);

        ModifyActivity.startModify(this, intent, REQUEST_MODIFY, new ModifyActivity.ModifyListener() {

            @Override
            public Observable<Boolean> onResult(String result) {
                //将Observable<BaseResponse> 转换成 Observable<Boolean>
                return requestWeb(title, result).map(new Func1<BaseResponse, Boolean>() {
                            @Override
                            public Boolean call(BaseResponse baseResponse) {
                                return baseResponse.getStatus() == 0;
                            }
                        });
            }
        });
    }


    /**
     * 这里执行每个修改的网络请求
     */
    private Observable<BaseResponse> requestWeb(String title, String result) {
        switch (title) {
            case "名字":
                return WebModel.modifyName(result);
            case "地址":
                return WebModel.modifyAddress(result);
            default:
                return WebModel.modifyDesc(result);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_MODIFY && data != null) {
            String content = data.getStringExtra(ModifyActivity.EXTRA_CONTENT);
            currentMofify.setText(content);
        }
    }
}