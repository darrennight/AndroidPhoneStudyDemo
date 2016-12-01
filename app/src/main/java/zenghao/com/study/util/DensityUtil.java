package zenghao.com.study.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;
 
public class DensityUtil { 
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */ 
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */ 
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    } 
 
    /** 
     * 获取手机宽度 
     */ 
    public static int getDisplayWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    } 
    /** 
     * 获取手机高度 
     */ 
    public static int getDisplayHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    } 
 
    /** 
     * 判断view上的点击是不是快速多次点击 
     */ 
    private static long lastClickTime=0;
    public static boolean isFastDoubleClick() { 
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 2000) {
            return true; 
        } 
        lastClickTime = time;
        return false; 
    } 
 
    /** 
     * 修改整个界面所有控件的字体大小 
     * @param root 
     * @param size 
     */ 
    public static void changeTextSize(View root, int size) {
        if (root instanceof TextView) {
            ((TextView) root).setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
        } 
    } 
 
    /** 
     * 修改整个界面所有控件的字体,如果获取的viewgroup的childcount只有一个，使用递归，注意强转问题 
     * @param root 
     * @param path 
     * @param act 
     */ 
    public static void changeFonts(ViewGroup root, String path, Activity act) {
        // path是字体路径 
        Typeface tf = Typeface.createFromAsset(act.getAssets(), path);
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } 
        } 
    } 
 
    // 不改变控件位置，修改控件大小 
    public static void changeWH(View v,int W,int H)
    { 
        LayoutParams params = (LayoutParams)v.getLayoutParams();
        params.width = W;
        params.height = H;
        v.setLayoutParams(params);
    } 
 
    // 修改控件的高 
    public static void changeH(View v,int H)
    { 
        LayoutParams params = (LayoutParams)v.getLayoutParams();
        params.height = H;
        v.setLayoutParams(params);
    } 
 
    /** 
     * 设置当前界面为全屏模式 
     */ 
    public static void setFullScreen(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    } 
 
    /** 
     * 如果当前为全屏，那么取消全屏模式，回到正常的模式 
     */ 
    public static void cancelFullScreen(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    } 
 
    /** 
     * 判断当前手机是否是全屏 
     * 
     * @return 如果是true，那么当前就是全屏 
     */ 
    public static boolean isFullScreen(Activity activity) {
        int flag = activity.getWindow().getAttributes().flags;
        if ((flag & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            return true; 
        } else { 
            return false; 
        } 
    } 
 
    /** 
     * 判断当前屏幕是否是横屏 
     * 
     * @param activity 当前的activity 
     * @return 如果true就是竖屏 
     */ 
    public static boolean isVerticalScreen(Activity activity) {
        int flag = activity.getResources().getConfiguration().orientation;
        if (flag == 0) {
            return false; 
        } else { 
            return true; 
        } 
    } 
 
    /** 
     * 获取顶部状态栏高度 
     * 
     * @return 顶部状态栏高度 
     */ 
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try { 
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return statusBarHeight;
    } 
} 