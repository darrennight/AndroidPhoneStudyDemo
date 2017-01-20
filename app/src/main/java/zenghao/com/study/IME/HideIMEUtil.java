package zenghao.com.study.IME;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class HideIMEUtil {
 
    public static void wrap(Activity activity) {
        ViewGroup contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        wrap(contentParent);
    } 
 
    public static void wrap(Fragment fragment) {
        ViewGroup contentParent = (ViewGroup) fragment.getView().getParent();
        wrap(contentParent);
    } 


    /**布局最外层添加了一层*/
    public static void wrap(ViewGroup contentParent) {

        for(int i=0 ;i<contentParent.getChildCount();i++){
            View view = contentParent.getChildAt(i);
            Log.e("====child",view.getClass().getName().toString());
        }

        View content = contentParent.getChildAt(0);
        contentParent.removeView(content);
 
        ViewGroup.LayoutParams p = content.getLayoutParams();
        AutoHideIMEFrameLayout layout = new AutoHideIMEFrameLayout(content.getContext());
        layout.addView(content);
 
        contentParent.addView(layout, new ViewGroup.LayoutParams(p.width, p.height));


        for(int i=0 ;i<contentParent.getChildCount();i++){
            View view = contentParent.getChildAt(i);
            Log.e("====child",view.getClass().getName().toString());
        }
    } 
} 