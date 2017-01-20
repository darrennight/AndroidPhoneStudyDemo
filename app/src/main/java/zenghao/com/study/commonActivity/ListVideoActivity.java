package zenghao.com.study.commonActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.R;

/**
 *列表视频滑动播放
 * @author zenghao
 * @since 17/1/5 下午3:53
 */
public class ListVideoActivity extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);
        mListView = ((ListView) findViewById(R.id.lv_video));

        List<String> list = new ArrayList<>();
        list.add("/storage/emulated/0/DCIM/Video/V61027-114752.mp4");
        list.add("/storage/emulated/0/DCIM/Video/V61027-114803.mp4");
        list.add("/storage/emulated/0/DCIM/Video/V61027-171052.mp4");
        list.add("/storage/emulated/0/DCIM/Video/V61027-171101.mp4");
        list.add("/storage/emulated/0/DCIM/Video/V61027-171110.mp4");
        MyAdapter adapter = new MyAdapter(this,list);
        mListView.setAdapter(adapter);
    }




    private class MyAdapter extends BaseAdapter{

        private List<String> mList;
        private Context mContext;
        public MyAdapter(Context context,List<String> list){
            this.mList = list;
            this.mContext = context;

        }


        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder ;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_video,parent,false);
                holder.mVideoView = ((TextureView) convertView.findViewById(R.id.ttv_video));
                convertView.setTag(holder);
            }else{
                holder = ((ViewHolder) convertView.getTag());
            }

            try{
                final MediaPlayer  mMediaPlayer= new MediaPlayer();
                mMediaPlayer.setDataSource(mList.get(position));
               // mMediaPlayer.setSurface(surfaceView);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp){
                        mMediaPlayer.start();
                    }
                });
                mMediaPlayer.prepare();
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }

        class ViewHolder{
            TextureView mVideoView;
        }
    }
}
