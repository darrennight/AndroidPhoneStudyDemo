package zenghao.com.study.picker01;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import zenghao.com.study.picker01.view.CharacterPickerView;
import zenghao.com.study.picker01.view.CharacterPickerWindow;
import zenghao.com.study.picker01.view.OnOptionChangedListener;

/**
 * https://github.com/imkarl/CharacterPickerView
 */
public class PickerActivity01 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 用View的方式实现
//        showView();
        // 用PopupWindow的方式实现
        showWindow();

    }

    private void showView() {
        RelativeLayout layout = new RelativeLayout(this);
        setContentView(layout);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2Px(250));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        CharacterPickerView pickerView = new CharacterPickerView(this);
        //pickerView.setMaxTextSize(28);
        layout.addView(pickerView, layoutParams);

        //初始化选项数据
        OptionsWindowHelper.setPickerData(pickerView);
        //初始化选中项
        //pickerView.setCurrentItems(1, 2, 3);

        //设置监听事件
        pickerView.setOnOptionChangedListener(new OnOptionChangedListener() {
            @Override
            public void onOptionChanged(int option1, int option2, int option3) {
                Log.e("test", option1 + "," + option2 + "," + option3);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private String province, city, area;
    private void showWindow() {
        Button button = new Button(this);
        button.setText("点击弹窗");
        setContentView(button);

        //初始化
        final CharacterPickerWindow window = OptionsWindowHelper.builder(PickerActivity01.this, new OptionsWindowHelper.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(String province, String city, String area) {
                PickerActivity01.this.province = province;
                PickerActivity01.this.city = city;
                PickerActivity01.this.area = area;
                Log.e("main", province + "," + city + "," + area);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出
                window.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                Log.e("main", province + "," + city + "," + area);
                // 设置默认选中的三级项目
                OptionsWindowHelper.setCurrentPositions(window, province, city, area);
            }
        });

    }


    public static int dp2Px(float dp) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
