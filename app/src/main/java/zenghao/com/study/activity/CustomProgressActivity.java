package zenghao.com.study.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import zenghao.com.study.R;
import zenghao.com.study.view.SaleProgressView;

/**
 *
 * @author zenghao
 * @since 2018/1/17 下午5:05
 */
public class CustomProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_progress);

        final SaleProgressView  saleProgressView = (SaleProgressView) findViewById(R.id.spv);

        SeekBar seekBar = (SeekBar) findViewById(R.id.sb_seek);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                saleProgressView.setTotalAndCurrentCount(100,i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
