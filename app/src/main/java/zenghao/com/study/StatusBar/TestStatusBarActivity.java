package zenghao.com.study.StatusBar;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/7/26.
 */
public class TestStatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        测试不用toolbar自定义title效果
        
        setContentView(R.layout.activity_test_status_bar);
//        Toolbar bar = ((Toolbar) findViewById(R.id.test_toolbar));
//        setSupportActionBar(bar);


//        testfitsSystemWindows();
        setStatusBG();

    }

    private void testfitsSystemWindows(){
        //状态栏透明 下面是一个图片显示风格
//        避免在每个布局文件中处理android:fitsSystemWindows="true"
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
    }

    /**
     * 设置状态栏背景颜色
     */
    private void setStatusBG(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            ViewGroup firstChildAtDecorView = ((ViewGroup) ((ViewGroup)getWindow().getDecorView()).getChildAt(0));
            View statusView = new View(this);
            ViewGroup.LayoutParams statusViewLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight());
            //颜色的设置可抽取出来让子类实现之
            statusView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            firstChildAtDecorView.addView(statusView, 0, statusViewLp);
        }
    }
    private int getStatusBarHeight() {
        int resId = getResources().getIdentifier("status_bar_height","dimen","android");
        if(resId>0){
            return getResources().getDimensionPixelSize(resId);
        }
        return 0;
    }
}
