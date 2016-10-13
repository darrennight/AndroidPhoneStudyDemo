package zenghao.com.study.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import zenghao.com.study.MainActivity;
import zenghao.com.study.R;

/**
 * 启动页播放视频
 *
 * @author Lee
 *
 * SurfaceView因为不同于一般的view，它有自己良好的缓冲以及数据存取机制，系统对他有特殊处理。
 * 当surfaceview第一次在当前activity上添加的时候，系统会给WindowManager重新排布局，relayout，
 * 这样就会黑一下，这个只会出现在第一次，以后再添加surfaceview时就不会黑屏了。

    解决：

    可以在没有进入surfaceview的界面（比如很多程序一进去就有个Loading界面）的时候，
    在其它界面的layout.xml文件中添加一个SurfaceView，宽和高都弄成0dp，这样对布局没影响，
    而且这个东西使得surfaceview第一次出现了，那么也就是说，下一次我们真正用到SurfaceView的时候就不会闪屏了。

    2.viewpager中有surfaceview播放视频，来回滑屏时，关于黑屏移动问题

    解决：

    surfaceview.setZorderTop(true);

    surfaceview.getHolder().setFormat(SurfaceView.TRANSPARENT);
 */
public class LogoFristActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    MediaPlayer player;
    SurfaceView surface;
    SurfaceHolder surfaceHolder;
    int stopBtnFlag = 0;
    private ImageView id_start_home_page_button;
    private boolean isClick;

    private  int mPos;

    public LogoFristActivity() {}

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_frist);
        if (getIntent() != null) {
            stopBtnFlag = getIntent().getIntExtra("first_guid", 0);
        }


        surface = (SurfaceView) this.findViewById(R.id.id_video);

        //第一次加载surfaceView时会黑屏一下 下面两行代码在activity中是可以解决的 但是fragment不行
        //解决方法 在splash 布局中添加一个0dp的surfaceView即可
        //surface.setZOrderOnTop(true);//会让surface布局到最前面挡住其他控件
        surface.getHolder().setFormat(PixelFormat.TRANSLUCENT);


        id_start_home_page_button = (ImageView) findViewById(R.id.id_start_home_page_button);
        final ImageView stopPageBtn = (ImageView) findViewById(R.id.id_stop_play);

//        stopPageBtn.setOnClickListener(v -> {
//            player.stop();
//            finish();
//        });


        surfaceHolder = surface.getHolder();//SurfaceHolder是SurfaceView的控制接口
        surfaceHolder.addCallback(this);//因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//Surface类型
        surface.setKeepScreenOn(true);

        id_start_home_page_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                Intent intent = new Intent(LogoFristActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        id_start_home_page_button.setOnClickListener(v -> {
//            player.stop();
//            isClick = true;
//            startActivity(new Intent(LogoFristActivity.this, LoginHomeActivity.class));
//            finish();
//        });


        if (stopBtnFlag == 1) {
            id_start_home_page_button.setVisibility(View.GONE);
            stopPageBtn.setVisibility(View.VISIBLE);
        } else {
            id_start_home_page_button.setVisibility(View.VISIBLE);
            stopPageBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        //必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
        try {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDisplay(surfaceHolder);
            //设置显示视频显示在SurfaceView上

            AssetFileDescriptor fileDescriptor = getAssets().openFd("my_ui.mp4");

            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
//            player.setDataSource("http://123.56.113.202:18085/upload/mp4/e7ecdc91caed45ed80d0ef0da16244aa.mp4");
            player.prepare();
            player.seekTo(mPos);
            player.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);//循环播放
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                /*player.stop();
                Intent intent = new Intent(LogoFristActivity.this, MainActivity.class);
                startActivity(intent);*/


//                id_start_home_page_button.setImageResource(R.drawable.login_into);
//                SharedPreferencesHelper.getEditor().putBoolean(SharedPreferencesConstants.ISVIDEOPLAY, true).commit();
            }
        });
       /* player.setOnCompletionListener(mp -> {
            //播放结束后的动作
            player.stop();
            id_start_home_page_button.setImageResource(R.drawable.login_into);
            SharedPreferencesHelper.getEditor().putBoolean(SharedPreferencesConstants.ISVIDEOPLAY, true).commit();
        });*/
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {

        if(player!=null && player.isPlaying()){
            mPos = player.getCurrentPosition();
            Log.e("===","pos"+mPos);
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player!=null && player.isPlaying()) {
            player.stop();
            player.release();
        }

        if (!isClick) {
            //startActivity(new Intent(LogoFristActivity.this, LoginHomeActivity.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //finish();
    }

}
