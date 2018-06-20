package zenghao.com.study.ToastCompat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import zenghao.com.study.R;

/**
 * https://github.com/drakeet/ToastCompat
 */
public class ToastMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_compat);

        ToastCompat.makeText(this, "hello Toast BadTokenException 解决", Toast.LENGTH_SHORT)
                .show();
            /*.setBadTokenListener(toast -> {
                Log.e("failed toast", "hello");
            }).show();*/
    }
}