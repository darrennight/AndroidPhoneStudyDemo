package zenghao.com.study.plugin.ReSkin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import zenghao.com.study.R;
import zenghao.com.study.plugin.ReSkin.base.SkinBaseActivity;
import zenghao.com.study.plugin.ReSkin.config.SkinConfig;
import zenghao.com.study.plugin.ReSkin.loader.SkinManager;
import zenghao.com.study.plugin.ReSkin.utils.SkinFileUtils;

/**
 *
 * @author zenghao
 * @since 16/10/13 下午4:27
 *
 * https://github.com/hongyangAndroid/ChangeSkin
 * http://moduhackers.github.io/2016/01/22/android-reskin/
 * https://github.com/daixiao1986/Reskin
 * http://www.jianshu.com/p/af7c0585dd5b
 * https://github.com/burgessjp/ThemeSkinning
 * https://github.com/burgessjp/MaterialDesignDemo
 * https://github.com/burgessjp/ThemeSkinning
 * http://blog.zhaiyifan.cn/2015/09/10/Android%E6%8D%A2%E8%82%A4%E6%8A%80%E6%9C%AF%E6%80%BB%E7%BB%93/
 * https://github.com/fengjundev/Android-Skin-Loader
 */
public class ReSkinActivity extends SkinBaseActivity {

    private TextView mChange;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_skin);
        mChange = ((TextView) findViewById(R.id.tv_change_skin));
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpSkinFile();
                SkinManager.getInstance().init(ReSkinActivity.this);
                SkinManager.getInstance().loadSkin();
            }
        });
    }



    private void setUpSkinFile() {
        try {
            String[] skinFiles = getAssets().list(SkinConfig.SKIN_DIR_NAME);
            for (String fileName : skinFiles) {
                File file = new File(SkinFileUtils.getSkinDir(this), fileName);
                if (!file.exists())
                    SkinFileUtils.copySkinAssetsToDir(this, fileName, SkinFileUtils.getSkinDir(this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
