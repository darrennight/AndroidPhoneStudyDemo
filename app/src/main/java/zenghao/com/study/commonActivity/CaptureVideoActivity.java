package zenghao.com.study.commonActivity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import zenghao.com.study.R;

/**
 * 视频截图
 *
 * @author zenghao
 * @since 16/10/26 下午5:54
 */
public class CaptureVideoActivity extends AppCompatActivity {

    private Button mBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_video);
        mBtn = ((Button) findViewById(R.id.btn_capture));
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///storage/emulated/0/DCIM/Video/V61024-184707.mp4
                String path = "/storage/emulated/0/DCIM/Video/V61021-170126.mp4";
                createVideoThumbnail(path);
            }
        });
    }

    public static File getTrickVideoCover(){
         String ROOT_PATH = "/studyTest/capture/trackvideo/";
        File file = new File(Environment.getExternalStorageDirectory(), ROOT_PATH);
        if(!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


    /**
     * 足迹视频封面存储
     * @param bitmap
     */
    private static String saveVideoCover(Bitmap bitmap){

        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)){

            File dirFile  = getTrickVideoCover();  //目录转化成文件夹
            if (!dirFile .exists()) {				//如果不存在，那就建立这个文件夹
                dirFile .mkdirs();
            }							//文件夹有啦，就可以保存图片啦
            File file = new File(dirFile, System.currentTimeMillis()+".jpg");// 在SDcard的目录下创建图片文,以当前时间为其命名
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return file.getPath();
        }
        return null;
    }

    /***
     * 获取视频第一帧 图片
     * @param filePath
     * @return
     */
    public static String createVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            // 取得视频的长度(单位为毫秒)
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);


            for(int i=1;i<=3;i++){
                bitmap = retriever.getFrameAtTime(i*1000 * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
                saveVideoCover(bitmap);
            }





            //bitmap = retriever.getFrameAtTime();//第一帧
        } catch(IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        return saveVideoCover(bitmap);
    }



    private void test(String filePath){
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            // 取得视频的长度(单位为毫秒)
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);




            for(int i=1;i<=3;i++){
                bitmap = retriever.getFrameAtTime(i*1000 * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
                saveVideoCover(bitmap);
            }





            //bitmap = retriever.getFrameAtTime();//第一帧
        } catch(IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
    }


    /**
     * 刷新指定item
     *
     * @param index item在listview中的位置
     */
   /* public void updateItem(int index){

        // 获取当前可以看到的item位置
        int visiblePosition = glCover.getFirstVisiblePosition();
        View view = glCover.getChildAt(index - visiblePosition);
        RelativeLayout rlCheck = (RelativeLayout) view.findViewById(R.id.rlCheck);

        boolean isSeleted = selection.get(index, false);
        if (isSeleted) {
            rlCheck.setVisibility(View.VISIBLE);
        } else {
            rlCheck.setVisibility(View.GONE);
        }

    }*/

    /***
     * 局部更新封面
     * @param index
     */
    /*public void updateItemCover(int index){

        // 获取当前可以看到的item位置
        int visiblePosition = glCover.getFirstVisiblePosition();
        int last = glCover.getLastVisiblePosition();

        if(index>=visiblePosition && index<=last){
            View view = glCover.getChildAt(index - visiblePosition);
            SimpleDraweeView VideoCover = ((SimpleDraweeView) view.findViewById(R.id.sdv_track_img));

            Image image = (Image) getItem(index);

            FrescoManager.loadFilePath(image.getVideoThum()==null?"":image.getVideoThum()).resize(Utility.dp2px(getApplicationContext(),80),Utility.dp2px(getApplicationContext(),80))
                    .placeholderImage(R.drawable.photo_placeholder)
                    .failureImage(R.drawable.photo_placeholder)
                    .autoPlayAnimations(false)
                    .into(VideoCover);
        }
    }*/
}
