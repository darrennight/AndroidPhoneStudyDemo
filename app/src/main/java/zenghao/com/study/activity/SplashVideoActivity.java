package zenghao.com.study.activity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
import zenghao.com.study.R;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/10/9 下午7:05
 */
public class SplashVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView videoview;
    private Button btn_start;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_video);
        initView();
    }
    /**
     * 初始化
     */
    private void initView() {

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        videoview = (VideoView) findViewById(R.id.videoview);

         String uri = "android.resource://" + getPackageName() + "/" + R.raw.my_ui;
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse(uri));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                Toast.makeText(this,"进入了主页", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
