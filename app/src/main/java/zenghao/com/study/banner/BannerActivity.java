package zenghao.com.study.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import zenghao.com.study.R;

/**
 * https://github.com/xiwenhec/Banner
 *
 *
 * https://github.com/saiwu-bigkoo/Android-ConvenientBanner
 * https://github.com/ToxicBakery/ViewPagerTransforms
 * https://github.com/JakeWharton/salvage
 * https://github.com/imbryk/LoopingViewPager
 * @author zenghao
 * @since 16/11/28 上午11:11
 */
public class BannerActivity extends AppCompatActivity {

    private static final String TAG = AppCompatActivity.class.getSimpleName();
    private Banner mBanner;
    private List<String> mDatas = new ArrayList<>();
    Button mRefreshButton;
    int num = 1;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        mContext = this;

        mBanner = (Banner) findViewById(R.id.id_banner);

        for (int i=0;i<1;i++){
            mDatas.add(i+"");
        }

        final BannerAdapter adapter = new BannerAdapter<String>(mDatas) {
            @Override
            protected void bindTips(TextView tv, String bannerModel) {
                tv.setText(bannerModel);
            }

            @Override
            public void bindImage(ImageView imageView, String bannerModel) {
                //Glide.with(mContext)
                //        .load(bannerModel.getImageUrl())
                //        .placeholder(R.mipmap.empty)
                //        .error(R.mipmap.error)
                //        .into(imageView);

                imageView.setImageResource(R.mipmap.ic_launcher);
            }

        };
        mBanner.setBannerAdapter(adapter);

        mRefreshButton = (Button) findViewById(R.id.button);


        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.clear();
                for (int i=0;i<5;i++){
                    mDatas.add(i+"");
                }
                mBanner.notifiDataHasChanged();
            }
        });

    }



}
