package zenghao.com.study.plugin.ReSkin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
    private ListView mListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_skin);
        mListView = ((ListView) findViewById(R.id.lv_test_reskin));
        mChange = ((TextView) findViewById(R.id.tv_change_skin));
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpSkinFile();
                SkinManager.getInstance().init(ReSkinActivity.this);
                SkinManager.getInstance().loadSkin();
            }
        });

        mListView.setAdapter(new MyAdapter(this));
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

    class MyAdapter extends BaseAdapter{

        private Context mContext;
        public MyAdapter(Context context){
            this.mContext = context;
        }
        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_reskin,parent,false);
                holder = new ViewHolder();
                holder.textView = ((TextView) convertView.findViewById(R.id.tv_item_reskin));
                convertView.setTag(holder);
            }else{

                holder = ((ViewHolder) convertView.getTag());
            }

            holder.textView.setText(position+"");

            return convertView;
        }


       class ViewHolder {
           TextView textView;
       }
    }
}
