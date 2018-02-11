package zenghao.com.study.picker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import zenghao.com.study.R;
import zenghao.com.study.picker.view.TimePickerView;

/**
 * https://github.com/Bigkoo/Android-PickerView
 *
 * @author zenghao
 * @since 2017/11/29 上午10:27
 */
public class MainPickerActivity extends AppCompatActivity {

    private Button test = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_picker);
        test = ((Button) findViewById(R.id.btn_test));

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker();
            }
        });
    }



    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {//选中事件回调
                Log.e("====",getTime(date));

            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                //.setDividerType(WheelView.DividerType.WRAP)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                //                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色

                .isCyclic(true)
                .gravity(Gravity.CENTER)
                .build();


        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}
