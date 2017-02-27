package zenghao.com.study.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ScreenUtil {
 
    private int getScreenHight(Activity activity) {
        // 获取屏幕高 
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point.y + (isMeizu() ? 0 : getNavigationBarHeight(activity));
    } 
 
    /** 
     * 获取虚拟按键栏高度 
     */ 
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            } 
        } 
        return result;
    } 
 
    /** 
     * 获取状态栏高度 
     */ 
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        } 
        return result;
    } 
 
    /** 
     * 检查是否存在虚拟按键栏 
     */ 
    private static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag 
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            } 
            return hasNav;
        } else { // fallback 
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        } 
    } 
 
    /** 
     * 判断虚拟按键栏是否重写 
     */ 
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try { 
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
                e.printStackTrace();
            } 
        } 
        return sNavBarOverride;
    } 
 
    /** 
     * 判断是否meizu手机 
     */ 
    public static boolean isMeizu() { 
        return Build.BRAND.equals("Meizu");
    } 
 
    /** 
     * 获取魅族手机底部虚拟键盘高度 
     */ 
    public static int getSmartBarHeight(Context context) {
        try { 
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("mz_action_button_min_height");
            int height = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return 0; 
    } 
} 