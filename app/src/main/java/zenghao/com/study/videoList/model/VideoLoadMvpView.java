package zenghao.com.study.videoList.model;

import android.media.MediaPlayer;
import zenghao.com.study.videoList.widget.TextureVideoView;

public interface VideoLoadMvpView {
 
    TextureVideoView getVideoView();
 
    void videoBeginning(); 
 
    void videoStopped(); 
 
    void videoPrepared(MediaPlayer player);
 
    void videoResourceReady(String videoPath);
} 