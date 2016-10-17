package zenghao.com.study.plugin.ReSkin.listener;

import android.view.View;
import android.widget.TextView;
import java.util.List;
import zenghao.com.study.plugin.ReSkin.attr.base.DynamicAttr;

public interface IDynamicNewView {
    void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
 
    void dynamicAddView(View view, String attrName, int attrValueResId);
 
    /** 
     * add the textview for font switch 
     * 
     * @param textView textview 
     */ 
    void dynamicAddFontView(TextView textView);
} 