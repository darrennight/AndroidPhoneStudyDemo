package zenghao.com.study.commonActivity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class FullScreenActivity extends AppCompatActivity {
    @Override 
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(); 
    } 
 
    @Override 
    protected void onResume() { 
        super.onResume(); 
        hideSystemUI(getWindow().getDecorView()); 
 
    } 
 
    @Override 
    protected void onPause() { 
        showSystemUI(getWindow().getDecorView()); 
        super.onPause(); 
    } 
 
    private void setTheme() { 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    } 
 
    private static void hideSystemUI(View view) {
        // Set the IMMERSIVE flag. 
        // Set the content to appear under the system bars so that the content 
        // doesn't resize when the system bars hide and show. 
        int systemUiFlag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            systemUiFlag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION// hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;// hide status bar
 
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                systemUiFlag |= View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            } 
        } 
        view.setSystemUiVisibility(systemUiFlag);
    } 
 
    //取消全屏模式 
    private void showSystemUI(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    } 
} 