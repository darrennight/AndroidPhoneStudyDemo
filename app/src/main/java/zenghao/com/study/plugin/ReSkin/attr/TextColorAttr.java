package zenghao.com.study.plugin.ReSkin.attr;

import android.view.View;
import android.widget.TextView;
import zenghao.com.study.plugin.ReSkin.attr.base.SkinAttr;
import zenghao.com.study.plugin.ReSkin.loader.SkinManager;

/**
 *
 */
public class TextColorAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                tv.setTextColor(SkinManager.getInstance().getColorStateList(attrValueRefId));
            }
        }
    }
}
