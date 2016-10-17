package zenghao.com.study.plugin.ReSkin.loader;

import android.graphics.Typeface;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.plugin.ReSkin.utils.TypefaceUtils;

/***
 * 更改字体 使用
 */
public class TextViewRepository {
    private static List<TextView> mTextViews = new ArrayList<>();
 
    public static void add(TextView textView) {
        mTextViews.add(textView);
        textView.setTypeface(TypefaceUtils.CURRENT_TYPEFACE);
    } 
 
    public static void clear() { 
        mTextViews.clear();
    } 
 
    public static void applyFont(Typeface tf) {
        for (TextView textView : mTextViews) {
            textView.setTypeface(tf);
        } 
    } 
} 