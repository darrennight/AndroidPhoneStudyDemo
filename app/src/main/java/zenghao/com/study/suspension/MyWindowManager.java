package zenghao.com.study.suspension;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import zenghao.com.study.R;

/***
 * https://github.com/pengjianbo/FloatViewFinal
 * https://github.com/YuanKJ-/ExToast
 * https://github.com/liaohuqiu/android-UCToast
 */
public class MyWindowManager {

    private MyWindowManager(){};
    private static MyWindowManager mInstance = new MyWindowManager();
    public static MyWindowManager  getInstance(){
        if(mInstance == null){
            mInstance = new MyWindowManager();
        }
        return mInstance;
    }

    private String mCurentTxt = "tool.breadtrip.com.breadtriptool.MainActivity";
    /**activity 名字*/
    private TextView mContent;

    private static LinearLayout mLayoutWindow;
  
    /**
     * 用于控制在屏幕上添加或移除悬浮窗 
     */  
    private static WindowManager mWindowManager;
  
    public  void createWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();  
        int screenHeight = windowManager.getDefaultDisplay().getHeight();  
        if (mLayoutWindow == null) {
            mLayoutWindow = ((LinearLayout) View.inflate(context, R.layout.window_activity_name, null));
            mContent = ((TextView) mLayoutWindow.findViewById(R.id.tv_ac_name));
            mContent.setText(mCurentTxt);
            WindowManager.LayoutParams param=new WindowManager.LayoutParams();

            /*
            7.1.1之前可以使用TYPE_TOAST 之后需要使用TYPE_SYSTEM_ALERT并且申请权限

            //target23 开始需要权限申请 使用TYPE_TOAST就不需要 但是
            //7.1.1系统做了修改TYPE_TOAST 只能显示Toast时长就消失
            //param.type=WindowManager.LayoutParams.TYPE_TOAST;
            param.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;*/

            param.type=WindowManager.LayoutParams.TYPE_TOAST;
            param.format=1;
            param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
            param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            param.flags = param.flags | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            param.flags = param.flags | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制

            //param.alpha = 1.0f;

            param.gravity=Gravity.LEFT|Gravity.TOP;   //调整悬浮窗口至左上角
            //以屏幕左上角为原点，设置x、y初始值
            param.x=0;
            param.y=0;

            //设置悬浮窗口长宽数据
            param.width=screenWidth;
            param.height=200;

            windowManager.addView(mLayoutWindow, param);
        }
    }

    public void removeWindow(Context context) {
        if (mLayoutWindow != null) {
            WindowManager windowManager = getWindowManager(context);  
            windowManager.removeView(mLayoutWindow);
            mLayoutWindow = null;
        }  
    }  
  

    public void upContent(String s){
        mCurentTxt = s;
        mContent.setText(s);
    }

    public void upLocation(Context context ,int gravity){
        if (mLayoutWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            WindowManager.LayoutParams param=new WindowManager.LayoutParams();
            int screenWidth = windowManager.getDefaultDisplay().getWidth();
            param.type=WindowManager.LayoutParams.TYPE_TOAST;
            param.format=1;
            param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
            param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            param.flags = param.flags | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            param.flags = param.flags | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制

            //param.alpha = 1.0f;

            param.gravity=Gravity.LEFT|gravity;   //调整悬浮窗口至左上角
            //以屏幕左上角为原点，设置x、y初始值
            param.x=0;
            param.y=0;

            //设置悬浮窗口长宽数据
            param.width=screenWidth;
            param.height=200;

            windowManager.updateViewLayout(mLayoutWindow, param);
        }
    }

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }



  
}  