package zenghao.com.study.commonActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import zenghao.com.study.R;
import zenghao.com.study.view.FlowLayout;
import zenghao.com.study.view.OkFlowLayout;


/**
 *
 * @author zenghao
 * @since 17/4/5 下午6:11
 */
public class TestFlowLayoutActivity extends AppCompatActivity implements View.OnClickListener{

    String[] texts = new String[]{
            "good", "bad", "understand", "it is a good day !",
            "how are you", "ok", "fine", "name", "momo",
            "lankton", "lan", "flowlayout demo", "soso"
    };

    int length;

    private OkFlowLayout okFlowLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        fillAutoSpacingLayout();

        length = texts.length;
        okFlowLayout = (OkFlowLayout) findViewById(R.id.flowlayout);
        findViewById(R.id.btn_add_random).setOnClickListener(this);
        findViewById(R.id.btn_relayout1).setOnClickListener(this);
        findViewById(R.id.btn_remove_all).setOnClickListener(this);
        findViewById(R.id.btn_relayout2).setOnClickListener(this);
        findViewById(R.id.btn_specify_line).setOnClickListener(this);
    }

    private void fillAutoSpacingLayout() {
        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.flow);
        String[] dummyTexts = getResources().getStringArray(R.array.lorem_ipsum);

        for (String text : dummyTexts) {
            TextView textView = buildLabel(text);
            flowLayout.addView(textView);
        }
    }

    private TextView buildLabel(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setPadding((int)dpToPx(16), (int)dpToPx(8), (int)dpToPx(16), (int)dpToPx(8));
        textView.setBackgroundResource(R.drawable.label_bg);

        return textView;
    }

    private float dpToPx(float dp){
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_random:
                int ranHeight = dip2px(this, 30);
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
                lp.setMargins(dip2px(this, 10), 0, dip2px(this, 10), 0);
                TextView tv = new TextView(this);
                tv.setPadding(dip2px(this, 15), 0, dip2px(this, 15), 0);
                tv.setTextColor(Color.parseColor("#FF3030"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                int index = (int)(Math.random() * length);
                tv.setText(texts[index]);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setLines(1);
                tv.setBackgroundResource(R.drawable.bg_tag);
                okFlowLayout.addView(tv, lp);
                break;
            case R.id.btn_remove_all:
                okFlowLayout.removeAllViews();
                break;
            case R.id.btn_relayout1:
                okFlowLayout.relayoutToCompress();
                break;
            case R.id.btn_relayout2:
                okFlowLayout.relayoutToAlign();
                break;
            case R.id.btn_specify_line:
                okFlowLayout.specifyLines(3);
                break;
            default:
                break;
        }
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
