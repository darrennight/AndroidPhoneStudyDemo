package zenghao.com.study.plugin.ReSkin.base;

import android.app.Application;

import java.io.File;
import java.io.IOException;
import zenghao.com.study.plugin.ReSkin.config.SkinConfig;
import zenghao.com.study.plugin.ReSkin.loader.SkinManager;
import zenghao.com.study.plugin.ReSkin.utils.SkinFileUtils;

/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:10:54
 */
public class SkinBaseApplication extends Application {

    public void onCreate() {

        super.onCreate();
        initSkinLoader();
    }

    /**
     * Must call init first
     */
    private void initSkinLoader() {
        //setUpSkinFile();
        SkinManager.getInstance().init(this);
        //SkinManager.getInstance().loadSkin();
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
