package zenghao.com.study.plugin.ReSkin.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import zenghao.com.study.plugin.ReSkin.attr.base.DynamicAttr;
import zenghao.com.study.plugin.ReSkin.config.SkinConfig;
import zenghao.com.study.plugin.ReSkin.listener.IDynamicNewView;
import zenghao.com.study.plugin.ReSkin.listener.ISkinUpdate;
import zenghao.com.study.plugin.ReSkin.loader.SkinInflaterFactory;
import zenghao.com.study.plugin.ReSkin.loader.SkinManager;
import zenghao.com.study.plugin.ReSkin.statusbar.StatusBarUtil;
import zenghao.com.study.plugin.ReSkin.utils.SkinL;

/**
 *
 * 需要实现换肤功能的Activity就需要继承于这个Activity
 */
public class SkinBaseActivity extends AppCompatActivity implements ISkinUpdate, IDynamicNewView {

    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory();
        mSkinInflaterFactory.setAppCompatActivity(this);
        LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory);
        super.onCreate(savedInstanceState);
        changeStatusColor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
        mSkinInflaterFactory.clean();
    }

    @Override
    public void onThemeUpdate() {
        Log.i("SkinBaseActivity", "onThemeUpdate");
        mSkinInflaterFactory.applySkin();
        changeStatusColor();
    }

    public void changeStatusColor() {
        if (!SkinConfig.isCanChangeStatusColor()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SkinL.i("SkinBaseActivity", "changeStatus");
            int color = SkinManager.getInstance().getColorPrimaryDark();
            StatusBarUtil statusBarBackground = new StatusBarUtil(
                    this, color);
            if (color != -1)
                statusBarBackground.setStatusBarbackColor();
        }
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    @Override
    public void dynamicAddView(View view, String attrName, int attrValueResId) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    @Override
    public void dynamicAddFontView(TextView textView) {
        mSkinInflaterFactory.dynamicAddFontEnableView(textView);
    }

}
